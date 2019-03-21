@file:JvmName("ContextUtils")
@file:Suppress("UnusedImport")

package mohamedalaa.mautils.material_design

import android.app.Application
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import mohamedalaa.mautils.core_android.backgroundCompat
import mohamedalaa.mautils.core_android.dismissToast
import mohamedalaa.mautils.material_design.custom_classes.BaseApplication

/**
 * More concise & efficient way to [Snackbar] since you don't have to call [Snackbar.show],
 *
 * **Also** it has additional functionality
 *
 * - if your [Application] extends from [BaseApplication] then the [Snackbar] object
 *
 * will be stored in [BaseApplication.snackbar] so it gives 2 advantages
 * 1. can be dismissed via [dismissSnackbar]
 * 2. whenever you call this fun to [Snackbar], if previous [Snackbar] has not took it's full [Snackbar.getDuration]
 * it will be dismissed immediately followed by the [Snackbar.show] of current [Snackbar],
 * so you ensure no delayed [Snackbar]s will happen at all isa.
 *
 * - provide [modifications] in case you want to make changes for the UI of the [Snackbar]
 * by accessing it's view via [Snackbar.getView] isa.
 *
 * @param msg message to be displayed to the user in [Snackbar] isa.
 * @param duration duration to be used, Either [Snackbar.LENGTH_SHORT] (default) or [Snackbar.LENGTH_LONG]
 * @param modifications any modifications to the [Snackbar] object before showing,
 * Ex. changing background Using [Snackbar.getView] then [View.backgroundCompat].
 *
 * @see BaseApplication
 * @see dismissToast
 */
fun Context.snackbar(view: View,
                     msg: String,
                     duration: Int = Snackbar.LENGTH_SHORT,
                     modifications: ((Snackbar) -> Unit)? = null) {
    (applicationContext as? BaseApplication)?.apply {
        snackbar?.dismiss()

        snackbar = Snackbar.make(view, msg, duration)
        snackbar?.apply {
            // User modifications
            modifications?.invoke(this)

            show()
        }
    } ?: Snackbar.make(view, msg, duration).apply {
        // User modifications
        modifications?.invoke(this)

        show()
    }
}

/**
 * Works only if application extends [BaseApplication], used to dismiss the [Snackbar]
 * that's bounded to [BaseApplication] via [snackbar] isa.
 *
 * @see snackbar
 */
fun Context.dismissSnackbar() {
    (applicationContext as? BaseApplication)?.apply {
        snackbar?.dismiss()

        snackbar = null
    }
}