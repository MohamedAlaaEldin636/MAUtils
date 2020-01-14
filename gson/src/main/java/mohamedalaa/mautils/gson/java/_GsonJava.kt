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

@file:JvmName("GsonUtils")

package mohamedalaa.mautils.gson.java

import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import mohamedalaa.mautils.gson.addTypeAdapters
import mohamedalaa.mautils.gson.internal.canonicalizeOrNull
import mohamedalaa.mautils.gson.privateGeneratedGson
import mohamedalaa.mautils.gson.toJsonOrNull
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * ### CAUTION
 * 1. This should only be used by java consumer code isa.
 * 2. If the type has type params then you have to use [GsonConverter.toJsonOrNull] instead isa.
 *
 * ### Usage
 * - Converts `receiver` object to a JSON String OR `null` in case of any error isa.
 * - [elementClass] param isn't needed to be provided as [Class] will be inferred from `receiver`
 * **BUT** needed in case of using `superclass` of the given `receiver` which is needed in
 * case of classes annotated with [MASealedAbstractOrInterface] isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @return JSON String OR `null` if any problem occurs isa.
 *
 * @see toJsonJava
 * @see fromJsonOrNullJava
 */
@JvmOverloads
@JvmName("toJsonOrNull")
fun <E> E?.toJsonOrNullJava(elementClass: Class<E>? = null, gson: Gson? = null): String? = this?.run {
    val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson

    try {
        if (elementClass == null) {
            usedGson.toJson(this)
        }else {
            usedGson.toJson(this, elementClass)
        }
    } catch (e: Exception) { null }
}

/**
 * - Same as [toJsonOrNullJava] but throws [RuntimeException] in case of any problems instead of
 * returning `null` isa.
 *
 * @throws RuntimeException in case if any problem occurred while converting isa.
 *
 * @see toJsonOrNullJava
 * @see fromJsonJava
 */
@JvmOverloads
@JvmName("toJson")
fun <E> E?.toJsonJava(elementClass: Class<E>? = null, gson: Gson? = null): String = toJsonOrNullJava(elementClass, gson)
    ?: throw RuntimeException("Cannot convert $this to JSON String")

/**
 * ### CAUTION
 * 1. This should only be used by java consumer code isa.
 * 2. If the type has type params then you have to use [GsonConverter.fromJsonOrNull] instead isa.
 *
 * ### Usage
 * - Converts `this JSON String` to object of type [E], OR null in case of any error occurs isa.
 *
 * @param elementClass [Class] of the of type [E] to be used isa.
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @param E type to be converted to, from given JSON String.
 *
 * @return object of type [E] from given JSON String OR null if any problem occurs isa.
 *
 * @see fromJsonJava
 * @see toJsonOrNullJava
 */
@JvmOverloads
@JvmName("fromJsonOrNull")
fun <E> String?.fromJsonOrNullJava(elementClass: Class<E>, gson: Gson? = null): E? = this?.run {
    (elementClass as? Class<*>)?.kotlin?.objectInstance?.apply {
        @Suppress("UNCHECKED_CAST")
        return@run this as E?
    }

    val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson

    try { usedGson.fromJson(this, elementClass) } catch (e: Exception) { null }
}

/**
 * - Same as [fromJsonOrNullJava] but throws [RuntimeException] in case of any problems instead of
 * returning `null` isa.
 *
 * @throws RuntimeException in case if any problem occurred while converting isa.
 *
 * @see fromJsonOrNullJava
 * @see toJsonJava
 */
@JvmOverloads
@JvmName("fromJson")
fun <E> String?.fromJsonJava(elementClass: Class<E>, gson: Gson? = null): E = fromJsonOrNullJava(elementClass, gson)
    ?: throw RuntimeException("Cannot convert $this to $elementClass")

