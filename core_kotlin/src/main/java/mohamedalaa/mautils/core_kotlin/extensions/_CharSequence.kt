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

@file:JvmName("CharSequenceUtils")

package mohamedalaa.mautils.core_kotlin.extensions

import kotlin.text.contains as kotlinContains

/**
 * - Same as [kotlinContains] but can be used in case of nullable `receiver` & [other] as well
 * which presents a good syntax in case you want use the operator `in` instead of explicitly
 * using `?` for nullability check isa.
 *
 * @return false if `receiver` and/or [other] are/is `null`, otherwise same result as [kotlinContains] isa.
 */
@Suppress("INAPPLICABLE_OPERATOR_MODIFIER")
@JvmOverloads
operator fun CharSequence?.contains(other: CharSequence?, ignoreCase: Boolean = false): Boolean {
    return this?.kotlinContains(other ?: return false, ignoreCase) ?: false
}

/**
 * - Same as [kotlinContains] but can be used in case of nullable `receiver` & [char] as well
 * which presents a good syntax in case you want use the operator `in` instead of explicitly
 * using `?` for nullability check isa.
 *
 * @return false if `receiver` and/or [char] are/is `null`, otherwise same result as [kotlinContains] isa.
 */
@Suppress("INAPPLICABLE_OPERATOR_MODIFIER")
@JvmOverloads
operator fun CharSequence?.contains(char: Char?, ignoreCase: Boolean = false): Boolean {
    return this?.kotlinContains(char ?: return false, ignoreCase) ?: false
}

/**
 * @return true if `receiver` [CharSequence.contains] any item in the given [charSequences] isa.
 */
fun CharSequence?.containsAny(vararg charSequences: CharSequence): Boolean
    = charSequences.any { this.contains(it) }

/**
 * @return al indices of the [string] occurrences in `receiver` isa, starting with [startIndex]
 * and with condition of [ignoreCase] isa, **Note** might return an empty list in case no occurrences
 * found or [startIndex] was not < `receiver`'s [CharSequence.length]
 *
 * @see CharSequence.indexOfOrNull
 * @see CharSequence.lastIndexOfOrNull
 */
fun CharSequence.allIndicesOf(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): List<Int> {
    var toBeUsedStartIndex = startIndex
    val indices = mutableListOf<Int>()
    while (toBeUsedStartIndex < length) {
        indexOfOrNull(string, startIndex = toBeUsedStartIndex, ignoreCase = ignoreCase)?.apply {
            indices += this

            toBeUsedStartIndex = inc()
        } ?: break
    }

    return indices
}

/**
 * Alternative for [String.indexOf] to benefit from kotlin nullability check instead of -1 check isa.
 *
 * @return index of the first occurrence of [string] or `null` if none is found.
 *
 * @see CharSequence.lastIndexOfOrNull
 * @see CharSequence.allIndicesOf
 */
@JvmSynthetic // Java consumer code doesn't have nullability check of `?` so no use of below function isa.
fun CharSequence.indexOfOrNull(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val index = indexOf(string, startIndex, ignoreCase)
    return if (index == -1) null else index
}

/**
 * Alternative for [String.lastIndexOf] to benefit from kotlin nullability check instead of -1 check isa.
 *
 * @return index of the last occurrence of [string] or `null` if none is found.
 *
 * @see CharSequence.indexOfOrNull
 * @see CharSequence.allIndicesOf
 */
@JvmSynthetic // Java consumer code doesn't have nullability check of `?` so no use of below function isa.
fun CharSequence.lastIndexOfOrNull(string: String, startIndex: Int = lastIndex, ignoreCase: Boolean = false): Int? {
    val index = lastIndexOf(string, startIndex, ignoreCase)
    return if (index == -1) null else index
}
