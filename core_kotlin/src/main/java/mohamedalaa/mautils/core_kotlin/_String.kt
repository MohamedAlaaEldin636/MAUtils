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

@file:JvmName("StringUtils")

package mohamedalaa.mautils.core_kotlin

/**
 * Parses the string as an [Int] number and returns the result OR [fallback] if the string
 * is not a valid representation of a number, or the string is null isa.
 */
fun String?.toIntOrElse(fallback: Int): Int
    = this?.toIntOrNull() ?: fallback

/** @return index of the first occurrence among [stringsArray] or `null` if none is found. */
@JvmOverloads
fun String.nearestIndexOf(vararg stringsArray: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val indices = stringsArray.mapNotNull { indexOfOrNull(it, startIndex, ignoreCase) }

    return indices.min()
}