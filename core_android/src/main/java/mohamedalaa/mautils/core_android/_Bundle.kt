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

package mohamedalaa.mautils.core_android

import android.os.*
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import androidx.core.os.bundleOf
import java.io.Serializable

/** @return true if `receiver` is null or isEmpty */
fun Bundle?.isNullOrEmpty(): Boolean = this == null || this.isEmpty

/** @return [T] instance corresponding to given [key] after being casted or null if not found isa. */
inline fun <reified T> Bundle?.getOrNull(key: String?): T? = this?.get(key) as? T

/** @return [T] instance corresponding to given [key] after being casted or throws [RuntimeException] if not found isa. */
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
 * @see sizeInBytes
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
 * @see sizeInBytes
 */
fun Bundle.addValues(vararg values: Any?) {
    values.forEachIndexed { index, value ->
        val key = index.toString()

        addValue(key, value)
    }

    putInt(BUNDLE_KEY_OBJECTS_SIZE, values.size)
}

/**
 * Returns a new [Bundle] with the given [values] as elements, and keys are the indices
 * so when retrieve it ensure same order of indices isa, to retrieve it use below code.
 * ```
 * // Kotlin Devs, check sample tests in library for more examples isa.
 *
 * val getterBundle = bundle.getKGetterBundle()
 * // Note must be in same order they added in isa.
 * val retrievedInt = getterBundle.get<Int>()
 * val retrievedStringList = getterBundle.getOrNull<List<String>>()
 *
 * // Java Devs, check sample tests in library for more examples isa.
 *
 * JGetterBundle getterBundle = BundleUtils.getJGetterBundle(bundle);
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
 * 1- Use buildBundleGson of gson module to support **custom classes** as well,
 *
 * which can be serialized/deserialized using gson as well isa.
 *
 * **Warning**
 *
 * 1- Check out this [Link](https://developer.android.com/guide/components/activities/parcelables-and-bundles)
 * Which shows limit to be saved in a bundle.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 *
 * @see addValues
 * @see sizeInBytes
 */
fun buildBundle(vararg values: Any?): Bundle
    = Bundle().apply { addValues(*values.map { it }.toTypedArray()) }

/** Returns the size of a [Bundle] in Bytes or null if cannot and error occurred while measuring isa. */
fun Bundle.sizeInBytes() : Int? = kotlin.runCatching {
    val parcel = Parcel.obtain()
    parcel.writeValue(this)

    val bytes = parcel.marshall()
    parcel.recycle()

    bytes.size
}.getOrNull()

/**
 * Used by kotlin devs only, for same functionality for java devs see [KGetterBundle]
 *
 * Used to retrieve [Bundle] vales created by [buildBundle] or [addValues] isa.
 */
@JvmSynthetic
fun Bundle.getKGetterBundle() = KGetterBundle(this)

/**
 * Used by java devs only, for same functionality for kotlin devs see [JGetterBundle]
 *
 * Used to retrieve [Bundle] vales created by [buildBundle] or [addValues] isa.
 */
fun Bundle.getJGetterBundle() = JGetterBundle(this)

class JGetterBundle internal constructor(private val bundle: Bundle) {

    private var counter = 0

    /**
     * @return [T] instance after being casted or null if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundle] or [addValues] isa.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> getOrNull(): T? {
        val key = counter.toString()
        counter++

        return bundle.get(key) as? T
    }

    /**
     * @return [T] instance after being casted or throws [RuntimeException] if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundle] or [addValues] isa.
     */
    fun <T> get(): T
        = getOrNull() ?: throw RuntimeException("Cannot get <T> from key `${counter.dec()}`")

}

class KGetterBundle internal constructor(@PublishedApi internal val bundle: Bundle) {

    @PublishedApi
    internal var counter = 0

    /**
     * @return [T] instance after being casted or null if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundle] or [addValues] isa.
     */
    inline fun <reified T> getOrNull(): T? {
        val key = counter.toString()
        counter++

        return bundle.get(key) as? T
    }

    /**
     * @return [T] instance after being casted or throws [RuntimeException] if not found isa,
     *
     * Note you must respect same order of inserting values via [buildBundle] or [addValues] isa.
     */
    inline fun <reified T> get(): T
        = getOrNull() ?: throw RuntimeException("Cannot get ${T::class} from key `${counter.dec()}`")

}
