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
@file:JvmName("SharedPrefUtils")

package mohamedalaa.mautils.shared_pref_core

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import mohamedalaa.mautils.core_kotlin.extensions.contains

/**
 * - **NOTE** All removals are done first isa.
 * - Calls [SharedPreferences.Editor.clear] isa.
 * @return value of [SharedPreferences.Editor.commit] or null if Used [SharedPreferences.Editor.apply] isa.
 *
 * @see sharedPrefRemoveKey
 */
@SuppressLint("ApplySharedPref")
@JvmName("clearAll")
@JvmOverloads
fun Context.sharedPrefClearAll(
    fileName: String,
    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false
): Boolean? {
    val editor = applicationContext.getSharedPreferences(fileName, mode).edit()
    editor.clear()

    return if (commit) {
        editor.commit()
    }else {
        editor.apply()

        null
    }
}

/**
 * - **NOTE** All removals are done first isa.
 * @return value of [SharedPreferences.Editor.commit] or null if Used [SharedPreferences.Editor.apply] isa.
 *
 * @see sharedPrefClearAll
 * @see sharedPrefHasKey
 */
@SuppressLint("ApplySharedPref")
@JvmName("removeKey")
@JvmOverloads
fun Context.sharedPrefRemoveKey(
    fileName: String,
    key: String,
    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false
): Boolean? {
    val editor = applicationContext.getSharedPreferences(fileName, mode).edit()
    editor.remove(key)

    return if (commit) {
        editor.commit()
    }else {
        editor.apply()

        null
    }
}

/** @return true if [key] exists in `receiver`, otherwise false is returned isa. */
fun SharedPreferences.hasKey(key: String) = key in all?.keys

/** @return true if [key] exists in this [fileName] with the specified [mode], otherwise false is returned isa. */
@JvmName("hasKey")
@JvmOverloads
fun Context.sharedPrefHasKey(
    fileName: String,
    key: String,
    mode: Int = Context.MODE_PRIVATE
): Boolean {
    val sharedPref = applicationContext.getSharedPreferences(fileName, mode)

    return key in sharedPref.all?.keys
}
