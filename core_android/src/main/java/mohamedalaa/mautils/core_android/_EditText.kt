@file:JvmName("EditTextUtils")

package mohamedalaa.mautils.core_android

import android.os.Handler
import android.text.InputFilter
import android.widget.EditText

/**
 * Sets max maxLength that can be typed in `this` [EditText], using [InputFilter.LengthFilter] isa.
 *
 * @param maxLength maxLength required, Ex. if == 1, then only 1 char can be typed isa.
 * @param overrideOtherFilters if true a new array is used for [EditText.setFilters] isa.
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
 * then action is performed after [Handler.post] after [EditText.post] isa.
 * @param handlerExecution performs the action after [Handler.post], and if [postExecution] is true
 * then action is performed after [Handler.post] after [EditText.post] isa.
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