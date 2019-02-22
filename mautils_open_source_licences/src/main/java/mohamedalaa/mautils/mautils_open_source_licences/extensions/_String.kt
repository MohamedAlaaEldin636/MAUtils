package mohamedalaa.mautils.mautils_open_source_licences.extensions

fun String.indexOfOrNull(string: String): Int? {
    val index = indexOf(string)
    return if (index == -1) null else index
}