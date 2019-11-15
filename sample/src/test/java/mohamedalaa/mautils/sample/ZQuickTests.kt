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

package mohamedalaa.mautils.sample

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.InspectableProperty
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.java.GsonConverter
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.sample.custom_classes.helper_classes.toStringOrEmpty
import mohamedalaa.mautils.sample.shared_pref_.mautils_sharedPref.SharedPref_SomeClassName_NoContext
import mohamedalaa.mautils.sample.shared_pref_.mautils_sharedPref.sharedPref_SomeClassName_clearAll
import mohamedalaa.mautils.sample.shared_pref_.mautils_sharedPref.sharedPref_SomeClassName_registerSharedPrefChangeListener
import mohamedalaa.mautils.shared_pref_annotation.*
import mohamedalaa.mautils.shared_pref_core.sharedPrefClearAll
import mohamedalaa.mautils.shared_pref_core.sharedPrefGetComplex
import mohamedalaa.mautils.shared_pref_core.sharedPrefSetComplex
import kotlin.reflect.KClass

abstract class AAAAA : SharedPreferences.OnSharedPreferenceChangeListener {
    private fun Context.h1() {
        // this.sharedPref_SomeClassName_as()
        SharedPref_SomeClassName_NoContext.fileName()

        sharedPref_SomeClassName_registerSharedPrefChangeListener(this@AAAAA)

        sharedPref_SomeClassName_clearAll()
        //sharedPref_SomeClassName_SetPerson(null)
        //sharedPref_SomeClassName_GetPerson()
    }
}
/*
internal typealias DefValueType = Map<Int, Pair<String, Boolean>>
internal typealias ValueType = Map<Int, Pair<String, Boolean>>
internal typealias CustomValueType = Map<Int, Pair<String, Boolean>>

internal val gsonConverter = object : GsonConverter<DefValueType>(){}
private const val valueCanBeNullable = true

internal val defaultOrByUserDefValue: DefValueType = emptyMap()

@Suppress("RemoveExplicitTypeArguments", "unused")
// just a comment isa.
@JvmName("setName")
@JvmOverloads
@Synchronized
fun Context.sharedPref_SomeClassName_SetName(
    value: CustomValueType,
    commit: Boolean = false
): Boolean? {
    val convertedValueAnyToString = value.run { toString()*//*dev code*//* }

    return sharedPrefSetComplex<String*//*? might be nullable isa.*//*>(
        privateFileName,

        "name",
        convertedValueAnyToString,
        valueCanBeNullable *//*removeKeyIfValueIsNull*//*,

        Context.MODE_PRIVATE,
        commit,

        null*//* or gsonConverter*//*
    )
}
@Suppress("RemoveExplicitTypeArguments", "unused")
// just a comment isa.
@JvmName("getName")
@JvmOverloads
@Synchronized
fun Context.sharedPref_SomeClassName_GetName(
    value: CustomValueType,
    commit: Boolean = false
): Boolean? {
    val convertedValueAnyToString = value.run { toString()*//*dev code*//* }

    return sharedPrefSetComplex<String*//*? might be nullable isa.*//*>(
        privateFileName,

        "name",
        convertedValueAnyToString,
        valueCanBeNullable *//*removeKeyIfValueIsNull*//*,

        Context.MODE_PRIVATE,
        commit,

        null*//* or gsonConverter*//*
    )
}


@Suppress("unused", "RemoveExplicitTypeArguments")
// just comment to separate suppress annotation isa.
@JvmName("getName2")
@JvmOverloads
@Synchronized
internal fun Context.sharedPref_SomeClassName_GetName2(
    defValue: DefValueType = defaultOrByUserDefValue
): DefValueType {
    val convertedValueAnyToString = defValue.run { toJson()*//*dev code*//* }

    return sharedPrefGetComplex<String>(
        privateFileName,
        "name2",
        convertedValueAnyToString,

        Context.MODE_PRIVATE,

        null*//* or gsonConverter*//*
    ).run { fromJson() }
}

@Suppress("RemoveExplicitTypeArguments", "unused")
// just a comment isa.
@JvmName("setName1")
@JvmOverloads
@Synchronized
fun Context.sharedPref_SomeClassName_SetName1(
    value: ValueType,
    commit: Boolean = false
): Boolean? = sharedPrefSetComplex<ValueType>(
    privateFileName,

    "name1",
    value,
    valueCanBeNullable *//*removeKeyIfValueIsNull*//*,

    Context.MODE_PRIVATE,
    commit,

    null*//* or gsonConverter*//*
)

@Suppress("unused", "RemoveExplicitTypeArguments")
@JvmName("getName")
@JvmOverloads
@Synchronized
internal fun Context.sharedPref_SomeClassName_GetName(
    defValue: DefValueType = defaultOrByUserDefValue
): DefValueType = sharedPrefGetComplex<DefValueType>(
    privateFileName,
    "name",
    defValue,

    Context.MODE_PRIVATE,

    gsonConverter*//*or null isa.*//*
)

@Suppress("unused")
@JvmName("setBoolean1")
@JvmOverloads
@Synchronized
internal fun Context.sharedPref_SomeClassName_SetBoolean1(
    value: Boolean,
    commit: Boolean = false
): Boolean? = sharedPrefSetComplex(
    privateFileName,
    "boolean1",
    value,
    true*//*removeKeyIfValueIsNull todo check .support + type isa.*//*,

    Context.MODE_PRIVATE,
    commit,

    GsonConverterClassName()*//*or null isa.*//*
)

private class GsonConverterClassName : GsonConverter<Boolean>()

*//*
* @JvmName("setBoolean1")
 * @JvmOverloads
 * @Synchronized
 * fun Context.sharedPref_SomeClassName_SetBoolean1(
 *      value: Boolean, commit: Boolean = false
 * ): Boolean? = sharedPrefSetComplex<kotlin.Boolean>(
 *      privateFileName,
 *      "boolean1",
 *      value,
 *      Context.MODE_PRIVATE,
 *      commit,
 *      acc to if nullable setter is enabled isa., // removeIfValueParamIsNullOtherwiseThrowException
 *      *arrayOf()
 *)
 *//*


*//*
@Synchronized
fun Context.sharedPref__TempValues_GetForKitkatOrAboveBeforeFolderSelectionCurrentTimeMillis(defValue: Long
        = 0L): Long = sharedPrefGetComplex<Long>(privateFileName,
        "forKitkatOrAboveBeforeFolderSelectionCurrentTimeMillis", defValue, Context.MODE_PRIVATE,
        false, *arrayOf())
 *//*
@JvmName("getName")
@JvmOverloads
@Synchronized
internal fun Context.sharedPref_SomeClassName_GetName(
    defValue: Long = 0L*//*or acc to that type isa.*//*
): Long*//*same as defValue isa.*//* {
    return sharedPrefGetComplex<Long>(
        privateFileName,
        "name",
        defValue,
        Context.MODE_PRIVATE,
        null
    )
}
@JvmName("getCustomClass")
@JvmOverloads
@Synchronized
internal fun Context.sharedPref_SomeClassName_GetCustomClass(
    defValue: CustomClass? = null*//*or acc to that type isa.*//*
): CustomClass?*//*same as defValue isa.*//* {

    val v1: Pair<Pair<Int?, Float?>?, String?> = GsonConverterNum1().fromJson("")

    sharedPrefSetComplex("", "", "")

    return sharedPrefGetComplex<CustomClass?>( // same as return & defValue type isa.
        privateFileName,
        "customClass",
        defValue,
        Context.MODE_PRIVATE,
        null // gsonConverter // GsonConverter from annotation is better isa, and remind user to make import statement isa.
    )
}
// todo also make shakl el getter law manual conversion msln isa.

private val privateFileName = "privateFileName"

internal data class CustomClass(
    val int: Int,
    val string: String
)

private fun <T> array1(maSharedPrefKeyValuePair: MASharedPrefKeyValuePair, context: Context, array: Array<T>) {
    array.javaClass.componentType


    context.sharedPrefSetComplex("fileName", "k1", "v1")

    context.sharedPrefClearAll("fileName")
}

internal fun <T> callMe1(
    value: T,
    conversion: (T) -> String
) {

}
@Suppress("NOTHING_TO_INLINE")
internal inline fun <T> callMe1(
    value: T,
    convertToString: ConvertToString<T>
) {

}

private fun caller1() {
    val value = 7
    callMe1(value, ::conversion1)

    callMe1(value, SingletonObjectIsa)
}
private fun conversion1(int: Int): String {
    return int.toStringOrEmpty()
}

interface ConvertToString <T> {
    fun convertToString(t: T): String
}
interface ConvertFromString <T> {
    fun convertFromString(string: String): T
}
object SingletonObjectIsa : ConvertToString<Any>, ConvertFromString<Int> {
    //override fun convertToString(t: Int): String = t.toStringOrEmpty()
    override fun convertToString(t: Any): String =
        if (t is Int) t.inc().toStringOrEmpty() else ""
    override fun convertFromString(string: String): Int = string.toIntOrNull() ?: 0
}

internal fun Context.zert_Sh_Pref(){}
internal fun Context.zert__ZX_Yu(){}
internal fun Context.zert__uu_kl(){}

private fun Context.fff1() {
    zert_Sh_Pref()
    zert__ZX_Yu()
    zert__uu_kl()

    sharedPrefSetComplex("", "", 9)

    //MAOnSharedPrefChangeListener
}

fun midoooooooooooo() {

}

internal fun <T : Any> KClass<T>.aa(): Class<T> = java

object InternalUtils {

    @JvmStatic
    fun convert(kClass: KClass<*>): Class<*> = kClass.java

}

private fun s() {
    *//*val a = MASharedPrefKeyValuePair.Builder.dd()
    val k = a.kClass*//*
}

private fun Context.sharedPref_TempValues_asSharedPreferences() = getSharedPreferences("", Context.MODE_PRIVATE)

@mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair.Container
private class _A
@mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs
private class _A2

*//*@MASharedPrefKeyValuePair(
    type = MAParameterizedKClass(
        nonNullKClasses = [String::class]
    )
)
@MASharedPrefKeyValuePair(
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(List::class, nullable = true),
            MAKClass(String::class)
        ]
    // but in this case ignore the setterCanBeNull bool
    // since auto if is null make setter/getter nullable bs feh moshkela kda isa.
    // when u set to null u actually will get default value not null value isa.
    )
)*//*
@mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair(
    name = "",
    type = MAParameterizedKClass(
        nonNullKClasses = [String::class]
    )
    //convertAnyToString = null
)
private abstract class A1 : SharedPreferences.OnSharedPreferenceChangeListener {
    private fun ddd(context: Context, sharedPref: SharedPreferences) {
        sharedPref.registerOnSharedPreferenceChangeListener(this)

        val aa = context.sharedPrefSetComplex("", "", 5)
        val i = context.sharedPrefGetComplex<Int>("", "", 5)
    }

    // sharedPref, fileName, key, bs 34an tb2a general afdal khaleha sharedPref msh el specific da isa.
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {}
}

abstract class I1 : SharedPreferences.OnSharedPreferenceChangeListener {

    final override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        //sharedPreferences!!.
    }

    *//*abstract *//*fun onSharedPrefChange(
        sharedPrefFileName: String,
        key: String,
        sharedPreferences: SharedPreferences
    ) {
        //sharedPreferences.registerOnSharedPreferenceChangeListener()
        //sharedPreferences.unregisterOnSharedPreferenceChangeListener()
    }

}
*//*
private class C1 : MAOnSharedPrefChangeListener {
    override fun onChange(sharedPrefFileName: String, key: String) {
        logError("$sharedPrefFileName, $key")
    }
}
private fun f1(s: MAOnSharedPrefChangeListener) {

}*//*
private fun f2() {
    //f1(MAOnSharedPrefChangeListener)
}*/
