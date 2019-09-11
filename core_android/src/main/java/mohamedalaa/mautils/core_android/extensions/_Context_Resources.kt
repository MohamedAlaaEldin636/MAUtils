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

@file:JvmMultifileClass
@file:JvmName("ContextUtils")

package mohamedalaa.mautils.core_android.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

/** @return [Drawable] from drawable res if valid otherwise null will be returned isa. */
@JvmOverloads
fun Context.getDrawableFromResOrNull(@DrawableRes drawableRes: Int, @ColorInt tintColorInt: Int? = null): Drawable? = runCatching {
    if (tintColorInt == null) {
        ContextCompat.getDrawable(this, drawableRes)
    }else {
        val drawable = ContextCompat.getDrawable(this, drawableRes)?.mutate()
        drawable.tintCompat(tintColorInt)

        drawable
    }
}.getOrNull()

/**
 * @return same as [getDrawableFromResOrNull] but throws exception instead of returning null isa.
 */
@JvmOverloads
fun Context.getDrawableFromRes(@DrawableRes drawableRes: Int, @ColorInt tintColorInt: Int? = null): Drawable
    = getDrawableFromResOrNull(drawableRes, tintColorInt) ?: throw RuntimeException("invalid drawable res provided")

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