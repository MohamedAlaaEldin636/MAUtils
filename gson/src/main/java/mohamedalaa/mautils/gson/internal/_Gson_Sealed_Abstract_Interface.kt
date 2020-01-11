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

package mohamedalaa.mautils.gson.internal

import com.google.gson.*
import mohamedalaa.mautils.core_android.extensions.logError
import mohamedalaa.mautils.core_kotlin.extensions.*
import mohamedalaa.mautils.gson.*
import mohamedalaa.mautils.gson.allAnnotatedClassesAsString
import mohamedalaa.mautils.gson.java.fromJsonOrNull2
import mohamedalaa.mautils.gson.java.fromJsonOrNullJava
import mohamedalaa.mautils.gson.java.toJsonOrNull2
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type

private const val KEY_CLASS_FULL_NAME = "KEY_CLASS_FULL_NAME"
private const val KEY_NORMAL_SERIALIZATION_JSON_STRING = "KEY_NORMAL_SERIALIZATION_JSON_STRING"

internal class JsonSerializerForSealedClasses : JsonSerializer<Any> {

    override fun serialize(
        src: Any?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        if (src == null) {
            return null
        }

        val innerJsonObject = JSONObject()
        runCatching {
            val classDeclaredFields = src.javaClass.declaredFields.filterNotNull()
            val classDeclaredFieldsNames = classDeclaredFields.map { it.name }
            val allSuperclassesDeclaredFields = src.javaClass.declaredFieldsForSuperclassesOnly().filter {
                it.name !in classDeclaredFieldsNames // so there will be no clash of same name isa. ( might instead produce error in future isa. )
            }
            for (field in (classDeclaredFields + allSuperclassesDeclaredFields)) {
                val isAccessible = field.isAccessible
                field.isAccessible = true

                val fieldInstance = field.get(src)
                var jsonValue = fieldInstance.toJsonOrNull2(field.genericType)
                val jsonKey = field.name

                /*warnPrintLn("${field.type}, ${fieldInstance?.javaClass}, ${field.genericType}, " +
                    "${(field.genericType as? Class<*>)?.kotlin?.isSealed}")*/ // todo use genericType directly kda isa.
                if (field.type != fieldInstance?.javaClass) {
                    val fieldTypeAsString = field.type.toStringOrEmpty()
                    if (allAnnotatedClassesAsString.any { it in fieldTypeAsString }) {
                        jsonValue = serialize(fieldInstance, field.type, context).toStringOrNull()
                    }/*else infoPrintLn("with else isa.")*/
                }/*else if (field.type.toStringOrEmpty() != field.genericType.toStringOrEmpty()) {
                    errorPrintLn("NEW CHECK isa")
                    // jsonValue = ; toJson2 isa.
                }*/else if (field.needSpecialSerialize()) {
                    // todo only if field.type == exact sealed class isa. ezan == msh in el allAnnotated ones isa.
                    //errorPrintLn(field.type.toStringOrEmpty())

                    // todo la2 elle gowa homa bs ell mmkn yet3emelohom kda bs da yet3emel 3ade isa.... new function here isa..
                    jsonValue = serialize2(fieldInstance, field.type, context).toStringOrNull()
                }

                innerJsonObject.put(
                    jsonKey,
                    when (fieldInstance) {
                        null -> null
                        is String, is Boolean, is Int, is Long, is Double, is Enum<*> -> fieldInstance
                        else -> if (jsonValue == null) null else runCatching {
                            JSONObject(jsonValue)
                        }.getOrElse {
                            runCatching { JSONArray(jsonValue) }.getOrNull()
                        }
                    }
                )

                field.isAccessible = isAccessible
            }
        }

        val jsonObject = JSONObject()
        jsonObject.put(KEY_CLASS_FULL_NAME, src.javaClass.name)
        jsonObject.put(KEY_NORMAL_SERIALIZATION_JSON_STRING, innerJsonObject)

        return runCatching { JsonParser().parse(jsonObject.toString()) }.getOrNull()
    }

