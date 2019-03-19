package mohamedalaa.mautils.core_kotlin

/**
 * Parses the string as an [Int] number and returns the result OR [fallback] if the string
 * is not a valid representation of a number, or the string is null isa.
 */
fun String?.toIntOrElse(fallback: Int): Int
    = this?.toIntOrNull() ?: fallback

/** @return index of the first occurrence among [stringsArray] or `null` if none is found. */
@JvmOverloads
fun String.nearestIndexOf(vararg stringsArray: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val indices = stringsArray.mapNotNull { indexOfOrNull(it, startIndex, ignoreCase) }

    return indices.min()
}