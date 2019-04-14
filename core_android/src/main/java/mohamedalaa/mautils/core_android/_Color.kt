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

@file:JvmName("ColorUtils")

package mohamedalaa.mautils.core_android

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorInt
import androidx.core.graphics.alpha
import androidx.databinding.BindingConversion

/** alpha value of color between 0f full transparent to 1f full opaque */
val Int.alphaAsFloat: Float
    get() = Color.alpha(this).toFloat().div(255f)

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
    if (range.start < 0f || range.start > 1f || range.endInclusive < 0f || range.endInclusive > 1f) {
        throw RuntimeException("Incorrect range, since value == $range isa.")
    }else {
        return alphaAsFloat in range
    }
}

/**
 * @return true if near to be black than being white, **Warning** we don't consider alpha,
 * being blended however if alpha is < 50% opaque then this fun return false
 * regardless of rgb value in color isa.
 */
fun Int.isNearToBlack(): Boolean {
    if (alphaAsFloat < 0.5f) {
        return false
    }

    val greyScale = ((0.2126 * Color.red(this))
        + (0.7152 * Color.green(this))
        + (0.0722 * Color.blue(this)))

    return greyScale < 128
}

/**
 * @return [ColorDrawable] with the `receiver` isa,
 *
 * Note it's annotated with [BindingConversion] which means you can use attributes
 * that takes drawable to take int colors instead
 * Ex. android:background="@{isBooleanTrue ? @color/colorAccent : @color/colorPrimary}"
 */
@BindingConversion
fun Int.toDrawable() = ColorDrawable(this)