    fun serialize2(
        src: Any?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        if (src == null) {
            return null
        }

        val innerJsonObject = JSONObject()
        runCatching {
            val classDeclaredFields = src.javaClass.declaredFields.filterNotNull()
            val classDeclaredFieldsNames = classDeclaredFields.map { it.name }
            val allSuperclassesDeclaredFields = src.javaClass.declaredFieldsForSuperclassesOnly().filter {
                it.name !in classDeclaredFieldsNames // so there will be no clash of same name isa. ( might instead produce error in future isa. )
            }
            for (field in (classDeclaredFields + allSuperclassesDeclaredFields)) {
                val isAccessible = field.isAccessible
                field.isAccessible = true

                val fieldInstance = field.get(src)
                var jsonValue = fieldInstance.toJsonOrNull2(field.genericType)
                val jsonKey = field.name

                /*warnPrintLn("${field.type}, ${fieldInstance?.javaClass}, ${field.genericType}, " +
                    "${(field.genericType as? Class<*>)?.kotlin?.isSealed}")*/ // todo use genericType directly kda isa.
                if (field.type != fieldInstance?.javaClass) {
                    val fieldTypeAsString = field.type.toStringOrEmpty()
                    if (allAnnotatedClassesAsString.any { it in fieldTypeAsString }) {
                        jsonValue = serialize(fieldInstance, field.type, context).toStringOrNull()
                    }/*else infoPrintLn("with else isa.")*/
                }/*else if (field.type.toStringOrEmpty() != field.genericType.toStringOrEmpty()) {
                    errorPrintLn("NEW CHECK isa")
                    // jsonValue = ; toJson2 isa.
                }*/else if (field.needSpecialSerialize()) {
                    // todo only if field.type == exact sealed class isa. ezan == msh in el allAnnotated ones isa.
                    //errorPrintLn(field.type.toStringOrEmpty())

                    // todo la2 elle gowa homa bs ell mmkn yet3emelohom kda bs da yet3emel 3ade isa.... new function here isa..
                    jsonValue = serialize2(fieldInstance, field.type, context).toStringOrNull()
                }

                innerJsonObject.put(
                    jsonKey,
                    when (fieldInstance) {
                        null -> null
                        is String, is Boolean, is Int, is Long, is Double, is Enum<*> -> fieldInstance
                        else -> if (jsonValue == null) null else runCatching {
                            JSONObject(jsonValue)
                        }.getOrElse {
                            runCatching { JSONArray(jsonValue) }.getOrNull()
                        }
                    }
                )

                field.isAccessible = isAccessible
            }
        }

        return runCatching { JsonParser().parse(innerJsonObject.toString()) }.getOrNull()
    }

}

internal class JsonDeserializerForSealedClasses : JsonDeserializer<Any> {

