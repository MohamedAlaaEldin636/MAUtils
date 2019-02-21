@file:JvmName("MathUtils")

package mohamedalaa.mautils.core_kotlin

import java.math.BigDecimal

/** Rounds to the nearest [decimalPlace] count, with roundingMode == [BigDecimal.ROUND_HALF_UP] */
fun Float.round(decimalPlace: Int): Float {
    var bigDecimal = BigDecimal(this.toString())
    bigDecimal = bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bigDecimal.toFloat()
}

/** Rounds to the nearest [decimalPlace] count, with roundingMode == [BigDecimal.ROUND_HALF_UP] */
fun Double.round(decimalPlace: Int): Float {
    var bigDecimal = BigDecimal(this.toString())
    bigDecimal = bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bigDecimal.toFloat()
}