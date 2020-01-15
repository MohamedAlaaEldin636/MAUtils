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

package mohamedalaa.mautils.shared_pref_processor

import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair
import mohamedalaa.mautils.shared_pref_processor.extensions.*
import mohamedalaa.mautils.shared_pref_processor.extensions.kotlinpoet.addFullImportStringsList
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

fun RoundEnvironment.processMASharedPrefPairAndFileConfigs(processingEnv: ProcessingEnvironment) = processingEnv.performIfNotNull {
    // All MASharedPrefPair annotated classes from container any from non-repeated annotations isa.
    val maSharedPrefAllElements = (
        (getElementsAnnotatedWith(MASharedPrefPair.Container::class.java) +
            getElementsAnnotatedWith(MASharedPrefPair::class.java)).mapNotNull { it as? TypeElement }
        ).distinct()

    val allToBeGeneratedFilesIsa = mutableListOf<FileSpec>()
    for (element in maSharedPrefAllElements.distinct()) {
        // -- Construct kotlin file isa. -- //

        // Fetch file configs or default file configs AND all key/value annotations isa.
        val annMASharedPrefFileConfigs = element.getAnnotationsByType(MASharedPrefFileConfigs::class.java)
            .getOrNull(0) ?: MASharedPrefField_ConfigsUtils.getDefaultInstanceOfAnnotation()!!
        val annMASharedPrefPairList = element.getAnnotationsByType(MASharedPrefPair::class.java)
            .filterNotNull()

        if (annMASharedPrefPairList.isEmpty()) {
            // Maybe can be in future done with lintPublish as an IDE error isa.
            warning(
                "Using ${MASharedPrefFileConfigs::class} without ${MASharedPrefPair::class} " +
                    "OR Using ${MASharedPrefPair.Container::class} Not logical so class is ignored."
            )

            continue
        }

        // Check for java support isa.
        var atLeastOneFunNeedsJavaSupport = false
        loop@ for (item in annMASharedPrefPairList.map { it.supportJavaConsumerCode }) {
            when (item) {
                MASharedPrefPair.JavaConsumerCode.SUPPORT -> {
                    atLeastOneFunNeedsJavaSupport = true
                    break@loop
                }
                MASharedPrefPair.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS -> {
                    if (annMASharedPrefFileConfigs.supportJavaConsumerCode) {
                        atLeastOneFunNeedsJavaSupport = true
                        break@loop
                    }
                }
                else -> {}
            }
        }

        // Initial setups -> fileName, annotations, imports, globalConstants
        val annotatedClassName = element.simpleName.toStringOrNull() ?: continue
        val fullImportStringsList = mutableListOf<String>()
        val kFileBuilder = FileSpec.builder(
            element.asClassName().packageName,
            Constants.getKotlinFileName(annotatedClassName)
        ).apply {
            // @file:JvmName("SharedPref$annotatedClassName") only if java is needed to be supported isa.
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
                "mohamedalaa.mautils.shared_pref_core.sharedPrefSet",
                "mohamedalaa.mautils.shared_pref_core.sharedPrefGet",
                "android.content.Context"
            )
            fullImportStringsList += annMASharedPrefFileConfigs.imports.toList()
            // Add privateFileName val to be used as file name in getters and setter functions isa,
            // And for that element(class) to be references so lint should make no warning towards it isa.
            addProperty(
                PropertySpec.builder(VAR_NAME_PRIVATE_FILE_NAME, String::class, KModifier.PRIVATE)
                    .mutable(false)
                    .initializer("$annotatedClassName::class.java.name")
                    .build()
            )
        }

        // File Configs
        setupMASharedPrefFileConfigs(
            annMASharedPrefFileConfigs,
            kFileBuilder,
            annotatedClassName,
            fullImportStringsList,
            contextClassName
        )

        // -- Add functions for each key/value annotation isa. -- //

        // get all field annotations isa. (to build all functions isa.)
        val functions = mutableListOf<FunSpec>()
        val allFieldsNamesInsideSharedPrefFile = mutableListOf<String>()
        for (item in annMASharedPrefPairList) {
            // Value type of this key isa.
            val valueTypeName = item.asTypeName(this)

            // Setter fun isa.
            functions += processingEnv.buildSetterFun(
                annotatedClassName,
                item,
                annMASharedPrefFileConfigs.supportJavaConsumerCode,
                valueTypeName
            )

            // Getter fun isa.
            val annotationMirror = element.annotationMirrors.firstOrNull {
                val areSameAnnotationType = it.annotationType.toStringOrEmpty() == typeUtils.getDeclaredType(
                    elementUtils.getTypeElement(MASharedPrefPair::class.qualifiedName)
                ).toStringOrEmpty()

                areSameAnnotationType && it.elementValues.any { (executableElement, _) ->
                    executableElement.simpleName.toStringOrEmpty() == MASharedPrefPair::defaultValue.name
                }
            }
            functions += processingEnv.buildGetterFun(
                annotatedClassName,
                item,
                annMASharedPrefFileConfigs.supportJavaConsumerCode,
                annotationMirror,
                valueTypeName
            )

            allFieldsNamesInsideSharedPrefFile += item.name
        }
        if (allFieldsNamesInsideSharedPrefFile.size != allFieldsNamesInsideSharedPrefFile.distinct().size) {
            processingEnv.error(
                "Cannot have 2 or more same names(keys) in ${mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair::class.simpleName} for the same file isa" +
                    ", Repeated keys isa -> ${allFieldsNamesInsideSharedPrefFile - allFieldsNamesInsideSharedPrefFile.distinct()}" +
                    ", All keys isa $allFieldsNamesInsideSharedPrefFile, "
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