    fun deserialize2(
        jsonAsString: String?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any? {
        return runCatching {
            val jsonArray = JSONArray(jsonAsString)

            jsonArray.toString().fromJsonOrNull2(typeOfT!!)
        }.getOrNull()
    }

    // todo this do not understand serialize in some cases isa.
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any? {
        val jsonAsString = json?.toString()
        if (jsonAsString.isNullOrEmpty()) {
            return null
        }

        // todo handle case if was array JSON ARRAY ISA.
        val jsonObject = runCatching {
            JSONObject(jsonAsString)
        }.getOrElse {
            return deserialize2(jsonAsString, typeOfT, context)

            //JSONObject(jsonAsString)
        }

        val classFullName = jsonObject.optString(KEY_CLASS_FULL_NAME) ?: (typeOfT as? Class<*>)?.name.orEmpty()
        val jClass = runCatching { Class.forName(classFullName) }.getOrNull() ?: typeOfT as? Class<*>
        if (jClass != null) {
            jClass.kotlin.objectInstance?.apply {
                return this
            }
        }
        val normalSerializationJsonObject = jsonObject.optJSONObject(
            KEY_NORMAL_SERIALIZATION_JSON_STRING
        ) ?: jsonObject

        if (KEY_CLASS_FULL_NAME in normalSerializationJsonObject.toString()
            && KEY_NORMAL_SERIALIZATION_JSON_STRING in normalSerializationJsonObject.toString()) {

            warnPrintLn("typeOfT $typeOfT")
            val allKeys = normalSerializationJsonObject.keys()
            val newParamsWithKeys = mutableListOf<Pair<Any?, String>>()
            runCatching {
                for (index in 0 until normalSerializationJsonObject.length()) {
                    val key = allKeys.next()
                    val value = normalSerializationJsonObject.get(key)
                    val valueAsString = value.toStringOrEmpty()

                    /*if (key == "timing") {
                        infoPrintLn("timing, ${value == valueAsString} ->\n$value,\n$valueAsString")

                        if (value is JSONObject) {
                            var valueClass = if (jClass == null) null else jClass.runCatching {
                                declaredFieldsForSuperclassesOnly(declaredFields.toList()).firstOrNull {
                                    it.name == key
                                }?.type
                            }.getOrNull()
                            if (value is JSONObject && value.optString(KEY_CLASS_FULL_NAME).isNullOrEmpty().not()) {
                                valueClass = runCatching { Class.forName(value.optString(KEY_CLASS_FULL_NAME)) }
                                    .getOrNull() ?: valueClass
                            }

                            errorPrintLn("$jClass")
                            errorPrintLn("$valueClass")
                            errorPrintLn("${runCatching { JsonParser().parse(valueAsString) }.getOrNull()}")
                            errorPrintLn(deserialize(
                                runCatching { JsonParser().parse(valueAsString) }.getOrNull(),
                                valueClass ?: jClass,
                                context
                            ))
                        }
                    }*/

                    val newValue = when (value) {
                        is JSONObject, is JSONArray -> {
                            val valueClass = if (jClass == null) null else jClass.runCatching {
                                declaredFieldsForSuperclassesOnly(declaredFields.toList()).firstOrNull {
                                    it.name == key
                                }?.genericType
                            }.getOrNull()
                            /*if (value is JSONObject && value.optString(KEY_CLASS_FULL_NAME).isNullOrEmpty().not()) {
                                valueClass = runCatching { Class.forName(value.optString(KEY_CLASS_FULL_NAME)) }
                                    .getOrNull() ?: valueClass
                            }*/

                            runCatching {
                                deserialize(
                                    runCatching { JsonParser().parse(valueAsString) }.getOrElse {
                                        println("HHHHHHHHHHHHHHHHHHHH")
                                        println("H/ $it")

                                        null
                                    },
                                    valueClass ?: jClass,
                                    context
                                )
                            }.getOrElse {
                                infoPrintLn("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII")
                                infoPrintLn("$valueClass ?: $jClass")
                                infoPrintLn("E/ $it")
                            }

                            wtfPrintLn("valueClass ?: jClass -> $valueClass ?: $jClass")
                            deserialize(
                                runCatching { JsonParser().parse(valueAsString) }.getOrNull(),
                                valueClass ?: jClass,
                                context
                            )
                        }
                        else -> value
                    }
                    newParamsWithKeys += newValue to key
                }
            }.getOrElse {
                errorPrintLn("EEEEEEEEEEEEERRRRRRRRRRRRRRROOOOOOOOOOOOOOOOOOORRRRRRRRRR")
                errorPrintLn(typeOfT.toStringOrEmpty())

                errorPrintLn(allKeys.toList())
                errorPrintLn("E/ $it")
                throw it
            }

            if ("class mohamedalaa.mautils.sample.gson.model.repeat.RepeatUntilPolicy" == typeOfT.toStringOrEmpty()) {
                errorPrintLn("newParamsWithKeys $newParamsWithKeys")
            }
            if ("? extends mohamedalaa.mautils.sample.gson.model.repeat.RepeatPolicy" in typeOfT.toStringOrEmpty()) {
                errorPrintLn("newParamsWithKeys $newParamsWithKeys")
            }
            if ("class mohamedalaa.mautils.sample.gson.model.NotifyAsReminderOrAction" in typeOfT.toStringOrEmpty()) {
                errorPrintLn("newParamsWithKeys $newParamsWithKeys")
            }
            runCatching {
                for (constructor in jClass!!.constructors) {
                    runCatching {
                        val size = constructor.parameterTypes.size

                        val constructorParams = newParamsWithKeys.subList(0, size).map { it.first }
                        val otherParamsWithKeys = newParamsWithKeys.subList(size, newParamsWithKeys.size)

                        if ("class mohamedalaa.mautils.sample.gson.model.repeat.RepeatUntilPolicy" == typeOfT.toStringOrEmpty()) {
                            infoPrintLn("constructorParams $constructorParams")
                            infoPrintLn("otherParamsWithKeys $otherParamsWithKeys")
                        }
                        return constructor.newInstance(*constructorParams.toTypedArray()).apply {
                            if ("class mohamedalaa.mautils.sample.gson.model.repeat.RepeatUntilPolicy" == typeOfT.toStringOrEmpty()) {
                                infoPrintLn("full class isa is $this")
                            }
                            if ("? extends mohamedalaa.mautils.sample.gson.model.repeat.RepeatPolicy" == typeOfT.toStringOrEmpty()) {
                                infoPrintLn("full class isa is $this")
                            }
                        }.applyIf(otherParamsWithKeys.isNotEmpty()) {
                            for ((param, key) in otherParamsWithKeys) {
                                val field = runCatching {
                                    javaClass.getDeclaredFieldsForSuperclassesOnly(key)
                                }.getOrNull() ?: continue

                                warnPrintLn("1")

                                val isAccessible = field.isAccessible
                                field.isAccessible = true

                                field.set(this, param)

                                field.isAccessible = isAccessible
                            }
                        }
                    }.getOrElse {
                        if ("class mohamedalaa.mautils.sample.gson.model.repeat.RepeatUntilPolicy" == typeOfT.toStringOrEmpty()) {
                            infoPrintLn("error isa is $it")
                        }
                    }
                }
            }
        }

        return normalSerializationJsonObject.toString().fromJsonOrNullJava(jClass ?: return null)
    }

}

fun <T> Iterator<T>.toList(): List<T> {
    val list = mutableListOf<T>()

    for (item in this) {
        list += item
    }

    return list
}
