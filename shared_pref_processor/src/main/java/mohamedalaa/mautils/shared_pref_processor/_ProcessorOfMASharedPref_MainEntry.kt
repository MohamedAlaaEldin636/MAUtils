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
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefGsonConverter
import mohamedalaa.mautils.shared_pref_processor.extensions.*
import mohamedalaa.mautils.shared_pref_processor.extensions.kotlinpoet.addFullImportStringsList
import mohamedalaa.mautils.shared_pref_processor.extensions.kotlinpoet.analyzeDeeplyToListOfTypeNames
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

internal const val VAR_NAME_PRIVATE_FILE_NAME = "privateFileName"
internal const val VAR_NAME_VALUE = "value"
internal const val VAR_NAME_DEF_VALUE = "defValue"
internal const val VAR_NAME_COMMIT = "commit"
internal const val VAR_NAME_LISTENER = "listener"
internal const val VAR_NAME_CONVERTED_VALUE_ANY_TO_STRING = "convertedValueAnyToString"

internal val contextClassName = ClassName.bestGuess("android.content.Context")

fun RoundEnvironment.process(processingEnv: ProcessingEnvironment) = processingEnv.performIfNotNull {
    // All MASharedPrefKeyValuePair annotated classes from container any from non-repeated annotations isa.
    val maSharedPrefAllElements = (
        (getElementsAnnotatedWith(MASharedPrefKeyValuePair.Container::class.java) +
            getElementsAnnotatedWith(MASharedPrefKeyValuePair::class.java)).mapNotNull { it as? TypeElement }
        ).distinct()

    val allElementsAnnotatedWithGsonConverter = getElementsAnnotatedWith(MASharedPrefGsonConverter::class.java)
        .mapNotNull { it as? TypeElement }
    val listOfElementsAnnotatedByGsonConverterAndTheirTypeNamesListAnalyzed = allElementsAnnotatedWithGsonConverter.map {
        it to it.superclass!!.asTypeName().run {
            (this as ParameterizedTypeName).typeArguments.analyzeDeeplyToListOfTypeNames()
        }
    }

    val allToBeGeneratedFilesIsa = mutableListOf<FileSpec>()
    for (element in maSharedPrefAllElements.distinct()) {
        // -- Construct kotlin file isa. -- //

        // Fetch file configs or default file configs AND all key/value annotations isa.
        val annMASharedPrefFileConfigs = element.getAnnotationsByType(MASharedPrefFileConfigs::class.java)
            .getOrNull(0) ?: MASharedPrefField_ConfigsUtils.getDefaultInstanceOfAnnotation()!!
        val annMASharedPrefKeyValuePairList = element.getAnnotationsByType(MASharedPrefKeyValuePair::class.java)
            .filterNotNull()

        if (annMASharedPrefKeyValuePairList.isEmpty()) {
            warning(
                "Using ${MASharedPrefFileConfigs::class} without ${MASharedPrefKeyValuePair::class} " +
                    "OR Using ${MASharedPrefKeyValuePair.Container::class} Not logical so class is ignored."
            )

            continue
        }

        // Check for java support isa.
        var atLeastOneFunNeedsJavaSupport = false
        loop@ for (item in annMASharedPrefKeyValuePairList.map { it.supportJavaConsumerCode }) {
            when (item) {
                MASharedPrefKeyValuePair.JavaConsumerCode.SUPPORT -> {
                    atLeastOneFunNeedsJavaSupport = true
                    break@loop
                }
                MASharedPrefKeyValuePair.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS -> {
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
            element.asClassName().packageName + Constants.GENERATED_FILE_PACKAGE_SUFFIX_WITH_DOT,
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
                "mohamedalaa.mautils.shared_pref_core.sharedPrefSetComplex",
                "mohamedalaa.mautils.shared_pref_core.sharedPrefGetComplex",
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
        for (item in annMASharedPrefKeyValuePairList) {
            // Check manual conversion isa.
            val convertAnyToString = item.convertAnyToString
            val convertStringToAny = item.convertStringToAny
            val isManualConversion = when {
                convertAnyToString.isNotEmpty() && convertStringToAny.isNotEmpty() -> true
                convertAnyToString.isEmpty() && convertStringToAny.isEmpty() -> false
                else -> error(
                    "Either both of ${MASharedPrefKeyValuePair::class.qualifiedName}.${MASharedPrefKeyValuePair::convertAnyToString.name} " +
                        "& ${MASharedPrefKeyValuePair::class.qualifiedName}.${MASharedPrefKeyValuePair::convertStringToAny.name} " +
                        "are empty for auto conversion or both have code inside them for manual conversion isa."
                )
            }

            // Matched gson converter
            val matchedGsonConvertedTypeElement: TypeElement? = item.getMatchedGsonConverterOrNull(
                processingEnv,
                listOfElementsAnnotatedByGsonConverterAndTheirTypeNamesListAnalyzed
            )
            val gsonConverterSimpleName = if (matchedGsonConvertedTypeElement != null && isManualConversion.not()) {
                fullImportStringsList += matchedGsonConvertedTypeElement.qualifiedName.toStringOrEmpty()

                matchedGsonConvertedTypeElement.simpleName.toStringOrEmpty()
            }else {
                null
            }

            // Value type of this key isa.
            val valueTypeName = item.asTypeName(this)

            // SetComplex fun isa.
            functions += processingEnv.buildSetComplexFun(
                annotatedClassName,
                item,
                annMASharedPrefFileConfigs.supportJavaConsumerCode,
                gsonConverterSimpleName,
                valueTypeName,
                isManualConversion
            )

            // GetComplex fun isa.
            val annotationMirror = element.annotationMirrors.firstOrNull {
                val areSameAnnotationType = it.annotationType.toStringOrEmpty() == typeUtils.getDeclaredType(
                    elementUtils.getTypeElement(MASharedPrefKeyValuePair::class.qualifiedName)
                ).toStringOrEmpty()

                areSameAnnotationType && it.elementValues.any { (executableElement, _) ->
                    executableElement.simpleName.toStringOrEmpty() == MASharedPrefKeyValuePair::defaultValue.name
                }
            }
            functions += processingEnv.buildGetComplexFun(
                annotatedClassName,
                item,
                annMASharedPrefFileConfigs.supportJavaConsumerCode,
                gsonConverterSimpleName,
                annotationMirror,
                valueTypeName,
                isManualConversion
            )

            allFieldsNamesInsideSharedPrefFile += item.name
        }
        if (allFieldsNamesInsideSharedPrefFile.size != allFieldsNamesInsideSharedPrefFile.distinct().size) {
            processingEnv.error(
                "Cannot have 2 or more same names(keys) in ${mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair::class.simpleName} for the same file isa" +
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
