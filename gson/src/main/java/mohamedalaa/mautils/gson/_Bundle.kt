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
import mohamedalaa.mautils.core_android.extensions.*
import mohamedalaa.mautils.gson.java.GsonConverter
import mohamedalaa.mautils.gson.java.fromJsonOrNullJava

private const val BUNDLE_KEY_OBJECTS_SIZE = "BUNDLE_KEY_OBJECTS_SIZE"

/**
 * - Used in case you want any object to be added as json string in Bundle while using
 * [buildBundleGson], [addValuesGson] or [addValueGson]
 *
 * **Benefits**
 * 1. Sometimes String is lighter in being saved in bundle than a serializable object
 * Ex. Imagine a huge complicated object and you want to save a small list of it in bundle
 * using any of the 3 fun above will save list as serializable which makes huge size
 * while there is specific limit for bundle in android see
 * [Link](https://developer.android.com/guide/components/activities/parcelables-and-bundles)
 *
 * **Usage**
 * ```
 * val bundle = buildBundleGson(
 *      customObject.forceUsingJsonInBundle(),
 *      12,
 *      "other objects"
 * )
 *
 * // To retrieve it
 * val getterBundleGson = bundle.getterBundleGson()
 * val r1 = getterBundleGson<CustomObject>()
 * assertEquals(customObject, r1) // true
 * ```
 */
inline fun <reified E> E?.forceUsingJsonInBundle(gson: Gson? = null) = ForceUsingJsonInBundle(toJsonOrNull(gson))

data class ForceUsingJsonInBundle @PublishedApi internal constructor (val jsonString: String?)

/**
 * Same as [buildBundleGson] but for 1 value only, And return same value of `receiver` but with the
 * provided key/value pair isa.
 *
 * @see addValuesGson
 * @see buildBundleGson
 * @see getterBundleGson
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
 * Same as [buildBundleGsonForced] but for 1 value only, And return same value of `receiver`
 * but with the provided key/value pair isa.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmOverloads
fun Bundle.addValueGsonForced(key: String?, value: Any?, gson: Gson? = null) {
    putString(key, value.toJsonOrNull(gson))
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
 * val getterBundleGson = bundle.getterBundleGson()
 * // Note must be in same order they added in isa.
 * val retrievedCustomObject = getterBundleGson.get<CustomObject>()
 * val retrievedListOfCustomObject = getterBundleGson.get<List<CustomObject>>()
 *
 * // Java Devs
 *
 * GetterBundleGson getterBundleGson = BundleUtils.javaGetterBundleGson(bundle);
 * // Note must be in same order they added in isa.
 * int[] primitiveIntArray = getterBundleGson.getOrNull();
 * CustomObject customObject = getterBundleGson.get();
 * ```
 *
 * ### Notes
 * - It's same as androidx.core.os.bundleOf with 3 additional benefits isa.
 * 1. Supports [SparseArray]<[Parcelable]> like regular [Bundle] does, this is currently not in androidx.core.os.bundleOf.
 * 2. No need to create keys for insertion and retrieval of values, but must be in order when retrieving it isa.
 * 3. Supports any custom classes that can be serialized using [toJson] isa.
 *
 * ### Limitations
 * 1. If the custom type needs [GsonConverter] **( For java consumer code only )** then it needs
 * to be done manually so pass value as string generated from [GsonConverter.toJson] isa,
 * And to retrieve it either call [GetterBundleGson.javaGetOrNull] with [GsonConverter] instance
 * **OR** get value as [String] `getterBundleGson.get<String>()` then use [GsonConverter.fromJson]
 * to get the value isa.
 *
 * ### How it works
 * 1. If forced to be converted by json via [forceUsingJsonInBundle] then [toJsonOrNull] is used isa.
 * 2. Else then we try to use [Bundle.addValue] and in case of any error [toJsonOrNull] is used instead.
 *
 * ### When
 * - It's obvious that this and [buildBundleGsonForced] are so similar but when to use each one
 *
 * 1. In case a lot of variables needs regular [buildBundle] and a fallback is to use [toJsonOrNull]
 * use this fun, **Note** no [RuntimeException] will be thrown in any error since `null` will be
 * put instead, **Another Note** you can use [forceUsingJsonInBundle] with any number of params to
 * force using [toJsonOrNull] for these params instead of trying [Bundle.addValue].
 *
 * 2. In case All of the variables are custom objects and need [toJsonOrNull] then use
 * [buildBundleGsonForced] so also note no [RuntimeException] will be thrown and in that case
 * `null` will be put instead isa.
 *
 * 3. It's recommended to use this fun with [forceUsingJsonInBundle] when needed
 * since gson not able to serialize everything for example .toJson works with [Double] very good
 * but with other numbers, deserialization will not be correct.
 *
 * @see addValueGson
 * @see startActivityBundleGson
 * @see startActivityBundleGsonForced
 */
