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

package com.maproductions.mohamedalaa.library_common_processors

import com.maproductions.mohamedalaa.kotlin_poet.analyzeDeeplyToListOfTypeNames
import com.maproductions.mohamedalaa.kotlin_poet.convertJavaToKotlinTypeAsTypeName
import com.maproductions.mohamedalaa.kotlin_poet.custom_classes.KotlinpoetUtils
import com.maproductions.mohamedalaa.library_common_annotations.MAParameterizedKClass
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import mohamedalaa.mautils.core_kotlin.extensions.triple
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror

/**
 * - Converts `receiver` to [TypeName] which can be [ParameterizedTypeName] if there is more than
 * `1` type in [MAParameterizedKClass.nonNullKClasses] if used **OR** [MAParameterizedKClass.maKClass]
 * if used instead isa.
 *
 * @param convertJavaToKotlin pass this param to [analyzeDeeplyToListOfTypeNames]'s param isa.
 */
fun MAParameterizedKClass.asTypeName(processingEnv: ProcessingEnvironment, convertJavaToKotlin: Boolean = true): TypeName {
    val listOfTypeMirrorsAndIsNullable = getAllKClassesNotNullAsTypeMirrorsAndIsNullable(processingEnv)

    val mutableListOfTypeNameAndNumberOfTypeArgsAndIsNullable = mutableListOf<Triple<TypeName, Int, Boolean>>()
    for ((typeMirror, isNullable) in listOfTypeMirrorsAndIsNullable) {
        val typeElement = processingEnv.typeUtils.asElement(typeMirror) as? TypeElement
        mutableListOfTypeNameAndNumberOfTypeArgsAndIsNullable += if (typeElement == null) {
            typeMirror.asTypeName().run {
                if (convertJavaToKotlin) convertJavaToKotlinTypeAsTypeName() else this
            } to 0 triple isNullable
        }else {
            typeMirror.asTypeName().run {
                if (convertJavaToKotlin) convertJavaToKotlinTypeAsTypeName() else this
            } to typeElement.typeParameters.size triple isNullable
        }
    }

    return mutableListOfTypeNameAndNumberOfTypeArgsAndIsNullable.toTypeName(processingEnv)
}

// ---- Private fun

private fun MAParameterizedKClass.getAllKClassesNotNullAsTypeMirrorsAndIsNullable(
    processingEnv: ProcessingEnvironment
): List<Pair<TypeMirror, Boolean>> = processingEnv.run {
    val (nonNullTypeMirrorAndIsNullable, maKClassTypeMirrorAndIsNullable) = getNonNullAndMAKClassTypeMirrorAndIsNullable()
    when {
        nonNullTypeMirrorAndIsNullable.isNotEmpty() && maKClassTypeMirrorAndIsNullable.isNotEmpty() -> processingEnv.error(
            "Only One of ${MAParameterizedKClass::nonNullKClasses.name}, " +
                "${MAParameterizedKClass::maKClass.name} needed to be provided in " +
                "${MAParameterizedKClass::class} isa."
        )
        nonNullTypeMirrorAndIsNullable.isEmpty() && maKClassTypeMirrorAndIsNullable.isEmpty() -> processingEnv.error(
            "nothing is provided while we need one of " +
                "${MAParameterizedKClass::nonNullKClasses.name}, " +
                "${MAParameterizedKClass::maKClass.name} to be provided in " +
                "${MAParameterizedKClass::class} isa."
        )
        nonNullTypeMirrorAndIsNullable.isNotEmpty() -> {
            nonNullTypeMirrorAndIsNullable
        }
        else -> {
            maKClassTypeMirrorAndIsNullable
        }
    }
}

private fun MAParameterizedKClass.getNonNullAndMAKClassTypeMirrorAndIsNullable() : Pair<List<Pair<TypeMirror, Boolean>>, List<Pair<TypeMirror, Boolean>>> {
    val nonNullTypeMirrorAndIsNullable = (try {
        @Suppress("UNCHECKED_CAST")
        nonNullKClasses.toList() as List<TypeMirror>
    }catch (e: MirroredTypesException) {
        e.typeMirrors
    }).map { it to false }

    val maKClassTypeMirrorAndIsNullable = maKClass.map {
        try {
            (it.kClass as TypeMirror) to it.nullable
        }catch (e: MirroredTypesException) {
            e.typeMirrors.first() to it.nullable
        }
    }

    return nonNullTypeMirrorAndIsNullable to maKClassTypeMirrorAndIsNullable
}

/** typeName, no. of type params, isNullable */
private fun List<Triple<TypeName, Int, Boolean>>.toTypeName(processingEnv: ProcessingEnvironment): TypeName = processingEnv.run {
    when {
        isEmpty() -> error(
            "type in ${MAParameterizedKClass::class} has no types at all isa."
        )
        size == 1 -> {
            first().run { first.copy(nullable = third) }
        }
        else -> {
            KotlinpoetUtils.parameterizedTypeName(
                first().run { first.copy(nullable = third) },
                *drop(1).getTypeParamsOfAnotherTypeAfterAllChecksMadeIsa().toTypedArray()
            )
        }
    }
}

private fun List<Triple<TypeName, Int, Boolean>>.getTypeParamsOfAnotherTypeAfterAllChecksMadeIsa(): List<TypeName> {
    val list = mutableListOf<TypeName>()

    val params = toMutableList()
    while (params.isNotEmpty()) {
        val (typeName, noOfTypeParams, isNullable) = params.first()
        params.removeAt(0)

        list += if (noOfTypeParams < 1) {
            typeName.copy(nullable = isNullable)
        }else {
            val (typeParams, newParams) = params.getNeededTypeParamsOnly(noOfTypeParams)

            params.clear()
            params += newParams

            KotlinpoetUtils.parameterizedTypeName(
                typeName.copy(nullable = isNullable),
                *typeParams.toTypedArray()
            )
        }
    }

    return list
}

private fun List<Triple<TypeName, Int, Boolean>>.getNeededTypeParamsOnly(
    noOfItemsOfList: Int
): Pair<List<TypeName>, List<Triple<TypeName, Int, Boolean>>> {
    val params = toMutableList()

    val result = mutableListOf<TypeName>()
    var numberNeeded = noOfItemsOfList
    while (numberNeeded > 0) {
        val (typeName, noOfTypeParams, isNullable) = params.first()
        params.removeAt(0)

        result += if (noOfTypeParams < 1) {
            typeName.copy(nullable = isNullable)
        }else {
            val (typeParams, newParams) = params.getNeededTypeParamsOnly(noOfTypeParams)

            params.clear()
            params += newParams

            KotlinpoetUtils.parameterizedTypeName(
                typeName.copy(nullable = isNullable),
                *typeParams.toTypedArray()
            )
        }

        numberNeeded--
    }

    return result to params
}
