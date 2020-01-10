/*
 * Copyright (c) 2019 Mohamed Alaa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

@file:JvmMultifileClass
@file:JvmName("MapUtils")

package mohamedalaa.mautils.core_kotlin.extensions

/** @return same as [Map.containsKey] with benefit of returning false if `this` is null */
operator fun <K, V> Map<out K, V>?.contains(key: K): Boolean = this?.containsKey(key) ?: false

/** @return random [Map.Entry] or null, in case of any `receiver` is null or [Map.entries] is empty isa. */
fun <K, V> Map<K, V>?.randomEntryOrNull(): Map.Entry<K, V>? = this?.entries.randomOrNull()

/**
 * @return random [Map.Entry] or throws exception, in case of any error isa.
 *
 * @throws RuntimeException in case of any error while getting random item isa.
 *
 * @see randomKey
 * @see randomValue
 */
fun <K, V> Map<K, V>?.randomEntry(): Map.Entry<K, V> = randomEntryOrNull().throwIfNull(
    "Cannot get random entry from $this"
)

/** @return random [Map.Entry.key] or null, in case of any error */
fun <K, V> Map<K, V>?.randomKeyOrNull(): K? = this?.keys.randomOrNull()

/**
 * @return random [Map.Entry.key] or throws exception, in case of any error isa.
 *
 * @throws RuntimeException in case of any error while getting random item isa.
 *
 * @see randomEntry
 * @see randomValue
 */
fun <K, V> Map<K, V>?.randomKey(): K = randomKeyOrNull().throwIfNull(
    "Cannot get random key from $this"
)

/** @return random [Map.Entry.value] or null, in case of any error */
fun <K, V> Map<K, V>?.randomValueOrNull(): V? = this?.values.randomOrNull()

/**
 * @return random [Map.Entry.value] or throws exception, in case of any error isa.
 *
 * @throws RuntimeException in case of any error while getting random item isa.
 *
 * @see randomEntry
 * @see randomKey
 */
fun <K, V> Map<K, V>?.randomValue(): V = randomValueOrNull().throwIfNull(
    "Cannot get random key from $this"
)
