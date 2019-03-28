@file:JvmName("GeneralMapUtils")

package mohamedalaa.mautils.core_kotlin

/**
 * Creates a new read-only [Map], where each element is calculated
 * by calling the specified [buildAction] fun, the returned [Map.size] is guaranteed to be
 * like [size] if [checkSize] is true since otherwise null would return, else then [Map.size] is unknown.
 *
 * @return `null` if [checkSize] is true && after applying [buildAction] to each index
 * the result had 2 similar keys so returned [Map] has different size than given [size].
 */
fun <K, V> buildMapOrNull(size: Int, checkSize: Boolean = true, buildAction: (Int) -> Pair<K, V>): Map<K, V>?
    = List(size) { it }.associate { buildAction(it) }.run { if (checkSize && size != this.size) null else this }

/**
 * Same as [buildMapOrNull], but instead of the case that returns `null`
 * a [RuntimeException] is thrown instead isa.
 */
fun <K, V> buildMap(size: Int, checkSize: Boolean = true, buildAction: (Int) -> Pair<K, V>): Map<K, V>
    = buildMapOrNull(size, checkSize, buildAction) ?: throw RuntimeException("Generated map's size != size param")