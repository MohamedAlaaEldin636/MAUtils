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

package mohamedalaa.mautils.core_kotlin

import java.text.SimpleDateFormat
import java.util.*

/**
 * Uses [SimpleDateFormat] to format given `receiver` and it must be time in millis
 *
 * since the standard base time known as "the epoch", namely January 1, 1970, 00:00:00 GMT isa.
 *
 * @see SimpleDateFormat.format
 */
@JvmOverloads
fun Long.format(pattern: String, local: Locale = Locale.getDefault()): String
    = SimpleDateFormat(pattern, local).format(Date(this))