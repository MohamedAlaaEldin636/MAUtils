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

@file:JvmName("IterableUtils")

package mohamedalaa.mautils.core_kotlin.extensions

/**
 * Same as [Iterable.sumByDouble] but for [Float] type.
 *
 * @return the sum of all values produced by [selector] function applied to each element in the collection.
 *
 * @see sumByLong
 */
inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/**
 * Same as [Iterable.sumByDouble] but for [Float] type.
 *
 * @return the sum of all values produced by [selector] function applied to each element in the collection.
 *
 * @see sumByFloat
 */
inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

/**
 * @return true if element is inside `receiver`, false if `receiver` is null or element isn't inside it.
 *
 * @see indexOfOrNull
 */
operator fun <T> Iterable<T>?.contains(element: T): Boolean {
    if (this == null) {
        return false
    }

    return indexOf(element) >= 0
}

/**
 * @return first index of [element], or `null` if not inside `receiver`,
 * Gives more convenience for kotlin devs due to nullability checks isa.
 *
 * @see Iterable.contains
 */
fun <T> Iterable<T>.indexOfOrNull(element: T): Int?
    = indexOf(element).run { if (this == -1) null else this }

/**
 * Accumulates transformed value with [transform] starting with the first element
 * and applying [operation] from left to right ( after transforming right with [transform] )
 * to current accumulator value and each element.
 *
 * @return null in case of empty `receiver` or result from [operation] or [transform] whichever closer.
 *
 * @see mapOne
 * @see List.isEmpty
 * @see Iterable.reduce
 * @see Iterable.fold
 */
inline fun <T, R> Iterable<T>.mapOneOrNull(operation: (old: R, new: R) -> R, transform: (T) -> R): R? {
    var element: R? = null
    for (it in this) {
        element = element?.run {
            operation(this, transform(it))
        } ?: transform(it)
    }

    return element
}

/**
 * Same as [mapOneOrNull], but instead of null in case of empty list a [RuntimeException] is thrown instead.
 *
 * @throws RuntimeException if `receiver` is empty.
 *
 * @see mapOneOrNull
 * @see List.isEmpty
 * @see Iterable.reduce
 * @see Iterable.fold
 */
inline fun <T, R> Iterable<T>.mapOne(mapped: (old: R, new: R) -> R, block: (T) -> R): R
    = mapOneOrNull(mapped, block) ?: throw RuntimeException("List was empty")

/**
 * @return first element that is instance of [R] after being casted to [R], or null if not found isa.
 *
 * @see Iterable.filterIsInstance
 * @see firstIsInstance
 */
inline fun <reified R> Iterable<*>.firstIsInstanceOrNull(): R? {
    for (it in this) {
        if (it is R) return it
    }

    return null
}

/**
 * @return same as [firstIsInstanceOrNull] but throws [RuntimeException] in case of a null result isa.
 *
 * @throws RuntimeException if no item in the list can be of instance [R].
 *
 * @see Iterable.firstIsInstanceOrNull
 * @see Iterable.filterIsInstance
 * @see Iterable.allIndicesOf
 */
inline fun <reified R> Iterable<*>.firstIsInstance(): R
    = firstIsInstanceOrNull() ?: throw RuntimeException("Cannot apply predicate")

/**
 * @return all indices of [element] in `receiver`, or empty list if element doesn't exist isa.
 *
 * @see Iterable.firstIsInstanceOrNull
 */
fun <T> Iterable<T>.allIndicesOf(element: T): List<Int> = mapIndexedNotNull { index, it ->
    if (it == element) index else null
}

/**
 * Returns a list of pairs of each two other adjacent elements in `receiver`, See below examples.
 * ```
 * Given -> Result
 * [] -> []
 * [1] -> [(1, null]
 * [2, 3, 5, 6] -> [(2, 3), (5, 6)]
 * ```
 *
 * @see Iterable.zipWithNext
 * @see Iterable.partitionIndexed
 * @see Iterable.zipFullReceiver
 */
fun <T> Iterable<T>.pairedIteration(): List<Pair<T, T?>> {
    val pairOfLists = partitionIndexed { index, _ -> index.isEven() }

    return pairOfLists.first.zipFullReceiver(pairOfLists.second)
}

/**
 * - Returns a list of pairs built from the elements of `receiver` and [other] iterables
 * with the same index.
 *
 * - The returned list has length of the **highest** iterable, not shortest like in [Iterable.zip].
 *
 * @see zipFullReceiver
 * @see zipFullOther
 * @see pairedIteration
 * @see partitionIndexed
 * @see zipSameSize
 */
infix fun <T, R> Iterable<T>.zipFull(other: Iterable<R>): List<Pair<T?, R?>> {
    val firstIterator = iterator()
    val secondIterator = other.iterator()

    return generateSequence {
        val partOne = firstIterator.safeNext()
        val partTwo = secondIterator.safeNext()

        if (partOne == null && partTwo == null) null else partOne to partTwo
    }.toList()
}

/**
 * @return Same as [zipFull] but returned list has length of `receiver`.
 *
 * @see zipFullOther
 * @see zipFull
 */
infix fun <T, R> Iterable<T>.zipFullReceiver(other: Iterable<R>): List<Pair<T, R?>> {
    return if (count() > other.count()) {
        zipFull(other).mapNotNull { (it.first ?: return@mapNotNull null) to it.second }
    }else {
        zip(other)
    }
}

/**
 * @return Same as [zipFull] but returned list has length of [other].
 *
 * @see zipFullReceiver
 * @see zipFull
 */
infix fun <T, R> Iterable<T>.zipFullOther(other: Iterable<R>): List<Pair<T?, R>> {
    return if (count() > other.count()) {
        zipFull(other).mapNotNull { it.first to (it.second ?: return@mapNotNull null) }
    }else {
        zip(other)
    }
}

/**
 * @return Ensure both [Iterable]s (`receiver` and [other]) have same size or throws [RuntimeException]
 * then calls [Iterable.zip]
 *
 * @see zipFull
 */
infix fun <T, R> Iterable<T>.zipSameSize(other: Iterable<R>): List<Pair<T, R>> {
    if (count() != other.count()) throw RuntimeException("Different sizes")
    return zip(other)
}

/** @return Same as [Iterable.partition] but with indices as well. */
inline fun <T> Iterable<T>.partitionIndexed(predicate: (index: Int, element: T) -> Boolean): Pair<List<T>, List<T>> {
    val first = mutableListOf<T>()
    val second = mutableListOf<T>()
    for ((index, element) in withIndex()) {
        if (predicate(index, element)) {
            first.add(element)
        }else {
            second.add(element)
        }
    }
    return Pair(first, second)
}
