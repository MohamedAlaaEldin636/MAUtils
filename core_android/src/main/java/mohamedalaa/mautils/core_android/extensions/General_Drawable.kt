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

@file:JvmName("General_DrawableUtils")

package mohamedalaa.mautils.core_android.extensions

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.*
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.RequiresApi

/**
 * Create rounded rectangle drawable with ripple if [rippleColor] is not `null`, Note for android
 * API >= [Build.VERSION_CODES.LOLLIPOP] then RippleDrawable is Used isa, otherwise
 * [StateListDrawable] with [LayerDrawable] for other APIs isa.
 *
 * @param solidColor color of the shape (base state -> means on no clicks or focus or other interactions isa).
 * @param radiusInPx radius in pixels of the rounded rectangle, if you only have radius in DP
 * then use [Context.dpToPx] isa.
 * @param rippleColor ripple color used for states, Ex. pressed view isa.
 * @param forcePreLollipop if you want to get result of drawable in pre lollipop devices, defaults to false isa.
 *
 * @return drawable of rounded rect shape with [rippleColor] if not null isa.
 *
 * @see getCompatRippleDrawable
 * @see getRoundRect
 */
@JvmOverloads
fun getRoundRect(@ColorInt solidColor: Int,
                 @Px radiusInPx: Int,
                 @ColorInt rippleColor: Int? = null,
                 forcePreLollipop: Boolean = false): Drawable {
    val creationFunOfBaseDrawable: (Int) -> Drawable = {
        getSolidRoundRect(
            it,
            radiusInPx
        )
    }

    return getDrawableWith(
        creationFunOfBaseDrawable,
        solidColor,
        rippleColor,
        forcePreLollipop
    )
}

/**Create rounded rectangle drawable with ripple if [rippleColor] is not null, Note for android
 * API >= [Build.VERSION_CODES.LOLLIPOP] then RippleDrawable is Used isa, otherwise
 * [StateListDrawable] with [LayerDrawable] for other APIs isa.
 *
 * @param colors Colors array must contain at least 2 colors, to create fragient color isa.
 * @param radiusInPx radius in pixels of the rounded rectangle, if you only have radius in DP
 * then use [Context.dpToPx] isa.
 * @param orientation orientation of gradient colors isa.
 * @param rippleColor ripple color used for states, Ex. pressed view isa.
 * @param forcePreLollipop if you want to get result of drawable in pre lollipop devices, defaults to false isa.
 *
 * @return drawable of rounded rect shape with [rippleColor] if not null isa.
 *
 * @throws RuntimeException in case [colors] doesn't have at least 2 colors isa.
 *
 * @see getCompatRippleDrawable
 * @see getRoundRect
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@JvmOverloads
fun getRoundRect(@ColorInt colors: IntArray,
                 @Px radiusInPx: Int,
                 orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
                 @ColorInt rippleColor: Int? = null,
                 forcePreLollipop: Boolean = false): Drawable {
    val baseDrawable: Drawable = getGradientRoundRect(
        colors,
        radiusInPx,
        orientation
    )
        ?: throw RuntimeException("colors array for gradient drawable must have at least 2 colors")
    val anotherInstanceOfBaseDrawableWithFilledWhiteColor: Drawable =
        getSolidRoundRect(Color.WHITE, radiusInPx)

    val creationFunOfRippleDrawable: (Int) -> Drawable = {
        getSolidRoundRect(
            it,
            radiusInPx
        )
    }

    return getDrawableWith(
        baseDrawable,
        anotherInstanceOfBaseDrawableWithFilledWhiteColor,
        creationFunOfRippleDrawable,
        rippleColor,
        forcePreLollipop,
        colors.any { it.inOpaqueRange(0f..0.5f) })
}

/**
 * Create circle drawable with ripple if [rippleColor] is not null, Note for android
 * API >= [Build.VERSION_CODES.LOLLIPOP] then RippleDrawable is Used isa, otherwise
 * [StateListDrawable] with [LayerDrawable] for other APIs isa.
 *
 * @param solidColor color of the shape (base state -> means on no clicks or focus or other interactions isa).
 * then use [Context.dpToPx] isa.
 * @param rippleColor ripple color used for states, Ex. pressed view isa.
 * @param forcePreLollipop if you want to get result of drawable in pre lollipop devices, defaults to false isa.
 *
 * @return drawable of circle shape with [rippleColor] if not null isa.
 *
 * @see getCompatRippleDrawable
 * @see getCircle
 */
