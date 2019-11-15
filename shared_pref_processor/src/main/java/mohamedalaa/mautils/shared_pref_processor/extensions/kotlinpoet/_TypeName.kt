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

package mohamedalaa.mautils.shared_pref_processor.extensions.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import mohamedalaa.mautils.shared_pref_processor.extensions.KotlinpoetUtils
import javax.lang.model.element.Element
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap
import kotlin.reflect.jvm.internal.impl.name.FqName

/**
 * @return If given `receiver` is not [ParameterizedTypeName] then returns a [List] of single item
 * which is the `receiver` otherwise it keeps analyzing all type parameters to return a list sorted
 * as they appear isa, Ex,. List<Pair<Pair<Int, Float>, String>> returns List, Pair, Pair, Int etc... isa.
 */
fun TypeName.analyzeDeeplyToListOfTypeNames(convertJavaPrimitivesToKotlin: Boolean = true): List<TypeName> {
    val conversionBlock: TypeName.() -> TypeName = {
        if (convertJavaPrimitivesToKotlin) {
            convertPrimitivesAndStringOfJavaToKotlinAsTypeName()
        }else {
            this
        }
    }

    return if (this is ParameterizedTypeName) {
        val mutableList = mutableListOf<TypeName>()
        mutableList += rawType.conversionBlock()
        for (type in typeArguments) {
            mutableList += type.analyzeDeeplyToListOfTypeNames(convertJavaPrimitivesToKotlin)
        }
        mutableList
    }else {
        listOf(this.conversionBlock())
    }
}

/** @see analyzeDeeplyToListOfTypeNames */
fun List<TypeName>.analyzeDeeplyToListOfTypeNames(convertJavaPrimitivesToKotlin: Boolean = true): List<TypeName> {
    val mutableList = mutableListOf<TypeName>()
    for (item in this) {
        mutableList += item.analyzeDeeplyToListOfTypeNames(convertJavaPrimitivesToKotlin)
    }

    return mutableList
}

/**
 * Converts [String] and all primitives (int, byte, short, long, float, double, boolean, and char) isa.
 */
fun TypeName.convertPrimitivesAndStringOfJavaToKotlinAsTypeName(): TypeName {
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

fun TypeName.getDefaultValueAsKotlinCodeExpression(): String {
    return when (this) {
        String::class.java.asTypeName(), String::class.asTypeName() -> "\"\""

        Int::class.javaObjectType.asTypeName(),
        Int::class.javaPrimitiveType!!.asTypeName(),
        Int::class.asTypeName() -> "0"

        Byte::class.javaObjectType.asTypeName(),
        Byte::class.javaPrimitiveType!!.asTypeName(),
        Byte::class.asTypeName() -> "0"

        Short::class.javaObjectType.asTypeName(),
        Short::class.javaPrimitiveType!!.asTypeName(),
        Short::class.asTypeName() -> "0"

        Long::class.javaObjectType.asTypeName(),
        Long::class.javaPrimitiveType!!.asTypeName(),
        Long::class.asTypeName() -> "0L"

        Float::class.javaObjectType.asTypeName(),
        Float::class.javaPrimitiveType!!.asTypeName(),
        Float::class.asTypeName() -> "0f"

        Double::class.javaObjectType.asTypeName(),
        Double::class.javaPrimitiveType!!.asTypeName(),
        Double::class.asTypeName() -> "0.0"

        Boolean::class.javaObjectType.asTypeName(),
        Boolean::class.javaPrimitiveType!!.asTypeName(),
        Boolean::class.asTypeName() -> "false"

        Char::class.javaObjectType.asTypeName(),
        Char::class.javaPrimitiveType!!.asTypeName(),
        Char::class.asTypeName() -> "'\\u0000'"

        else -> "null"
    }
}

@Suppress("unused")
private fun Element.javaToKotlinType(): TypeName =
    asType().asTypeName().javaToKotlinType()

private fun TypeName.javaToKotlinType(): TypeName {
    return if (this is ParameterizedTypeName) {
        KotlinpoetUtils.parameterizedTypeName(
            rawType.javaToKotlinType() as ClassName,
            *typeArguments.map { it.javaToKotlinType() }.toTypedArray()
        )
    }else {
        val className =
            JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(FqName(toString()))
                ?.asSingleFqName()?.asString()

        return if (className == null) {
            this
        } else {
            ClassName.bestGuess(className)
        }
    }
}
