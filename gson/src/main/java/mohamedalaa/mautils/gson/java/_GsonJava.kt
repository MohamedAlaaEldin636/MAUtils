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
import mohamedalaa.mautils.gson.privateGeneratedGson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Converts `receiver` object to a JSON String OR null in case of any error isa.
 *
 * **Warning**
 *
 * If `receiver` has type parameters, then you have to use [GsonConverter.toJsonOrNullJava] isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @return JSON String OR null if any problem occurs isa.
 *
 * @see toJsonJava
 * @see fromJsonOrNullJava
 */
@JvmOverloads
@JvmName("toJsonOrNull")
fun <E> E?.toJsonOrNullJava(elementClass: Class<E>, gson: Gson? = null): String? = this?.run {
    val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson

    try { usedGson.toJson(this, elementClass) } catch (e: Exception) { null }
}

/**
 * Converts `receiver` object to a JSON String OR throws exception in case of any error isa.
 *
 * **Warning**
 *
 * If `receiver` has type parameters, then you have to use [GsonConverter.toJsonJava] isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @throws RuntimeException in case if any problem occurred while converting isa.
 *
 * @return JSON String OR throws exception if any problem occurs isa.
 *
 * @see toJsonOrNullJava
 * @see fromJsonJava
 */
@JvmOverloads
@JvmName("toJson")
fun <E> E?.toJsonJava(elementClass: Class<E>, gson: Gson? = null): String = toJsonOrNullJava(elementClass, gson)
    ?: throw RuntimeException("Cannot convert $this to JSON String")

/**
 * ### Usage
 * - Converts `this JSON String` to object of type [elementClass], OR null in case of any error occurs isa.
 *
 * ### Warning
 *
 * - If `receiver` has type parameters, then you have to use [GsonConverter.fromJsonOrNull] isa.
 *
 * @param elementClass class of the `receiver` to be used isa.
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @return object of type <E> from given JSON String OR null if any problem occurs isa.
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
 * Converts `this JSON String` to object of type [elementClass], OR throws exception in case of any error occurs isa.
 *
 * **Warning**
 *
 * If `receiver` has type parameters, then you have to use [GsonConverter.fromJson] isa.
 *
 * @param elementClass class of the `receiver` to be used isa.
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @return object of type <[E]> from given JSON String OR throws exception if any problem occurs isa.
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
 * ## Description
 * Used only if your Object that needs to be converted to/from JSON-String has type parameters isa,
 *
 * otherwise consider using [String.fromJsonOrNullJava], [String.toJsonOrNullJava], [String.fromJsonJava]
 * OR [String.toJsonJava] isa.
 *
 * Used by java developers OR kotlin developers if and only if has non-invariant nested type parameters,
 *
 * See [mohamedalaa.mautils.gson.fromJsonOrNull] for more clarification isa.
 * ## How to use
 * - Must be in Java language or in kotlin but with additional 1 line of code in java isa.
 * ```
 * // Java consumer used one time (if needed more check below so that you don't repeat same code)
 * CustomWithTypeParam<CustomObject, Integer> customWithTypeParam = new CustomWithTypeParam<>();
 * String jsonString = new GsonConverter<CustomWithTypeParam<CustomObject, Integer>>(){}.toJsonOrNullJava(customWithTypeParam);
 * CustomWithTypeParam<CustomObject, Integer> fromJsonObject
 *      = new GsonConverter<CustomWithTypeParam<CustomObject, Integer>>(){}.fromJsonOrNullJava(jsonString);
 * // by now -> customWithTypeParam == fromJsonObject isa.
 *
 * // OR in case the GsonConverter needed in KOTLIN code will be used in several places
 * // make one class then re-use it isa.
 *
 * // In GsonConverterCustomWithTypeParamCustomObjectInteger.java file
 * class GsonConverterCustomWithTypeParamCustomObjectInteger
 *     extends GsonConverter<CustomWithTypeParam<CustomObject, Integer>> {}
 *
 * // then to use it, you can do it from KOTLIN or JAVA
 * // kotlin
 * val fromJsonObject = GsonConverterCustomWithTypeParamCustomObjectInteger().fromJsonOrNullJava(jsonString)
 * // java
 * CustomWithTypeParam<CustomObject, Integer> fromJsonObject
 *      = new GsonConverterCustomWithTypeParamCustomObjectInteger().fromJsonOrNullJava(jsonString);
 * ```
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 */
abstract class GsonConverter<E>(private val gson: Gson? = null) {

