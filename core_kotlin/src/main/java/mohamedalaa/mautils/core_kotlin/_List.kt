@file:JvmMultifileClass
@file:JvmName("ListUtils")

package mohamedalaa.mautils.core_kotlin

import kotlin.random.Random

/** Performs given [block] only if list is not null and not empty isa. */
inline fun <T, R: List<T>> R?.performIfNotNullNorEmpty(block: R.() -> Unit) {
    if (this != null && this.isNotEmpty()) {
        this.block()
    }
}

/**
 * @param mapped fun invoked in each iteration only if `this list` is at least have a [List.size] of 2 or more.
 * when reached 2nd item, old value is last [block] and new is current [block] result,
 * then from 3rd time and above, old value is the value computed from [mapped] and new is the current [block] result isa,
 * @param block fun that takes each item in list as a param in the fun then returns a different result isa.
 *
 * @return null in case of empty list or object transformed by [mapped] or [block] whichever closer.
 *
 * @see [mapOne]
 * @see [List.isEmpty]
 */
inline fun <T, R> List<T>.mapOneOrNull(mapped: (old: R, new: R) -> R, block: (T) -> R): R? {
    var element: R? = null
    forEach {
        element = element?.run {
            mapped(this, block(it))
        } ?: block(it)
    }

    return element
}

/**
 * Same as [mapOneOrNull], but instead of null in case of empty list a [RuntimeException] is thrown instead isa.
 *
 * @return object transformed by [mapped] or [block] whichever closer.
 *
 * @throws RuntimeException if `this list` is empty
 *
 * @see [mapOneOrNull]
 * @see [List.isEmpty]
 */
inline fun <T, R> List<T>.mapOne(mapped: (old: R, new: R) -> R, block: (T) -> R): R
        = mapOneOrNull(mapped, block) ?: throw RuntimeException("List was empty")

/** @return same as [List.contains] with benefit that it returns false if `list` is null,
 * so using operator `in` with nullable value can be achieved isa. */
operator fun <T> List<T>?.contains(element: T): Boolean = this?.contains(element) ?: false

/**
 * @return random `item` in `this list`, or null if the list is null or empty.
 *
 * @see [randomGet]
 */
fun <T> List<T>?.randomGetOrNull(): T? = this?.run {
    if (size > 0) this[Random.nextInt(size)] else null
}

/**
 * @return same as [randomGetOrNull] but instead of returning null, a [RuntimeException] is thrown instead isa.
 *
 * @throws RuntimeException in case the receiver is null or empty.
 */
fun <T> List<T>?.randomGet(): T = randomGetOrNull() ?: throw RuntimeException("List is ${if (this == null) "null" else "empty"}")

/**
 * @return first index that is instance of [R], or null otherwise isa.
 *
 * @see [List.filterIsInstance]
 * @see [firstIsInstance]
 */
inline fun <reified R> List<*>.firstIsInstanceOrNull(): R? {
    forEach {
        return (it as? R) ?: return@forEach
    }

    return null
}

/**
 * @return same as [firstIsInstanceOrNull] but throws exception in case of a null result isa.
 *
 * @see [List.filterIsInstance]
 *
 * @throws RuntimeException if no item in the list can be of instance [R].
 */
inline fun <reified R> List<*>.firstIsInstance(): R = firstIsInstanceOrNull() ?: throw RuntimeException("Cannot apply predicate")

/** @return all indices of same element param in list, or empty list if element doesn't exist isa. */
fun <T> List<T>.getAllIndicesOf(element: T): List<Int> = mapIndexedNotNull { index, it ->
    if (it == element) index else null
}

/**
 * Returns a [ArrayList] filled with all elements of `this list` isa.
 */
fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}