@JvmOverloads
fun getCircle(@ColorInt solidColor: Int,
              @ColorInt rippleColor: Int? = null,
              forcePreLollipop: Boolean = false): Drawable {
    val creationFunOfBaseDrawable: (Int) -> Drawable = {
        getSolidCircle(
            it
        )
    }

    return getDrawableWith(
        creationFunOfBaseDrawable,
        solidColor,
        rippleColor,
        forcePreLollipop
    )
}

/**
 * Create circle drawable with ripple if [rippleColor] is not null, Note for android
 * API >= [Build.VERSION_CODES.LOLLIPOP] then RippleDrawable is Used isa, otherwise
 * [StateListDrawable] with [LayerDrawable] for other APIs isa.
 *
 * @param colors Colors array must contain at least 2 colors, to create fragient color isa.
 * @param orientation orientation of gradient colors isa.
 * @param rippleColor ripple color used for states, Ex. pressed view isa.
 * @param forcePreLollipop if you want to get result of drawable in pre lollipop devices, defaults to false isa.
 *
 * @return drawable of circle shape with [rippleColor] if not null isa.
 *
 * @throws RuntimeException in case [colors] doesn't have at least 2 colors isa.
 *
 * @see getCompatRippleDrawable
 * @see getCircle
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@JvmOverloads
fun getCircle(@ColorInt colors: IntArray,
              orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
              @ColorInt rippleColor: Int? = null,
              forcePreLollipop: Boolean = false): Drawable {
    val baseDrawable: Drawable = getGradientCircle(
        colors,
        orientation
    )
        ?: throw RuntimeException("colors array for gradient drawable must have at least 2 colors")
    val anotherInstanceOfBaseDrawableWithFilledWhiteColor: Drawable =
        getSolidCircle(Color.WHITE)

    val creationFunOfRippleDrawable: (Int) -> Drawable = {
        getSolidCircle(
            it
        )
    }

    return getDrawableWith(
        baseDrawable,
        anotherInstanceOfBaseDrawableWithFilledWhiteColor,
        creationFunOfRippleDrawable,
        rippleColor,
        forcePreLollipop,
        colors.any { it.inOpaqueRange(0f..0.5f) })
}

/**
 * @param anotherInstanceOfBaseDrawableWithFilledWhiteColor ensure same drawable as [baseDrawable]
 * but with [Color.WHITE] as it's filled color no matter what the shape is isa, Ex. [ColorDrawable], [getCircle], [getRoundRect] isa.
 *
 * @return if API >= [Build.VERSION_CODES.LOLLIPOP] RippleDrawable is used,
 * otherwise a [StateListDrawable] with states is returned instead isa.
 */
@JvmOverloads
fun getCompatRippleDrawable(baseDrawable: Drawable,
                            anotherInstanceOfBaseDrawableWithFilledWhiteColor: Drawable,
                            @ColorInt rippleColor: Int,
                            creationFunOfBaseDrawable: (Int) -> Drawable,
                            forcePreLollipop: Boolean = false,
                            needsRippleMask: Boolean = false): Drawable {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !forcePreLollipop) {
        getRippleDrawable(
            baseDrawable,
            anotherInstanceOfBaseDrawableWithFilledWhiteColor,
            rippleColor,
            needsRippleMask
        )
    }else {
        getStateListDrawable(
            baseDrawable,
            rippleColor,
            creationFunOfBaseDrawable
        )
    }
}