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

@file:JvmName("ContextUtils")

package mohamedalaa.mautils.gson

import android.app.Activity
import android.content.Context
import mohamedalaa.mautils.core_android.extensions.javaStartActivity
import mohamedalaa.mautils.core_android.extensions.startActivity

/**
 * Combination of [startActivity] && [buildBundleGson]
 */
inline fun <reified T : Activity> Context.startActivityBundleGson(vararg values: Any?)
    = startActivity<T>(buildBundleGson(*values))

@JvmName("startActivityBundleGson")
fun <T : Activity> Context.javaStartActivityBundleGson(jClass: Class<T>, vararg values: Any?)
    = javaStartActivity(jClass, buildBundleGson(*values))

/**
 * Combination of [startActivity] && [buildBundleGsonForced]
 */
inline fun <reified T : Activity> Context.startActivityBundleGsonForced(vararg values: Any?)
    = startActivity<T>(buildBundleGsonForced(*values))

@JvmName("startActivityBundleGsonForced")
fun <T : Activity> Context.javaStartActivityBundleGsonForced(jClass: Class<T>, vararg values: Any?)
    = javaStartActivity(jClass, buildBundleGsonForced(*values))
