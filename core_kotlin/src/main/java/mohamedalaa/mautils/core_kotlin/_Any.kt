@file:JvmName("AnyUtils")

package mohamedalaa.mautils.core_kotlin

/** @return null if receiver is null, otherwise conversion to string is returned isa. */
fun Any?.toStringOrNull(): String? = this?.toString()

