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
import com.squareup.kotlinpoet.asTypeName
import mohamedalaa.mautils.core_kotlin.extensions.triple
import mohamedalaa.mautils.shared_pref_annotation.MAParameterizedKClass
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair
import mohamedalaa.mautils.shared_pref_processor.extensions.kotlinpoet.convertJavaToKotlinTypeAsTypeName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror

/** takes care of nullability isa do not worry isa. */
fun MASharedPrefPair.asTypeName(processingEnv: ProcessingEnvironment): TypeName {
    val listOfTypeMirrorsAndIsNullable = getAllKClassesNotNullAsTypeMirrorsAndIsNullable(processingEnv)

    val mutableListOfTypeNameAndNumberOfTypeArgsAndIsNullable = mutableListOf<Triple<TypeName, Int, Boolean>>()
    for ((typeMirror, isNullable) in listOfTypeMirrorsAndIsNullable) {

        val typeElement = processingEnv.typeUtils.asElement(typeMirror) as? TypeElement
        mutableListOfTypeNameAndNumberOfTypeArgsAndIsNullable += if (typeElement == null) {
            typeMirror.asTypeName() to 0 triple isNullable
        }else {
            typeMirror.asTypeName() to typeElement.typeParameters.size triple isNullable
        }
    }

    return mutableListOfTypeNameAndNumberOfTypeArgsAndIsNullable.map {
        it.first.convertJavaToKotlinTypeAsTypeName() to it.second triple it.third
    }.toTypeName(processingEnv)
}

/** @return true if nullable class, we don't look at any type parameters isa. */
fun MASharedPrefPair.classIsNullable(): Boolean {
    val (nonNullTypeMirrorAndIsNullable, _) = getNonNullAndMAKClassTypeMirrorAndIsNullable()

    return if (nonNullTypeMirrorAndIsNullable.isNotEmpty()) {
        false
    }else {
        type.maKClass.first().nullable
    }
}

private fun MASharedPrefPair.getAllKClassesNotNullAsTypeMirrors(
    processingEnv: ProcessingEnvironment
): List<TypeMirror> = getAllKClassesNotNullAsTypeMirrorsAndIsNullable(processingEnv).map { it.first }

private fun MASharedPrefPair.getAllKClassesNotNullAsTypeMirrorsAndIsNullable(
    processingEnv: ProcessingEnvironment
): List<Pair<TypeMirror, Boolean>> = processingEnv.run {
    val (nonNullTypeMirrorAndIsNullable, maKClassTypeMirrorAndIsNullable) = getNonNullAndMAKClassTypeMirrorAndIsNullable()
    when {
        nonNullTypeMirrorAndIsNullable.isNotEmpty() && maKClassTypeMirrorAndIsNullable.isNotEmpty() -> error(
            "Only One of ${MAParameterizedKClass::nonNullKClasses.name}, " +
                "${MAParameterizedKClass::maKClass.name} needed to be provided in " +
                "${MAParameterizedKClass::class} isa."
        )
        nonNullTypeMirrorAndIsNullable.isEmpty() && maKClassTypeMirrorAndIsNullable.isEmpty() -> error(
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

private fun MASharedPrefPair.getNonNullAndMAKClassTypeMirrorAndIsNullable()
    : Pair<List<Pair<TypeMirror, Boolean>>, List<Pair<TypeMirror, Boolean>>> {
    val nonNullTypeMirrorAndIsNullable = (try {
        @Suppress("UNCHECKED_CAST")
        type.nonNullKClasses.toList() as List<TypeMirror>
    }catch (e: MirroredTypesException) {
        e.typeMirrors
    }).map { it to false }

    val maKClassTypeMirrorAndIsNullable = type.maKClass.map {
        try {
            (it.kClass as TypeMirror) to it.nullable
        }catch (e: MirroredTypesException) {
            e.typeMirrors.first() to it.nullable
        }
    }

    return nonNullTypeMirrorAndIsNullable to maKClassTypeMirrorAndIsNullable
}
