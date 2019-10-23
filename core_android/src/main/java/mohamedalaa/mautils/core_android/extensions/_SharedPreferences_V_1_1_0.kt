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

package mohamedalaa.mautils.core_android.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import mohamedalaa.mautils.core_android.custom_classes.SharedPrefSupportedTypesParams
import mohamedalaa.mautils.core_kotlin.custom_classes.MutablePair
import mohamedalaa.mautils.core_kotlin.extensions.contains

/**
 * - **NOTE** All removals are done first isa.
 * - Calls [SharedPreferences.Editor.clear] isa.
 * @return value of [SharedPreferences.Editor.commit] or null if Used [SharedPreferences.Editor.apply] isa.
 *
 * @see sharedPrefRemoveKey
 */
@SuppressLint("ApplySharedPref")
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

/** @return true if [key] exists in this [fileName] with the specified [mode], otherwise false is returned isa. */
fun Context.sharedPrefHasKey(
    fileName: String,
    key: String,
    mode: Int = Context.MODE_PRIVATE
): Boolean {
    val sharedPref = applicationContext.getSharedPreferences(fileName, mode)

    return key in sharedPref.all?.keys
}

/**
 * - [value] can **Only** be `null` if [removeIfValueParamIsNullOtherwiseThrowException] is true
 * which in this case [sharedPrefRemoveKey] will be used with the given [key] isa,
 * otherwise a [RuntimeException] will be thrown since actually in shared preferences no null values
 * are saved but a null value removes the key from the shared pref instead.
 * - Supports [Set] of other types not just [String] an they can have nullable item so like you
 * can pass [Set]<[String]?> you as well can pass [Set]<[Int]?> BUT when retrieve set with nullable
 * item in it you must pass acceptNullableItemInSet = true in [sharedPrefGetComplex] fun isa,
 * **HOWEVER** all saved as [Set] so order
 * is never granted to be the same and there cannot be duplications and in that case all duplicated
 * items will be removed leaving distinct items only like calling [List.distinct] isa,
 * Also no nested type params so you can provide [Set]<[Boolean]> but not [Set]<[Set]<[Boolean]>> isa,
 * - Supports [Pair] && [MutablePair] of other types as well but other types CANNOT be null isa.
 * - Despite of the above additional supported types (which as well may increase) I highly encourage
 * using this for very simple small tiny amount of data for a data that might increase or originally
 * a little non-small that consider saving in SQL or Files not shared preferences isa.
 *
 * ### TODO S for future plans (Not currently Supported) isa.
 * 1. support nested type params isa.
 * 2. support set order isa.
 *
 * @return value of [SharedPreferences.Editor.commit] or null if Used [SharedPreferences.Editor.apply] isa.
 *
 * @throws ClassCastException if [sharedPrefSupportedTypesParamsArray] referred to different type
 * than in [value], which is used when providing [Set], [Pair] or [MutablePair] isa, OR if number of
 * [sharedPrefSupportedTypesParamsArray] items not the same as the number of the class type params
 * for ex. [Set] needs 1 type param, while [Pair] needs 2 isa.
 * @throws RuntimeException if [value] is null && [removeIfValueParamIsNullOtherwiseThrowException] is false,
 * in case you want to delete the [key] if [value] is null pass true to [removeIfValueParamIsNullOtherwiseThrowException] isa.
 * @throws RuntimeException if [value] is Unsupported type.
 */
@SuppressLint("ApplySharedPref")
inline fun <reified T> Context.sharedPrefSetComplex(
    fileName: String,
    key: String,
    value: T,
    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false,
    removeIfValueParamIsNullOtherwiseThrowException: Boolean = false,
    vararg sharedPrefSupportedTypesParamsArray: SharedPrefSupportedTypesParams = arrayOf(SharedPrefSupportedTypesParams.STRING)
): Boolean? {
    return internal_sharedPrefSetComplex(
        removeIfValueParamIsNullOtherwiseThrowException,
        fileName,
        key,
        value,
        T::class.java,
        mode,
        commit,
        *sharedPrefSupportedTypesParamsArray
    )
}

/**
 * [defValue] can be null since we check if the [key] exists before getting the value isa.
 *
 * @param acceptNullableItemInSet if you request nullable item in set pass true here otherwise
 * an exception will be thrown isa.
 *
 * @see sharedPrefSetComplex
 */
inline fun <reified T> Context.sharedPrefGetComplex(
    fileName: String,
    key: String,
    defValue: T,
    mode: Int = Context.MODE_PRIVATE,
    acceptNullableItemInSet: Boolean = false,
    vararg sharedPrefSupportedTypesParamsArray: SharedPrefSupportedTypesParams = arrayOf(SharedPrefSupportedTypesParams.STRING)
): T {
    return javaSharedPrefGetComplex(fileName, key, defValue, T::class.java, mode, acceptNullableItemInSet, *sharedPrefSupportedTypesParamsArray)
}

@Suppress("UNCHECKED_CAST")
@JvmName("sharedPrefGetComplex")
@JvmOverloads
fun <T> Context.javaSharedPrefGetComplex(
    fileName: String,
    key: String,
    defValue: T,
    jClass: Class<T>,
    mode: Int = Context.MODE_PRIVATE,
    acceptNullableItemInSet: Boolean = false,
    vararg sharedPrefSupportedTypesParamsArray: SharedPrefSupportedTypesParams = arrayOf(SharedPrefSupportedTypesParams.STRING)
): T {
    return internal_javaSharedPrefGetComplex(fileName, key, defValue, jClass, mode, acceptNullableItemInSet, *sharedPrefSupportedTypesParamsArray)
}
