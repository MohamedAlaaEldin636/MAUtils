@file:JvmName("TextViewUtils")

package mohamedalaa.mautils.core_android

import android.text.TextWatcher
import android.widget.TextView

/**
 * invokes given [block] only inside [TextWatcher.afterTextChanged] isa, since it is commonly
 * that reaction on text change is only desirable after text has been changed, however if you
 * want full control consider using [TextView.addTextChangedListenerMA] isa.
 */
fun TextView.addAfterTextChangedListener(block: (textView: TextView) -> Unit) = addTextChangedListenerMA {
    afterTextChanged {
        block(this@addAfterTextChangedListener)
    }
}