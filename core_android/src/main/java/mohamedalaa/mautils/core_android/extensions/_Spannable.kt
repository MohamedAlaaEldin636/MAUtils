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

@file:JvmName("SpannableUtils")

package mohamedalaa.mautils.core_android.extensions

import android.text.Spannable
import androidx.core.text.set
import mohamedalaa.mautils.core_kotlin.extensions.indexOfOrNull
import java.util.*

/**
 * - Uses [androidx.core.text.set] for the whole [Spannable] isa.
 */
operator fun Spannable.plusAssign(span: Any) {
    this[0..length] = span
}

/**
 * - Uses [androidx.core.text.set] for the [Char] at provided [index] only isa.
 */
operator fun Spannable.set(index: Int, span: Any) {
    this[index..(index.inc())] = span
}

/**
 * - Same as [androidx.core.text.set] for each span in [spansList] isa.
 * ```
 * spannable.spanAll(0..3, listOf(
 *      ForegroundColorSpan(Color.BLACK),
 *      BackgroundColorSpan(Color.BLUE)
 * ))
 * ```
 */
fun Spannable.spanAll(range: IntRange, spansList: List<Any>) {
    for (span in spansList) {
        this[range] = span
    }
}

/**
 * - Uses [androidx.core.text.set] where [IntRange.start] is first occurrence of [string] in
 * `receiver` and  [IntRange.endInclusive] (which is actually exclusive due to to [Spannable.setSpan] end param)
 * is [IntRange.start] + [String.length] isa.
 *
 * @see Spannable.spanAll
 */
operator fun Spannable.set(string: String, span: Any) {
    val startInclusive = indexOfOrNull(string) ?: return
    val endExclusive = startInclusive + string.length
    this[startInclusive..endExclusive] = span
}

/**
 * - Spans evey char in [string] by returned value of [generateNewSpan],
 * if conditions of [ignoreCase], [allChars] are met.
 *
 * @param string all characters that need to be spanned.
 * @param ignoreCase if true then the spanning process will be case sensitive, default is false isa.
 * @param allChars true means all chars in `receiver` will be spanned if found inside [string], default is true,
 * while false means the span will happen if whole [string] is in `receiver`, see below for more explanation isa.
 * Ex. "ab ac ab ac".[spanChars]("ac", allChars = true, ...) then each 'a' & each 'c' in receiver will be spanned
 * while if it was false -> then only each occurrence of "ac" together will be spanned so
 * 'a' in "ab" won't be spanned isa.
 *
 * @return list of indices that got spanned isa.
 */
@JvmOverloads
fun Spannable.spanChars(
    string: String?,
    ignoreCase: Boolean = false,
    allChars: Boolean = true,
    generateNewSpan: () -> Any
): List<Int> {
    if (string.isNullOrEmpty()) {
        return emptyList()
    }

    val modifiedString = if (ignoreCase) string.toLowerCase(Locale.getDefault()) else string

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

