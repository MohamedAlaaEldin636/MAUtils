package mohamedalaa.mautils.core_kotlin

fun String.indexOfOrNull(string: String): Int? {
    val index = indexOf(string)
    return if (index == -1) null else index
}

/**
 * @return Parses the string as an [Int] number and returns the result OR [fallback] if the string
 * is not a valid representation of a number, or the string is null isa.
 */
fun String?.toIntOrElse(fallback: Int): Int {
    this?.apply {
        return@toIntOrElse toIntOrNull() ?: fallback
    }

    return fallback
}