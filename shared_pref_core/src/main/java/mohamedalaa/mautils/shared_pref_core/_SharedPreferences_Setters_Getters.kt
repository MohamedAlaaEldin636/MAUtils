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
@file:Suppress("FunctionName")

package mohamedalaa.mautils.shared_pref_core

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.java.GsonConverter
import mohamedalaa.mautils.gson.java.fromJsonJava
import mohamedalaa.mautils.gson.java.toJsonOrNullJava
import mohamedalaa.mautils.gson.toJsonOrNull
import mohamedalaa.mautils.shared_pref_core.internal.internal_sharedPrefGetComplex
import mohamedalaa.mautils.shared_pref_core.internal.internal_sharedPrefSetComplex

/**
 * ### VIP Notes
 *
 * - [value] can **Only** be `null` if [removeKeyIfValueIsNull] is true
 * which in this case [sharedPrefRemoveKey] will be used with the given [key] isa,
 * otherwise a [RuntimeException] will be thrown since actually in shared preferences no null values
 * are saved but a null value removes the key from the shared pref instead.
 *
 * ### Supported types
 *
 * 1. All Supported types by [SharedPreferences].
 *
 * 2. Any other type which will be converted by [gsonConverter] if not-null else [toJsonOrNull]
 * will be used isa.
 *
 * @return value of [SharedPreferences.Editor.commit] or null if Used [SharedPreferences.Editor.apply] isa.
 *
 * @throws RuntimeException if [value] is null && [removeKeyIfValueIsNull] is false, or conversion
 * of custom type returns null && [removeKeyIfValueIsNull] is false isa.
 */
@SuppressLint("ApplySharedPref")
inline fun <reified T> Context.sharedPrefSetComplex(
    fileName: String,

    key: String,
    value: T,
    removeKeyIfValueIsNull: Boolean = false,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false,

    gsonConverter: GsonConverter<T>? = null
): Boolean? {
    val convertToString: (T) -> String? = {
        if (gsonConverter != null) gsonConverter.toJsonOrNull(it) else it.toJsonOrNull()
    }

    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        removeKeyIfValueIsNull,
        mode,
        commit,
        convertToString
    )
}
/** @see [sharedPrefSetComplex] */
@SuppressLint("ApplySharedPref")
@JvmName("sharedPrefSetComplexJava")
@JvmOverloads
fun <T> Context.javaSharedPrefSetComplex(
    fileName: String,

    key: String,
    value: T,
    jClass: Class<T>,
    removeKeyIfValueIsNull: Boolean = false,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false,

    gsonConverter: GsonConverter<T>? = null
): Boolean? {
    val convertToString: (T) -> String? = {
        if (gsonConverter != null) gsonConverter.toJsonOrNull(it) else it.toJsonOrNullJava(jClass)
    }

    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        removeKeyIfValueIsNull,
        mode,
        commit,
        convertToString
    )
}

/**
 * Acc. to [T]'s class we get the value directly if one of the supported values of [SharedPreferences]
 * otherwise if [gsonConverter] is not null then [GsonConverter.fromJson] is used else [fromJson] is used isa.
 *
 * @see [sharedPrefSetComplex]
 */
inline fun <reified T> Context.sharedPrefGetComplex(
    fileName: String,

    key: String,
    defValue: T,

    mode: Int = Context.MODE_PRIVATE,

    gsonConverter: GsonConverter<T>? = null
): T {
    val convertFromString: (String?) -> T = {
        if (gsonConverter != null) gsonConverter.fromJson(it) else it.fromJson()
    }

    return internal_sharedPrefGetComplex(
        fileName,
        key,
        defValue,
        T::class.java,
        mode,
        convertFromString
    )
}
/**
 * @see sharedPrefGetComplex
 */
@Suppress("UNCHECKED_CAST")
@JvmName("sharedPrefGetComplexJava")
@JvmOverloads
fun <T> Context.javaSharedPrefGetComplex(
    fileName: String,

    key: String,
    defValue: T,
    jClass: Class<T>,

    mode: Int = Context.MODE_PRIVATE,

    gsonConverter: GsonConverter<T>? = null
): T {
    val convertFromString: (String?) -> T = {
        if (gsonConverter != null) gsonConverter.fromJson(it) else it.fromJsonJava(jClass)
    }

    return internal_sharedPrefGetComplex(
        fileName,
        key,
        defValue,
        jClass,
        mode,
        convertFromString
    )
}
