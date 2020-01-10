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

@file:JvmName("MenuItemUtils")

package mohamedalaa.mautils.core_android.extensions

import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.MenuItem
import androidx.annotation.ColorInt

/**
 * - This approach uses [ForegroundColorSpan] && [StyleSpan] for the [MenuItem.setTitle] isa.
 *
 * @param typefaceStyleInt if `null` then [StyleSpan] won't be used at all, Otherwise can be only
 * one of the following values isa -> [Typeface.NORMAL], [Typeface.BOLD], [Typeface.ITALIC],
 * [Typeface.BOLD_ITALIC], in case of any other value then [Typeface.NORMAL] will be used instead isa.
 *
 * @see setIconTint
 */
@JvmOverloads
fun MenuItem.setTitleColorAndStyle(@ColorInt color: Int, typefaceStyleInt: Int? = null) {
    val spannableString = SpannableString(title)

    // Color
    spannableString += ForegroundColorSpan(color)

    // Style
    if (typefaceStyleInt != null) {
        spannableString += StyleSpan(typefaceStyleInt)
    }

    title = spannableString
}


/**
 * - Tinting [MenuItem.getIcon] using [Drawable.tintColorFilter] if [useTintColorFilterNotCompat] is `true`
 * otherwise it uses [Drawable.tintCompat] isa, and provides [porterDuffMode] if not-null to
 * either of these functions along with [mutate] value as well isa.
 *
 * @param mutate if true then we apply tinting to [MenuItem.getIcon] + [Drawable.mutate] then use
 * this new [Drawable] with [MenuItem.setIcon] isa.
 *
 * @see setTitleColorAndStyle
 */
@JvmOverloads
fun MenuItem.setIconTint(
    @ColorInt color: Int,
    porterDuffMode: PorterDuff.Mode? = null,
    mutate: Boolean = true,
    useTintColorFilterNotCompat: Boolean = false
) {
    val newIcon = icon.run { if (mutate) mutate() else this }

    if (useTintColorFilterNotCompat) {
        if (porterDuffMode != null){
            newIcon.tintColorFilter(color, porterDuffMode, mutate)
        }else {
            newIcon.tintColorFilter(color, mutate = mutate)
        }
    }else {
        newIcon.tintCompat(color, porterDuffMode)
    }

    icon = newIcon
}
