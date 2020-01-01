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
import mohamedalaa.mautils.core_kotlin.*
import mohamedalaa.mautils.core_kotlin.extensions.applyIf
import mohamedalaa.mautils.core_kotlin.extensions.contains
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import mohamedalaa.mautils.gson.allAnnotatedClasses
import mohamedalaa.mautils.gson.allAnnotatedClassesAsString
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.java.fromJsonJava
import mohamedalaa.mautils.gson.toJsonOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Field
import java.lang.reflect.Type
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaType

// todo in the special cases of sealed class vars in a data class or direct selead class
//  I don't check fields in super classes as well isa.

private val gson = GsonBuilder()
    .serializeNulls()
    .setLenient()
    .enableComplexMapKeySerialization()
    .create()

private fun generateDoubleCheckGson(excludedClasses: List<Class<*>>): Gson {
    val gsonBuilder = GsonBuilder()

    allAnnotatedClasses?.forEach {
        if (it in excludedClasses) return@forEach

        gsonBuilder.registerTypeAdapter(it, JsonSerializerForSealedClasses())
        gsonBuilder.registerTypeAdapter(it, JsonDeserializerForSealedClasses())
    }

    return gsonBuilder
        .serializeNulls()
        .setLenient()
        .enableComplexMapKeySerialization()
        .create()
}

private const val KEY_CLASS_FULL_NAME = "KEY_CLASS_FULL_NAME"
private const val KEY_NORMAL_SERIALIZATION_JSON_STRING = "KEY_NORMAL_SERIALIZATION_JSON_STRING"

private inline fun <R> runCatchingToNull(block: () -> R): R?
    = runCatching { block() }.getOrNull()

// todo move below isa.
fun Class<*>.declaredFieldsForSuperclassesOnly(initialList: List<Field> = emptyList()): List<Field> {
    // interfaces
    val superclass = superclass
    val list = (superclass ?: return initialList).declaredFields.filterNotNull()
    return superclass.declaredFieldsForSuperclassesOnly(initialList + list)
}

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
            // todo zawwed el beow one isa.
            val classDeclaredFields = src.javaClass.declaredFields.filterNotNull()
            val classDeclaredFieldsNames = classDeclaredFields.map { it.name }
            val allSuperclassesDeclaredFields = src.javaClass.declaredFieldsForSuperclassesOnly().filter {
                it.name !in classDeclaredFieldsNames // so there will be no clash of same name isa.
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
                    // todo what if value was enum is that ok or not isa ?!
                    when (fieldInstance) {
                        null -> null
                        is String, is Boolean, is Int, is Long, is Double -> fieldInstance
                        else -> if (jsonValue == null) null else runCatching {
                            JSONObject(jsonValue)
                        }.getOrElse {
                            runCatching { JSONArray(jsonValue) }.getOrNull()
                        }
                    }
                    /*when (fieldInstance) {
                        null -> null
                        is String -> fieldInstance
                        is Boolean -> jsonValue
                        is Int -> jsonValue
                        is Long -> jsonValue
                        is Double -> jsonValue
                        else -> if (jsonValue == null) null else runCatching {
                            JSONObject(jsonValue)
                        }.getOrElse {
                            runCatching { JSONArray(jsonValue) }.getOrNull()
                        }
                    }*/
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

// todo move below isa.
fun Class<*>.getDeclaredFieldsForSuperclassesOnly(name: String): Field? {
    val allFields = declaredFieldsForSuperclassesOnly()
    return allFields.firstOrNull {
        it.name == name
    }
}

internal class JsonDeserializerForSealedClasses : JsonDeserializer<Any> {

    private val excludedClasses = mutableListOf<Class<*>>()

    // todo maybe del this later isa.
    private fun generateDoubleCheckGson(excludeClass: Class<*>): Gson {
        excludedClasses += excludeClass

        return generateDoubleCheckGson(excludedClasses)
    }

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
        val jClass = runCatchingToNull { Class.forName(classFullName) } ?: typeOfT as? Class<*>
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
                        // todo what if key not direct inside it isa. t2reban solved by updating above ?: return null
                        // but try to test it i mean value hna aha feha sealed class bs msh direct child f el value la2 gowa 7ad tany isa.
                        val clazz = if (jClass == null) null else jClass.runCatching {
                            //cyanPrintLn("$jClass ${fields.toList()}")
                            //cyanPrintLn("-> ${jClass.methods.toList()}")
                            //cyanPrintLn("-> ${jClass.kotlin.declaredMemberProperties.toList()}")
                            // todo maybe try all propertied not the declared ones only isa. for superclasses isa.

                            fields.firstOrNull {
                                it?.name == key
                            }?.type ?: kotlin.declaredMemberProperties.firstOrNull {
                                it.name == key
                            }?.returnType?.javaType
                        }.getOrNull()

                        deserialize(
                            runCatching { JsonParser().parse(valueAsString) }.getOrNull(),
                            clazz ?: jClass/*wrong but not needed isa.*/, // param class from reflection isa. OR field with name key brdo reflection isa.
                            context
                        )
                    }
                    else -> value
                }
                newParamsWithKeys += newValue to key
            }

            // todo wrong what if child inside child we need to check if direct or noot by looping each JSON key/value pair isa.
            runCatching {
                // todo keep trying every constructor isa. and if no way return null isa.

                // newParams . subList acc to constructor params isa.


                /*errorPrintLn("jClass $jClass")
                errorPrintLn("normalSerializationJsonObject $normalSerializationJsonObject")*/


                // todo reflection should be for every case not this special case
                // so either use no-args one or the below approach then set every field
                // BUT what if class is selead class msh 3aref see el class type mn el json isa.
                for (constructor in jClass!!.constructors) {
                    runCatching {
                        val size = constructor.parameterTypes.size

                        val constructorParams = newParamsWithKeys.subList(0, size).map { it.first }
                        val otherParamsWithKeys = newParamsWithKeys.subList(size, newParamsWithKeys.size)

                        /*infoPrintLn("size $size, newParams $newParams")
                        warnPrintLn("constructor.parameterTypes ${constructor.parameterTypes.toList()}")*/

                        //infoPrintLn("size $size, newParams.size ${newParams.size}")

                        return constructor.newInstance(*constructorParams.toTypedArray()).applyIf(otherParamsWithKeys.isNotEmpty()) {
                            for ((param, key) in otherParamsWithKeys) {
                                val field = runCatching {
                                    javaClass.getDeclaredFieldsForSuperclassesOnly(key)!!
                                }.getOrElse {
                                    errorPrintLn("error isa is $it")

                                    null
                                } ?: continue

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

        if (jClass == null) return null

        var normalDeserialization = runCatchingToNull { gson.fromJson(normalSerializationJsonObject.toString(), jClass) }

        val doubleCheckGson = generateDoubleCheckGson(jClass)
        if (normalDeserialization != normalSerializationJsonObject.toString().fromJson(doubleCheckGson)) {
            normalDeserialization = normalSerializationJsonObject.toString().fromJsonJava(jClass, doubleCheckGson)
        }

        return normalDeserialization
    }

}
