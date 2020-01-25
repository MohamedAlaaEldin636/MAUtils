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

import com.maproductions.mohamedalaa.kotlin_poet.convertJavaToKotlinTypeAsTypeName
import com.maproductions.mohamedalaa.kotlin_poet.custom_classes.KotlinpoetUtils
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element

fun Element.asTypeName(convertJavaToKotlinType: Boolean = true): TypeName {
    val typeName = asType().asTypeName()
    if (convertJavaToKotlinType.not() || typeName !is ParameterizedTypeName) return typeName.run {
        if (convertJavaToKotlinType) convertJavaToKotlinTypeAsTypeName() else this
    }

    // Result needed List of pair of typeName and number of type params isa.
    val listOfTypeNamesAndNumberOfTypeParams = typeName.analyzeDeeplyToListOfTypeNamesAndNumberOfTypeParams(convertJavaToKotlinType)

    // Merge this lit to 1 type params isa.
    return listOfTypeNamesAndNumberOfTypeParams.mergeToTypeName()
}

private fun TypeName.analyzeDeeplyToListOfTypeNamesAndNumberOfTypeParams(convertJavaToKotlinType: Boolean = true): List<Pair<TypeName, Int>> {
    val conversionBlock: TypeName.() -> TypeName = {
        if (convertJavaToKotlinType) {
            convertJavaToKotlinTypeAsTypeName()
        }else {
            this
        }
    }

    return if (this is ParameterizedTypeName) {
        val mutableList = mutableListOf<Pair<TypeName, Int>>()
        mutableList += rawType.conversionBlock() to typeArguments.size
        for (type in typeArguments) {
            mutableList += type.analyzeDeeplyToListOfTypeNamesAndNumberOfTypeParams(convertJavaToKotlinType)
        }
        mutableList
    }else {
        listOf(this.conversionBlock() to 0)
    }
}

private fun List<Pair<TypeName, Int>>.mergeToTypeName(): TypeName {
    return when {
        isEmpty() -> throw RuntimeException(
            "unexpected error code 7382zdea3232g isa."
        )
        size == 1 -> {
            first().first
        }
        else -> {
            KotlinpoetUtils.parameterizedTypeName(
                first().first,
                *drop(1).getTypeParamsOfAnotherTypeAfterAllChecksMadeIsa().toTypedArray()
            )
        }
    }
}

private fun List<Pair<TypeName, Int>>.getTypeParamsOfAnotherTypeAfterAllChecksMadeIsa(): List<TypeName> {
    val list = mutableListOf<TypeName>()

    val params = toMutableList()
    while (params.isNotEmpty()) {
        val (typeName, noOfTypeParams) = params.first()
        params.removeAt(0)

        list += if (noOfTypeParams < 1) {
            typeName
        }else {
            val (typeParams, newParams) = params.getNeededTypeParamsOnly(noOfTypeParams)

            params.clear()
            params += newParams

            KotlinpoetUtils.parameterizedTypeName(
                typeName,
                *typeParams.toTypedArray()
            )
        }
    }

    return list
}

private fun List<Pair<TypeName, Int>>.getNeededTypeParamsOnly(
    noOfItemsOfList: Int
): Pair<List<TypeName>, List<Pair<TypeName, Int>>> {
    val params = toMutableList()

    val result = mutableListOf<TypeName>()
    var numberNeeded = noOfItemsOfList
    while (numberNeeded > 0) {
        val (typeName, noOfTypeParams) = params.first()
        params.removeAt(0)

        result += if (noOfTypeParams < 1) {
            typeName
        }else {
            val (typeParams, newParams) = params.getNeededTypeParamsOnly(noOfTypeParams)

            params.clear()
            params += newParams

            KotlinpoetUtils.parameterizedTypeName(
                typeName,
                *typeParams.toTypedArray()
            )
        }

        numberNeeded--
    }

    return result to params
}
