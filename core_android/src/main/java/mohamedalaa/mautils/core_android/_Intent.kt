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
 * [R.string.you_do_not_have_application_that_can_open_web_links] will be shown, default is true isa.
 * @param createIntentChooser if true [Intent.createChooser] will be used, default is false.
 *
 * @return true if [Intent] can be handled by the system, false means no app can handle it,
 * which if [showToastOnFailure] is true then it's msg will be shown isa.
 *
 * @see toast
 */
@JvmOverloads
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