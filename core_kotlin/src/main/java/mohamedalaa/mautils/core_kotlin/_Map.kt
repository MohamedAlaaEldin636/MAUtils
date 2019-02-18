@file:JvmName("MapUtils")

package mohamedalaa.mautils.core_kotlin

/** @return same as [Map.containsKey] with benefit of returning false if `this` is null */
operator fun <K, V> Map<out K, V>?.contains(key: K): Boolean = this?.containsKey(key) ?: false

/** @return random [Map.Entry] or null, in case of any error */
fun <K, V> Map<K, V>?.randomEntryOrNull(): Map.Entry<K, V>? = this?.run { entries.toList().randomGetOrNull() }

/**
 * @return random [Map.Entry] or throws exception, in case of any error isa.
 *
 * @throws RuntimeException in case of any error while getting random item isa.
 *
 * @see randomKey
 * @see randomValue
 */
fun <K, V> Map<K, V>?.randomEntry(): Map.Entry<K, V> = randomEntryOrNull()
    ?: throw RuntimeException("Cannot get random entry from $this")

/** @return random [Map.Entry.key] or null, in case of any error */
fun <K, V> Map<K, V>?.randomKeyOrNull(): K? = this?.run { keys.toList().randomGetOrNull() }

/**
 * @return random [Map.Entry.key] or throws exception, in case of any error isa.
 *
 * @throws RuntimeException in case of any error while getting random item isa.
 *
 * @see randomEntry
 * @see randomValue
 */
fun <K, V> Map<K, V>?.randomKey(): K = randomKeyOrNull()
    ?: throw RuntimeException("Cannot get random key from $this")

/** @return random [Map.Entry.value] or null, in case of any error */
fun <K, V> Map<K, V>?.randomValueOrNull(): V? = this?.run { values.toList().randomGetOrNull() }

/**
 * @return random [Map.Entry.value] or throws exception, in case of any error isa.
 *
 * @throws RuntimeException in case of any error while getting random item isa.
 *
 * @see randomEntry
 * @see randomKey
 */
fun <K, V> Map<K, V>?.randomValue(): V = randomValueOrNull()
    ?: throw RuntimeException("Cannot get random key from $this")

/** Converts map to [List]<[Pair]<[Map.Entry.key], [Map.Entry.value]>> */
fun <K, V> Map<K, V>.mapToPairList(): List<Pair<K, V>> = map { it.key to it.value }