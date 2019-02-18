package mohamedalaa.mautils.core_kotlin

/** @return true if first or second == [element] */
operator fun <T> Pair<T, T>?.contains(element: T) = if (this == null) false else first == element || second == element
