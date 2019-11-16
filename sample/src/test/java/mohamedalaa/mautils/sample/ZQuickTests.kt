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
import mohamedalaa.mautils.gson.fromJsonOrNull
import mohamedalaa.mautils.gson.toJsonOrNull
import mohamedalaa.mautils.sample.general_custom_classes.GsonConverterPairOfPairOfIntAndSetOfFloatAndString
import mohamedalaa.mautils.sample.general_custom_classes.Person
import mohamedalaa.mautils.sample.shared_pref_.SharedPref_SomeClassName_NoContext
import mohamedalaa.mautils.sample.shared_pref_.sharedPref_SomeClassName_asSharedPreferences
import mohamedalaa.mautils.sample.shared_pref_.sharedPref_SomeClassName_clearAll
import mohamedalaa.mautils.sample.shared_pref_.sharedPref_SomeClassName_registerSharedPrefChangeListener
import mohamedalaa.mautils.shared_pref_core.*
import kotlin.test.assertEquals

private const val privateFileName = ""
private class P3 {
    @Synchronized
    fun Context.sharedPref_SomeClassName_SetPersonManualConversion(
        value: Person?, commit: Boolean = false
    ): Boolean? {
        val convertedValueAnyToString = value.run { toJsonOrNull() }
        return sharedPrefSet<String?>(
            privateFileName,
            "personManualConversion",
            convertedValueAnyToString,
            true,
            Context.MODE_PRIVATE,
            commit,
            null
        )
    }
    @Synchronized
    fun Context.sharedPref_SomeClassName_GetPersonManualConversion(
        defValue: Person? = null
    ): Person? {
        val convertedValueAnyToString = defValue.run { toJsonOrNull() }
        return sharedPrefGet<String?>(
            privateFileName,
            "personManualConversion",
            convertedValueAnyToString,
            Context.MODE_PRIVATE,
            null
        ).run {
            fromJsonOrNull()
        }
    }

    @Synchronized
    fun Context.sharedPref_SomeClassName_SetNestedTypeParamWithGsonConverterConversion(
        value: List<Pair<Pair<Int, Set<Float>>, String>>, commit: Boolean = false
    ): Boolean? = sharedPrefSet<List<Pair<Pair<Int, Set<Float>>, String>>>(
        privateFileName,
        "nestedTypeParamWithGsonConverterConversion",
        value,
        false,
        Context.MODE_PRIVATE,
        commit,
        GsonConverterPairOfPairOfIntAndSetOfFloatAndString()
    )
    @Synchronized
    fun Context.sharedPref_SomeClassName_GetNestedTypeParamWithGsonConverterConversion(
        defValue: List<Pair<Pair<Int, Set<Float>>, String>> = emptyList()
    ): List<Pair<Pair<Int, Set<Float>>, String>> = sharedPrefGet<List<Pair<Pair<Int, Set<Float>>, String>>>(
        privateFileName,
        "nestedTypeParamWithGsonConverterConversion",
        defValue,
        Context.MODE_PRIVATE,
        GsonConverterPairOfPairOfIntAndSetOfFloatAndString()
    )
}
private class P2 {
    @Synchronized
    fun Context.sharedPref_SomeClassName_clearAll(commit: Boolean = false): Boolean? =
        sharedPrefClearAll(privateFileName, Context.MODE_PRIVATE, commit)

    fun Context.sharedPref_SomeClassName_registerSharedPrefChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener
    ): Unit = sharedPref_SomeClassName_asSharedPreferences().registerOnSharedPreferenceChangeListener(listener)
    fun Context.sharedPref_SomeClassName_unregisterSharedPrefChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener
    ): Unit = sharedPref_SomeClassName_asSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener)

    @Synchronized
    fun Context.sharedPref_SomeClassName_SetPersonWithDefaultValue(
        value: Person, commit: Boolean = false
    ): Boolean? = sharedPrefSet<Person>(
        privateFileName,
        "personWithDefaultValue",
        value,
        false,
        Context.MODE_PRIVATE,
        commit,
        null
    )
    @Synchronized
    fun Context.sharedPref_SomeClassName_GetPersonWithDefaultValue(
        defValue: Person = Person()
    ): Person = sharedPrefGet<Person>(
        privateFileName,
        "personWithDefaultValue",
        defValue,
        Context.MODE_PRIVATE,
        null
    )
}
private class PrivateClass {
    @Synchronized
    fun Context.sharedPref_SomeClassName_SetKeepScreenOn(
        value: Boolean, commit: Boolean = false
    ): Boolean? = sharedPrefSet<Boolean>(
        privateFileName,
        "keepScreenOn",
        value,
        false,
        Context.MODE_PRIVATE,
        commit,
        null
    )

