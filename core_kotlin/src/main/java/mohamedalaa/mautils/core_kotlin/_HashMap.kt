@file:JvmName("HashMapUtils")

package mohamedalaa.mautils.core_kotlin

import java.util.HashMap

/** Remove all mapping that has keys inside given [list] isa. */
fun <K> HashMap<K, *>.removeAll(list: List<K>): Unit = list.forEach { this.remove(it) }

/** Remove all mapping that has keys inside given [map] keys isa. */
fun <K> HashMap<K, *>.removeAll(map: Map<K, *>): Unit = removeAll(map.keys.toList())