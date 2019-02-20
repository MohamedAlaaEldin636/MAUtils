@file:JvmName("TextInputLayoutUtils")

package mohamedalaa.mautils.core_android_support_packages

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputLayout
import mohamedalaa.mautils.core_android.firstMatchingViewOrNull

/**
 * Sets on click listener for [TextInputLayout.passwordToggleView] isa.
 *
 * @see setPasswordVisibilityToggleTint
 */
fun TextInputLayout.setPasswordToggleClick(block: TextInputLayout.(view: View) -> Unit) {
    val togglePasswordButton = findTogglePasswordButton(this)
    togglePasswordButton?.setOnClickListener {
        this@setPasswordToggleClick.block(it)
    }
}

/**
 * Sets drawable tint of [TextInputLayout.passwordToggleDrawable] isa.
 *
 * @see setPasswordToggleClick
 */
fun TextInputLayout.setPasswordVisibilityToggleTint(@ColorInt color: Int) {
    setPasswordVisibilityToggleTintList(ColorStateList.valueOf(color))
}

private fun findTogglePasswordButton(viewGroup: ViewGroup): CheckableImageButton?
    = viewGroup.firstMatchingViewOrNull { it is CheckableImageButton } as? CheckableImageButton
