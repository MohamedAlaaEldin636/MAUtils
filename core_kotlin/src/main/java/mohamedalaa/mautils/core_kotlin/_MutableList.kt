@file:JvmMultifileClass
@file:JvmName("ListUtils")

package mohamedalaa.mautils.core_kotlin

/**
 * Adds [element] only if not inside `receiver`
 *
 * @return true if added, false otherwise
 */
fun <T> MutableList<T>.addIfNotInside(element: T): Boolean = (element !in this).apply {
    if (this) {
        add(element)
    }
}