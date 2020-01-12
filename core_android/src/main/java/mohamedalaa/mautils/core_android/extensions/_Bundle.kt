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

@file:JvmName("BundleUtils")

package mohamedalaa.mautils.core_android.extensions

import android.os.*
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import androidx.core.os.bundleOf
import org.jetbrains.annotations.Contract
import java.io.Serializable

/** @return true if `receiver` is null or isEmpty */
@Contract("null -> true", pure = true)
fun Bundle?.isNullOrEmpty(): Boolean = this == null || this.isEmpty

/** @return same [Bundle] if `not-null` OR new [Bundle] if `null isa. */
@Contract("!null -> param1; null -> new", pure = true)
fun Bundle?.orEmpty(): Bundle = this ?: Bundle()

/**
 * @return value of given [key] casted to [T] type or `null` if the [key] doesn't exist or if
 * can't be casted isa.
 */
inline fun <reified T> Bundle?.getOrNull(key: String?): T? = this?.get(key) as? T

/**
 * Same as [Bundle.getOrNull] but instead of a `null` result [RuntimeException] is thrown instead isa.
 */
inline fun <reified T> Bundle?.get(key: String?): T = getOrNull<T>(key)
    ?: throw RuntimeException("Cannot get ${T::class}, from key == $key isa.")

private const val BUNDLE_KEY_OBJECTS_SIZE = "BUNDLE_KEY_OBJECTS_SIZE"

/**
 * puts 1 [value] with given [key] in a bundle.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 *
 * @see addValues
 * @see buildBundle
 * @see sizeInBytesOrNull
 */
fun Bundle.addValue(key: String?, value: Any?) {
    when(value) {
        null -> putString(key, null) // Any nullable type will suffice.

        // Primitives
        is Boolean -> putBoolean(key, value)
        is Byte -> putByte(key, value)
        is Char -> putChar(key, value)
        is Double -> putDouble(key, value)
        is Float -> putFloat(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Short -> putShort(key, value)

        // Other
        is CharSequence -> putCharSequence(key, value) // contains ( String )
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)

        // Primitives arrays
        is BooleanArray -> putBooleanArray(key, value)
        is ByteArray -> putByteArray(key, value)
        is CharArray -> putCharArray(key, value)
        is DoubleArray -> putDoubleArray(key, value)
        is FloatArray -> putFloatArray(key, value)
        is IntArray -> putIntArray(key, value)
        is LongArray -> putLongArray(key, value)
        is ShortArray -> putShortArray(key, value)

        // Other arrays
        is Array<*> -> {
            val componentType = value::class.java.componentType
                ?: throw IllegalArgumentException("Illegal value array type null for key \"$key\"")
            @Suppress("UNCHECKED_CAST") // Checked by reflection.
            when {
                Parcelable::class.java.isAssignableFrom(componentType) -> {
                    // contains ( SparseArray )
                    putParcelableArray(key, value as Array<Parcelable>)
                }
                String::class.java.isAssignableFrom(componentType) -> {
                    putStringArray(key, value as Array<String>)
                }
                CharSequence::class.java.isAssignableFrom(componentType) -> {
                    putCharSequenceArray(key, value as Array<CharSequence>)
                }
                Serializable::class.java.isAssignableFrom(componentType) -> {
                    putSerializable(key, value)
                }
                else -> {
                    val valueType = componentType.canonicalName
                    throw IllegalArgumentException(
                        "Illegal value array type $valueType for key \"$key\"")
                }
            }
        }

        // Serializable contains ( Enum, All Arrays )
        is Serializable -> putSerializable(key, value)

        else -> {
            @Suppress("UNCHECKED_CAST")
            if (Build.VERSION.SDK_INT >= 18 && value is IBinder) {
                putBinder(key, value)
            }else if (Build.VERSION.SDK_INT >= 21 && value is Size) {
                putSize(key, value)
            }else if (Build.VERSION.SDK_INT >= 21 && value is SizeF) {
                putSizeF(key, value)
            }else if (value is SparseArray<*> && value as? SparseArray<Parcelable> != null) {
                putSparseParcelableArray(key, value)
            }else {
                val valueType = value.javaClass.canonicalName
                throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
            }
        }
    }
}

/**
 * Exactly same as [buildBundle], but for a [Bundle] instance instead of creating a new one isa.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 *
 * @see addValue
 * @see sizeInBytesOrNull
 */
fun Bundle.addValues(vararg values: Any?) {
    values.forEachIndexed { index, value ->
        val key = index.toString()

        addValue(key, value)
    }

    putInt(BUNDLE_KEY_OBJECTS_SIZE, values.size)
}

/**
 * Same as [addValue] but for several values isa, where [Pair.first] is key and [Pair.second] is the value isa.
 */
