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

@file:JvmName("SimpleDateFormat")

package mohamedalaa.mautils.core_kotlin.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Uses [SimpleDateFormat] to format given `receiver` and it must be time in millis
 *
 * since the standard base time known as "the epoch", namely January 1, 1970, 00:00:00 GMT isa.
 *
 * See [https://developer.android.com/reference/java/text/SimpleDateFormat.html](https://developer.android.com/reference/java/text/SimpleDateFormat.html)
 *
 * @see SimpleDateFormat.format
 */
@JvmOverloads
fun Long.format(pattern: String, local: Locale = Locale.getDefault()): String
    = SimpleDateFormat(pattern, local).format(Date(this))

/**
 * - Uses [SimpleDateFormat] to format each item in given array using [format] only if [Pair.second]
 * is `true`, otherwise it uses the string in [Pair.first] as-is, then joins the array with
 * [joinToString] isa.
 *
 * - Useful in cases where you want to have a specific string which might change according to
 * language setting for Ex. notice the bold word -> 7 Jan **at** 7:33 PM what if you wanna change
 * **at** according to selected language of the device then this function is better be used
 * and here is how to use it isa.
 * ```
 * System.currentTimeMillis().format(
 *      "d MMM" to true,
 *      getString(R.string.at) to false,
 *      "h:mm a" to true
 * )
 * ```
 */
@JvmOverloads
fun Long.format(vararg pairs: Pair<String, Boolean>, local: Locale = Locale.getDefault()): String {
    return pairs.joinToString(separator = "") {
        if (it.second) format(it.first, local) else it.first
    }
}
