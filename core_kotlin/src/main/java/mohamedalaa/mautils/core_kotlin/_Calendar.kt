package mohamedalaa.mautils.core_kotlin

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
