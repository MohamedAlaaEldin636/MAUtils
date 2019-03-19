@file:JvmName("IteratorUtils")

package mohamedalaa.mautils.core_kotlin

/** @return next element in `receiver` or null if no more elements in `receiver`. */
fun <T> Iterator<T>.safeNext(): T? = if (hasNext()) next() else null