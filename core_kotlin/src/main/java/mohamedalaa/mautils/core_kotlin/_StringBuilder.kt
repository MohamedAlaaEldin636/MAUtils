package mohamedalaa.mautils.core_kotlin

/** Quick append to `this` isa. */
operator fun StringBuilder.plusAssign(value: String) {
    this.append(value)
}