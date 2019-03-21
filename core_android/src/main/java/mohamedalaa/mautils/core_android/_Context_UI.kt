@file:JvmMultifileClass
@file:JvmName("ContextUtils")

package mohamedalaa.mautils.core_android

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import mohamedalaa.mautils.core_android.custom_classes.BaseApplication

/**
 * More concise & efficient way to [Toast] since you don't have to call [Toast.show],
 *
 * **Also** it has additional functionality
 *
 * - if your [Application] extends from [BaseApplication] then the [Toast] object
 *
 * will be stored in [BaseApplication.toast] so it gives 2 advantages
 * 1. can be dismissed via [dismissToast]
 * 2. whenever you call this fun to [Toast], if previous [Toast] has not took it's full [Toast.getDuration]
 * it will be dismissed immediately followed by the [Toast.show] of current [Toast],
 * so you ensure no delayed toasts to happen at all isa.
 *
 * - provide [modifications] in case you want to make changes for the UI of the [Toast]
 * by accessing it's view via [Toast.getView] isa.
 *
 * @param msg message to be displayed to the user in [Toast] isa.
 * @param duration duration to be used, Either [Toast.LENGTH_SHORT] (default) or [Toast.LENGTH_LONG]
 * @param modifications any modifications to the [Toast] object before showing,
 * Ex. changing background Using [Toast.getView] then [View.backgroundCompat].
 *
 * @see BaseApplication
 * @see dismissToast
 */
@JvmOverloads
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT, modifications: ((Toast) -> Unit)? = null) {
    (applicationContext as? BaseApplication)?.apply {
        toast?.cancel()

        toast = Toast.makeText(this, msg, duration)
        toast?.apply {
            // User modifications
            modifications?.invoke(this)

            show()
        }
    } ?: Toast.makeText(this, msg, duration).apply {
        // User modifications
        modifications?.invoke(this)

        show()
    }
}

/**
 * Works only if Your [Application] extends [BaseApplication], used to dismiss the [Toast]
 * that's bounded to [BaseApplication] via [toast] isa.
 *
 * @see toast
 */
fun Context.dismissToast() {
    (applicationContext as? BaseApplication)?.apply {
        toast?.cancel()

        toast = null
    }
}