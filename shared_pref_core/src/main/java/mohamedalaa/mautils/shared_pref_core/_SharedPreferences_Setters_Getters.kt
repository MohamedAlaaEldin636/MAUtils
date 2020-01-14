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
import mohamedalaa.mautils.gson.java.GsonConverter
import mohamedalaa.mautils.gson.toJsonOrNull
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
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
 * 2. Any other type which will be converted using [toJsonOrNull] isa.
 *
 * @return value of [SharedPreferences.Editor.commit] or null if Used [SharedPreferences.Editor.apply] isa.
 *
 * @throws RuntimeException if [value] is null && [removeKeyIfValueIsNull] is false, or conversion
 * of custom type returns null && [removeKeyIfValueIsNull] is false isa.
 */
@SuppressLint("ApplySharedPref")
inline fun <reified T> Context.sharedPrefSet(
    fileName: String,

    key: String,
    value: T,
    removeKeyIfValueIsNull: Boolean = false,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false
): Boolean? {
    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        removeKeyIfValueIsNull,
        mode,
        commit
    )
}

/**
 * - Deprecated use instead [sharedPrefSet] without [gsonConverter] param, since it's not
 * needed for kotlin consumer code anymore isa.
 */
@Deprecated(
    "No need for gsonConversion for kotlin consumer code",
    level = DeprecationLevel.WARNING
)
@SuppressLint("ApplySharedPref")
inline fun <reified T> Context.sharedPrefSet(
    fileName: String,

    key: String,
    value: T,
    removeKeyIfValueIsNull: Boolean = false,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false,

    @Suppress("UNUSED_PARAMETER")
    gsonConverter: GsonConverter<T>? = null
): Boolean? {
    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        removeKeyIfValueIsNull,
        mode,
        commit
    )
}
/**
 * @param jClass not needed to be provided as [Class] will be inferred from [value]
 * **BUT** needed in case of using `superclass` of the given [value] which is needed in
 * case of classes annotated with [MASealedAbstractOrInterface] isa.
 *
 * @see [sharedPrefSet]
 */
@SuppressLint("ApplySharedPref")
@JvmName("set")
@JvmOverloads
fun <T> Context.javaSharedPrefSet(
    fileName: String,

    key: String,
    value: T,
    jClass: Class<T>?,
    removeKeyIfValueIsNull: Boolean = false,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false,

    gsonConverter: GsonConverter<T>? = null
): Boolean? {
    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        jClass,
        removeKeyIfValueIsNull,
        mode,
        commit,
        gsonConverter
    )
}

/**
 * - [T] can be `null`, however if not-null and key doesn't exist then an exception will be thrown isa.
 *
 * @param [T] type of value you want to retrieve isa.
 *
 * @return instance of [T] with given [key] isa.
 */
inline fun <reified T> Context.sharedPrefGet(
    fileName: String,

    key: String,
    defValue: T,

    mode: Int = Context.MODE_PRIVATE
): T {
    return internal_sharedPrefGetComplex(
        fileName,
        key,
        defValue,
        mode
    )
}
/**
 * - Deprecated use instead [sharedPrefGet] without [gsonConverter] param, since it's not
 * needed for kotlin consumer code anymore isa.
 */
@Deprecated(
    "No need for gsonConversion for kotlin consumer code",
    level = DeprecationLevel.WARNING
)
inline fun <reified T> Context.sharedPrefGet(
    fileName: String,

    key: String,
    defValue: T,

    mode: Int = Context.MODE_PRIVATE,

    @Suppress("UNUSED_PARAMETER") gsonConverter: GsonConverter<T>? = null
): T {
    return internal_sharedPrefGetComplex(
        fileName,
        key,
        defValue,
        mode
    )
}

/**
 * @param jClass can be `null` ONLY if [gsonConverter] isn't `null` otherwise exception is thrown
 * since it is impossible to infer the type of the value you are trying to get isa.
 */
@Suppress("UNCHECKED_CAST")
@JvmName("get")
@JvmOverloads
fun <T> Context.javaSharedPrefGet(
    fileName: String,

    key: String,
    defValue: T,
    jClass: Class<T>?,

    mode: Int = Context.MODE_PRIVATE,

    gsonConverter: GsonConverter<T>? = null
): T {
    if (jClass == null && gsonConverter == null) throw RuntimeException(
        "only 1 of jClass or gsonConverter can be `null` not both isa."
    )

    return internal_sharedPrefGetComplex(
        fileName,
        key,
        defValue,
        jClass,
        mode,
        gsonConverter
    )
}
