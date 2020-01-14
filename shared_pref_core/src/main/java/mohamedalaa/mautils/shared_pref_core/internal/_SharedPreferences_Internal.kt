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
import mohamedalaa.mautils.gson.fromJsonOrNull
import mohamedalaa.mautils.gson.java.GsonConverter
import mohamedalaa.mautils.gson.java.fromJsonOrNullJava
import mohamedalaa.mautils.gson.java.toJsonOrNullJava
import mohamedalaa.mautils.gson.toJsonOrNull
import mohamedalaa.mautils.shared_pref_core.hasKey

@SuppressLint("ApplySharedPref")
@PublishedApi
internal inline fun <reified T> Context.internal_sharedPrefSetComplex(
    fileName: String,

    key: String,
    value: T,
    removeKeyIfValueIsNull: Boolean,

    mode: Int,
    commit: Boolean
): Boolean? {
    val editor = applicationContext.getSharedPreferences(fileName, mode).edit()

    val useJsonConversion = editor.saveOrNotIfNeedJsonConversion(key, value)
    if (useJsonConversion) {
        val json = value.toJsonOrNull()
        if (json == null) {
            if (removeKeyIfValueIsNull) {
                editor.remove(key)
            }else {
                throw RuntimeException(
                    "passed null value while removeKeyIfValueIsNull is false isa"
                )
            }
        }else {
            editor.putString(key, json)
        }
    }

    return if (commit) {
        editor.commit()
    }else {
        editor.apply()

        null
    }
}
@SuppressLint("ApplySharedPref")
@PublishedApi
internal fun <T> Context.internal_sharedPrefSetComplex(
    fileName: String,

    key: String,
    value: T,
    jClass: Class<T>?,
    removeKeyIfValueIsNull: Boolean,

    mode: Int,
    commit: Boolean,

    gsonConverter: GsonConverter<T>? = null
): Boolean? {
    val editor = applicationContext.getSharedPreferences(fileName, mode).edit()

    val useJsonConversion = editor.saveOrNotIfNeedJsonConversion(key, value)
    if (useJsonConversion) {
        val json = if (gsonConverter != null) gsonConverter.toJsonOrNull(value) else value.toJsonOrNullJava(jClass)
        if (json == null) {
            if (removeKeyIfValueIsNull) {
                editor.remove(key)
            }else {
                throw RuntimeException(
                    "passed null value while removeKeyIfValueIsNull is false isa"
                )
            }
        }else {
            editor.putString(key, json)
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
 * @return `true` if saved meaning [value] is not-null && is one of the normally supported types
 * for [SharedPreferences], `false` meaning needs `gson module` conversion isa.
 */
@PublishedApi
internal fun <T> SharedPreferences.Editor.saveOrNotIfNeedJsonConversion(key: String, value: T): Boolean {
    var useJsonConversion = false
    when (value) {
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Set<*> -> {
            try {
                @Suppress("UNCHECKED_CAST")
                putStringSet(key, value as Set<String?>)
            }catch (throwable: ClassCastException) {
                useJsonConversion = true
            }
        }
        else -> useJsonConversion = true
    }
    return useJsonConversion
}

@Suppress("UNCHECKED_CAST")
@PublishedApi
internal inline fun <reified T> Context.internal_sharedPrefGetComplex(
    fileName: String,

    key: String,
    defValue: T,

    mode: Int
): T {
    val sharedPref = applicationContext.getSharedPreferences(fileName, mode)

    val (possibleValue, useJsonConversion) = sharedPref.getAndIfNeedJsonConversion(
        key,
        defValue,
        T::class.java
    )

    return if (useJsonConversion) {
        val value = sharedPref.getString(key, null)?.fromJsonOrNull<T>()

        value as T
    }else {
        possibleValue as T
    }
}
@Suppress("UNCHECKED_CAST")
@Synchronized
@PublishedApi
internal fun <T> Context.internal_sharedPrefGetComplex(
    fileName: String,

    key: String,
    defValue: T,
    jClass: Class<T>?,

    mode: Int,

    gsonConverter: GsonConverter<T>? = null
): T {
    val sharedPref = applicationContext.getSharedPreferences(fileName, mode)

    val (possibleValue, useJsonConversion) = if (jClass == null) {
        null to true
    }else {
        sharedPref.getAndIfNeedJsonConversion(
            key,
            defValue,
            jClass
        )
    }

    return if (useJsonConversion) {
        val value = if (jClass == null) null else sharedPref.getString(key, null)?.run {
            if (gsonConverter != null) gsonConverter.fromJson(this) else fromJsonOrNullJava(jClass)
        }

        value as T
    }else {
        possibleValue as T
    }
}

/**
 * @return [Pair.second] `false` [jClass] is one of the normally supported types for [SharedPreferences],
 * `true` meaning needs `gson module` conversion isa.
 */
@Suppress("UNCHECKED_CAST")
@PublishedApi
internal fun <T> SharedPreferences.getAndIfNeedJsonConversion(
    key: String,
    defValue: T,
    jClass: Class<T>
): Pair<T?, Boolean> {
    if (hasKey(key).not()) {
        return defValue to false
    }

    return when (jClass) {
        String::class.java -> {
            getString(key, defValue as? String) as T to false
        }
        Int::class.javaPrimitiveType, Int::class.javaObjectType -> {
            getInt(key, defValue as? Int ?: 0) as T to false
        }
        Boolean::class.javaPrimitiveType, Boolean::class.javaObjectType -> {
            getBoolean(key, defValue as? Boolean ?: false) as T to false
        }
        Long::class.javaPrimitiveType, Long::class.javaObjectType -> {
            getLong(key, defValue as? Long ?: 0L) as T to false
        }
        Float::class.javaPrimitiveType, Float::class.javaObjectType -> {
            getFloat(key, defValue as? Float ?: 0f) as T to false
        }
        Set::class.java -> {
            try {
                getStringSet(key, defValue as? Set<String?>) as T to false
            }catch (classCastException: ClassCastException) {
                null to true
            }
        }
        else -> null to true
    }
}
