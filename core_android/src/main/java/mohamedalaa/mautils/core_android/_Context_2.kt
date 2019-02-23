@file:JvmMultifileClass
@file:JvmName("ContextUtils")

package mohamedalaa.mautils.core_android

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.showKeyboardFor(view: View, requestFocus: Boolean = false) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return

    if (requestFocus) {
        view.requestFocus()
    }

    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.hideKeyboardFrom(view: View, clearFocus: Boolean = false) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return

    if (clearFocus) {
        view.clearFocus()
    }

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/** @return true if soft keyboard is currently shown, Note this is not 100% accurate. */
fun Context.isKeyboardShown(): Boolean {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager

    return imm?.isAcceptingText ?: false
}