@JvmOverloads
fun buildBundleGson(vararg values: Any?, gson: Gson? = null): Bundle
    = Bundle().apply { addValuesGson(*values, gson = gson) }

/**
 * Same as [buildBundleGson] but with several differences See **When** section in documentation
 * of [buildBundleGson] isa.
 *
 * @see startActivityBundleGson
 * @see startActivityBundleGsonForced
 */
@JvmOverloads
fun buildBundleGsonForced(vararg values: Any?, gson: Gson? = null): Bundle
    = Bundle().apply { addValuesGsonForced(*values.map { it }.toTypedArray(), gson = gson) }

////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      Bundle Gson With Keys
//
////////////////////////////////////////////////////////////////////////////////////////////////////

private fun Bundle.privateAddValuesGsonWithKeys(vararg pairedValues: Pair<String, Any?>, gson: Gson?, forced: Boolean) {
    pairedValues.forEach {
        if (forced) {
            addValueGsonForced(it.first, it.second, gson)
        }else {
            addValueGson(it.first, it.second, gson)
        }
    }
}

/**
 * Combination of [addValuesGson] && [Bundle.addValuesWithKeys]
 *
 * @see addValuesGsonForcedWithKeys
 * @see buildBundleGsonWithKeys
 * @see buildBundleGsonForcedWithKeys
 */
@JvmOverloads
fun Bundle.addValuesGsonWithKeys(vararg pairedValues: Pair<String, Any?>, gson: Gson? = null)
    = privateAddValuesGsonWithKeys(pairedValues = *pairedValues, gson = gson, forced = false)

/**
 * Combination of [addValuesGsonForced] && [Bundle.addValuesWithKeys]
 */
@JvmOverloads
fun Bundle.addValuesGsonForcedWithKeys(vararg pairedValues: Pair<String, Any?>, gson: Gson? = null)
    = privateAddValuesGsonWithKeys(pairedValues = *pairedValues, gson = gson, forced = true)

/**
 * Combination of [buildBundleGson] && [buildBundleWithKeys]
 *
 * @see addValuesGsonWithKeys
 * @see addValuesGsonForcedWithKeys
 * @see addValuesGsonForced
 * @see buildBundleGsonForcedWithKeys
 */
@JvmOverloads
fun buildBundleGsonWithKeys(vararg pairedValues: Pair<String, Any?>, gson: Gson? = null): Bundle
    = Bundle().apply { addValuesGsonWithKeys(*pairedValues, gson = gson) }

/**
 * Combination of [buildBundleGsonForced] && [buildBundleWithKeys]
 */
@JvmOverloads
fun buildBundleGsonForcedWithKeys(vararg pairedValues: Pair<String, Any?>, gson: Gson? = null): Bundle
    = Bundle().apply { addValuesGsonForcedWithKeys(*pairedValues, gson = gson) }

fun Bundle?.getterBundleGson(): GetterBundleGson = GetterBundleGson(orEmpty())

class GetterBundleGson internal constructor(@PublishedApi internal val bundle: Bundle) {

