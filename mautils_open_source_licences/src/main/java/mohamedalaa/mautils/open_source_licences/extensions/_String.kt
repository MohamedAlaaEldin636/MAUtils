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

package mohamedalaa.mautils.open_source_licences.extensions

internal fun String.indexOfOrNull(string: String): Int? {
    val index = indexOf(string)
    return if (index == -1) null else index
}

internal operator fun String?.contains(charArray: CharArray): Boolean {
    if (this == null) {
        return false
    }

    charArray.forEach {
        if (it !in this) {
            return false
        }
    }

    return true
}

internal fun List<String>.toSingleString(): String {
    var result = ""
    forEach {
        result += it
    }

    return result
}