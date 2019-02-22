@file:JvmName("ContextUtils")

package mohamedalaa.mautils.core_android

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat

/** Layout inflater from `this context`, used [LayoutInflater.from] */
val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

/**
 * Inflates a layout from res
 *
 * @return rootView in the provided [layoutRes] isa.
 */
fun Context.inflateLayout(@LayoutRes layoutRes: Int,
                          viewGroup: ViewGroup? = null,
                          attachToRoot: Boolean = false): View {
    return layoutInflater.inflate(layoutRes, viewGroup, attachToRoot)
}

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

/**
 * Shows toast message with [duration], after making any modifications in the [modifications] fun isa.
 *
 * @param msg Message to show the user in the [Toast]
 * @param duration duration of the msg, Either [Toast.LENGTH_SHORT] OR [Toast.LENGTH_LONG] isa.
 * @param modifications any modifications to the [Toast] object before showing, Ex. changing background Using [Toast.getView].
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT, modifications: ((Toast) -> Unit)? = null) {
    Toast.makeText(this, msg, duration).apply {
        // User modifications
        modifications?.invoke(this)

        show()
    }
}

/**
 * Using [Context.startActivity] with given [url] to launch a browser isa.
 *
 * @param url web link url to launch isa.
 * @param showToastOnFailure if true a toast msg
 * [R.string.you_do_not_have_application_that_can_open_web_links] will be shown isa.
 */
fun Context.launchWebLink(url: String, showToastOnFailure: Boolean = true): Boolean {
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    val canHandleIntent = webIntent.resolveActivity(packageManager) != null
    if (canHandleIntent) {
        startActivity(webIntent)
    }else if (showToastOnFailure) {
        toast(getString(R.string.you_do_not_have_application_that_can_open_web_links), Toast.LENGTH_SHORT)
    }

    return canHandleIntent
}