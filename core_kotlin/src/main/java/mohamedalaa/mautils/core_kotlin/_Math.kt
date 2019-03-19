@file:JvmName("MathUtils")

package mohamedalaa.mautils.core_kotlin

import java.math.BigDecimal

/**
 * Rounds to the nearest [decimalPlace] count, with roundingMode == [BigDecimal.ROUND_HALF_UP]
 * ```
 * val value1 = 0.33333334f.round(3)
 * val value2 = 0.66666667f.round(3)
 * println(value1) // 0.333
 * println(value2) // 0.667
 * ```
 *
 * @see Double.round
 */
fun Float.round(decimalPlace: Int): Float {
    var bigDecimal = BigDecimal(this.toString())
    bigDecimal = bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bigDecimal.toFloat()
}

/**
 * Rounds to the nearest [decimalPlace] count, with roundingMode == [BigDecimal.ROUND_HALF_UP]
 *
 * @see Float.round
 */
fun Double.round(decimalPlace: Int): Float {
    var bigDecimal = BigDecimal(this.toString())
    bigDecimal = bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bigDecimal.toFloat()
}