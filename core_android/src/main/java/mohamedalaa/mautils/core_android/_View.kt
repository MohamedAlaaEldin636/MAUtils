@file:JvmName("ViewUtils")

package mohamedalaa.mautils.core_android

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

/**
 * @return [View.getX] for this and all of it's parent viewGroups as a summation isa.
 *
 * @see getRootX
 * @see getRootCenterPointF
 */
fun View.getRootX(): Float {
    var value = 0f
    for (view in listOfParentViews(true)) {
        value += view.x
    }

    return value
}

/**
 * @return [View.getY] for this and all of it's parent viewGroups as a summation isa.
 *
 * @see getRootX
 * @see getRootCenterPointF
 */
fun View.getRootY(): Float {
    var value = 0f
    for (view in listOfParentViews(true)) {
        value += view.y
    }

    return value
}

/**
 * @return [getRootX] plus half of `this` view width as [PointF.x],
 * and [getRootY] plus half of `this` view width as [PointF.y]
 */
fun View.getRootCenterPointF(): PointF {
    val x = getRootX() + width.toFloat().div(2)
    val y = getRootY() + height.toFloat().div(2)

    return PointF(x, y)
}

/**
 * @param includeThisView if true then add `receiver` at index 0, in case it's needed in the list, default is false isa.
 *
 * @return list containing all parents [ViewGroup]s, including `this` if [includeThisView] is true isa.
 */
@JvmOverloads
fun View.listOfParentViews(includeThisView: Boolean = false): MutableList<View> {
    val listOfViews = mutableListOf<View>()

    var view: View? = this
    if (includeThisView) {
        listOfViews.add(this)
    }

    var keepGoing = true
    while (keepGoing) {
        view = (view?.parent as? ViewGroup)?.apply {
            listOfViews.add(this)
        }

        if (view == null) {
            keepGoing = false
        }
    }

    return listOfViews
}

/**
 * Performs [View.performClick], but with [View.isPressed] effect so pressed effect appear,
 * as if user clicked on the view isa.
 *
 * @param initialDelayInMillis delay before performing [View.performClick] isa.
 * @param pressedEffectDurationInMillis duration where [View.isPressed] is kept true, after [View.performClick] isa.
 */
@JvmOverloads
fun View.performClickWithPressed(initialDelayInMillis: Long = 0L, pressedEffectDurationInMillis: Long = 250L) {
    val block = {
        performClick()

        isPressed = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            postOnAnimationDelayed({
                isPressed = false
            }, pressedEffectDurationInMillis)
        }else {
            postDelayed({
                isPressed = false
            }, pressedEffectDurationInMillis)
        }
    }

    if (initialDelayInMillis > 0L) {
        Handler().postDelayed({
            block()
        }, initialDelayInMillis)
    }else {
        block()
    }
}

/**
 * Animate [View.getHeight], and [View.getWidth] with given [durationInMillis] and [listener] isa.
 *
 * **Note**
 *
 * 1- This method is not good for all cases for Ex. in ConstraintLayout due to constraints
 * this might not work properly, so only use this in case of a fixed height and/or width isa.
 * 2- if both [newHeight] & [newHeight] are both null, then no anim is done and [listener]
 * is never invoked isa.
 *
 * @param newHeight new Height to animate to, if null then only width is animated isa.
 * @param newWidth new Width to animate to, if null then only height is animated isa.
 */
@JvmOverloads
fun View.animHeightAndWidth(newHeight: Int?, newWidth: Int?, durationInMillis: Long = 250L, listener: Animator_AnimatorListener_Typealias? = null) {
    if (newHeight == null && newWidth == null) {
        return
    }

    val animHeight = newHeight?.run { ValueAnimator.ofInt(measuredHeight, newHeight) }
    animHeight?.addUpdateListener {
        val value = it.animatedValue as Int

        val params = layoutParams
        params.height = value
        layoutParams = params
    }

    val animWidth = newWidth?.run { ValueAnimator.ofInt(measuredWidth, newWidth) }
    animWidth?.addUpdateListener {
        val value = it.animatedValue as Int

        val params = layoutParams
        params.width = value
        layoutParams = params
    }

    val set = AnimatorSet()
    val animatorList = listOfNotNull(animHeight, animWidth)
    set.playTogether(animatorList)
    set.addListenerMA(listener)
    set.duration = durationInMillis
    set.start()
}

/**
 * Compatible version for [View.setBackground] for API < 16 works as get/set The Drawable
 * to use as the background, or null to remove/get the background isa.
 *
 * Easier approach than repeating checks of [Build.VERSION] to toggle between [View.setBackground]
 * & [View.setBackgroundDrawable]
 */
@Suppress("DEPRECATION")
var View.backgroundCompat: Drawable?
    get() = background
    set(value) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        background = value
    }else {
        setBackgroundDrawable(value)
    }

/** Sets background tint according to given [color] isa. */
fun View.setBackgroundTint(@ColorInt color: Int) {
    val drawable = DrawableCompat.wrap(background ?: return) ?: return
    backgroundCompat = drawable
    DrawableCompat.setTint(drawable, color)
}

// ---- Generic type parameters

/**
 * Invokes [block] inside [View.post], however `this` view will be [block]'s receiver isa,
 * and if [addHandlerPost] then [View.post] will be wrapped inside [Handler.post] isa.
 */
@JvmOverloads
inline fun <V: View> V.postWithReceiver(addHandlerPost: Boolean = false, crossinline block: V.() -> Unit) {
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