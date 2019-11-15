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

package mohamedalaa.mautils.shared_pref_processor.extensions

import com.squareup.kotlinpoet.TypeName
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair
import javax.annotation.processing.ProcessingEnvironment

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

/** typeName, no. of type params, isNullable */
fun List<Triple<TypeName, Int, Boolean>>.toTypeName(processingEnv: ProcessingEnvironment): TypeName = processingEnv.run {
    when {
        isEmpty() -> error(
            "type in ${MASharedPrefKeyValuePair::class} has no types at all isa."
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
