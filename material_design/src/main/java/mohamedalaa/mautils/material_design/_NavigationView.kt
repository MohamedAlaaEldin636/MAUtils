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

@file:JvmName("NavigationViewUtils")

package mohamedalaa.mautils.material_design

import android.view.MenuItem
import androidx.core.view.forEachIndexed
import com.google.android.material.navigation.NavigationView
import mohamedalaa.mautils.core_android.extensions.indexOfOrNull

fun NavigationView.indexOfMenuItem(menuItem: MenuItem)
    = menu.indexOfOrNull(menuItem)

/**
 * set/get current menu item by index
 * @see selectedMenuItem
 */
var NavigationView.selectedMenuItemIndex: Int
    get() {
        menu.forEachIndexed { index, item -> if (item.isChecked) return index }

        return 0
    }
    set(value) {
        val item = menu.getItem(value)

        item?.isChecked = true
    }

/**
 * set/get current menu item
 * @see selectedMenuItemIndex
 */
var NavigationView.selectedMenuItem: MenuItem
    get() = menu.getItem(selectedMenuItemIndex)
    set(value) = menu.findItem(value.itemId).run { isChecked = true }