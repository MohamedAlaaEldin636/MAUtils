package mohamedalaa.mautils.mautils_view_model.extensions

fun String.indexOfOrNull(string: String): Int? {
    val index = indexOf(string)
    return if (index == -1) null else index
}