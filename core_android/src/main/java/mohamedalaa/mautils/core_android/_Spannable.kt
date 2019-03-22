@file:JvmName("SpannableUtils")

package mohamedalaa.mautils.core_android

import android.text.Spannable
import androidx.core.text.set

/**
 * Uses [Spannable.setSpan] for the whole [Spannable] isa.
 */
operator fun Spannable.plusAssign(span: Any) {
    this[0..length] = span
}

/**
 * Uses [Spannable.setSpan] for the [Char] at provided [index] only isa.
 */
operator fun Spannable.set(index: Int, span: Any) {
    this[index..(index.inc())] = span
}

/**
 * Spans evey char in [string] by returned value of [generateNewSpan],
 * if conditions of [ignoreCase], [allChars] are met.
 *
 * @param string all characters that need to be spanned.
 * @param ignoreCase if true then the spanning process will be case sensitive, default is false isa.
 * @param allChars true means all chars in `receiver` will be spanned if found inside [string], default is true,
 *
 * while false means the span will happen if whole [string] is in `receiver`, see below for more explanation isa.
 *
 * Ex. "ab ac ab ac".[spanChars] ("ac", allChars = true, ...) then each 'a' in receiver will be spanned
 *
 * while if it was false -> then only both "ac" will be spanned
 *
 * @return list of indices that got spanned isa.
 */
@JvmOverloads
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

