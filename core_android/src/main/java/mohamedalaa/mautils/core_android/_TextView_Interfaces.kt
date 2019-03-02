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