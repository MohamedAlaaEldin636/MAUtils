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

@file:JvmName("ViewUtils")

package mohamedalaa.mautils.core_android.extensions

import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.postDelayed
import androidx.core.view.postOnAnimationDelayed

/**
 * @return [View.getX] for this and all of it's parent viewGroups as a summation isa.
 *
 * @see getRootX
 * @see getRootCenterPointF
 * @see listOfParentViews
 */
fun View.getRootX(): Float {
    var value = x
    for (view in listOfParentViews()) {
        value += view.x
    }

    return value
}

/**
 * @return [View.getY] for this and all of it's parent viewGroups as a summation isa.
 *
 * @see getRootX
 * @see getRootCenterPointF
 * @see listOfParentViews
 */
fun View.getRootY(): Float {
    var value = y
    for (view in listOfParentViews()) {
        value += view.y
    }

    return value
}

/**
 * @return [getRootX] plus half of `this` view's width as [PointF.x],
 * and [getRootY] plus half of `this` view's width as [PointF.y]
 */
fun View.getRootCenterPointF(): PointF {
    val x = getRootX() + width.toFloat().div(2)
    val y = getRootY() + height.toFloat().div(2)

    return PointF(x, y)
}

/**
 * @return list containing all parents([ViewGroup]s) of this `receiver` isa.
 *
 * @see getRootCenterPointF
 */
fun View.listOfParentViews(): MutableList<ViewGroup> {
    val listOfViews = mutableListOf<ViewGroup>()

    var view: View? = this
    while (view != null) {
        view = (view.parent as? ViewGroup)?.apply {
            listOfViews.add(this)
        }
    }

    return listOfViews
}

/**
 * - Performs [View.performClick], but with [View.isPressed] effect so pressed effect appears,
 * as if user clicked on the view isa.
 * - Useful to show ripple effect so it appears like user clicked it isa.
 *
 * @param initialDelayInMillis delay before performing [View.performClick] isa.
 * @param pressedEffectDurationInMillis duration where [View.isPressed] is kept true, after [View.performClick] isa.
 */
@JvmOverloads
fun View.performClickWithPressed(initialDelayInMillis: Long = 0L, pressedEffectDurationInMillis: Long = 250L) {
    val block: () -> Unit = {
        performClick()

        isPressed = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postOnAnimationDelayed(pressedEffectDurationInMillis) {
                isPressed = false
            }
        }else {
            postDelayed(pressedEffectDurationInMillis) {
                isPressed = false
            }
        }
    }

    if (initialDelayInMillis > 0L) {
        postDelayed(initialDelayInMillis, block)
    }else {
        block()
    }
}

/**
 * - Compatible version for [View.setBackground] for API < 16 works as getter/setter
 * to use as the background, or `null` to remove/get the background isa.
 *
 * - Easier approach than repeating checks of [Build.VERSION] to toggle between [View.setBackground]
 * & [View.setBackgroundDrawable]
 *
 * @see setBackgroundTintCompat
 */
var View.backgroundCompat: Drawable?
    get() = background
    set(value) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = value
    }else {
        @Suppress("DEPRECATION")
        setBackgroundDrawable(value)
    }

/**
 * - Sets background tintColorFilter color according to given [color], if is `null` then
 * nothing happens to tintColorFilter color isa,
 * - Sets background tintColorFilter mode according to given [porterDuffMode], if is `null`
 * then nothing happens to tintColorFilter mode isa.
 * - So this fun is useless if both params are `null`.
 *
 * @param porterDuffMode Tinting mode used, if `null` nothing changes in mode,
 * default is [PorterDuff.Mode.DST_ATOP]
 * @param mutate if you want to to use [Drawable.mutate] before any change isa.
 *
 * @see backgroundCompat
 */
fun View.setBackgroundTintCompat(
    @ColorInt color: Int? = null,
    porterDuffMode: PorterDuff.Mode? = null,
    mutate: Boolean = false
) {
    val drawable = DrawableCompat.wrap((if (mutate) background.mutate() else background) ?: return) ?: return
    porterDuffMode?.apply { DrawableCompat.setTintMode(drawable, porterDuffMode) }
    color?.apply { DrawableCompat.setTint(drawable, color) }
    backgroundCompat = drawable
}

// ---- Generic type parameters

/**
 * - Invokes [block] inside [View.post], however `receiver` view will be [block]'s `receiver` isa,
 * and if [addHandlerPost] then [View.post] will be wrapped inside [Handler.post] isa.
 *
 * @see performClickWithPressed
 */
@JvmOverloads
fun <V: View> V.postWithReceiver(addHandlerPost: Boolean = false, block: V.() -> Unit) {
    post {
        if (addHandlerPost) {
            Handler().post {
                block()
            }
        }else {
            block()
        }
    }
}
