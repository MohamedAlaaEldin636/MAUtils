package mohamedalaa.mautils.mautils_open_source_licences.extensions

internal fun String.indexOfOrNull(string: String): Int? {
    val index = indexOf(string)
    return if (index == -1) null else index
}

internal operator fun String?.contains(charArray: CharArray): Boolean {
    if (this == null) {
        return false
    }

    charArray.forEach {
        if (it !in this) {
            return false
        }
    }

    return true
}