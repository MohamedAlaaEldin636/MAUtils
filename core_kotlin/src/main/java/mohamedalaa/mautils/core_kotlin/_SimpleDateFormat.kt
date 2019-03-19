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