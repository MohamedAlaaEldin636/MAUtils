@file:JvmName("GsonUtils")

package mohamedalaa.mautils.mautils_gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.Exception

/**
 * Converts `this JSON String` to object of type [E], or null in case of any error occurs isa,
 * then depending on [check] if true the conversion result is returned, however if false null is
 * returned instead.
 *
 * **Common Usage**
 * 1- when trying to deserialize Object of non null type parameter(s), then using [check] is useful isa.
 *
 * **Notes**
 * 1- this is used for increasing checking a little bit however, you still can just use [fromJsonOrNull]
 * only if you do know what you are doing, otherwise an unexpected behavior might occur isa.
 * 2- Also if [check] is not accurate an unexpected behavior might occur, so either you really
 * sure about using `this` or consider all type params as might be nullable isa.
 *
 * @param check if returns false, then null is returned from `this fun`, however if true
 * then conversion is done, which returns <E> or null in case of any error isa.
 *
 * @see fromJsonOrNullQuick
 * @see fromJsonCheck
 */
inline fun <reified E: Any, reified R: Any> String?.fromJsonOrNullCheck(check: R.() -> Boolean): E? = this?.run {
    val (gson, collectionType) = generateGsonAndCollectionTypeAsPair<E>()

    val generic: E? = try { gson.fromJson(this, collectionType) } catch (e: Exception) { null }

    if ((generic as? R)?.check() == true) generic else null
}

/**
 * Same as [fromJsonOrNullCheck], but instead of retuning null, a [RuntimeException] is thrown instead isa.
 *
 * @throws RuntimeException in case of any error in conversion or [check] is not true.
 */
inline fun <reified E: Any, reified R: Any> String?.fromJsonCheck(check: R.() -> Boolean): E
    = fromJsonOrNullCheck(check) ?: throw RuntimeException("Cannot convert $this to ${E::class}")

/**
 * Same as [fromJsonOrNullCheck], with difference of having 1 type parameter instead of 2 isa.
 *
 * @see [fromJsonQuick]
 */
inline fun <reified E: Any> String?.fromJsonOrNullQuick(check: E.() -> Boolean): E?
    = fromJsonOrNullCheck(check)

/**
 * Same as [fromJsonOrNullQuick], but instead of retuning null, a [RuntimeException] is thrown instead isa.
 *
 * @throws RuntimeException in case of any error in conversion or [check] is not true.
 */
inline fun <reified E: Any> String?.fromJsonQuick(check: E.() -> Boolean): E
    = fromJsonOrNullQuick(check) ?: throw RuntimeException("Cannot convert $this to ${E::class}")

/**
 * Common actions to support [fromJsonOrNullCheck]
 */
enum class JsonCheck {
    NON_NULL_ELEMENTS_LIST,
    NON_NULL_MAP_ENTRIES,
    NON_NULL_MAP_KEYS,
    NON_NULL_MAP_VALUES,
}

/**
 * Same as [fromJsonOrNullCheck], but using [JsonCheck], for built in checks supported.
 */
inline fun <reified E: Any> String?.fromJsonOrNullCheck(jsonCheck: JsonCheck): E? = if (this == null) null else when(jsonCheck) {
    JsonCheck.NON_NULL_ELEMENTS_LIST -> {
        fromJsonOrNullCheck<E, List<*>> { filterNotNull().size == size }
    }
    JsonCheck.NON_NULL_MAP_ENTRIES -> fromJsonOrNullCheck<E, Map<*, *>> {
        keys.toList().filterNotNull().size == size && values.toList().filterNotNull().size == size
    }
    JsonCheck.NON_NULL_MAP_KEYS -> fromJsonOrNullCheck<E, Map<*, *>> {
        keys.toList().filterNotNull().size == size
    }
    JsonCheck.NON_NULL_MAP_VALUES -> fromJsonOrNullCheck<E, Map<*, *>> {
        values.toList().filterNotNull().size == size
    }
}

/**
 * Same as [fromJsonOrNullCheck], but instead of retuning null, a [RuntimeException] is thrown instead isa.
 *
 * @throws RuntimeException in case of any error in conversion or [jsonCheck] is not satisfied.
 */
inline fun <reified E: Any> String?.fromJsonCheck(jsonCheck: JsonCheck): E
    = fromJsonOrNullCheck(jsonCheck) ?: throw RuntimeException("Cannot convert $this to ${E::class}")

