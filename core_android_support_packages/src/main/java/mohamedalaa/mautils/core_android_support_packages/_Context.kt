@file:JvmName("ContextUtils")

package mohamedalaa.mautils.core_android_support_packages

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import mohamedalaa.mautils.core_android.toast as coreAndroidLibToast

/**
 * Shows snackbar, with benefit of cancelling old snackbar before the new show in case if [useBaseApplication] is true,
 * And your application class extends [BaseApplication], and is in the manifest.
 * This benefit is useful in case you want to show a snackbar on interaction and it's undo action,
 * Ex. when a user clicks on Like a snackbar says you liked this, and pressing dislike,
 * snackbar tells user you disliked it, so it should dismiss last snackbar and shows current one isa.
 *
 * @param view view to search it's parent view groups for a coordinator layout, so usual behaviours are done,
 * like lifting [FloatingActionButton] and swiping `this` snackbar to dismiss it, if no coordinator layout found then behaviors
 * won't be done, however snackbar still shown isa.
 * @param msg message to be displayed to the user in `this` [Snackbar] isa.
 * @param duration duration to be used, one of [Snackbar.LENGTH_SHORT], [Snackbar.LENGTH_LONG] or [Snackbar.LENGTH_INDEFINITE]
 * @param useBaseApplication true means it will use [BaseApplication.snackbar] which guarantees old snackbar to be dismissed
 * before `this` new one is shown, Only if last one was as well done by [BaseApplication.snackbar] isa.
 * @param modifications any modifications to the [Snackbar] object before showing, Ex. changing background Using [Snackbar.getView].
 *
 * @see [dismissSnackbar]
 * @see [BaseApplication]
 */
fun Context.snackbar(view: View,
                     msg: String,
                     duration: Int = Snackbar.LENGTH_SHORT,
                     useBaseApplication: Boolean = true,
                     modifications: ((Snackbar) -> Unit)? = null) {
    val snackbar = Snackbar.make(view, msg, duration)

    if (useBaseApplication){
        val baseApplication = applicationContext as? BaseApplication ?: return

        baseApplication.snackbar?.dismiss()
        baseApplication.snackbar = snackbar
        baseApplication.snackbar?.apply {
            modifications?.invoke(this)

            show()
        }
    }else{
        snackbar.show()
    }
}

/**
 * Works only if application extends [BaseApplication], used to dismiss the snackbar bound to it isa.
 *
 * @see [snackbar]
 */
fun Context.dismissSnackbar() {
    (applicationContext as? BaseApplication)?.apply {
        this.snackbar?.apply {
            dismiss()
        } ?: return

        this.snackbar = null
    }
}

/**
 * Same as [Context.coreAndroidLibToast], however with an additional functionality added if param [useBaseApplication]
 * set to true, which is ensuring canceling last [BaseApplication.toast] before showing new one.
 *
 * @param msg message to be displayed to the user in `this` [Toast] isa.
 * @param duration duration to be used, Either [Toast.LENGTH_SHORT] ot [Toast.LENGTH_LONG]
 * @param useBaseApplication true means it will use [BaseApplication.toast] which guarantees old toast to be dismissed
 * before `this` new one is shown, Only if last one was as well done by [BaseApplication.toast] isa.
 * @param modifications any modifications to the [Toast] object before showing, Ex. changing background Using [Toast.getView].
 *
 * @see [Context.coreAndroidLibToast]
 * @see [BaseApplication]
 * @see [dismissToast]
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT, useBaseApplication: Boolean = true, modifications: ((Toast) -> Unit)? = null) {
    if (useBaseApplication) {
        val baseApplication = applicationContext as? BaseApplication ?: return

        val toast = Toast.makeText(this, msg, duration)

        baseApplication.toast?.cancel()
        baseApplication.toast = toast

        baseApplication.toast?.apply {
            // User modifications
            modifications?.invoke(this)

            show()
        }
    }else {
        coreAndroidLibToast(msg, duration, modifications)
    }
}

/**
 * Works only if application extends [BaseApplication], used to dismiss the snackbar bound to it isa.
 *
 * @see [toast]
 */
fun Context.dismissToast() {
    (applicationContext as? BaseApplication)?.apply {
        toast?.cancel()

        toast = null
    }
}