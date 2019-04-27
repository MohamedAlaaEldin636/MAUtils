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

package mohamedalaa.mautils.room_gson_processor.extensions

import androidx.room.TypeConverter
import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.core_kotlin.containsAny
import mohamedalaa.mautils.core_kotlin.nearestIndexOf
import mohamedalaa.mautils.core_kotlin.removeAll
import mohamedalaa.mautils.room_gson_annotation.ProcessorConstants
import mohamedalaa.mautils.room_gson_processor.utils.KotlinpoetUtils
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

fun noTypeParamBuild(element: Element): Pair<String, TypeSpec.Builder> {
    // 1. File Name ( Same as KClass name )
    val (simpleName, qualifiedName) = if (element is TypeElement) {
        element.simpleName.toString() to element.qualifiedName.toString()
    }else {
        val fullName = element.asType().toString()
        val index = fullName.lastIndexOf(".").inc()

        fullName.substring(index) to fullName
    }
    val kFileAndObjectName = ProcessorConstants.generationClassNamePrefix + simpleName

    // 2. Create object of that name isa.
    val objectBuilder = TypeSpec.objectBuilder(kFileAndObjectName)

    // 3. Build functions
    val functions = buildFunctions(simpleName, qualifiedName)
    functions.forEach {
        objectBuilder.addFunction(it)
    }

    // 4. kFile name ( to ) object builder
    return kFileAndObjectName to objectBuilder
}

private fun buildFunctions(classSimpleName: String, classFullName: String): List<FunSpec> {
    val mutableList = mutableListOf<FunSpec>()

    // Name of fun
    val fromStringFunName = "${classSimpleName.decapitalize()}FromString"
    val toStringFunName = "${classSimpleName.decapitalize()}ToString"

    // Builders
    val fromStringFun = FunSpec.builder(fromStringFunName)
    val toStringFun = FunSpec.builder(toStringFunName)

    // Return Type
    val typeNameString = String::class.asTypeName().copy(nullable = true)
    val typeNameClass = ClassName.bestGuess(classFullName).copy(nullable = true)
    fromStringFun.returns(typeNameClass)
    toStringFun.returns(typeNameString)

    // Annotations
    fromStringFun.addAnnotation(TypeConverter::class)
    toStringFun.addAnnotation(TypeConverter::class)

    // Parameters
    fromStringFun.addParameter("string", typeNameString)
    toStringFun.addParameter("value", typeNameClass)

    // Body
    fromStringFun.addStatement(
        "return string.fromJsonOrNull<$classSimpleName>()"
    )
    toStringFun.addStatement(
        "return value.toJsonOrNull()"
    )

    mutableList += toStringFun.build()
    mutableList += fromStringFun.build()
    return mutableList
}

fun withTypeParamBuild(element: Element): Pair<String, TypeSpec.Builder> {
    val fullTypeName = element.asType().toString()

    // 1. File Name ( Same as KClass name )
    val simpleName = withSimpleName(fullTypeName)
    val kFileAndObjectName = ProcessorConstants.generationClassNamePrefix + simpleName

    // 2. Create object of that name isa.
    val objectBuilder = TypeSpec.objectBuilder(kFileAndObjectName)

    // 3. Build functions
    val functions = withBuildFunctions(simpleName, fullTypeName)
    functions.forEach {
        objectBuilder.addFunction(it)
    }

    // 4. kFile name ( to ) object builder
    return kFileAndObjectName to objectBuilder
}

/** Same as [buildFunctions] but when second param in this fun is type param class isa. */
private fun withBuildFunctions(classSimpleName: String, fullTypeName: String): List<FunSpec> {
    val mutableList = mutableListOf<FunSpec>()

    // Name of fun
    val fromStringFunName = "${classSimpleName.decapitalize()}FromString"
    val toStringFunName = "${classSimpleName.decapitalize()}ToString"

    // Builders
    val fromStringFun = FunSpec.builder(fromStringFunName)
    val toStringFun = FunSpec.builder(toStringFunName)

    // Return Type
    val typeNameString = String::class.asTypeName().copy(nullable = true)
    val typeNameClass = withFullTypeName(fullTypeName)
    fromStringFun.returns(typeNameClass)
    toStringFun.returns(typeNameString)

    // Annotations
    fromStringFun.addAnnotation(TypeConverter::class)
    toStringFun.addAnnotation(TypeConverter::class)

    // Parameters
    fromStringFun.addParameter("string", typeNameString)
    toStringFun.addParameter("value", typeNameClass)

    // Body
    fromStringFun.addStatement(
        "return string.fromJsonOrNull<$typeNameClass>()"
    )
    toStringFun.addStatement(
        "return value.toJsonOrNull()"
    )

    mutableList += toStringFun.build()
    mutableList += fromStringFun.build()
    return mutableList
}

private fun String.extractPureNameFromExtendName(): String {
    return if (contains(" ")) {
        substring(lastIndexOf(" ").inc())
    }else {
        this
    }
}

private fun withFullTypeName(fullTypeName: String): TypeName {
    var tempFullTypeName = fullTypeName

    var tempTypeName: TypeName? = null
    while (tempFullTypeName.contains("<")) {
        val lastOpenIndex = tempFullTypeName.lastIndexOf("<")
        val startOpenOrCommaIndex = tempFullTypeName.lastIndexOfAny(listOf("<", ","), startIndex = lastOpenIndex.dec())
        val classFullNameTypeParamParent = tempFullTypeName.substring(startOpenOrCommaIndex.inc(), lastOpenIndex)

        val startCloseIndex = tempFullTypeName.indexOf(">")
        val stringBetweenBrackets = tempFullTypeName.substring(lastOpenIndex.inc(), startCloseIndex)
        val classFullNameTypeParamChildren = stringBetweenBrackets.split(",").map {
            if (it.isNotEmpty()) {
                ClassName.bestGuess(it.extractPureNameFromExtendName()).copy(nullable = true)
            }else {
                tempTypeName ?: throw RuntimeException("Empty string need type name but is null")
            }
        }

        tempTypeName = KotlinpoetUtils.parameterizedTypeName(
            ClassName.bestGuess(classFullNameTypeParamParent),
            *classFullNameTypeParamChildren.toTypedArray()
        ).copy(nullable = true)

        tempFullTypeName = if (startOpenOrCommaIndex == -1) "" else tempFullTypeName.substring(
            0, startOpenOrCommaIndex.inc()
        ) + tempFullTypeName.substring(
            startCloseIndex.inc()
        )
    }

    return tempTypeName ?: throw RuntimeException("null type name in with so strange isa.")
}

/**
 * return like-simple name from full name with type params
 * Ex. java.lang.Map<...Integer, ...Integer> returns MapIntegerInteger isa.
 */
private fun withSimpleName(fullTypeName: String): String {
    var tempFullTypeName = fullTypeName.removeAll(">")
    var value = ""
    while (tempFullTypeName.containsAny("<", ",")) {
        val lastIndex = tempFullTypeName.nearestIndexOf("<", ",") ?: throw RuntimeException("Cannot find < or ,")

        val fullClassName = tempFullTypeName.substring(0, lastIndex)
        tempFullTypeName = tempFullTypeName.substring(lastIndex.inc())

        value += fullClassName.simpleNameFromFullName()
    }

    value += tempFullTypeName.simpleNameFromFullName()

    return value
}

/** @return Simple name from a class full name Ex. java.lang.String returns String isa. */
private fun String.simpleNameFromFullName(): String
    = if ("." !in this) this else substring(lastIndexOf(".").inc())