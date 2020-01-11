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

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSerializationContext
import mohamedalaa.mautils.core_kotlin.extensions.*
import mohamedalaa.mautils.gson.allAnnotatedClassesAsString
import mohamedalaa.mautils.gson.declaredFieldsForSuperclassesOnly
import mohamedalaa.mautils.gson.toJsonOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Field
import java.lang.reflect.Type

internal fun Field.needSpecialSerialize(): Boolean {
    val classDeclaredFields = type.declaredFields.filterNotNull()
    val classDeclaredFieldsNames = classDeclaredFields.map { it.name }
    val allSuperclassesDeclaredFields = type.declaredFieldsForSuperclassesOnly().filter {
        it.name !in classDeclaredFieldsNames // so there will be no clash of same name isa. ( might instead produce error in future isa. )
    }
    for (field in (classDeclaredFields + allSuperclassesDeclaredFields)) {
        if (allAnnotatedClassesAsString.any { it in field.type.toStringOrEmpty() }) {
            val jClass = field.genericType as? Class<*> ?: continue
            val additionalRequirementMet = jClass.isInterface || jClass.kotlin.isSealed || jClass.kotlin.isAbstract
            if (additionalRequirementMet) return true
        }
    }

    return false
}

internal fun JsonSerializerSpecial.Companion.serialize(
    src: Any?,
    typeOfSrc: Type?,
    context: JsonSerializationContext?
): JsonElement? {
    if (src == null) {
        return null
    }

    warnPrintLn("${src.javaClass}, $typeOfSrc")

    val jsonObject = JSONObject()
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

            println(field.type.toStringOrEmpty())
            if ("RepeatUntilPolicy" in field.type.toStringOrEmpty()) {
                errorPrintLn("${field.type.toStringOrEmpty()}, ${field.type != fieldInstance?.javaClass}")
            }
            if (field.type != fieldInstance?.javaClass) {
                val fieldTypeAsString = field.type.toStringOrEmpty()
                if (allAnnotatedClassesAsString.any { it in fieldTypeAsString }) {
                    jsonValue = JsonSerializerSpecial.serialize(fieldInstance, field.type, context).toStringOrNull()
                }
            }else if (field.needSpecialSerialize()) {
                jsonValue = JsonSerializerSpecial.serialize(fieldInstance, field.type, context).toStringOrNull()
            }

            jsonObject.put(
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

    return runCatching { JsonParser().parse(jsonObject.toString()) }.getOrNull()
}

internal class JsonSerializerSpecial {
    companion object
}
