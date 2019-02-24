@file:JvmName("SpannableUtils")

package mohamedalaa.mautils.core_android

import android.text.Spannable
import android.text.Spanned

/**
 * Uses [Spannable.setSpan] for the whole [Spannable] isa.
 */
operator fun Spannable.plusAssign(span: Any) {
    // todo use this isa, bs ba3d check below isa.
    setSpan(span, 0, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}

operator fun Spannable.set(index: Int, span: Any) {
    setSpan(span, index, index.inc(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

/** excludes last char isa. */
operator fun Spannable.set(intRange: IntRange, span: Any) {
    setSpan(span, intRange.start, intRange.endInclusive, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

/**
 * @return list of indices that got spanned isa.
 */
fun Spannable.spanChars(string: String?,
                        ignoreCase: Boolean = false,
                        allChars: Boolean = true,
                        generateNewSpan: () -> Any): List<Int> {
    if (string.isNullOrEmpty()) {
        return emptyList()
    }

    val modifiedString = if (ignoreCase) string.toLowerCase() else string

    if (allChars) {
        val mutableList = mutableListOf<Int>()

        forEachIndexed { index, char ->
            val modifiedChar = if (ignoreCase) char.toLowerCase() else char

            if (modifiedChar in modifiedString) {
                this[index] = generateNewSpan()
                mutableList += index
            }
        }

        return mutableList
    }else {
        val mutableList = mutableListOf<Int>()

        var currentSpannable = this.toString()
        var startIndex = currentSpannable.indexOf(modifiedString, ignoreCase = ignoreCase)
        var tempCounter = 0
        while (startIndex >= 0) {
            val endIndex = startIndex.plus(modifiedString.length)

            val a1 = tempCounter.plus(startIndex)
            val a2 = tempCounter.plus(endIndex)
            this[a1..a2] = generateNewSpan()
            mutableList += a1 until a2

            currentSpannable = if (endIndex == length) return mutableList else {
                tempCounter += endIndex

                currentSpannable.substring(endIndex)
            }
            startIndex = currentSpannable.indexOf(modifiedString, ignoreCase = ignoreCase)
        }

        return mutableList
    }
}