    @JvmName("getKeepScreenOn")
    @JvmOverloads
    @Synchronized
    fun Context.sharedPref_SomeClassName_GetKeepScreenOn(
        defValue: Boolean = false
    ): Boolean = sharedPrefGet<Boolean>(
        privateFileName,
        "keepScreenOn",
        defValue,
        Context.MODE_PRIVATE,
        null
    )

    @JvmName("setMySetOfStrings")
    @JvmOverloads
    @Synchronized
    fun Context.sharedPref_SomeClassName_SetMySetOfStrings(
        value: Set<String?>, commit: Boolean = false
    ): Boolean? = sharedPrefSet<Set<String?>>(
        privateFileName,
        "mySetOfStrings",
        value,
        false,
        Context.MODE_PRIVATE,
        commit,
        null
    )
    @JvmName("getMySetOfStrings")
    @JvmOverloads
    @Synchronized
    fun Context.sharedPref_SomeClassName_GetMySetOfStrings(
        defValue: Set<String?> = setOf()
    ): Set<String?> = sharedPrefGet<Set<String?>>(
        privateFileName,
        "mySetOfStrings",
        defValue,
        Context.MODE_PRIVATE,
        null
    )
}

private fun Context.a1(
    context: Context,
    int: Int?
) {
    context.sharedPrefClearAll("fileName")
    context.sharedPrefRemoveKey("fileName", "key")
    context.sharedPrefHasKey("fileName", "key")

    sharedPrefSet("fileName", "key", int, removeKeyIfValueIsNull = true)
    assertEquals(
        int,
        sharedPrefGet<Int?>("fileName", "key", null)
    )
    val b = sharedPrefGet<Int?>("fileName", "key", null)

    // Custom Type
    data class Address(val fullDetails: String, val abbreviation: String)
    data class Person(val name: String, val age: Int, val address: Address)
    val listOfPerson = emptyList<Person>()// listOf(person1, person2)
    context.sharedPrefSet(
        "fileName",
        "key",
        listOfPerson,
        commit = true /* default to false meaning using .apply() to make the change */,
        mode = Context.MODE_PRIVATE /* default value */
    )
    assertEquals(
        listOfPerson,
        context.sharedPrefGet("fileName", "key", emptyList())
    ) // true
}

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

    return sharedPrefSet<String*//*? might be nullable isa.*//*>(
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

    return sharedPrefSet<String*//*? might be nullable isa.*//*>(
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

    return sharedPrefGet<String>(
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
): Boolean? = sharedPrefSet<ValueType>(
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
): DefValueType = sharedPrefGet<DefValueType>(
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
): Boolean? = sharedPrefSet(
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
 * ): Boolean? = sharedPrefSet<kotlin.Boolean>(
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
        = 0L): Long = sharedPrefGet<Long>(privateFileName,
        "forKitkatOrAboveBeforeFolderSelectionCurrentTimeMillis", defValue, Context.MODE_PRIVATE,
        false, *arrayOf())
 *//*
@JvmName("getName")
@JvmOverloads
@Synchronized
internal fun Context.sharedPref_SomeClassName_GetName(
    defValue: Long = 0L*//*or acc to that type isa.*//*
): Long*//*same as defValue isa.*//* {
    return sharedPrefGet<Long>(
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

    sharedPrefSet("", "", "")

    return sharedPrefGet<CustomClass?>( // same as return & defValue type isa.
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


    context.sharedPrefSet("fileName", "k1", "v1")

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

    sharedPrefSet("", "", 9)

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

        val aa = context.sharedPrefSet("", "", 5)
        val i = context.sharedPrefGet<Int>("", "", 5)
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
