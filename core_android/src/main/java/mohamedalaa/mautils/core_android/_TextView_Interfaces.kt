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

package mohamedalaa.mautils.core_android

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

typealias TextView_TextWatcher_Typealias = MATextViewTextWatcher.() -> Unit

class MATextViewTextWatcher(listener: TextView_TextWatcher_Typealias?): TextWatcher {

    init {
        listener?.invoke(this)
    }

    private var _beforeTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null
    private var _onTextChanged: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null
    private var _afterTextChanged: ((s: Editable?) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _beforeTextChanged?.invoke(s, start, count, after)
    }

    /** execute [action] when [beforeTextChanged] is triggered isa. */
    infix fun MATextViewTextWatcher.beforeTextChanged(action: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)?): MATextViewTextWatcher
        = apply { _beforeTextChanged = action }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _onTextChanged?.invoke(s, start, before, count)
    }

    /** execute [action] when [onTextChanged] is triggered isa. */
    infix fun MATextViewTextWatcher.onTextChanged(action: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)?): MATextViewTextWatcher
        = apply { _onTextChanged = action }

    override fun afterTextChanged(s: Editable?) {
        _afterTextChanged?.invoke(s)
    }

    /** execute [action] when [afterTextChanged] is triggered isa. */
    infix fun MATextViewTextWatcher.afterTextChanged(action: ((s: Editable?) -> Unit)?): MATextViewTextWatcher
        = apply { _afterTextChanged = action }

}