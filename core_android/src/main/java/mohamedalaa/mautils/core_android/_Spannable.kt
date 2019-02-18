package mohamedalaa.mautils.core_android

import android.text.Spannable
import android.text.Spanned

/**
 * Uses [Spannable.setSpan] for the whole [Spannable] isa.
 */
operator fun Spannable.plusAssign(span: Any) {
    setSpan(span, 0, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}