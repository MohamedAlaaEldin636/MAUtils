@file:JvmName("TextViewUtils")

package mohamedalaa.mautils.core_android

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * invokes given [block] only inside [TextWatcher.afterTextChanged] isa.
 */
fun TextView.addAfterTextChangedListener(block: (textView: TextView) -> Unit) {
    addTextChangedListener(object: TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            block(this@addAfterTextChangedListener)
        }
    })
}