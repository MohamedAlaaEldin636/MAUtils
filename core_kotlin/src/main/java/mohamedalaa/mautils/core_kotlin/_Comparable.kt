@file:JvmName("ComparableUtils")

package mohamedalaa.mautils.core_kotlin

/**
 * @return min value of both of the comparable(s)
 *
 * @see [minOrNull]
 */
infix fun <T: Comparable<T>> T.min(other: T): T = if (this < other) this else other

/**
 * @return min value of both of the comparable(s), or null if they are equal
 *
 * @see [min]
 */
infix fun <T: Comparable<T>> T.minOrNull(other: T): T? = if (this == other) null else min(other)

/**
 * @return max value of both of the comparable(s), or null if they are equal
 *
 * @see [maxOrNull]
 */
infix fun <T: Comparable<T>> T.max(other: T): T = if (this > other) this else other

/**
 * @return max value of both of the comparable(s) isa.
 *
 * @see [max]
 */
infix fun <T: Comparable<T>> T.maxOrNull(other: T): T? = if (this == other) null else max(other)