fun Bundle.addValuesWithKeys(vararg pairedValues: Pair<String, Any?>)
    = pairedValues.forEach { addValue(it.first, it.second) }

/**
 * Exactly same as [bundleOf] but with one benefit
 *
 * 1- Supports [SparseArray]<[Parcelable]> like regular [Bundle] does, this is currently not in [bundleOf].
 */
fun buildBundleWithKeys(vararg pairedValues: Pair<String, Any?>)
    = Bundle().apply { addValuesWithKeys(*pairedValues) }

/**
 * Returns a new [Bundle] with the given [values] as elements, and keys are the indices
 * so when retrieve it ensure same order of indices isa, to retrieve it use below code.
 * ```
 * // Kotlin Devs, check sample tests in library for more examples isa.
 *
 * val getterBundle = bundle.getterBundle()
 * // Note must be in same order they added in isa.
 * val retrievedInt = getterBundle.get<Int>()
 * val retrievedStringList = getterBundle.getOrNull<List<String>>()
 * val retrievedAnotherInt: Int = getterBundle.get()
 *
 * // Java Devs, check sample tests in library for more examples isa.
 *
 * GetterBundle getterBundle = BundleUtils.getterBundle(bundle);
 * // Note must be in same order they added in isa.
 * int[] primitiveIntArray = getterBundle.getOrNull();
 * String string = getterBundle.get();
 * ```
 *
 * **Notes**
 *
 * It's same as [bundleOf] with 2 additional benefits isa.
 *
 * 1- Supports [SparseArray]<[Parcelable]> like regular [Bundle] does, this is currently not in [bundleOf].
 *
 * 2- No need to create keys for insertion and retrieval of values, but must be in order when retrieving it isa.
 *
 * **More VIP Info**
 *
 * 1- Use buildBundleGson if you use gson module in this library to support **custom classes** as well isa.
 *
 * **Warning**
 *
 * 1- Check out this [Link](https://developer.android.com/guide/components/activities/parcelables-and-bundles)
 * Which shows limit to be saved in a bundle.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 *
 * @see addValues
 * @see sizeInBytesOrNull
 */
fun buildBundle(vararg values: Any?): Bundle
    = Bundle().apply { addValues(*values) }

/** Returns the size of a [Bundle] in Bytes or null if cannot and error occurred while measuring isa. */
fun Bundle.sizeInBytesOrNull() : Int? = runCatching {
    val parcel = Parcel.obtain()
    parcel.writeValue(this)

    val bytes = parcel.marshall()
    parcel.recycle()

    bytes.size
}.getOrNull()

/**
 * - Used to retrieve [Bundle] vales created by [buildBundle] or [addValues] isa.
 */
@JvmSynthetic
fun Bundle.getterBundle(): GetterBundle = GetterBundle(this)

/**
 * - Used to easily retrieve values inserted in a [Bundle] by using [buildBundle] or [addValues] isa.
 * - Note you must respect same order of inserting values when retrieving values from getter functions isa.
 * - To get instance of this class use [Bundle.getterBundle] isa.
 */
class GetterBundle internal constructor(@PublishedApi internal val bundle: Bundle) {

    @PublishedApi
    internal var counter = 0

    /**
     * @return [T] instance after being casted or null if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundle] or [addValues] isa.
     *
     * @see get
     */
    @JvmName("kotlinGetOrNull")
    inline fun <reified T> getOrNull(): T? {
        val key = counter.toString()
        counter++

        return bundle.get(key) as? T
    }

    /**
     * - For java consumer code only, for same fun for kotlin use [getOrNull] isa.
     *
     * @return [T] instance after being casted or null if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundle] or [addValues] isa.
     *
     * @see javaGet
     */
    @Suppress("UNCHECKED_CAST")
    @JvmName("getOrNull")
    fun <T> javaGetOrNull(): T? {
        val key = counter.toString()
        counter++

        return bundle.get(key) as? T
    }

    /**
     * @return [T] instance after being casted or throws [RuntimeException] if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundle] or [addValues] isa.
     *
     * @see getOrNull
     */
    @JvmName("kotlinGet")
    inline fun <reified T> get(): T
        = getOrNull() ?: throw RuntimeException("Cannot get ${T::class} from key `${counter.dec()}`")

    /**
     * - For java consumer code only, for same fun for kotlin use [get] isa.
     *
     * @return [T] instance after being casted or throws [RuntimeException] if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundle] or [addValues] isa.
     *
     * @see javaGetOrNull
     */
    @JvmName("get")
    fun <T> javaGet(): T
        = javaGetOrNull<T>() ?: throw RuntimeException("Cannot get <T> from key `${counter.dec()}`")

}
