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

var SearchView.text: String?
    get() {
        return firstNestedViewIsInstanceOrNull<EditText>()?.text?.toString()
    }
    set(value) {
        if (value == null) {
            return
        }

        firstNestedViewIsInstanceOrNull<EditText> {
            this.setText(value)
        }
    }

fun SearchView.setIconsTint(@ColorInt color: Int) {
    allNestedViewsIsInstanceOrNull<ImageView> {
        this.setColorFilter(color)
    }
}

/**
 * Using [listener] for [SearchView.setOnQueryTextListener] instead of regular [SearchView.OnQueryTextListener],
 * for more concise & idiomatic coding, for an example See [MASearchViewOnQueryTextListener] isa.
 */
fun SearchView.setOnQueryTextListenerMA(listener: SearchView_OnQueryTextListener_Typealias?) {
    val genListener =
        MASearchViewOnQueryTextListener(listener)
    setOnQueryTextListener(genListener)
}
