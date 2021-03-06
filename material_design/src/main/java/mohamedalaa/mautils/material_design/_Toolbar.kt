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

@file:JvmName("ToolbarUtils")

package mohamedalaa.mautils.material_design

import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.core.text.toSpannable
import androidx.core.view.forEach
import mohamedalaa.mautils.core_android.extensions.firstNestedViewOrNull
import mohamedalaa.mautils.core_android.extensions.plusAssign
import mohamedalaa.mautils.core_android.extensions.tintCompat

/**
 * tint [Toolbar.getNavigationIcon] && [Toolbar.getOverflowIcon] && all icons in [Toolbar.getMenu] isa.
 */
@JvmOverloads
fun Toolbar.setIconsTint(@ColorInt color: Int, changeTitleTextColor: Boolean = false) {
    menu.forEach {
        it.icon.tintCompat(color)

        if (changeTitleTextColor) {
            it.title = it.title.toSpannable().apply { this += ForegroundColorSpan(color) }
        }
    }
    overflowIcon.tintCompat(color)
    navigationIcon.tintCompat(color)
}

/**
 * Finds [Toolbar.getNavigationIcon]`s View as ImageView if exists or null instead isa.
 */
fun Toolbar.findNavIconViewOrNull(): ImageView? {
    return firstNestedViewOrNull {
        it is ImageView && it.contentDescription == navigationContentDescription
    } as? ImageView
}