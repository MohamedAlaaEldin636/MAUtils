@file:JvmName("NumberUtils")

package mohamedalaa.mautils.core_kotlin

/**
 * @return true if number is even
 *
 * @see [Int.isOdd]
 */
fun Int.isEven(): Boolean = rem(2) == 0

/**
 * @return true if number is even
 *
 * @see [Float.isOdd]
 */
fun Float.isEven(): Boolean = rem(2) == 0F

/**
 * @return true if number is even
 *
 * @see [Long.isOdd]
 */
fun Long.isEven(): Boolean = rem(2) == 0L

/**
 * @return true if number is even
 *
 * @see [Double.isOdd]
 */
fun Double.isEven(): Boolean = rem(2) == 0.0

/**
 * @return true if number is odd
 *
 * @see [Int.isEven]
 */
fun Int.isOdd(): Boolean = isEven().not()

/**
 * @return true if number is odd
 *
 * @see [Float.isEven]
 */
fun Float.isOdd(): Boolean = isEven().not()

/**
 * @return true if number is odd
 *
 * @see [Long.isEven]
 */
fun Long.isOdd(): Boolean = isEven().not()

/**
 * @return true if number is odd
 *
 * @see [Double.isEven]
 */
fun Double.isOdd(): Boolean = isEven().not()