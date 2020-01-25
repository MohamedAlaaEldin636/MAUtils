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

package mohamedalaa.mautils.room_gson_processor

import com.maproductions.mohamedalaa.library_common_processors.asTypeName
import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverterType
import mohamedalaa.mautils.room_gson_processor.extensions.buildToAndFromConversionFunctions
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

fun RoundEnvironment.process(processingEnv: ProcessingEnvironment) = processingEnv.performIfNotNull {
    val maRoomGsonTypeConverterElements = getElementsAnnotatedWith(MARoomGsonTypeConverter::class.java).filterNotNull()
    val maRoomGsonTypeConverterTypeElements = (
        (getElementsAnnotatedWith(MARoomGsonTypeConverterType.Container::class.java) +
            getElementsAnnotatedWith(MARoomGsonTypeConverterType::class.java)
        )
    ).distinct().filterNotNull()

    // -- MARoomGsonTypeConverter -- //

    // Create conversion functions toJson/fromJson for each type isa.
    val allFunctionsFromOneTypeAnnotation = mutableListOf<FunSpec>()
    for (element in maRoomGsonTypeConverterElements) {
        allFunctionsFromOneTypeAnnotation += buildToAndFromConversionFunctions(element)
    }

    // Ensure no duplications happened isa.
    allFunctionsFromOneTypeAnnotation.clearAllThenSet(
        allFunctionsFromOneTypeAnnotation.distinctBy {
            it.name
        }
    )

    // KClass
    val kClass = TypeSpec.classBuilder(Constants.generationClassSimpleName)
        .addFunctions(allFunctionsFromOneTypeAnnotation)
        .build()

    // KFile
    val kFile = FileSpec.builder(Constants.generationPackage, Constants.generationClassSimpleName)
        .addImport("mohamedalaa.mautils.gson", "fromJsonOrNull", "toJsonOrNull")
        .addType(kClass)
        .build()

    // -- MARoomGsonTypeConverterType -- //

    val allFunctionsFromSeveralTypesAnnotation = mutableListOf<FunSpec>()
    val listOfKFiles = mutableListOf<FileSpec>()
    for (element in maRoomGsonTypeConverterTypeElements) {
        val annMASharedPrefPairList = element.getAnnotationsByType(MARoomGsonTypeConverterType::class.java).filterNotNull()
        if (element !is TypeElement || annMASharedPrefPairList.isEmpty()) continue

        // Create conversion functions toJson/fromJson for each type isa.
        allFunctionsFromSeveralTypesAnnotation.clear()
        for (annotation in annMASharedPrefPairList) {
            val typeName = annotation.value.asTypeName(this)
            allFunctionsFromSeveralTypesAnnotation += buildToAndFromConversionFunctions(typeName)
        }

        // Ensure no duplications happened isa.
        allFunctionsFromSeveralTypesAnnotation.clearAllThenSet(
            allFunctionsFromSeveralTypesAnnotation.distinctBy {
                it.name
            }
        )

        // KClass
        val kClassAndKFileSimpleName = Constants.generationClassSimpleName + element.simpleName.toStringOrEmpty()
        val typeSpec = TypeSpec.classBuilder(kClassAndKFileSimpleName)
            .addType(
                TypeSpec.companionObjectBuilder()
                    .addProperty(
                        PropertySpec.builder("privateClassName", String::class, KModifier.PRIVATE)
                            .initializer("${element.simpleName}::class.java.simpleName")
                            .build()
                    )
                    .build()
            )
            .addFunctions(allFunctionsFromSeveralTypesAnnotation)
            .build()

        // KFile
        listOfKFiles += FileSpec.builder(element.qualifiedName.toStringOrEmpty().getPackageNameOnly(), kClassAndKFileSimpleName)
            .addImport("mohamedalaa.mautils.gson", "fromJsonOrNull", "toJsonOrNull")
            .addImport("", element.qualifiedName.toStringOrEmpty())
            .addType(typeSpec)
            .build()
    }

    // Generate all the code made above isa.
    try {
        kFile.writeTo(processingEnv.filer)

        for (item in listOfKFiles) {
            try {
                item.writeTo(processingEnv.filer)
            }catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun <E> MutableList<E>.clearAllThenSet(other: List<E>) {
    clear()
    addAll(other)
}

private fun String.getPackageNameOnly(): String {
    return if ("." !in this) this else {
        val lastIndex = lastIndexOf(".")

        substring(0, lastIndex)
    }
}
