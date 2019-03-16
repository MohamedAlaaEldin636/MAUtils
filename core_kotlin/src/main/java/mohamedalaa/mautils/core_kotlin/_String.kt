package mohamedalaa.mautils.core_kotlin

/**
 * For kotlin users this is better than [String.indexOf] due to nullability checks in the language isa.
 *
 * @return index of the first occurrence of [string] or `null` if none is found.
 */
fun String.indexOfOrNull(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val index = indexOf(string, startIndex, ignoreCase)
    return if (index == -1) null else index
}

/**
 * Parses the string as an [Int] number and returns the result OR [fallback] if the string
 * is not a valid representation of a number, or the string is null isa.
 */
fun String?.toIntOrElse(fallback: Int): Int {
    this?.apply {
        return@toIntOrElse toIntOrNull() ?: fallback
    }

    return fallback
}

/** @return index of the first occurrence among [stringsArray] or `null` if none is found. */
fun String.nearestIndexOf(vararg stringsArray: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val indices = stringsArray.mapNotNull { indexOfOrNull(it, startIndex, ignoreCase) }

    return indices.min()
}