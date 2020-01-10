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

@file:JvmName("SearchViewUtils")

package mohamedalaa.mautils.core_android.extensions

import android.widget.*
import androidx.annotation.ColorInt

/**
 * set/get text color of [SearchView] isa.
 *
 * @see hintTextColor
 */
var SearchView.textColor: Int?
    get() {
        return firstNestedViewIsInstanceOrNull<EditText>()?.currentTextColor
    }
    set(value) {
        if (value == null) {
            return
        }

        firstNestedViewIsInstanceOrNull<EditText> {
            this.setTextColor(value)
        }
    }

/**
 * set/get hint text color of [SearchView] isa.
 *
 * @see textColor
 */
var SearchView.hintTextColor: Int?
    get() {
        return firstNestedViewIsInstanceOrNull<EditText>()?.currentHintTextColor
    }
    set(value) {
        if (value == null) {
            return
        }

        firstNestedViewIsInstanceOrNull<EditText> {
            this.setHintTextColor(value)
        }
    }

/**
 * set/get text of [SearchView] isa.
 *
 * @see textColor
 * @see hintText
 * @see hintTextColor
 */
var SearchView.text: CharSequence?
    get() {
        return firstNestedViewIsInstanceOrNull<EditText>()?.text
    }
    set(value) {
        firstNestedViewIsInstanceOrNull<EditText> {
            this.setText(value)
        }
    }

/**
 * set/get hint text of [SearchView] isa.
 *
 * @see textColor
 * @see text
 * @see hintTextColor
 */
var SearchView.hintText: CharSequence?
    get() {
        return firstNestedViewIsInstanceOrNull<EditText>()?.hint
    }
    set(value) {
        if (value == null) {
            return
        }

        firstNestedViewIsInstanceOrNull<EditText> {
            hint = value
        }
    }

/**
 * Performs [ImageView.setColorFilter] for every [ImageView] inside `receiver` with given [color] isa.
 *
 * @see allNestedViewsIsInstanceOrNull
 */
fun SearchView.setIconsTint(@ColorInt color: Int) {
    allNestedViewsIsInstanceOrNull<ImageView> {
        this.setColorFilter(color)
    }
}

/**
 * - More concise & idiomatic way instead of mandatory implementing all functions, just implement
 * what you want, see below Ex. on how to use it isa.
 * ```
 * fun setupOnQueryTextListener(searchView: SearchView) {
 *      searchView.setOnQueryTextListenerMA {
 *          onQueryTextChange {
 *              // Your code here
 *
 *              true // Depends on what you wanna achieve
 *          }
 *
 *          onQueryTextSubmit {
 *              // Your code here
 *
 *              false // Depends on what you wanna achieve
 *          }
 *      }
 * }
 * ```
 */
fun SearchView.setOnQueryTextListenerMA(listener: MASearchViewOnQueryTextListenerBuilder.() -> Unit) {
    setOnQueryTextListener(
        MASearchViewOnQueryTextListenerBuilder.getListener(listener)
    )
}
