@file:JvmName("BundleUtils")

package mohamedalaa.mautils.core_android

import android.os.*
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import androidx.core.os.bundleOf
import java.io.Serializable

/**
 * @return true if `receiver` is null or isEmpty
 */
fun Bundle?.isNullOrEmpty(): Boolean = this == null || this.isEmpty

inline fun <reified T> Bundle?.getOrNull(key: String?): T? = this?.get(key) as? T

inline fun <reified T> Bundle?.get(key: String?): T = getOrNull<T>(key)
    ?: throw RuntimeException("Cannot get ${T::class}, from key == $key isa.")

private const val BUNDLE_KEY_OBJECTS_SIZE = "BUNDLE_KEY_OBJECTS_SIZE"

/**
 * Exactly same as [buildBundle], but for a [Bundle] instance instead of creating a new one isa.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
fun Bundle.addValues(vararg values: Any?) {
    values.forEachIndexed { index, value ->
        val key = index.toString()

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
 * **More VIP Info***
 *
 * 1- Use buildBundleGson of mautils_gson module to support custom classes, which can be
 * serialized/deserialized using gson as well isa.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 *
 * @see addValues
 */
fun buildBundle(vararg values: Any?): Bundle
    = Bundle().apply { addValues(*values.map { it }.toTypedArray()) }

@JvmSynthetic
fun Bundle.getKGetterBundle() = KGetterBundle(this)

fun Bundle.getJGetterBundle() = JGetterBundle(this)

class JGetterBundle internal constructor(private val bundle: Bundle) {

    private var counter = 0

    @Suppress("UNCHECKED_CAST")
    fun <T> getOrNull(): T? {
        val key = counter.toString()
        counter++

        return bundle.get(key) as? T
    }

    fun <T> get(): T
        = getOrNull() ?: throw RuntimeException("Cannot get <T> from key `${counter.dec()}`")

}

class KGetterBundle internal constructor(@PublishedApi internal val bundle: Bundle) {

    @PublishedApi
    internal var counter = 0

    inline fun <reified T> getOrNull(): T? {
        val key = counter.toString()
        counter++

        return bundle.get(key) as? T
    }

    inline fun <reified T> get(): T
        = getOrNull() ?: throw RuntimeException("Cannot get ${T::class} from key `${counter.dec()}`")

}

