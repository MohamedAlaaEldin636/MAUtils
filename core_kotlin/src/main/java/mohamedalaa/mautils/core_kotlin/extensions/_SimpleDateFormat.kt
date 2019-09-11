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
 * this is better since above due to languages might change isa.
 *
 * @param pairs [Pair.second] == format -> means to format it (true) or ignore it (false) and put it as it is isa.
 *
todo docs val date = timeGameStarted.format("d MMM, yyyy $at : a")

e3mel format kaza marra isa
 */
@JvmOverloads
fun Long.format(vararg pairs: Pair<String, Boolean>, local: Locale = Locale.getDefault()): String {
    return pairs.joinToString(separator = "") {
        if (it.second) format(it.first, local) else it.first
    }
}