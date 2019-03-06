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

/**
 * Moves element from index [fromIndex] to index [toIndex] isa.
 */
fun <E> MutableList<E>.move(fromIndex: Int, toIndex: Int) {
    val element = elementAt(fromIndex)

    removeAt(fromIndex)
    add(toIndex, element)
}

/**
 * Swap element at [firstIndex] with element at [secondIndex] isa.
 */
fun <E> MutableList<E>.swap(firstIndex: Int, secondIndex: Int) {
    val firstElement = elementAt(firstIndex)
    val secondElement = elementAt(secondIndex)

    add(firstIndex, secondElement)
    removeAt(firstIndex.inc())

    add(secondIndex, firstElement)
    removeAt(secondIndex.inc())
}