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

@file:JvmName("TextViewUtils")

package mohamedalaa.mautils.core_android.extensions

import android.text.TextWatcher
import android.widget.TextView

/**
 * invokes given [block] only inside [TextWatcher.afterTextChanged] isa, since it is commonly
 *
 * that reaction on text change is only desirable after text has been changed, however if you
 *
 * want full control consider using [TextView.addTextChangedListenerMA] isa.
 */
fun TextView.addAfterTextChangedListener(block: (textView: TextView) -> Unit) = addTextChangedListenerMA {
    afterTextChanged {
        block(this@addAfterTextChangedListener)
    }
}

/**
 * Using [listener] for [TextView.addTextChangedListener] instead of regular approach,
 * for more concise & idiomatic coding isa.
 *
 * @see [MATextViewTextWatcher]
 */
fun TextView.addTextChangedListenerMA(listener: TextView_TextWatcher_Typealias?) {
    val genListener = MATextViewTextWatcher(listener)
    addTextChangedListener(genListener)
}