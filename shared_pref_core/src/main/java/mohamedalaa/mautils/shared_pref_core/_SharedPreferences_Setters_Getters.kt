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
import mohamedalaa.mautils.gson.java.toJsonOrNullJava
import mohamedalaa.mautils.gson.toJsonOrNull
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
import mohamedalaa.mautils.shared_pref_core.internal.internal_sharedPrefGetComplex
import mohamedalaa.mautils.shared_pref_core.internal.internal_sharedPrefSetComplex

/**
 * - Supports any type not just the ones supported by [SharedPreferences], Thanks to [toJsonOrNull]
 * conversion isa.
 *
 * @param value if `null` then [sharedPrefRemoveKey] will instead be used with the given [key], Since
 * in [SharedPreferences] there is nothing called saving `null` value it is instead a deletion for
 * the key isa.
 *
 * @return value of [SharedPreferences.Editor.commit] or null if Used [SharedPreferences.Editor.apply] isa.
 */
@SuppressLint("ApplySharedPref")
inline fun <reified T> Context.sharedPrefSet(
    fileName: String,

    key: String,
    value: T,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false
): Boolean? {
    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        mode,
        commit
    )
}

/**
 * ### Deprecation Reasons
 * - Use instead [sharedPrefSet] without [gsonConverter] param, since it's not
 * needed for kotlin consumer code anymore isa.
 * - Also no need for [removeKeyIfValueIsNull] since it should always be considered `true`, as it
 * should be known from devs there is no save to `null` value it is just a deletion for [SharedPreferences] isa.
 */
@Deprecated(
    "Use the other overloaded function of sharedPrefSet instead isa.",
    ReplaceWith(
        "sharedPrefSet(fileName = fileName, key = key, value = value, mode = mode, commit = commit)"
    ),
    DeprecationLevel.WARNING
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
    if (value == null && removeKeyIfValueIsNull.not()) {
        // Just to maintain compatibility isa.
        throw RuntimeException(
            "passed null value while removeKeyIfValueIsNull is false isa"
        )
    }
    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        mode,
        commit
    )
}
/**
 * - Supports any type not just the ones supported by [SharedPreferences], Thanks to
 * the conversion of [toJsonOrNullJava] or [gsonConverter] (if not `null`) isa.
 *
 * @param value if `null` then [sharedPrefRemoveKey] will instead be used with the given [key], Since
 * in [SharedPreferences] there is nothing called saving `null` value it is instead a deletion for
 * the key isa.
 *
 * @param jClass not needed to be provided as [Class] will be inferred from [value]
 * **BUT needed in case of using** `superclass` of the given [value] which is needed in
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

    jClass: Class<T>? = null,
    gsonConverter: GsonConverter<T>? = null,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false
): Boolean? {
    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        jClass,
        mode,
        commit,
        gsonConverter
    )
}
/**
 * ### Deprecation Reasons
 * - Use instead [javaSharedPrefSet] with nullable [jClass] param since most of the time it might
 * not be needed, check out [javaSharedPrefSet] docs for more info isa.
 * - Also no need for [removeKeyIfValueIsNull] since it should always be considered `true`, as it
 * should be known from devs there is no save to `null` value it is just a deletion for [SharedPreferences] isa.
 * - arrangement of params should be [jClass] -> [gsonConverter] -> [mode] -> [commit] for a better
 * coding experience isa.
 */
@Deprecated(
    "Use the other overloaded function of javaSharedPrefSet instead isa.",
    ReplaceWith(
        "javaSharedPrefSet(fileName = fileName, key = key, value = value, jClass = jClass, gsonConverter = gsonConverter, mode = mode, commit = commit)"
    ),
    DeprecationLevel.WARNING
)
@SuppressLint("ApplySharedPref")
@JvmName("set")
@JvmOverloads
fun <T> Context.javaSharedPrefSet(
    fileName: String,

    key: String,
    value: T,
    jClass: Class<T>,
    removeKeyIfValueIsNull: Boolean,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false,

    gsonConverter: GsonConverter<T>? = null
): Boolean? {
    if (value == null && removeKeyIfValueIsNull.not()) {
        // Just to maintain compatibility isa.
        throw RuntimeException(
            "passed null value while removeKeyIfValueIsNull is false isa"
        )
    }
    return internal_sharedPrefSetComplex(
        fileName,
        key,
        value,
        jClass,
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
 * @param defValue doesn't have to be provided, If not provided then primitive default values or `null`
 * will be used according to [T] type isa.
 *
 * @return instance of [T] with given [key] isa.
 */
inline fun <reified T> Context.sharedPrefGet(
    fileName: String,

    key: String,
    defValue: T? = null,

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
 * ### Deprecation reasons
 * - Use instead [sharedPrefGet] without [gsonConverter] param, since it's not
 * needed for kotlin consumer code anymore isa.
 * - [defValue] should be optional isa.
 */
@Deprecated(
    "Use the other overloaded function of sharedPrefGet instead isa.",
    ReplaceWith(
        "sharedPrefGet(fileName = fileName, key = key, defValue = defValue, mode = mode)"
    ),
    DeprecationLevel.WARNING
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
 * @param defValue doesn't have to be provided, If not provided then primitive default values or `null`
 * will be used according to [T] type isa.
 *
 * @param jClass can be `null` ONLY if [gsonConverter] isn't `null` otherwise exception is thrown
 * since it is impossible to infer the type of the value you are trying to get isa.
 */
@Suppress("UNCHECKED_CAST")
@JvmName("get")
@JvmOverloads
fun <T> Context.javaSharedPrefGet(
    fileName: String,

    key: String,
    defValue: T? = null,

    jClass: Class<T>? = null,
    gsonConverter: GsonConverter<T>? = null,

    mode: Int = Context.MODE_PRIVATE
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
/**
 * ### Deprecation Reasons
 * - Use instead [javaSharedPrefGet] with nullable [jClass] param since most of the time it might
 * not be needed, check out [javaSharedPrefGet] docs for more info isa.
 * - [defValue] should be optional isa.
 * - arrangement of params should be [j
 * Class] -> [gsonConverter] -> [mode] for a better coding experience isa.
 */
@Deprecated(
    "Use the other overloaded function of javaSharedPrefGet instead isa.",
    ReplaceWith(
        "javaSharedPrefGet(fileName = fileName, key = key, defValue = defValue, jClass = jClass, gsonConverter = gsonConverter, mode = mode)"
    ),
    DeprecationLevel.WARNING
)
@Suppress("UNCHECKED_CAST")
@JvmName("get")
@JvmOverloads
fun <T> Context.javaSharedPrefGet(
    fileName: String,

    key: String,
    defValue: T,
    jClass: Class<T>,

    mode: Int,

    gsonConverter: GsonConverter<T>? = null
): T {
    return internal_sharedPrefGetComplex(
        fileName,
        key,
        defValue,
        jClass,
        mode,
        gsonConverter
    )
}
