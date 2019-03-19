@file:JvmName("CharSequenceUtils")

package mohamedalaa.mautils.core_kotlin

import kotlin.text.contains as kotlinContains

/**
 * Same as [kotlinContains] but can check for nullable `receiver` & [other] as well isa.
 */
@Suppress("INAPPLICABLE_OPERATOR_MODIFIER")
@JvmOverloads
operator fun CharSequence?.contains(other: CharSequence?, ignoreCase: Boolean = false): Boolean {
    return this?.kotlinContains(other ?: return false, ignoreCase) ?: false
}

/**
 * Same as [kotlinContains] but can check for nullable `receiver` & [char] as well isa.
 */
@Suppress("INAPPLICABLE_OPERATOR_MODIFIER")
@JvmOverloads
operator fun CharSequence?.contains(char: Char?, ignoreCase: Boolean = false): Boolean {
    return this?.kotlinContains(char ?: return false, ignoreCase) ?: false
}

/**
 * For kotlin devs this is better than [String.indexOf] due to nullability checks in the language isa.
 *
 * @return index of the first occurrence of [string] or `null` if none is found.
 */
@JvmSynthetic
fun CharSequence.indexOfOrNull(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val index = indexOf(string, startIndex, ignoreCase)
    return if (index == -1) null else index
}