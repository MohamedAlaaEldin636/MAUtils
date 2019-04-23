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

package mohamedalaa.mautils.core_kotlin

/**
 * For kotlin devs this is better than [String.indexOf] due to nullability checks in the language isa.
 *
 * @return index of the first occurrence of [string] or `null` if none is found.
 */
@JvmSynthetic
fun CharSequence.indexOfOrNull(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val index = indexOf(string, startIndex, ignoreCase)
    return if (index == -1) null else index
}