    /**
     * Converts [element] object to a JSON String OR null in case of any error isa,
     *
     * Even if type [element] has type parameter isa, Note if [element] has no type parameters
     * Use [String.toJsonOrNullJava] instead, since it's more concise syntax isa.
     *
     * For **How to use example** See [GsonConverter]
     *
     * @param element object to convert to JSON-String isa.
     *
     * @return JSON String or null if any problem occurs isa.
     */
    fun toJsonOrNull(element: E?): String? {
        val type = getSuperclassTypeParameter(javaClass)

        return element.toJsonOrNullJavaPrivate(type, gson)
    }

    /**
     * Converts `receiver` object to a JSON String OR throws exception in case of any error isa,
     *
     * Even if type [element] has type parameter isa, Note if [element] has no type parameters
     * Use [String.toJsonOrNullJava] instead, since it's more concise syntax isa.
     *
     * For **How to use example** See [GsonConverter]
     *
     * @param element object to convert to JSON-String isa.
     *
     * @throws RuntimeException in case of any error while converting isa.
     *
     * @return JSON String or throws exception if any problem occurs isa.
     */
    fun toJson(element: E?): String = toJsonOrNull(element)
        ?: throw RuntimeException("Cannot convert $element to JSON String")

    /**
     * Converts [json] to object of type [E], or null in case of any error occurs isa,
     *
     * Even if type [E] has type parameter isa, Note if [E] has no type parameters
     * Use [String.fromJsonOrNullJava] instead, since it's more concise syntax isa.
     *
     * For **How to use example** See [GsonConverter]
     *
     * @return object of type <E> from given JSON String or null if any problem occurs isa.
     */
    fun fromJsonOrNull(json: String?): E? {
        val type = getSuperclassTypeParameter(javaClass)

        return json.fromJsonOrNullJavaPrivate<E>(type, gson)
    }

    /**
     * Converts [json] to object of type [E], or throws exception in case of any error occurs isa,
     *
     * Even if type [E] has type parameter isa, Note if [E] has no type parameters
     * Use [String.fromJsonOrNullJava] instead, since it's more concise syntax isa.
     *
     * For **How to use example** See [GsonConverter]
     *
     * @throws RuntimeException in case of any error while converting isa.
     *
     * @return object of type <E> from given JSON String or throws exception if any problem occurs isa.
     */
    fun fromJson(json: String?): E = fromJsonOrNull(json)
        ?: throw RuntimeException("Cannot convert $json to <E>")

    private fun getSuperclassTypeParameter(subclass: Class<*>): Type {
        val superclass = subclass.genericSuperclass
        if (superclass is Class<*>) {
            throw RuntimeException("Missing type parameter isa.")
        }

        val parameterizedType = superclass as ParameterizedType
        return `$Gson$Types`.canonicalize(parameterizedType.actualTypeArguments[0])
    }

}

private fun <E> E?.toJsonOrNullJavaPrivate(type: Type, gson: Gson?): String? = this?.run {
    val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson

    try { usedGson.toJson(this, type) } catch (e: Exception) { null }
}

private fun <E> String?.fromJsonOrNullJavaPrivate(type: Type, gson: Gson?): E? = this?.run {
    val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson

    try { usedGson.fromJson(this, type) } catch (e: Exception) { null }
}

