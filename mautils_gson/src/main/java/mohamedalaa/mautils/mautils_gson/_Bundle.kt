@file:JvmName("GsonBundleUtils")

package mohamedalaa.mautils.mautils_gson

import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import com.google.gson.Gson
import mohamedalaa.mautils.mautils_gson.java.GsonConverter
import mohamedalaa.mautils.mautils_gson.java.fromJsonOrNull
import java.io.Serializable

private const val BUNDLE_KEY_OBJECTS_SIZE = "BUNDLE_KEY_OBJECTS_SIZE"

/**
 * Exactly same as [buildBundleGson], but for a [Bundle] instance instead of creating a new one isa.
 *
 * @throws RuntimeException When a value cannot be serialized with [toJson] isa.
 */
fun Bundle.addValuesGson(vararg values: Any?, gson: Gson? = null) {
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

                if (componentType == null) {
                    putString(key, value.toJson(gson))

                    return@forEachIndexed
                }

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
                        putString(key, value.toJson(gson))

                        return@forEachIndexed
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
                    putString(key, value.toJson(gson))

                    return@forEachIndexed
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
 * val getterBundle = bundle.getKGetterBundleGson()
 * // Note must be in same order they added in isa.
 * val retrievedCustomObject = getterBundleGson.get<CustomObject>()
 * val retrievedListOfCustomObject = getterBundleGson.get<List<CustomObject>>()
 *
 * // Java Devs, check sample tests in library for more examples isa. todo doc
 *
 * JGetterBundle getterBundle = BundleUtils.getJGetterBundle(bundle);
 * // Note must be in same order they added in isa.
 * int[] primitiveIntArray = getterBundle.getOrNull();
 * String string = getterBundle.get();
 * ```
 *
 * **Notes**
 * - It's same as androidx.core.os.bundleOf with 3 additional benefits isa.
 * 1. Supports [SparseArray]<[Parcelable]> like regular [Bundle] does, this is currently not in androidx.core.os.bundleOf.
 * 2. No need to create keys for insertion and retrieval of values, but must be in order when retrieving it isa.
 * 3. Supports any custom classes that can be serialized using [toJson] isa.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmOverloads
fun buildBundleGson(vararg values: Any?, gson: Gson? = null): Bundle
    = Bundle().apply { addValuesGson(*values.map { it }.toTypedArray(), gson = gson) }

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
            any.fromJsonOrNull(elementClass, gson)
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
