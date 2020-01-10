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

@file:JvmName("EditTextUtils")

package mohamedalaa.mautils.core_android.extensions

import android.os.Handler
import android.text.InputFilter
import android.widget.EditText

/**
 * Sets maxLength that can be typed in `this` [EditText], using [InputFilter.LengthFilter] isa.
 *
 * @param maxLength maxLength required, Ex. if == 1, then only 1 char can be typed isa.
 * @param overrideOtherFilters if true a new array is used for [EditText.setFilters], default is false isa.
 */
@JvmOverloads
fun EditText.setMaxLength(maxLength: Int, overrideOtherFilters: Boolean = false) {
    val listOfFilters: MutableList<InputFilter> = if (overrideOtherFilters) {
        mutableListOf()
    }else {
        filters.filter { it !is InputFilter.LengthFilter }.toMutableList()
    }
    listOfFilters.add(0, InputFilter.LengthFilter(maxLength))
    filters = listOfFilters.toTypedArray()
}

/**
 * Sets blinking cursor's position to be after last char.
 *
 * @param postExecution performs the action after [EditText.post], and if [handlerExecution] is true
 * then action is performed after [Handler.post] which is after [EditText.post], default is false isa.
 * @param handlerExecution performs the action after [Handler.post], and if [postExecution] is true
 * then action is performed after [Handler.post] which is after [EditText.post], default is false isa.
 */
@JvmOverloads
fun EditText.setSelectionToLastChar(postExecution: Boolean = false, handlerExecution: Boolean = false) {
    val block: EditText.() -> Unit = { setSelection(this.text?.length ?: 0) }

    when {
        postExecution && handlerExecution -> this.post { Handler().post { block() } }
        postExecution -> this.post { block() }
        handlerExecution -> Handler().post { block() }
        else -> block()
    }
}