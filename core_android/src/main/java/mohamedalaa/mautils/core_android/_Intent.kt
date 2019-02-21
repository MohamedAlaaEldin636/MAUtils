@file:JvmName("IntentUtils")

package mohamedalaa.mautils.core_android

import android.content.Intent
import android.os.Bundle

/**
 * Exactly same as [Intent.getExtras].get([key]), but easier for nullability checks isa.
 */
inline fun <reified T> Intent?.getExtraOrNull(key: String): T? {
    this?.apply {
        extras?.apply {
            return this.get(key) as? T
        }
    }

    return null
}

/**
 * same as [getExtraOrNull], but throws exception instead if returning null isa.
 *
 * @throws RuntimeException in case of any error isa.
 */
inline fun <reified T> Intent?.getExtra(key: String): T {
    this?.apply {
        extras?.apply {
            return this.get(key) as T
        }
    }

    throw RuntimeException("Extras or this intent is null")
}

/**
 * @return true if `receiver` is null or it's [Intent.getExtras] is null or empty
 *
 * @see [Bundle.isNullOrEmpty]
 */
fun Intent?.isNullOrEmpty(): Boolean = this == null || extras.isNullOrEmpty()