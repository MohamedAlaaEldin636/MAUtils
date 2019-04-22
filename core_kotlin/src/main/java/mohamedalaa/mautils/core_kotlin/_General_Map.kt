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

@file:JvmName("General_MapUtils")

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