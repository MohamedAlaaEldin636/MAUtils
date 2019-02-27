package mohamedalaa.mautils.core_kotlin

operator fun <T> Iterable<T>?.contains(element: T): Boolean {
    if (this == null) {
        return false
    }

    if (this is Collection)
        return contains(element)
    return indexOf(element) >= 0
}