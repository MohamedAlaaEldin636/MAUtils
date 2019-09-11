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

@file:JvmName("MenuUtils")

package mohamedalaa.mautils.core_android.extensions

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed

/** @return index of [menuItem] in this `receiver` OR null if not found isa. */
fun Menu.indexOf(menuItem: MenuItem): Int? {
    forEachIndexed { index, item ->
        if (menuItem == item) {
            return index
        }
    }

    return null
}

/**
 * Changes icon tintColorFilter by given [color] for all menu items in this `receiver` isa.
 *
 * @see MenuItem.setIconTint
 * @see setItemsTitleColor
 */
fun Menu.setItemsIconTint(@ColorInt color: Int) = forEach {
    it.setIconTint(color)
}

/**
 * Changes title color by given [color] for all menu items in this `receiver` isa.
 *
 * @see MenuItem.setTitleColor
 * @see setItemsIconTint
 */
fun Menu.setItemsTitleColor(@ColorInt color: Int) = forEach {
    it.setTitleColor(color)
}

fun Menu.firstOrNull(predicate: (MenuItem) -> Boolean): MenuItem? {
    forEach {
        if (predicate(it)) {
            return it
        }
    }

    return null
}
