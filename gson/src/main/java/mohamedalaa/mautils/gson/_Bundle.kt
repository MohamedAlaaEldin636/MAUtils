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

@file:JvmName("GsonBundleUtils")

package mohamedalaa.mautils.gson

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import com.google.gson.Gson
import mohamedalaa.mautils.core_android.addValue
import mohamedalaa.mautils.core_android.buildBundle
import mohamedalaa.mautils.gson.java.GsonConverter
import mohamedalaa.mautils.gson.java.fromJsonOrNullJava

private const val BUNDLE_KEY_OBJECTS_SIZE = "BUNDLE_KEY_OBJECTS_SIZE"

/**
 * Used in case you want any object to be added as string in Bundle while using
 * [buildBundleGson], [addValuesGson] or [addValueGson]
 *
 * **Benefits**
 * 1. Sometimes String is lighter in being saved in bundle than a serializable object
 * Ex. Imagine a huge complicated object and you want to save a small list of it in bundle
 * using any of the 3 fun above will save list as serializable which makes huge size
 * while there is specific limit for bundle in android see [Link](https://developer.android.com/guide/components/activities/parcelables-and-bundles)
 *
 * **Usage**
 * buildBundleGson(
 *      customObject.forceUsingJsonInBundle(),
 *      12,
 *      "other objects"
 * )
 */
inline fun <reified E> E?.forceUsingJsonInBundle(gson: Gson? = null) = ForceUsingJsonInBundle(toJsonOrNull(gson))

data class ForceUsingJsonInBundle @PublishedApi internal constructor (val jsonString: String?)

/**
 * Same as [buildBundleGson] but for 1 value only isa.
 *
 * @see addValuesGson
 * @see buildBundleGson
 * @see getKGetterBundleGson
 * @see getJGetterBundleGson
 */
@JvmOverloads
fun Bundle.addValueGson(key: String?, value: Any?, gson: Gson? = null) {
    if (value is ForceUsingJsonInBundle) {
        putString(key, value.jsonString)
    }else {
        runCatching {
            addValue(key, value)
        }.getOrElse {
            putString(key, value.toJsonOrNull(gson))
        }
    }
}

/**
 * Same as [buildBundleGsonForced] but for 1 value only isa.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmOverloads
fun Bundle.addValueGsonForced(key: String?, value: Any?, gson: Gson? = null) {
    val stringValue = value.toJsonOrNull(gson)

    stringValue?.apply {
        putString(key, this)
    } ?: addValue(key, value)
}

private fun Bundle.privateAddValuesGson(vararg values: Any?, gson: Gson?, forced: Boolean) {
    values.forEachIndexed { index, value ->
        val key = index.toString()

        if (forced) {
            addValueGsonForced(key, value, gson)
        }else {
            addValueGson(key, value, gson)
        }
    }

    putInt(BUNDLE_KEY_OBJECTS_SIZE, values.size)
}

/**
 * Exactly same as [buildBundleGson], but for a [Bundle] instance instead of creating a new one isa.
 *
 * @see addValueGson
 */
@JvmOverloads
fun Bundle.addValuesGson(vararg values: Any?, gson: Gson? = null)
    = privateAddValuesGson(values = *values, gson = gson, forced = false)

