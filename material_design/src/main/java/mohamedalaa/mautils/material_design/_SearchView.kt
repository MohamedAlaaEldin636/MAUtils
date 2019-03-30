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

package mohamedalaa.mautils.material_design

import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import mohamedalaa.mautils.core_android.allNestedViewsIsInstanceOrNull
import mohamedalaa.mautils.core_android.firstNestedViewIsInstanceOrNull

var SearchView.textColor: Int?
    get() {
        firstNestedViewIsInstanceOrNull<EditText> {
            return this.currentTextColor
        }

        return null
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
        firstNestedViewIsInstanceOrNull<EditText> {
            return this.currentHintTextColor
        }

        return null
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
        firstNestedViewIsInstanceOrNull<EditText> {
            return this.text?.toString()
        }

        return null
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