    @PublishedApi
    internal var counter = 0

    /**
     * @return [T] instance after being casted if wan't saved as a json string or after being
     * converted by [fromJsonOrNull] if saved as json string or `null` if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundleGson], [addValuesGson],
     * [buildBundleGsonForced] or [addValuesGsonForced] isa.
     *
     * @param T type you want to get an instance of it isa.
     *
     * @see get
     */
    @JvmName("kotlinGetOrNull")
    inline fun <reified T> getOrNull(gson: Gson? = null): T? {
        val key = counter.toString()
        counter++

        return when (val any = bundle.get(key)) {
            is String -> when {
                T::class == String::class -> any as? T
                else -> any.fromJsonOrNull<T>(gson)
            }
            else -> any as? T
        }
    }

    /**
     * Same as [GetterBundleGson.getOrNull] but instead of returning `null` [RuntimeException]
     * is thrown instead isa.
     */
    @JvmName("kotlinGet")
    inline fun <reified T> get(gson: Gson? = null): T
        = getOrNull<T>(gson) ?: throw RuntimeException("Cannot get ${T::class} from key `${counter.dec()}`")

    /**
     * - You can use either no-args overloaded method, or just provide [elementClass] OR provide
     * [gsonConverter], And in any of theses 3 cases you can optionally pass [gson] instance if you
     * want.
     * - When to use each approach, Well that depend on [T],Check below example for more explanation isa.
     * ```
     * // Custom Class
     * BackupReminderOrAction backupReminderOrAction1 = new BackupReminderOrAction(
     *      "Some String",
     *      3231
     * );
     * // Custom With Type Param
     * CustomWithTypeParam<Float, String> pairs = new CustomWithTypeParam<>(
     *      3f, "a", "b", "c"
     * );
     *
     * Bundle bundle = GsonBundleUtils.buildBundleGson(
     *      "abc", // Type which is normally supported by `Bundle`
     *      backupReminderOrAction1,
     *      pairs
     * );
     *
     * GetterBundleGson getterBundleGson = GsonBundleUtils.getterBundleGson(bundle);
     *
     * // Value normally supported by `Bundle` -> No arguments need to be provided isa.
     * String string = getterBundleGson.getOrNull();
     * assertEquals(string, "abc");
     *
     * // Custom Class -> You have to provide the class as a parameter isa.
     * BackupReminderOrAction r1 = getterBundleGson.getOrNull(BackupReminderOrAction.class);
     * assertEquals(backupReminderOrAction1, r1);
     *
     * // Custom With Type Param -> You have to provide GsonConverter of the class as a parameter isa.
     * CustomWithTypeParam<Float, String> r2 = getterBundleGson.getOrNull(
     *      null,
     *      new GsonConverter<CustomWithTypeParam<Float, String>>() {}
     * );
     * assertEquals(pairs, r2);
     * // All assertions succeeded el7.
     * ```
     */
    @Suppress("UNCHECKED_CAST")
    @JvmOverloads
    @JvmName("getOrNull")
    fun <T> javaGetOrNull(elementClass: Class<T>? = null, gsonConverter: GsonConverter<T>? = null, gson: Gson? = null): T? {
        val key = counter.toString()
        counter++

        return when (val any = bundle.get(key)) {
            is String -> when {
                gsonConverter != null -> gsonConverter.fromJsonOrNull(any)
                elementClass == String::class.java -> any as? T
                elementClass != null -> any.fromJsonOrNullJava(elementClass, gson)
                else -> any as? T
            }
            else -> any as? T
        }
    }

    /** Same as [javaGetOrNull] but throws [RuntimeException] instead of retuning `null` isa. */
    @JvmOverloads
    @JvmName("get")
    fun <T> javaGet(elementClass: Class<T>? = null, gsonConverter: GsonConverter<T>? = null, gson: Gson? = null): T
        = javaGetOrNull(elementClass, gsonConverter, gson) ?: throw RuntimeException("Cannot get <T> from key `${counter.dec()}`")

}