/**
 * Exactly same as [buildBundleGsonForced], but for a [Bundle] instance instead of creating a new one isa.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmOverloads
fun Bundle.addValuesGsonForced(vararg values: Any?, gson: Gson? = null)
    = privateAddValuesGson(values = *values, gson = gson, forced = true)

/**
 * Returns a new [Bundle] with the given [values] as elements, and keys are the indices
 * so when retrieve it ensure same order of indices isa, to retrieve it use below code.
 * ```
 * // Kotlin Devs
 *
 * val getterBundle = bundle.getKGetterBundleGson()
 * // Note must be in same order they added in isa.
 * val retrievedCustomObject = getterBundleGson.get<CustomObject>()
 * val retrievedListOfCustomObject = getterBundleGson.get<List<CustomObject>>()
 *
 * // Java Devs
 *
 * JGetterBundle getterBundle = BundleUtils.getJGetterBundle(bundle);
 * // Note must be in same order they added in isa.
 * int[] primitiveIntArray = getterBundle.getOrNull();
 * String string = getterBundle.get();
 * ```
 *
 * ### Notes
 * - It's same as androidx.core.os.bundleOf with 3 additional benefits isa.
 * 1. Supports [SparseArray]<[Parcelable]> like regular [Bundle] does, this is currently not in androidx.core.os.bundleOf.
 * 2. No need to create keys for insertion and retrieval of values, but must be in order when retrieving it isa.
 * 3. Supports any custom classes that can be serialized using [toJson] isa.
 *
 * ### Limitations
 * 1. If needs [GsonConverter] then it needs to be done manually so pass value as string
 * generated from [GsonConverter.toJson], Note in retrieval this limitation does not exist isa.
 *
 * ### How it works
 * 1. If forced to be converted by json via [forceUsingJsonInBundle] then it's string value is used
 * 2. trying to use [Bundle.addValue] and in case of any error then [toJsonOrNull] is used instead.
 *
 * ### When
 * It's obvious that this and [buildBundleGsonForced] are so similar but when to use each one
 *
 * 1. in case a lot of variables needs regular [buildBundle] and a fallback is to use [toJsonOrNull]
 * use this fun, Note no [RuntimeException] will be thrown in any error since null will be put instead,
 * Another VIP Note is there can be exception to go firstly with [toJsonOrNull] in this case
 * by using [forceUsingJsonInBundle]
 *
 * 2. while [buildBundleGsonForced] is exactly the opposite if most of variables needs [toJsonOrNull]
 * and fallback to it if null is returned from [toJsonOrNull] is [buildBundle], Note so
 * [IllegalArgumentException] will be thrown due to [buildBundle] if an error occurred,
 * CANNOT Use [forceUsingJsonInBundle] otherwise unexpected behaviours will occur without errors isa.
 *
 * @see addValueGson
 */
@JvmOverloads
fun buildBundleGson(vararg values: Any?, gson: Gson? = null): Bundle
    = Bundle().apply { addValuesGson(*values.map { it }.toTypedArray(), gson = gson) }

/**
 * Same as [buildBundleGson] but with several differences See **When** section in doc of [buildBundleGson]
 */
@JvmOverloads
fun buildBundleGsonForced(vararg values: Any?, gson: Gson? = null): Bundle
    = Bundle().apply { addValuesGsonForced(*values.map { it }.toTypedArray(), gson = gson) }

class KGetterBundleGson internal constructor(@PublishedApi internal val bundle: Bundle) {

    @PublishedApi
    internal var counter = 0

    inline fun <reified T> getOrNull(gsonConverter: GsonConverter<T>? = null, gson: Gson? = null): T? {
        val key = counter.toString()
        counter++

        val any = bundle.get(key)
        return if (any is String && gsonConverter != null) {
            gsonConverter.fromJsonOrNull(any)
        }else {
            (if (any is String) any.fromJsonOrNull<T>(gson) else null) ?: bundle.get(key) as? T
        }
    }

    inline fun <reified T> get(gsonConverter: GsonConverter<T>? = null, gson: Gson? = null): T
        = getOrNull(gsonConverter, gson) ?: throw RuntimeException("Cannot get ${T::class} from key `${counter.dec()}`")

}

@JvmSynthetic
fun Bundle.getKGetterBundleGson() = KGetterBundleGson(this)

class JGetterBundleGson internal constructor(private val bundle: Bundle) {

    private var counter = 0

    @Suppress("UNCHECKED_CAST")
    @JvmOverloads
    fun <T> getOrNull(elementClass: Class<T>? = null, gson: Gson? = null): T? {
        val key = counter.toString()
        counter++

        val any = bundle.get(key)
        return if (any is String && elementClass != null) {
            // Custom object added by gson isa.
            any.fromJsonOrNullJava(elementClass, gson)
        }else {
            bundle.get(key) as? T
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getOrNullWithConverter(gsonConverter: GsonConverter<T>): T? {
        val key = counter.toString()
        counter++

        val any = bundle.get(key)
        return if (any is String) {
            // Custom object added by gson isa.
            gsonConverter.fromJsonOrNull(any)
        }else {
            bundle.get(key) as? T
        }
    }

    @JvmOverloads
    fun <T> get(elementClass: Class<T>? = null, gson: Gson? = null): T
        = getOrNull(elementClass, gson) ?: throw RuntimeException("Cannot get <T> from key `${counter.dec()}`")

    fun <T> getWithConverter(gsonConverter: GsonConverter<T>): T
        = getOrNullWithConverter(gsonConverter) ?: throw RuntimeException("Cannot get <T> from key `${counter.dec()}`")

}

fun Bundle.getJGetterBundleGson() = JGetterBundleGson(this)
