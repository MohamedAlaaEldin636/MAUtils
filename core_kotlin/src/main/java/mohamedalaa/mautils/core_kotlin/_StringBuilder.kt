@file:JvmName("StringBuilderUtils")

package mohamedalaa.mautils.core_kotlin

/** Quick append to `this` isa, useless in java since there is no operator fun usage in it isa. */
@JvmSynthetic
operator fun StringBuilder.plusAssign(value: String) {
    this.append(value)
}