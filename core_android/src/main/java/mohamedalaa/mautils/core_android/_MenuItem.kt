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

package mohamedalaa.mautils.core_android

import android.graphics.PorterDuff
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import androidx.annotation.ColorInt

/** Changes menu item's icon tint color according to given [color] isa. */
fun MenuItem.setIconTint(@ColorInt color: Int) {
    val tempIcon = icon
    tempIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN)

    icon = tempIcon
}

/** Changes menu item's title color according to given [color] isa. */
fun MenuItem.setTitleColor(@ColorInt color: Int) {
    val spannableString = SpannableString(title)
    spannableString += ForegroundColorSpan(color)

    title = spannableString
}