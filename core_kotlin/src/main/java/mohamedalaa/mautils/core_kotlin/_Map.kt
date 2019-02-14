@file:JvmName("MapUtils")

package mohamedalaa.mautils.core_kotlin

/** @return same as [Map.containsKey] with benefit of returning false if `this` is null */
operator fun <K, V> Map<out K, V>?.contains(key: K): Boolean = this?.containsKey(key) ?: false

/**  */
fun <K, V> Map<K, V>?.randomKeyOrNull(): K? = this?.run { keys.toList().randomGetOrNull() }

/** Converts map to [List]<[Pair]<[Map.Entry.key], [Map.Entry.value]>> */
fun <K, V> Map<K, V>.mapToPairList(): List<Pair<K, V>> = map { it.key to it.value }