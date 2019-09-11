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

@file:JvmName("SharedPrefUtils")

package mohamedalaa.mautils.core_android.extensions

import android.content.Context

inline fun <reified T> Context.sharedPrefGet(
    fileName: String,
    key: String,
    defValue: T,
    mode: Int = Context.MODE_PRIVATE
): T {
    return javaSharedPrefGet(fileName, key, defValue, T::class.java, mode)
}

inline fun <reified T> Context.sharedPrefSet(
    fileName: String,
    key: String,
    value: T,
    mode: Int = Context.MODE_PRIVATE
) {
    javaSharedPrefSet(fileName, key, value, T::class.java, mode)
}

@JvmName("sharedPrefGet")
@JvmOverloads
fun <T> Context.javaSharedPrefGet(
    fileName: String,
    key: String,
    defValue: T,
    jClass: Class<T>,
    mode: Int = Context.MODE_PRIVATE
): T {
    val sharedPref = applicationContext.getSharedPreferences(fileName, mode)

    if (sharedPref.contains(key).not()) {
        return defValue
    }

    val any: Any? = when (jClass) {
        String::class.java -> sharedPref.getString(key, null)
        Int::class.java, Int::class.javaObjectType -> sharedPref.getInt(key, 0)
        Boolean::class.java, Boolean::class.javaObjectType -> sharedPref.getBoolean(key, false)
        Long::class.java, Long::class.javaObjectType -> sharedPref.getLong(key, 0L)
        Float::class.java, Float::class.javaObjectType -> sharedPref.getFloat(key, 0f)
        Set::class.java -> sharedPref.getStringSet(key, null)
        else -> throw RuntimeException("Unsupported type $jClass in shared pref")
    }

    @Suppress("UNCHECKED_CAST")
    return any as? T ?: defValue
}

@JvmName("sharedPrefSet")
@JvmOverloads
fun <T> Context.javaSharedPrefSet(
    fileName: String,
    key: String,
    value: T,
    jClass: Class<T>,
    mode: Int = Context.MODE_PRIVATE
) {
    val sharedPrefEditor = applicationContext.getSharedPreferences(fileName, mode).edit()

    when (jClass) {
        String::class.java -> sharedPrefEditor.putString(key, value as String)
        Int::class.java, Int::class.javaObjectType -> sharedPrefEditor.putInt(key, value as Int)
        Boolean::class.java, Boolean::class.javaObjectType -> sharedPrefEditor.putBoolean(key, value as Boolean)
        Long::class.java, Long::class.javaObjectType -> sharedPrefEditor.putLong(key, value as Long)
        Float::class.java, Float::class.javaObjectType -> sharedPrefEditor.putFloat(key, value as Float)
        Set::class.java -> {
            @Suppress("UNCHECKED_CAST")
            sharedPrefEditor.putStringSet(key, value as MutableSet<String>)
        }
        else -> throw RuntimeException("Unsupported type $jClass in shared pref")
    }

    sharedPrefEditor.apply()
}
