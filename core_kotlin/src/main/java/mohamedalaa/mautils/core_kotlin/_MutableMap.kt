@file:JvmMultifileClass
@file:JvmName("MapUtils")

package mohamedalaa.mautils.core_kotlin

/** Remove all mapping that has keys inside given [list] isa. */
fun <K> MutableMap<K, *>.removeAll(list: List<K>): Unit = list.forEach { this.remove(it) }

/** Remove all mapping that has keys inside given [map] keys isa. */
fun <K> MutableMap<K, *>.removeAll(map: Map<K, *>): Unit = removeAll(map.keys.toList())