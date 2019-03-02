@file:JvmName("ColorUtils")

package mohamedalaa.mautils.core_android

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * Converts `receiver` color to same color but with given [alpha] ranging from 0f full transparent
 * to 1f full opaque isa, Ex. 20% opaque use 0.2f isa, if the given [alpha] not within the range
 * then a [RuntimeException] will be thrown isa.
 *
 * @param alpha value from 0f full transparent to 1f full opaque isa, to change returned color's alpha isa.
 * @param dependOnCurrentAlpha if true then provided alpha will depend on previous alpha, so
 * if `receiver`'s alpha was full transparent then no matter what [alpha] value is, the result is
 * full transparent, however if false then only provided [alpha] is considered in conversion isa,
 * default value is false.
 *
 * @return same color as provided but with the specified [alpha] isa.
 *
 * @throws RuntimeException in case if [alpha] not within the rang of 0f to 1f isa.
 *
 * @see inOpaqueRange
 */
@JvmOverloads
@ColorInt
fun Int.addColorAlpha(alpha: Float, dependOnCurrentAlpha: Boolean = false): Int {
    if (alpha < 0f || alpha > 1f) {
        throw RuntimeException("Alpha not withing the range of 0f to 1f, as it is -> $alpha isa.")
    }

    val usedAlpha = if(dependOnCurrentAlpha) {
        alpha.times(Color.alpha(this).toFloat()).toInt()
    }else {
        alpha.times(255f).toInt()
    }

    return Color.argb(
        usedAlpha,
        Color.red(this),
        Color.green(this),
        Color.blue(this))
}

/**
 * Checks if [Color.alpha] is within provided opaque [range] isa.
 *
 * @return true if [range] Ex. 0f..0.5f corresponds to `receiver` color opaque value,
 * which in this Ex. returns true if color is half opaque isa.
 *
 * @throws RuntimeException in case if [range] not within the range of 0f to 1f isa.
 *
 * @see addColorAlpha
 */
fun Int.inOpaqueRange(range: ClosedFloatingPointRange<Float>): Boolean {
    val alpha = Color.alpha(this).toFloat()

    if (range.start < 0f || range.start > 1f || range.endInclusive < 0f || range.endInclusive > 1f) {
        throw RuntimeException("Incorrect range, since value == $range isa.")
    }else {
        return alpha in range
    }
}

/**
 * **Warnings**
 *
 * 1- if color is argb, we don't consider alpha.
 *
 * 2- we don't consider gamma correctness
 *
 * @param color color to be checked isa.
 * @return true if near to be black than being white isa.
 */
fun Int.isNearToBlack(@ColorInt color: Int): Boolean {
    val greyScale = ((0.2126 * Color.red(color))
        + (0.7152 * Color.green(color))
        + (0.0722 * Color.blue(color)))

    return greyScale < 128
}