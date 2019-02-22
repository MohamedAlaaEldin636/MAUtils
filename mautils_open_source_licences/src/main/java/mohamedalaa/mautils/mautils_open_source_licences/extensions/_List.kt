package mohamedalaa.mautils.mautils_open_source_licences.extensions

/**
 * Returns a [ArrayList] filled with all elements of `this list` isa.
 */
fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}