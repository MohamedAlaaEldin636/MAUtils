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

@file:JvmMultifileClass
@file:JvmName("ContextUtils")

package mohamedalaa.mautils.core_android.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import mohamedalaa.mautils.core_android.R

/**
 * Now it's easier to launch activity, see below Ex. for clarification.
 * ```
 * context.startActivity<MyActivity>(
 *      buildBundleWithKeys(
 *          "key_1" to 4, // int
 *          "key_2" to "s", // string
 *          "key_3" to true // boolean
 *          // etc... isa.
 *      )
 * )
 * ```
 * And in case of using gson module in this library take a look at buildBundleGsonWithKeys isa.
 *
 * @see buildBundleWithKeys
 * @see buildBundle
 * @see startActivityBundle
 */
inline fun <reified T : Activity> Context.startActivity(bundle: Bundle? = null)
    = privateStartActivity(T::class.java, bundle)

/**
 * Combination of [startActivity] && [buildBundle]
 */
inline fun <reified T : Activity> Context.startActivityBundle(vararg values: Any?)
    = startActivity<T>(buildBundle(*values))

// ---- Internal && Private fun

@PublishedApi
internal fun Context.privateStartActivity(jClass : Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, jClass).apply {
        putExtras(bundle ?: return@apply)
    }
    startActivity(intent)
}

/**
 * - Tries to launch link by using [Intent.ACTION_VIEW] with [Intent.createChooser] with
 * [R.string.select_an_app] as title, but if can't we check [showToastIfCanNotLaunch] and if true
 * then we show a toast with msg [R.string.no_app_can_handle_this_action] isa.
 * - Note if [useGooglePlayAsLauncher] is used we try to explicitly use Google Play as the launcher
 * if can't we try to check any other browser if also can't then either a toast is shown or not
 * according to value of [showToastIfCanNotLaunch] isa.
 *
 * @param intentModificationsBlock in case you want to make any modifications on [Intent] before
 * launching it isa.
 * @param createIntentChooser if `true` then [Intent.createChooser] will be used to wrap the
 * [Intent] otherwise the [Intent] will be directly used isa.
 *
 * @return `true` if an [Activity] is launched, `false` otherwise isa.
 */
@JvmOverloads
fun Context.launchLink(
    link: String,
    showToastIfCanNotLaunch: Boolean = true,
    useGooglePlayAsLauncher: Boolean = false,
    createIntentChooser: Boolean = true,
    intentModificationsBlock: (Intent.() -> Unit)? = null
): Boolean {
    val packageManager = packageManager ?: return false
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(link)

        intentModificationsBlock?.invoke(this)

        if (useGooglePlayAsLauncher) {
            setPackage("com.android.vending")
        }
    }

    return when {
        intent.resolveActivityInfo(packageManager, 0) != null -> {
            if (useGooglePlayAsLauncher || createIntentChooser.not()) {
                startActivity(intent)
            }else {
                startActivity(Intent.createChooser(intent, getString(R.string.select_an_app)))
            }

            true
        }
        intent.getPackage() == null -> {
            if (showToastIfCanNotLaunch) {
                toast(getString(R.string.no_app_can_handle_this_action))
            }

            false
        }
        else -> {
            intent.setPackage(null)

            when {
                intent.resolveActivityInfo(packageManager, 0) != null -> {
                    if (createIntentChooser) {
                        startActivity(Intent.createChooser(intent, getString(R.string.select_an_app)))
                    }else {
                        startActivity(intent)
                    }

                    return true
                }
                showToastIfCanNotLaunch -> {
                    toast(getString(R.string.no_app_can_handle_this_action))
                }
            }

            false
        }
    }
}

/**
 * - Tries to launches email with [Intent.ACTION_SENDTO] action isa.
 *
 * @return `true` if an [Activity] is launched, `false` otherwise isa.
 */
fun Context.launchEMail(toEMailAddress: List<String>, subject: String? = null, body: String? = null) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:") // only email apps should handle this
        putExtra(Intent.EXTRA_EMAIL, toEMailAddress.toTypedArray())
        subject?.apply { putExtra(Intent.EXTRA_SUBJECT, subject) }
        body?.apply { putExtra(Intent.EXTRA_TEXT, body) }
    }

    if (intent.resolveActivityInfo(packageManager ?: return, 0) != null) {
        startActivity(Intent.createChooser(intent, getString(R.string.select_an_app)))
    }
}
