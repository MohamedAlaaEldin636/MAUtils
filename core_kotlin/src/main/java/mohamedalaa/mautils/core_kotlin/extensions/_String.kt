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

@file:JvmName("StringUtils")

package mohamedalaa.mautils.core_kotlin.extensions

import java.util.*

/**
 * Parses the string as an [Int] number and returns the result OR [fallback] if the string
 * is not a valid representation of a number, or the string is null isa.
 *
 * @see toBooleanOrNull
 */
fun String?.toIntOrElse(fallback: Int): Int = this?.toIntOrNull() ?: fallback

/**
 * @return index of the first occurrence of any item in [stringsArray] or `null` if none is found.
 *
 * @see removeAll
 */
@JvmOverloads
fun String.nearestIndexOf(vararg stringsArray: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val indices = stringsArray.mapNotNull { indexOfOrNull(it, startIndex, ignoreCase) }

    return indices.min()
}

/**
 * Removes all occurrences of each item in [values] isa.
 *
 * @see nearestIndexOf
 */
fun String.removeAll(vararg values: String): String {
    var tempFullString = this

    for (value in values) {
        tempFullString = tempFullString.replace(value, "")
    }

    return tempFullString
}

/**
 * - uses [kotlin.text.substring] with [startIndexInclusive] as it's start index and [endIndexExclusiveString]
 * as it's end index after searching for it's index and you can start the search from first occurrence
 * if [searchFirstNotLast] is `true` and from last occurrence if `false`, Also Note in case
 * no occurrence is found then [kotlin.text.substring] with [startIndexInclusive] only is used isa.
 */
fun String.substring(startIndexInclusive: Int, endIndexExclusiveString: String, searchFirstNotLast: Boolean = true): String {
    val result = if (searchFirstNotLast) {
        indexOfOrNull(endIndexExclusiveString)?.run {
            runCatching { this@substring.substring(startIndexInclusive, this) }.getOrNull()
        }
    }else {
        lastIndexOfOrNull(endIndexExclusiveString)?.run {
            runCatching { this@substring.substring(startIndexInclusive, this) }.getOrNull()
        }
    }

    return result ?: this@substring.substring(startIndexInclusive)
}

/**
 * @return `true` if the contents of this string is equal to the word "true"
 *
 * And `false` if the contents of this string is equal to the word "false",
 *
 * Otherwise `null` is returned isa,
 *
 * **Note we Ignore case of the `receiver` isa.**
 *
 * @see toIntOrElse
 */
fun String.toBooleanOrNull(): Boolean? {
    return when (toLowerCase(Locale.getDefault())) {
        true.toString() -> true
        false.toString() -> false
        else -> null
    }
}
