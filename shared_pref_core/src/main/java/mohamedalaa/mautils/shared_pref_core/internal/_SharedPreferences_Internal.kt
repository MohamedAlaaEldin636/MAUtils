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

@file:Suppress("FunctionName")

package mohamedalaa.mautils.shared_pref_core.internal

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import mohamedalaa.mautils.shared_pref_core.hasKey
import mohamedalaa.mautils.shared_pref_core.sharedPrefGet
import mohamedalaa.mautils.shared_pref_core.sharedPrefSet

/**
 * @param convertToString is used if [value] is not one of the supported types by [SharedPreferences] isa.
 *
 * @see [sharedPrefSet]
 */
@SuppressLint("ApplySharedPref")
@Synchronized
@PublishedApi
internal fun <T> Context.internal_sharedPrefSetComplex(
    fileName: String,

    key: String,
    value: T,
    removeKeyIfValueIsNull: Boolean,

    mode: Int,
    commit: Boolean,

    convertToString: (T) -> String?
): Boolean? {
    val editor = applicationContext.getSharedPreferences(fileName, mode).edit()

    when (value) {
        is String -> editor.putString(key, value)
        is Int -> editor.putInt(key, value)
        is Boolean -> editor.putBoolean(key, value)
        is Long -> editor.putLong(key, value)
        is Float -> editor.putFloat(key, value)
        is Set<*> -> {
            try {
                @Suppress("UNCHECKED_CAST")
                editor.putStringSet(key, value as Set<String?>)
            }catch (throwable: ClassCastException) {
                convertToString(value).apply {
                    if (this != null) {
                        editor.putString(key, this)
                    }else {
                        throw RuntimeException(
                            "passed null value while removeKeyIfValueIsNull is false from .toJsonOrNull() conversion isa"
                        )
                    }
                }
            }
        }
        else -> {
            if (value == null) {
                if (removeKeyIfValueIsNull) {
                    editor.remove(key)
                }else {
                    throw RuntimeException(
                        "passed null value while removeKeyIfValueIsNull is false isa"
                    )
                }
            }else {
                convertToString(value).apply {
                    if (this != null) {
                        editor.putString(key, this)
                    }else {
                        throw RuntimeException(
                            "passed null value while removeKeyIfValueIsNull is false from .toJsonOrNull() conversion isa"
                        )
                    }
                }
            }
        }
    }

    return if (commit) {
        editor.commit()
    }else {
        editor.apply()

        null
    }
}

/**
 * @param convertFromString is used if [jClass] don't refer to any of the supported types by [SharedPreferences] isa.
 *
 * @see [sharedPrefGet]
 */
@Suppress("UNCHECKED_CAST")
@Synchronized
@PublishedApi
internal fun <T> Context.internal_sharedPrefGetComplex(
    fileName: String,

    key: String,
    defValue: T,
    jClass: Class<T>,

    mode: Int,

    convertFromString: (String?) -> T
): T {
    val sharedPref = applicationContext.getSharedPreferences(fileName, mode)

    if (sharedPref.hasKey(key).not()) {
        return defValue
    }

    return when (jClass) {
        String::class.java -> {
            sharedPref.getString(key, defValue as? String) as T
        }
        Int::class.javaPrimitiveType, Int::class.javaObjectType -> {
            sharedPref.getInt(key, defValue as? Int ?: 0) as T
        }
        Boolean::class.javaPrimitiveType, Boolean::class.javaObjectType -> {
            sharedPref.getBoolean(key, defValue as? Boolean ?: false) as T
        }
        Long::class.javaPrimitiveType, Long::class.javaObjectType -> {
            sharedPref.getLong(key, defValue as? Long ?: 0L) as T
        }
        Float::class.javaPrimitiveType, Float::class.javaObjectType -> {
            sharedPref.getFloat(key, defValue as? Float ?: 0f) as T
        }
        Set::class.java -> {
            try {
                sharedPref.getStringSet(key, defValue as Set<String?>) as T
            }catch (classCastException: ClassCastException) {
                convertFromString(
                    sharedPref.getString(key, null)
                )
            }
        }
        else -> {
            convertFromString(
                sharedPref.getString(key, null)
            )
        }
    }
}
