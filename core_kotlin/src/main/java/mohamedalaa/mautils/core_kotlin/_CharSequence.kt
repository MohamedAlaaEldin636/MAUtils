package mohamedalaa.mautils.core_kotlin

operator fun CharSequence?.contains(char: Char): Boolean =
    if (this == null) false else indexOf(char) >= 0