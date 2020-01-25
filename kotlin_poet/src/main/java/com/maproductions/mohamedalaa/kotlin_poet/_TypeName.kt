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

package com.maproductions.mohamedalaa.kotlin_poet

import com.maproductions.mohamedalaa.kotlin_poet.custom_classes.KotlinpoetUtils
import com.squareup.kotlinpoet.*
import javax.lang.model.element.Element
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap
import kotlin.reflect.jvm.internal.impl.name.FqName

/**
 * @param convertJavaToKotlinType if `true` then each item in the returned [List] will be converted
 * using [convertJavaToKotlinTypeAsTypeName] isa.
 *
 * @return If given `receiver` is not [ParameterizedTypeName] then returns a [List] of single item
 * which is the `receiver` otherwise it keeps analyzing all type parameters to return a list sorted
 * as they appear isa, Ex,. List<Pair<Pair<Int, Float>, String>> returns List, Pair, Pair, Int etc... isa.
 */
fun TypeName.analyzeDeeplyToListOfTypeNames(convertJavaToKotlinType: Boolean = true): List<TypeName> {
    val conversionBlock: TypeName.() -> TypeName = {
        if (convertJavaToKotlinType) {
            convertJavaToKotlinTypeAsTypeName()
        }else {
            this
        }
    }

    return if (this is ParameterizedTypeName) {
        val mutableList = mutableListOf<TypeName>()
        mutableList += rawType.conversionBlock()
        for (type in typeArguments) {
            mutableList += type.analyzeDeeplyToListOfTypeNames(convertJavaToKotlinType)
        }
        mutableList
    }else {
        listOf(this.conversionBlock())
    }
}

/**
 * - Convert all items in `receiver` to applying [analyzeDeeplyToListOfTypeNames] to it isa.
 *
 * @see analyzeDeeplyToListOfTypeNames
 */
fun List<TypeName>.analyzeDeeplyToListOfTypeNames(convertJavaToKotlin: Boolean = true): List<TypeName> {
    val mutableList = mutableListOf<TypeName>()
    for (item in this) {
        mutableList += item.analyzeDeeplyToListOfTypeNames(convertJavaToKotlin)
    }

    return mutableList
}

/**
 * Converts [String] and all primitives (int, byte, short, long, float, double, boolean, and char)
 * & any other type to kotlin equivalent [TypeName] isa.
 */
fun TypeName.convertJavaToKotlinTypeAsTypeName(): TypeName {
    return when (this) {
        String::class.java.asTypeName() -> String::class.asTypeName()

        Int::class.javaObjectType.asTypeName(), Int::class.javaPrimitiveType!!.asTypeName() -> Int::class.asTypeName()
        Byte::class.javaObjectType.asTypeName(), Byte::class.javaPrimitiveType!!.asTypeName() -> Byte::class.asTypeName()
        Short::class.javaObjectType.asTypeName(), Short::class.javaPrimitiveType!!.asTypeName() -> Short::class.asTypeName()
        Long::class.javaObjectType.asTypeName(), Long::class.javaPrimitiveType!!.asTypeName() -> Long::class.asTypeName()
        Float::class.javaObjectType.asTypeName(), Float::class.javaPrimitiveType!!.asTypeName() -> Float::class.asTypeName()
        Double::class.javaObjectType.asTypeName(), Double::class.javaPrimitiveType!!.asTypeName() -> Double::class.asTypeName()
        Boolean::class.javaObjectType.asTypeName(), Boolean::class.javaPrimitiveType!!.asTypeName() -> Boolean::class.asTypeName()
        Char::class.javaObjectType.asTypeName(), Char::class.javaPrimitiveType!!.asTypeName() -> Char::class.asTypeName()

        else -> javaToKotlinType()
    }
}

// --- Private fun

private fun TypeName.javaToKotlinType(): TypeName {
    return when (this) {
        is WildcardTypeName -> {
            (outTypes.firstOrNull() ?: inTypes.first()).convertJavaToKotlinTypeAsTypeName()
        }
        is ParameterizedTypeName -> {
            KotlinpoetUtils.parameterizedTypeName(
                rawType.convertJavaToKotlinTypeAsTypeName() as ClassName,
                *typeArguments.map { it.convertJavaToKotlinTypeAsTypeName() }.toTypedArray()
            )
        }
        else -> {
            val className = JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(FqName(toString()))
                ?.asSingleFqName()?.asString()

            return if (className == null) {
                this
            } else {
                ClassName.bestGuess(className)
            }
        }
    }
}

