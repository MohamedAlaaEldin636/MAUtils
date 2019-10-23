/*
 * Copyright (c) 2019 Mohamed Alaa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package mohamedalaa.mautils.core_android_processor

import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.core_android_annotation.MASharedPrefField
import mohamedalaa.mautils.core_android_annotation.MASharedPrefField_Configs
import mohamedalaa.mautils.core_android_processor.extensions.*
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

internal const val PRIVATE_FILE_NAME_PROPERTY_NAME = "privateFileName"
internal const val SETTER_PARAM_NAME_VALUE = "value"
internal const val GETTER_PARAM_NAME_DEF_VALUE = "defValue"
internal const val SETTER_PARAM_NAME_COMMIT = "commit"

fun RoundEnvironment.process(processingEnv: ProcessingEnvironment) = processingEnv.performIfNotNull {
    // All MASharedPrefField annotated classes from container any from non-repeated annotations isa.
    val maSharedPrefFieldAllElements = (getElementsAnnotatedWith(MASharedPrefField.Container::class.java) +
        getElementsAnnotatedWith(MASharedPrefField::class.java)).mapNotNull { it as? TypeElement }.distinct()

    // Some Constants of kotlinpoet classes names isa.
    val contextClassName = ClassName.bestGuess("android.content.Context")

    val allToBeGeneratedFilesIsa = mutableListOf<FileSpec>()
    for (element in maSharedPrefFieldAllElements.distinct()) {
        // -- Construct kotlin file isa. -- //

        // Might be empty list in that case use default values isa.
        val annotationInstanceConfigsOfMASharedPrefField = element.getAnnotationsByType(MASharedPrefField_Configs::class.java)
            .getOrNull(0) ?: MASharedPrefField_ConfigsUtils.getDefaultInstanceOfAnnotation()!!
        val annotationInstanceOfMASharedPrefFieldList = element.getAnnotationsByType(MASharedPrefField::class.java).filterNotNull()

        var atLeastOneFunNeedsJavaSupport = false
        loop@ for (item in annotationInstanceOfMASharedPrefFieldList.map { it.supportJavaConsumerCode }) {
            when (item) {
                MASharedPrefField.JavaConsumerCode.SUPPORT -> {
                    atLeastOneFunNeedsJavaSupport = true
                    break@loop
                }
                MASharedPrefField.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS -> {
                    if (annotationInstanceConfigsOfMASharedPrefField.supportJavaConsumerCode) {
                        atLeastOneFunNeedsJavaSupport = true
                        break@loop
                    }
                }
                else -> {}
            }
        }

        val annotatedClassName = element.simpleName.toStringOrNull() ?: continue
        val fullImportStringsList = mutableListOf<String>()
        val kFileBuilder = FileSpec.builder(
            Constants.generatedFilePackage,
            Constants.getKotlinFileName(annotatedClassName)
        ).apply {
            // @file:JvmName("SharedPrefClassName") only if java is needed to be supported isa.
            if (atLeastOneFunNeedsJavaSupport) {
                addAnnotation(
                    AnnotationSpec.builder(JvmName::class)
                        .addMember(
                            "\"${Constants.getJavaStaticClassName(annotatedClassName)}\""
                        )
                        .build()
                )
            }
            // @file:Suppress("unused")
            addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("\"unused\"")
                    .build()
            )
            // Import annotated class so that no lint check will warn about it's not used isa.
            fullImportStringsList += listOf(
                element.qualifiedName.toString(),
                "mohamedalaa.mautils.core_android.extensions.sharedPrefSetComplex",
                "mohamedalaa.mautils.core_android.extensions.sharedPrefGetComplex",
                "android.content.Context",
                "mohamedalaa.mautils.core_android.custom_classes.SharedPrefSupportedTypesParams"
            )
            fullImportStringsList += annotationInstanceConfigsOfMASharedPrefField.imports.toList()
            // Add privateFileName val to be used as file name in getters and setter functions isa,
            // And for that element(class) to be references so lint should make no warning towards it isa.
            addProperty(
                PropertySpec.builder(PRIVATE_FILE_NAME_PROPERTY_NAME, String::class, KModifier.PRIVATE)
                    .mutable(false)
                    .initializer("$annotatedClassName::class.java.name")
                    .build()
            )
        }

        // -- Configs -- //

        if (annotationInstanceConfigsOfMASharedPrefField.addClearFun) {
            kFileBuilder.addFunction(
                FunSpec.builder("sharedPref_${annotatedClassName}_clearAll").apply {
                    /*
                    @JvmName("clearAll")
                    @JvmOverloads
                    @Synchronized
                    fun Context.sharedPref_SomeClassName_clearAll(commit: Boolean = false): Boolean? =
                            sharedPrefClearAll("SomeClassName", Context.MODE_PRIVATE, commit)
                     */

                    // Imports
                    fullImportStringsList += "mohamedalaa.mautils.core_android.extensions.sharedPrefClearAll"

                    // Annotations
                    if (atLeastOneFunNeedsJavaSupport) {
                        addAnnotation(
                            AnnotationSpec.builder(JvmName::class)
                                .addMember(
                                    "\"clearAll\""
                                )
                                .build()
                        )
                        addAnnotation(JvmOverloads::class)
                    }
                    addAnnotation(Synchronized::class)

                    // receiver && return values isa.
                    receiver(contextClassName)
                    returns(Boolean::class.asTypeName().copy(nullable = true))

                    // params isa.
                    addParameter(
                        ParameterSpec.builder(SETTER_PARAM_NAME_COMMIT, Boolean::class.asTypeName())
                            .defaultValue("false")
                            .build()
                    )

                    // function code isa.
                    addStatement(
                        "return sharedPrefClearAll(" +
                            "${PRIVATE_FILE_NAME_PROPERTY_NAME}, " +
                            "Context.MODE_PRIVATE, " +
                            SETTER_PARAM_NAME_COMMIT +
                            ")"
                    )
                }.build()
            )
        }
        if (annotationInstanceConfigsOfMASharedPrefField.addFileNameFun) {
            /*
            object SharedPrefSomeClassName {
                @JvmStatic
                fun fileName(): String = privateFileName
            }
             */
            kFileBuilder.addType(
                TypeSpec.objectBuilder(Constants.getJavaStaticClassName(annotatedClassName) + "NoContext").apply {
                    addFunction(
                        FunSpec.builder("fileName").apply {
                            if (atLeastOneFunNeedsJavaSupport) {
                                addAnnotation(JvmStatic::class)
                            }

                            returns(String::class)

                            addStatement("return $PRIVATE_FILE_NAME_PROPERTY_NAME")
                        }.build()
                    )
                }.build()
            )
        }

        // -- Add functions for each annotation isa. -- //

        // get all field annotations isa. (to build all functions isa.)
        val functions = mutableListOf<FunSpec>()
        val allFieldsNamesInsideSharedPrefFile = mutableListOf<String>()
        for (item in annotationInstanceOfMASharedPrefFieldList) {
            // SetComplex fun isa.
            functions += processingEnv.buildSetComplexFun(
                annotatedClassName,
                item,
                contextClassName,
                annotationInstanceConfigsOfMASharedPrefField.supportJavaConsumerCode
            )

            // GetComplex fun isa.
            functions += processingEnv.buildGetComplexFun(
                annotatedClassName,
                item,
                contextClassName,
                annotationInstanceConfigsOfMASharedPrefField.supportJavaConsumerCode
            )

            allFieldsNamesInsideSharedPrefFile += item.name
        }
        if (allFieldsNamesInsideSharedPrefFile.size != allFieldsNamesInsideSharedPrefFile.distinct().size) {
            processingEnv.error(
                "Cannot have 2 or more same names in ${MASharedPrefField::class.simpleName} for the same class isa" +
                ", all names $allFieldsNamesInsideSharedPrefFile, ${allFieldsNamesInsideSharedPrefFile.distinct()}" +
                ", repeated names isa -> ${allFieldsNamesInsideSharedPrefFile.apply { remove("boolean1") }}"
            )
        }

        // Put all functions (getters/setters) in the file isa.
        for (function in functions) {
            kFileBuilder.addFunction(function)
        }

        // add file to list isa.
        kFileBuilder.addFullImportStringsList(fullImportStringsList)
        allToBeGeneratedFilesIsa += kFileBuilder.build()
    }

    // Generate all files isa.
    for (kFile in allToBeGeneratedFilesIsa) {
        try {
            kFile.writeTo(processingEnv.filer)
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
