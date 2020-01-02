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
import mohamedalaa.mautils.core_kotlin.extensions.applyIf
import mohamedalaa.mautils.core_kotlin.extensions.contains
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import mohamedalaa.mautils.gson.*
import mohamedalaa.mautils.gson.allAnnotatedClassesAsString
import mohamedalaa.mautils.gson.java.fromJsonOrNullJava
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
                var jsonValue = fieldInstance.toJsonOrNull()
                val jsonKey = field.name

                if (field.type != fieldInstance?.javaClass) {
                    val fieldTypeAsString = field.type.toStringOrEmpty()
                    if (allAnnotatedClassesAsString.any { it in fieldTypeAsString }) {
                        jsonValue = serialize(fieldInstance, field.type, context).toStringOrNull()
                    }
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

}

internal class JsonDeserializerForSealedClasses : JsonDeserializer<Any> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any? {
        val jsonAsString = json?.toString()
        if (jsonAsString.isNullOrEmpty()) {
            return null
        }

        val jsonObject = JSONObject(jsonAsString)

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

            val allKeys = normalSerializationJsonObject.keys()
            val newParamsWithKeys = mutableListOf<Pair<Any?, String>>()
            for (index in 0 until normalSerializationJsonObject.length()) {
                val key = allKeys.next()
                val value = normalSerializationJsonObject.get(key)
                val valueAsString = value.toStringOrEmpty()

                val newValue = when (value) {
                    is JSONObject, is JSONArray -> {
                        val valueClass = if (jClass == null) null else jClass.runCatching {
                            declaredFieldsForSuperclassesOnly(declaredFields.toList()).firstOrNull {
                                it.name == key
                            }?.type
                        }.getOrNull()

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

            runCatching {
                for (constructor in jClass!!.constructors) {
                    runCatching {
                        val size = constructor.parameterTypes.size

                        val constructorParams = newParamsWithKeys.subList(0, size).map { it.first }
                        val otherParamsWithKeys = newParamsWithKeys.subList(size, newParamsWithKeys.size)

                        return constructor.newInstance(*constructorParams.toTypedArray()).applyIf(otherParamsWithKeys.isNotEmpty()) {
                            for ((param, key) in otherParamsWithKeys) {
                                val field = runCatching {
                                    javaClass.getDeclaredFieldsForSuperclassesOnly(key)
                                }.getOrNull() ?: continue

                                val isAccessible = field.isAccessible
                                field.isAccessible = true

                                field.set(this, param)

                                field.isAccessible = isAccessible
                            }
                        }
                    }
                }
            }
        }

        return normalSerializationJsonObject.toString().fromJsonOrNullJava(jClass ?: return null)
    }

}
