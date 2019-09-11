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

@file:JvmName("CalendarUtils")

package mohamedalaa.mautils.core_kotlin.extensions

import java.util.*

/** Exactly same as [Calendar.getInstance] */
val calendar: Calendar
    get() = Calendar.getInstance()

/**
 * Gets current year as string Ex. 2019, use it with [calendar], for more concise code isa,
 * ```
 * val text: String = calendar.currentYearAsString // 2019
 * ```
 */
val Calendar.currentYearAsString: String
    get() = get(Calendar.YEAR).toString()
