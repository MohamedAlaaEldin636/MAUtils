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

/**
 * ### VIP Notes
 *
 * - [value] can **Only** be `null` if [removeIfValueParamIsNullOtherwiseThrowException] is true
 * which in this case [sharedPrefRemoveKey] will be used with the given [key] isa,
 * otherwise a [RuntimeException] will be thrown since actually in shared preferences no null values
 * are saved but a null value removes the key from the shared pref instead.
 *
 * ### Supported types
 *
 * 1. All Supported types by [SharedPreferences].
 *
 * 2. Additionally we support [Set] of any of the supported types by [SharedPreferences] except
 * nested [Set] which can be supported in a custom way see number **3.**, so we not only support
 * [Set]<[String]> but as well [Set]<[Int]> and others and also if they contain nullable item
 * so for ex. we support [Set]<[Boolean]?> isa, **BUT NOTE THAT** if the provided [Set] is not
 * [Set]<[String]> you have to provide value to [maParameterizedKClass] for How to use it check
 * doc of [MAParameterizedKClass] isa.
 *
 * 3. Any Other Type -> **But** to do that you have to choose between 2 things either leave this library
 * to automatically convert for you from/to [String] to save it inside [SharedPreferences] or you
 * can provide that manually via [convertToString] param isa, and whichever option you choose you have
 * to fill [maParameterizedKClass] param with that class type, **So Which option do I choose** I will
 * explain how the auto way works and how the manual way works and you choose based on what
 * is the best option for you
 *      - **Auto Way** leave [convertToString] `null` and requires you to use the `gson` module in this library and that's all
 *      so you add `implementation 'com.github.MohamedAlaaEldin636.MAUtils:gson:$mautils_version'` in gradle
 *      and we will take care of the rest also as you know if you used `gson` module in case of
 *      nested type params for the `gson` you Have to use `GsonConverter` so add it in [gsonConverter]
 *      param isa, Also note toJsonOrNull() will always be used isa.
 *      - **Manual Way** just use [convertToString] param for the conversion and for the getter
 *      fun keep in mind that conversion to reverse it to get your custom object isa.
 *
 * ### CAUTION
 *
 * - the additional supported types and the easier and more concise way of declaring and accessing
 * [SharedPreferences] only made for a quicker development since time is so valuable but that doesn't
 * mean at all to replace [SharedPreferences] purpose with database or files, read the differences
 * between them and choose the most suitable option for you isa.
 *
 * @param maParameterizedKClass for ex. if your custom type is []
 *
 * @return value of [SharedPreferences.Editor.commit] or null if Used [SharedPreferences.Editor.apply] isa.
 *
 * @throws RuntimeException if [value] is null && [removeIfValueParamIsNullOtherwiseThrowException] is false,
 * in case you want to delete the [key] if [value] is null pass true to [removeIfValueParamIsNullOtherwiseThrowException] isa.
 * @throws RuntimeException if adding 2 values to [MAParameterizedKClass] params since only 1 is needed isa.
 *
 * @throws ClassCastException if [value] is expected to be [Set]<[String]> but found to be otherwise isa.
 */
/*@SuppressLint("ApplySharedPref")
inline fun <reified T> Context.sharedPrefSetComplex(
    fileName: String,

    key: String,
    value: T,
    removeKeyIfValueIsNull: Boolean = false,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false
): Boolean? {
    val convertToString: (T) -> String = {
        //"".toJs
        "" // todo to make that compileOnly I guess we need another module shared_pref isa.
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
}*/