/**
 * ### CAUTION
 * - Should be used only for java consumer code, Not for kotlin consumer code which instead should
 * use [mohamedalaa.mautils.gson.fromJsonOrNull], [mohamedalaa.mautils.gson.fromJson],
 * [mohamedalaa.mautils.gson.toJsonOrNull] OR [mohamedalaa.mautils.gson.toJson] isa.
 *
 * ### Description
 * - Used only if your Object that needs to be converted to/from JSON-String has type parameters isa,
 * Otherwise consider using [String.fromJsonOrNullJava], [String.fromJsonJava],
 * [String.toJsonOrNullJava] OR [String.toJsonJava] isa.
 *
 * ### How to use
 * ```
 * // Java consumer used one time (if needed more check below so that you don't repeat same code)
 * CustomWithTypeParam<CustomObject, Integer> customWithTypeParam = new CustomWithTypeParam<>();
 *
 * GsonConverter<CustomWithTypeParam<CustomObject, Integer>> gsonConverter
 *      = new GsonConverter<CustomWithTypeParam<CustomObject, Integer>>() {};
 *
 * String jsonString = gsonConverter.toJsonOrNull(customWithTypeParam);
 * CustomWithTypeParam<CustomObject, Integer> fromJsonObject = gsonConverter.fromJsonOrNull(jsonString);
 *
 * assertEquals(customWithTypeParam, fromJsonObject) // true.
 * ```
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @param E type to convert to/from JSON String.
 */
abstract class GsonConverter<E>(private val gson: Gson? = null) {

    internal companion object;

    /**
     * - Converts [element] object to a JSON String OR null in case of any error isa.
     *
     * - If [element]'s type [E] has no type parameters Use [String.toJsonOrNullJava] instead,
     * since it's more concise syntax isa.
     *
     * @return JSON String or null if any problem occurs isa.
     */
    fun toJsonOrNull(element: E?): String? {
        val type = getSuperclassTypeParameter(javaClass)

        val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson

        return try { usedGson.toJson(element, type) } catch (e: Exception) { null }
    }

    /**
     * - Same as [toJsonOrNull] but throws [RuntimeException] in case of any problems instead of
     * returning `null` isa.
     *
     * @throws RuntimeException in case of any error while converting isa.
     */
    fun toJson(element: E?): String = toJsonOrNull(element)
        ?: throw RuntimeException("Cannot convert $element to JSON String")

    /**
     * - Converts [json] to object of type [E], or null in case of any error occurs isa.
     *
     * - If [E] has no type parameters Use [String.fromJsonOrNullJava] instead,
     * since it's more concise syntax isa.
     *
     * @return object of type <E> from given JSON String or null if any problem occurs isa.
     */
    fun fromJsonOrNull(json: String?): E? {
        val type = getSuperclassTypeParameter(javaClass)

        (type as? Class<*>)?.kotlin?.objectInstance?.apply {
            @Suppress("UNCHECKED_CAST")
            return this as E?
        }

        val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson

        return try { usedGson.fromJson(json, type) } catch (e: Exception) { null }
    }

    /**
     * - Same as [fromJsonOrNull] but throws [RuntimeException] in case of any problems instead of
     * returning `null` isa.
     *
     * @throws RuntimeException in case of any error while converting isa.
     */
    fun fromJson(json: String?): E = fromJsonOrNull(json)
        ?: throw RuntimeException("Cannot convert $json to <E>")

    /** Gets full [Type] info without erasure for conversion using [Gson] isa. */
    private fun getSuperclassTypeParameter(subclass: Class<*>): Type {
        val superclass = subclass.genericSuperclass

        if (superclass is Class<*>) {
            throw RuntimeException("Missing type parameter isa.")
        }

        val parameterizedType = superclass as ParameterizedType
        return GsonConverter.canonicalizeOrNull(
            parameterizedType.actualTypeArguments[0]
        ) ?: `$Gson$Types`.canonicalize(parameterizedType.actualTypeArguments[0])
    }

}
