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

package mohamedalaa.mautils.core_android

import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
internal fun getRippleDrawable(baseDrawable: Drawable, rippleColor: Int): Drawable {
    return if (rippleColor.inOpaqueRange(0f..0.5f)) {
        // It means it is full or half transparent, so ripple won't be clear isa, so WHITE mask is better to be used isa.
        RippleDrawable(ColorStateList.valueOf(rippleColor), baseDrawable, ColorDrawable(Color.WHITE))
    }else {
        RippleDrawable(ColorStateList.valueOf(rippleColor), baseDrawable, null)
    }
}

/** As mush as possible behaves Acc to material design guidelines isa. */
internal fun getStateListDrawable(baseDrawable: Drawable,
                                 @ColorInt rippleColor: Int,
                                 creationFunOfBaseDrawable: (Int) -> Drawable): Drawable {
    val stateListDrawable = StateListDrawable()

    // -- Disabled
    val disabledDrawable = creationFunOfBaseDrawable(rippleColor.addColorAlpha(0.38f))
    stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled),
        getDrawableOnTopOfDrawable(baseDrawable, disabledDrawable))

    // -- Hover
    val hoverDrawable = creationFunOfBaseDrawable(rippleColor.addColorAlpha(0.08f))
    stateListDrawable.addState(intArrayOf(android.R.attr.state_hovered),
        getDrawableOnTopOfDrawable(baseDrawable, hoverDrawable))

    // -- Focused
    val focusDrawable = creationFunOfBaseDrawable(rippleColor.addColorAlpha(0.24f))
    stateListDrawable.addState(intArrayOf(android.R.attr.state_focused),
        getDrawableOnTopOfDrawable(baseDrawable, focusDrawable))

    // -- Selected
    val selectedDrawable = creationFunOfBaseDrawable(rippleColor.addColorAlpha(0.16f))
    stateListDrawable.addState(intArrayOf(android.R.attr.state_selected),
        getDrawableOnTopOfDrawable(baseDrawable, selectedDrawable))

    // -- Activated
    val activatedDrawable = creationFunOfBaseDrawable(rippleColor.addColorAlpha(0.24f))
    stateListDrawable.addState(intArrayOf(android.R.attr.state_activated),
        getDrawableOnTopOfDrawable(baseDrawable, activatedDrawable))

    // -- Pressed
    val pressedDrawable = creationFunOfBaseDrawable(rippleColor.addColorAlpha(0.32f))
    stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed),
        getDrawableOnTopOfDrawable(baseDrawable, pressedDrawable))

    // -- Dragged
    val draggedDrawable = creationFunOfBaseDrawable(rippleColor.addColorAlpha(0.16f))
    stateListDrawable.addState(intArrayOf(android.R.attr.state_drag_can_accept),
        getDrawableOnTopOfDrawable(baseDrawable, draggedDrawable))

    // -- Base
    stateListDrawable.addState(intArrayOf(), baseDrawable)

    return stateListDrawable
}

/** @return drawable [aboveDrawable] being above of [baseDrawable] isa. */
internal fun getDrawableOnTopOfDrawable(baseDrawable: Drawable,
                                       aboveDrawable: Drawable): LayerDrawable {
    val layers = arrayOf(baseDrawable, aboveDrawable)

    return LayerDrawable(layers)
}

internal fun getSolidRoundRect(@ColorInt solidColor: Int, @Px radiusInPx: Int): Drawable {
    val outerRadii = floatArrayOf(
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat()
    )

    val roundRectShape = RoundRectShape(outerRadii, null, null)
    val shapeDrawable = ShapeDrawable(roundRectShape)
    shapeDrawable.paint.color = solidColor

    return shapeDrawable
}


internal fun getSolidCircle(@ColorInt solidColor: Int): Drawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.OVAL
    gradientDrawable.setColor(solidColor)

    return gradientDrawable
}

internal fun getDrawableWith(creationFunOfBaseDrawable: (Int) -> Drawable,
                             @ColorInt solidColor: Int,
                             @ColorInt rippleColor: Int? = null,
                             forcePreLollipop: Boolean = false): Drawable {
    val baseDrawable = creationFunOfBaseDrawable(solidColor)

    return if (rippleColor == null) {
        baseDrawable
    }else {
        getCompatRippleDrawable(baseDrawable, rippleColor, creationFunOfBaseDrawable, forcePreLollipop)
    }
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
internal fun getDrawableWith(baseDrawable: Drawable,
                             creationFunOfRippleDrawable: (Int) -> Drawable,
                             @ColorInt rippleColor: Int? = null,
                             forcePreLollipop: Boolean = false): Drawable {
    return if (rippleColor == null) {
        baseDrawable
    }else {
        getCompatRippleDrawable(baseDrawable, rippleColor, creationFunOfRippleDrawable, forcePreLollipop)
    }
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
internal fun getGradientRoundRect(@ColorInt colors: IntArray,
                                  @Px radiusInPx: Int,
                                  orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM): Drawable? {
    if (colors.size < 2) {
        return null
    }

    val outerRadii = floatArrayOf(
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat(),
        radiusInPx.toFloat()
    )

    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.RECTANGLE
    gradientDrawable.colors = colors
    gradientDrawable.cornerRadii = outerRadii
    gradientDrawable.orientation = orientation

    return gradientDrawable
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
internal fun getGradientCircle(@ColorInt colors: IntArray,
                               orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM): Drawable? {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.shape = GradientDrawable.OVAL
    gradientDrawable.colors = colors
    gradientDrawable.orientation = orientation

    return gradientDrawable
}
