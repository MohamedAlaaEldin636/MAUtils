@file:JvmName("General_DrawableUtils")

package mohamedalaa.mautils.core_android

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.*
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.RequiresApi

/**
 * Create rounded rectangle drawable with ripple if [rippleColor] is not null, Note for android
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
    val creationFunOfBaseDrawable: (Int) -> Drawable = { getSolidRoundRect(it, radiusInPx) }

    return getDrawableWith(creationFunOfBaseDrawable, solidColor, rippleColor, forcePreLollipop)
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
    val baseDrawable: Drawable = getGradientRoundRect(colors, radiusInPx, orientation)
        ?: throw RuntimeException("colors array for gradient drawable must have at least 2 colors")

    val creationFunOfRippleDrawable: (Int) -> Drawable = { getSolidRoundRect(it, radiusInPx) }

    return getDrawableWith(baseDrawable, creationFunOfRippleDrawable, rippleColor, forcePreLollipop)
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
    val creationFunOfBaseDrawable: (Int) -> Drawable = { getSolidCircle(it) }

    return getDrawableWith(creationFunOfBaseDrawable, solidColor, rippleColor, forcePreLollipop)
}

/**Create circle drawable with ripple if [rippleColor] is not null, Note for android
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
    val baseDrawable: Drawable = getGradientCircle(colors, orientation)
        ?: throw RuntimeException("colors array for gradient drawable must have at least 2 colors")

    val creationFunOfRippleDrawable: (Int) -> Drawable = { getSolidCircle(it) }

    return getDrawableWith(baseDrawable, creationFunOfRippleDrawable, rippleColor, forcePreLollipop)
}

/**
 * @return if API >= [Build.VERSION_CODES.LOLLIPOP] RippleDrawable is used,
 * otherwise a [StateListDrawable] with states is returned instead isa.
 */
@JvmOverloads
fun getCompatRippleDrawable(baseDrawable: Drawable,
                            @ColorInt rippleColor: Int,
                            creationFunOfBaseDrawable: (Int) -> Drawable,
                            forcePreLollipop: Boolean = false): Drawable {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !forcePreLollipop) {
        getRippleDrawable(baseDrawable, rippleColor)
    }else {
        getStateListDrawable(baseDrawable, rippleColor, creationFunOfBaseDrawable)
    }
}