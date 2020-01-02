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

package mohamedalaa.mautils.gson

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.mautils.gson_annotation.GsonAnnotationConstants
import mohamedalaa.mautils.gson.internal.JsonDeserializerForSealedClasses
import mohamedalaa.mautils.gson.internal.JsonSerializerForSealedClasses
import java.lang.reflect.Type
import kotlin.Exception
import mohamedalaa.mautils.gson.java.GsonConverter

/**
 * Converts `this JSON String` to object of type [E], or null in case of any error occurs isa.
 *
 * **First Warning** (Compile time checks, Note below example)
 * ```
 * val jsonString = listOf(6, null, 53) // Nullable elements list (Nullable type parameter)
 * // below code makes no error will occur however listOfInts is currently actually List<Int?>?
 * val listOfInts: List<Int>? = jsonString.fromJsonOrNullJava()
 * // so to workaround it, either be sure 100% type parameter is not null or BETTER use nullable type parameters isa.
 * val listOfInts: List<Int?>? = jsonString.fromJsonOrNullJava()
 * ```
 *
 * **Second Warning** (Nested Type Parameters)
 *
 * Using any nested type parameters is valid and will be converted safely, however that's safe
 *
 * only if nested type parameters of object are invariant or so deep in nesting, So if they are variance annotated as (in) OR (out)
 *
 * then using a java workaround is a must or conversion will make unexpected results,
 *
 * The workaround is to create a Java class (Must be in Java class) and use [GsonConverter] isa.
 * ```
 * // Inside HelperJavaClass.java
 * public class HelperJavaClass {
 *      public static CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>> getCustomWithTypeParam(
 *              String jsonString) {
 *          return new GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>>(){}.fromJsonJava(jsonString);
 *      }
 * }
 *
 * // Then use it easily in your kotlin code as below
 * // KotlinClass.kt
 * val retrievedAny = HelperJavaClass.getCustomWithTypeParam(jsonString)
 * ```
 * Also note that using a nested type parameters containing a non-nesting non-invariant type parameters is completely valid and safe isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @return object of type <E> from given JSON String or null if any problem occurs isa.
 *
 * @see fromJson
 * @see toJsonOrNull
 */
inline fun <reified E> String?.fromJsonOrNull(gson: Gson? = null): E? = this?.run {
    E::class.objectInstance?.apply {
        return@run this
    }

    val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson
    //val collectionType = generateCollectionType<E>()

    //try { usedGson.fromJson(this, collectionType) } catch (e: Exception) { null }

    try { object : GsonConverter<E>(usedGson){}.fromJsonOrNull(this) } catch (e: Exception) { null }
    //     try { object : GsonConverter<E>(usedGson){}.toJsonOrNull(this) } catch (e: Exception) { null }
}

/**
 * Exactly same as [fromJsonOrNull], the only difference that instead of returning null
 * a [RuntimeException] is thrown instead, so returned type is guaranteed to be not null isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @return object of type <E> from given JSON String OR throws exception if any problem occurs isa.
 *
 * @throws RuntimeException in case of any error occurred during conversion process isa.
 *
 * @see fromJson
 * @see toJsonOrNull
 */
inline fun <reified E> String?.fromJson(gson: Gson? = null): E = fromJsonOrNull(gson)
    ?: throw RuntimeException("Cannot convert $this to object of type ${E::class}")

/**
 * Converts `receiver` object to a JSON String OR null in case of any error isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @return `receiver` object as JSON String OR null if any problem occurs isa.
 *
 * @see toJson
 * @see fromJsonOrNull
 */
inline fun <reified E> E?.toJsonOrNull(gson: Gson? = null): String? = this?.run {
    val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson
    //val collectionType = generateCollectionType<E>()

    //try { usedGson.toJson(this, collectionType) } catch (e: Exception) { null }

    // todo use below but after checking that it won't work in nested type variences isa.
    // or to be more exact any nested of type that is either interface or t2reban open/sealed/abstract isa.
    try { object : GsonConverter<E>(usedGson){}.toJsonOrNull(this) } catch (e: Exception) { null }
}
/*inline fun <reified E> E?.toJsonOrNull2(gson: Gson? = null): String? = this?.run {
    val usedGson = gson?.addTypeAdapters() ?: privateGeneratedGson
    val collectionType = generateCollectionType<E>()

    object : GsonConverter<E>(){}.toJsonOrNull(this)

    try { usedGson.toJson(this, collectionType) } catch (e: Exception) { null }
}*/

/**
 * Exactly same as [toJsonOrNull], the only difference that instead of returning null
 * a [RuntimeException] is thrown instead, so returned type is guaranteed to be not null isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [privateGeneratedGson] isa.
 *
 * @return `receiver` object as JSON String OR throws exception if any problem occurs isa.
 *
 * @throws RuntimeException in case of any error occurred during conversion process isa.
 *
 * @see toJsonOrNull
 * @see fromJson
 */
inline fun <reified E> E?.toJson(gson: Gson? = null): String = toJsonOrNull(gson)
    ?: throw RuntimeException("Cannot convert $this to JSON String")

// ---- Internal fun isa.

@Suppress("UNCHECKED_CAST")
internal val allAnnotatedClasses: List<Class<*>>? by lazy {
    runCatching {
        val jClass = Class.forName(GsonAnnotationConstants.generatedMASealedAbstractOrInterfaceFullName)

        val method = jClass.declaredMethods[0]

        val listOfClassesFullNames = method.invoke(null) as List<String>

        listOfClassesFullNames.mapNotNull {
            runCatching { Class.forName(it) }.getOrNull()
        }
    }.getOrNull()
}

internal val allAnnotatedClassesAsString: List<String> by lazy {
    allAnnotatedClasses.orEmpty().map { it.toStringOrEmpty() }
}

/**
 * - Default [Gson] object used for serialization/deserialization, the generated object is created by below code isa.
 * ```
 * GsonBuilder()
 *      .serializeNulls()
 *      .setLenient()
 *      .enableComplexMapKeySerialization()
 *      .create()
 * ```
 * - Used lazy property instead of a fun so that computation is only done once isa.
 */
@PublishedApi
internal val privateGeneratedGson: Gson by lazy {
    val gsonBuilder = GsonBuilder()

    gsonBuilder.addTypeAdapters()

    gsonBuilder
        .serializeNulls()
        .setLenient()
        .enableComplexMapKeySerialization()
        .create()
}

@PublishedApi
internal inline fun <reified E> generateCollectionType(): Type
    = object : TypeToken<E>(){}.type

@PublishedApi
internal fun Gson.addTypeAdapters(): Gson {
    return newBuilder().apply {
        addTypeAdapters()
    }.create()
}

// ---- Private fun isa.

private fun GsonBuilder.addTypeAdapters() {
    allAnnotatedClasses?.forEach {
        registerTypeAdapter(it, JsonSerializerForSealedClasses())
        registerTypeAdapter(it, JsonDeserializerForSealedClasses())
    }
}
