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

package mohamedalaa.mautils.core_android.extensions
/*

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import mohamedalaa.mautils.core_android.custom_classes.SharedPrefSupportedTypesParams
import mohamedalaa.mautils.core_kotlin.custom_classes.kclass_identifier.MAParameterizedKClass
import mohamedalaa.mautils.core_kotlin.custom_classes.MutablePair
import mohamedalaa.mautils.core_kotlin.extensions.*

*/
/** since pair will be saved as string below are keys for it isa, and escape any values like them with / isa. *//*

private const val PAIR_KEY_FOR_FIRST = "PAIR_KEY_FOR_FIRST="
private const val PAIR_KEY_FOR_SECOND = "PAIR_KEY_FOR_SECOND="
private const val PAIR_KEY_ESCAPE_VALUE = "/"

*/
/** @see [sharedPrefSetComplex] *//*

@SuppressLint("ApplySharedPref")
@PublishedApi
internal fun <T> Context.internal_sharedPrefSetComplex(
    fileName: String,

    key: String,
    value: T,
    removeKeyIfValueIsNull: Boolean,

    mode: Int,
    commit: Boolean,

    convertToString: (T) -> String
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
                editor.putString(key, convertToString(value))
            }
        }
        else -> {
            if (value == null) {
                if (removeKeyIfValueIsNull) {
                    editor.remove(key)
                }else {
                    throwRuntimeException(
                        "passed null value while removeKeyIfValueIsNull is false isa"
                    )
                }
            }else {
                editor.putString(key, convertToString(value))
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

private fun <T> private_sharedPrefSetComplex_customType(
    removeFileIfNull: Boolean,

    fileName: String,

    key: String,
    value: T,
    jClass: Class<T>,

    mode: Int = Context.MODE_PRIVATE,
    commit: Boolean = false,

    maParameterizedKClass: MAParameterizedKClass,
    gsonConverter: Any? = null,
    convertToString: ((T) -> String)? = null
) {
    val nonNullIsNotEmpty = maParameterizedKClass.nonNullKClasses.isNotEmpty()
    val maKClassIsNotEmpty = maParameterizedKClass.maKClass.isNotEmpty()
    val allJClassesAndNullable = when {
        nonNullIsNotEmpty && maKClassIsNotEmpty -> throwRuntimeException(
            "only one param in ${MAParameterizedKClass::class} has to be used not two isa."
        )
        nonNullIsNotEmpty || maKClassIsNotEmpty -> {
            if (nonNullIsNotEmpty) {
                maParameterizedKClass.nonNullKClasses.map { it.java to false }
            }else {
                maParameterizedKClass.maKClass.map { it.kClass.java to it.nullable }
            }
        }
        else -> throwRuntimeException(
            "${MAParameterizedKClass::class} one param is needed found none isa."
        )
    }

    // Check class before type params isa.
    if (jClass != allJClassesAndNullable[0].first) throwRuntimeException(
        "Provided value is of a different class [${jClass}] than the identified via ${MAParameterizedKClass::class} " +
            "where it's list allJClassesAndNullable is $allJClassesAndNullable isa."
    )

    // todo t2reban msh htnfa3 gher f el generated code isa.
    //
}

@Suppress("UNCHECKED_CAST")
@PublishedApi
internal fun <T> Context.internal_javaSharedPrefGetComplex(
    fileName: String,
    key: String,
    defValue: T,
    jClass: Class<T>,
    mode: Int = Context.MODE_PRIVATE,
    acceptNullableItemInSet: Boolean,
    vararg sharedPrefSupportedTypesParamsArray: SharedPrefSupportedTypesParams = arrayOf(SharedPrefSupportedTypesParams.STRING)
): T {
    val sharedPref = applicationContext.getSharedPreferences(fileName, mode)

    if (sharedPref.hasKey(key).not()) {
        return defValue
    }

    // returns null if null is supported otherwise def value isa.
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
            if (sharedPrefSupportedTypesParamsArray.size != 1) {
                throw RuntimeException("incompatible value with sharedPrefSupportedTypesParamsArray")
            }

            val stringsSet = sharedPref.getStringSet(
                key,
                defValue?.run { sharedPrefSupportedTypesParamsArray[0].convertSetOfSupportedTypeParamsToSetOfStrings(this) }
            )
            sharedPrefSupportedTypesParamsArray[0].convertSetOfStringsToSetOfSupportedTypeParams(stringsSet, acceptNullableItemInSet)
        }
        Pair::class.java, MutablePair::class.java -> {
            if (sharedPrefSupportedTypesParamsArray.size != 2) {
                throw RuntimeException("incompatible value with sharedPrefSupportedTypesParamsArray")
            }

            val (firstAsString, secondAsString) = decodePairSharedPrefStringValueForGet(
                sharedPref.getString(key, null) ?: return defValue
            )

            val first = sharedPrefSupportedTypesParamsArray[0].convertStringToValue(firstAsString)
            val second = sharedPrefSupportedTypesParamsArray[1].convertStringToValue(secondAsString)
            if (jClass == Pair::class.java) {
                Pair(first, second) as T
            }else {
                MutablePair(first, second) as T
            }
        }
        else -> throw RuntimeException("Unsupported type getting shared pref isa")
    }
}

*/
/**
 * Opposite of [convertSetOfSupportedTypeParamsToSetOfStrings] isa.
 *//*

@Suppress("UNCHECKED_CAST")
@PublishedApi
internal fun <T> SharedPrefSupportedTypesParams.convertSetOfStringsToSetOfSupportedTypeParams(
    setOfStrings: Set<String?>?,
    acceptNullableItemInSet: Boolean
): T {
    return when (this) {
        SharedPrefSupportedTypesParams.STRING -> {
            setOfStrings?.checkNullableItemInSet(acceptNullableItemInSet) as T
        }
        SharedPrefSupportedTypesParams.INT -> {
            setOfStrings?.map { it?.toIntOrNull() }?.toSet()?.checkNullableItemInSet(acceptNullableItemInSet) as T
        }
        SharedPrefSupportedTypesParams.BOOLEAN -> {
            setOfStrings?.map { it?.toBooleanOrNull() }?.toSet()?.checkNullableItemInSet(acceptNullableItemInSet) as T
        }
        SharedPrefSupportedTypesParams.LONG -> {
            setOfStrings?.map { it?.toLongOrNull() }?.toSet()?.checkNullableItemInSet(acceptNullableItemInSet) as T
        }
        SharedPrefSupportedTypesParams.FLOAT -> {
            setOfStrings?.map { it?.toFloatOrNull() }?.toSet()?.checkNullableItemInSet(acceptNullableItemInSet) as T
        }
    }
}

// ---- Private fun

private fun <T> Set<T>.checkNullableItemInSet(acceptNullableItemInSet: Boolean): Set<T> {
    return run {
        if (acceptNullableItemInSet) this else {
            if (any { it == null }) throwNullableItemException() else this
        }
    }
}

private fun throwNullableItemException(): Nothing
    = throwRuntimeException("nullable item inside Set while you didn't request a nullable item in Set for sharedPref isa.")

*/
/**
 * Convert [value] which is [Set] but of [String], [Int], [Boolean], [Long] or [Float]
 * to be [Set]<[String]> isa.
 *//*

@Suppress("UNCHECKED_CAST")
private fun <T> SharedPrefSupportedTypesParams.convertSetOfSupportedTypeParamsToSetOfStrings(value: T): Set<String?> {
    return when (this) {
        SharedPrefSupportedTypesParams.STRING -> {
            value as Set<String?>
        }
        SharedPrefSupportedTypesParams.INT -> {
            (value as Set<Int?>).map { it.toStringOrNull() }.toSet()
        }
        SharedPrefSupportedTypesParams.BOOLEAN -> {
            (value as Set<Boolean?>).map { it.toStringOrNull() }.toSet()
        }
        SharedPrefSupportedTypesParams.LONG -> {
            (value as Set<Long?>).map { it.toStringOrNull() }.toSet()
        }
        SharedPrefSupportedTypesParams.FLOAT -> {
            (value as Set<Float?>).map { it.toStringOrNull() }.toSet()
        }
    }
}

private fun SharedPrefSupportedTypesParams.convertValueToString(value: Any): String {
    return when (this) {
        SharedPrefSupportedTypesParams.STRING -> {
            value as String
        }
        SharedPrefSupportedTypesParams.INT -> {
            (value as Int).toString()
        }
        SharedPrefSupportedTypesParams.BOOLEAN -> {
            (value as Boolean).toString()
        }
        SharedPrefSupportedTypesParams.LONG -> {
            (value as Long).toString()
        }
        SharedPrefSupportedTypesParams.FLOAT -> {
            (value as Float).toString()
        }
    }
}

private fun SharedPrefSupportedTypesParams.convertStringToValue(string: String): Any {
    return when (this) {
        SharedPrefSupportedTypesParams.STRING -> {
            string
        }
        SharedPrefSupportedTypesParams.INT -> {
            string.toInt()
        }
        SharedPrefSupportedTypesParams.BOOLEAN -> {
            string.toBoolean()
        }
        SharedPrefSupportedTypesParams.LONG -> {
            string.toLong()
        }
        SharedPrefSupportedTypesParams.FLOAT -> {
            string.toFloat()
        }
    }
}

*/
/**
 * Since first key has always index of 0 no need to back it up isa, but on the other hand second key need all the possible back up isa.
 *//*

private fun encodePairSharedPrefStringValueForSet(firstAsString: String, secondAsString: String): String {
    val getModifiedStringFun = fun(originalString: String): String {
        var startIndex = 0
        var modifiedString = originalString
        while (startIndex < modifiedString.length) {
            val keyIndex = modifiedString.indexOfOrNull(PAIR_KEY_ESCAPE_VALUE, startIndex = startIndex) ?: break
            startIndex = keyIndex.inc() + PAIR_KEY_ESCAPE_VALUE.length

            modifiedString = modifiedString.substring(0, keyIndex) + PAIR_KEY_ESCAPE_VALUE + modifiedString.substring(keyIndex)
        }
        return modifiedString
    }

    val modifiedFirst = getModifiedStringFun(firstAsString)
    val modifiedSecond = getModifiedStringFun(secondAsString)

    return "$PAIR_KEY_FOR_FIRST$modifiedFirst$PAIR_KEY_ESCAPE_VALUE$PAIR_KEY_FOR_SECOND$modifiedSecond"
}

private fun decodePairSharedPrefStringValueForGet(fullSharedPrefStringValue: String): Pair<String, String> {
    val indexOfFirstKey = 0
    val indexOfSecondKey = fullSharedPrefStringValue.allIndicesOf(PAIR_KEY_FOR_SECOND).first {
        var numberOfEscapes = 0
        var index = it.dec()
        while (index >= 0 && fullSharedPrefStringValue[index].toString() == PAIR_KEY_ESCAPE_VALUE) {
            numberOfEscapes++
            index--
        }

        // If odd number then pick it up isa.
        numberOfEscapes.isOdd()
    }

    val firstAsString = fullSharedPrefStringValue.substring(
        indexOfFirstKey + PAIR_KEY_FOR_FIRST.length,
        indexOfSecondKey.dec()
    )
    val secondAsString = fullSharedPrefStringValue.substring(
        indexOfSecondKey + PAIR_KEY_FOR_SECOND.length
    )

    val doubleEscapeKey = PAIR_KEY_ESCAPE_VALUE + PAIR_KEY_ESCAPE_VALUE
    val getModifiedStringFun = fun(originalString: String): String {
        var startIndex = 0
        var modifiedString = originalString
        while (startIndex < modifiedString.length) {
            val keyIndex = modifiedString.indexOfOrNull(doubleEscapeKey, startIndex = startIndex) ?: break
            startIndex = keyIndex.inc() + doubleEscapeKey.length

            modifiedString = modifiedString.substring(0, keyIndex) + modifiedString.substring(keyIndex.inc())
        }
        return modifiedString
    }

    val modifiedFirst = getModifiedStringFun(firstAsString)
    val modifiedSecond = getModifiedStringFun(secondAsString)

    return modifiedFirst to modifiedSecond
}

private fun SharedPreferences.hasKey(key: String) = key in all?.keys
*/
