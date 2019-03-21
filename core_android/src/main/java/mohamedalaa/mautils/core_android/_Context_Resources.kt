@file:JvmMultifileClass
@file:JvmName("ContextUtils")

package mohamedalaa.mautils.core_android

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat

// ==> Layout

/** Layout inflater from `this context`, used [LayoutInflater.from] */
val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

/**
 * Inflates a layout from res
 *
 * @param parent provide [ViewGroup.LayoutParams] to the returned root view, default is `null`
 * @param attachToRoot if true then the returned view will be attached to [parent] if not `null`,
 * default is false isa.
 *
 * @return rootView in the provided [layoutRes] isa.
 */
@JvmOverloads
fun Context.inflateLayout(@LayoutRes layoutRes: Int,
                          parent: ViewGroup? = null,
                          attachToRoot: Boolean = false): View {
    return layoutInflater.inflate(layoutRes, parent, attachToRoot)
}

// ==> Color

/** @return color from res color with compatibility in consideration. */
@ColorInt
fun Context.getColorFromRes(@ColorRes res: Int): Int = ContextCompat.getColor(this, res)

/** @return color from attr res that refers to a color, Ex. R.attr.colorPrimary */
@ColorInt
fun Context.getColorFromAttrRes(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)

    return ContextCompat.getColor(this, typedValue.resourceId)
}

// ==> Drawable

/** @return Drawable object from drawable res */
fun Context.getDrawableFromResOrNull(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

/**
 * @return same as [getDrawableFromResOrNull] but throws exception instead of returning null isa.
 *
 * @throws [RuntimeException] in case of invalid drawable res isa.
 */
fun Context.getDrawableFromRes(@DrawableRes drawableRes: Int): Drawable
        = getDrawableFromResOrNull(drawableRes) ?: throw RuntimeException("invalid drawable res provided")

// ==> Dimen

/** @return converted dimen from dp (density-independent pixels) to px (pixels) */
fun Context.dpToPx(dp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics)

/** @return converted dimen from sp (scale-independent pixels) to px (pixels) */
fun Context.spToPx(sp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    sp.toFloat(),
    resources.displayMetrics)