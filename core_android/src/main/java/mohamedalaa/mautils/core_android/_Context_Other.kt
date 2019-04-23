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

package mohamedalaa.mautils.core_android

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Now it's easier to launch activity, see below Ex for clarification.
 *
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
 *
 * And in case of using gson module take a look at buildBundleGsonWithKeys.
 *
 * @see buildBundleWithKeys
 */
inline fun <reified T : Activity> Context.startActivity(bundle: Bundle? = null)
    = privateStartActivity(T::class.java, bundle)

@JvmName("startActivity")
@JvmOverloads
fun <T : Activity> Context.javaStartActivity(jClass: Class<T>, bundle: Bundle? = null)
    = privateStartActivity(jClass, bundle)

/**
 * Combination of [startActivity] && [buildBundle]
 */
inline fun <reified T : Activity> Context.startActivityBundle(vararg values: Any?)
    = startActivity<T>(buildBundle(*values))

@JvmName("startActivityBundle")
fun <T : Activity> Context.javaStartActivityBundle(jClass: Class<T>, vararg values: Any?)
    = javaStartActivity(jClass, buildBundle(*values))

// ---- Internal && Private fun

@PublishedApi
internal fun Context.privateStartActivity(jClass : Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, jClass).apply {
        if (bundle != null) {
            this.putExtras(bundle)
        }
    }
    startActivity(intent)
}
