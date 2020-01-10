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

@file:JvmName("SoftKeyboardUtils")

package mohamedalaa.mautils.core_android.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Shows soft keyboard for given [view] isa.
 *
 * @see hideKeyboardFrom
 */
@JvmOverloads
fun Context.showKeyboardFor(view: View, requestFocus: Boolean = false) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return

    if (requestFocus) {
        view.requestFocus()
    }

    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * Hides soft keyboard for given [view] isa.
 *
 * @see hideKeyboard
 * @see showKeyboardFor
 */
@JvmOverloads
fun Context.hideKeyboardFrom(view: View, clearFocus: Boolean = false) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return

    if (clearFocus) {
        view.clearFocus()
    }

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

/*
todo instead check this out isa.
1. check all show/hide fun here and if need to change flags
2. try for is shown hide with flag implicit thing and get result kda isa.
and if true re-show now u know it is shown and see if it makes ui noticed change isa.
 */
/** @return true if soft keyboard is currently shown, Note this is not 100% accurate. */
fun Context.isKeyboardShown(): Boolean {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager

    return imm?.isAcceptingText ?: false
}

/** Hides soft keyboard if currently shown, Not 100% accurate, for more accuracy try [Context.hideKeyboardFrom] */
fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return

    // Find the currently focused view, so we can grab the correct window token from it.
    val view = currentFocus ?: (window.decorView.rootView ?: return)

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}