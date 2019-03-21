@file:JvmName("IntentUtils")

package mohamedalaa.mautils.core_android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

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

// ---- Another receiver

/**
 * Using [Context.startActivity] with given [url] to launch a browser isa.
 *
 * @param url web link url to launch isa.
 * @param showToastOnFailure if true a toast msg
 * [R.string.you_do_not_have_application_that_can_open_web_links] will be shown isa.
 */
fun Context.launchWebLink(url: String, showToastOnFailure: Boolean = true, createIntentChooser: Boolean = false): Boolean {
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    val canHandleIntent = webIntent.resolveActivity(packageManager) != null
    if (canHandleIntent) {
        if (createIntentChooser) {
            startActivity(Intent.createChooser(webIntent, getString(R.string.open_web_link)))
        }else {
            startActivity(webIntent)
        }
    }else if (showToastOnFailure) {
        toast(getString(R.string.you_do_not_have_application_that_can_open_web_links), Toast.LENGTH_SHORT)
    }

    return canHandleIntent
}