/**
 * Converts `this JSON String` to object of type [E], or null in case of any error occurs isa,
 *
 * Note all type parameters are erased due to JVM Erasure so type parameters are considered nullable,
 *
 * so to be ok with kotlin nullability, consider using [fromJsonOrNullCheck] and it's alternatives isa.
 * ```
 * ```
 * **Warning** (Note below example)
 * ```
 * val jsonString = listOf(6, null, 53) // Nullable elements list (Nullable type parameter)
 * // below code makes no error will occur however listOfInts is currently actually List<Int?>?
 * val listOfInts: List<Int>? = jsonString.fromJsonOrNull()
 * // so to workaround it use below approach
 * val listOfInts: List<Int>? = jsonString.fromJsonOrNullCheck<List<Int>?, List<Int?>?> { filterNotNull().size == size }
 * ```
 * And since above is common you can use a shortcut using [fromJsonOrNullCheck]
 * with [JsonCheck.NON_NULL_ELEMENTS_LIST] isa.
 *
 * @return object of type <E> from given JSON String or null if any problem occurs isa.
 *
 * @see fromJson
 * @see toJsonOrNull
 */
inline fun <reified E> String?.fromJsonOrNull(): E? = this?.run {
    val (gson, collectionType) = generateGsonAndCollectionTypeAsPair<E>()

    try { gson.fromJson(this, collectionType) } catch (e: Exception) { null }
}

/**
 * Converts `this JSON String` to object of type [E], or throws exception in case of any error occurs isa,
 *
 * Note all type parameters are erased due to JVM Erasure so type parameters are considered nullable,
 *
 * so to be ok with kotlin nullability, consider using [fromJsonOrNullCheck] and it's alternatives isa.
 * ```
 * ```
 * **Warning** (Note below example)
 * ```
 * val jsonString = listOf(6, null, 53) // Nullable elements list (Nullable type parameter)
 * // below code makes no error will occur however listOfInts is currently actually List<Int?>?
 * val listOfInts: List<Int> = jsonString.fromJson()
 * // so to workaround it use below approach
 * val listOfInts: List<Int> = jsonString.fromJsonOrNullCheck<List<Int>, List<Int?>> { filterNotNull().size == size }
 * ```
 * And since above is common you can use a shortcut using [fromJsonOrNullCheck]
 * with [JsonCheck.NON_NULL_ELEMENTS_LIST] isa.
 *
 * @return object of type <E> from given JSON String or throws exception if any problem occurs isa.
 *
 * @see fromJson
 * @see toJsonOrNull
 */
inline fun <reified E> String?.fromJson(): E = fromJsonOrNull()
    ?: throw RuntimeException("Cannot convert $this to object of type ${E::class}")

/**
 * Converts `receiver` object to a JSON String OR null in case of any error isa.
 *
 * **Benefits**
 * 1- Faster, easier than regular gson approach, and very accurate,
 * However it is the same approach it just eliminates boilerplate code isa.
 * 2- works on lists, maps, custom objects, generics, without the need of you to do anything isa.
 *
 * **Usage**
 * on save instance state, Shared pref, However do not use that approach for these examples
 * unless the object is not huge, custom room conversion etc... isa.
 *
 * @return `receiver` object as JSON String or null if any problem occurs isa.
 *
 * @see toJson
 * @see fromJsonOrNull
 */
inline fun <reified E> E?.toJsonOrNull(): String? = this?.run {
    val (gson, collectionType) = generateGsonAndCollectionTypeAsPair<E>()

    try { gson.toJson(this, collectionType) } catch (e: Exception) { null }
}

/**
 * Converts `receiver` object to a JSON String OR throws exception in case of any error isa.
 *
 * **Benefits**
 * 1- Faster, easier than regular gson approach, and very accurate,
 * However it is the same approach it just eliminates boilerplate code isa.
 * 2- works on lists, maps, custom objects, generics, without the need of you to do anything isa.
 *
 * **Usage**
 * on save instance state, Shared pref, However do not use that approach for these examples
 * unless the object is not huge, custom room conversion etc... isa.
 *
 * @return `receiver` object as JSON String or throws exception if any problem occurs isa.
 *
 * @throws RuntimeException in case of any error occurred during conversion process isa.
 *
 * @see toJsonOrNull
 * @see fromJson
 */
inline fun <reified E> E?.toJson(): String = toJsonOrNull()
    ?: throw RuntimeException("Cannot convert $this to JSON String")

@PublishedApi
internal inline fun <reified E> generateGsonAndCollectionTypeAsPair(): Pair<Gson, Type> {
    val gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .enableComplexMapKeySerialization()
        .create()

    val collectionType = object : TypeToken<E>(){}.type

    return gson to collectionType
}
