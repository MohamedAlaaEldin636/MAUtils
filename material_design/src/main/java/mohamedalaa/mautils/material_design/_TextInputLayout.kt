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

@file:JvmName("TextInputLayoutUtils")

package mohamedalaa.mautils.material_design

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.google.android.material.internal.CheckableImageButton
import com.google.android.material.textfield.TextInputLayout
import mohamedalaa.mautils.core_android.firstNestedViewOrNull

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
    = viewGroup.firstNestedViewOrNull { it is CheckableImageButton } as? CheckableImageButton
