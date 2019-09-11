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

/** Remove all mapping that has keys inside given [list] isa. */
fun <K> MutableMap<K, *>.removeAll(list: List<K>): Unit = list.forEach { this.remove(it) }

/** Remove all mapping that has keys inside given [map] keys isa. */
fun <K> MutableMap<K, *>.removeAll(map: Map<K, *>): Unit = removeAll(map.keys.toList())