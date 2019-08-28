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
import mohamedalaa.mautils.gson.allAnnotatedClasses
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.java.fromJsonJava
import mohamedalaa.mautils.gson.toJson
import org.json.JSONObject
import java.lang.reflect.Type

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

internal class JsonSerializerForSealedClasses : JsonSerializer<Any> {

    private val excludedClasses = mutableListOf<Class<*>>()

    private fun generateDoubleCheckGson(excludeClass: Class<*>): Gson {
        excludedClasses += excludeClass

        return generateDoubleCheckGson(excludedClasses)
    }

    override fun serialize(
        src: Any?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        if (src == null) {
            return null
        }

        var normalSerializationJsonString = runCatchingToNull { gson.toJson(src, src::class.java) } ?: return null

        val doubleCheckGson = generateDoubleCheckGson(src::class.java)
        src.toJson(doubleCheckGson).apply {
            if (this != normalSerializationJsonString) {
                normalSerializationJsonString = this
            }
        }

        val jsonObject = JSONObject()
        jsonObject.put(KEY_CLASS_FULL_NAME, src.javaClass.name)
        jsonObject.put(KEY_NORMAL_SERIALIZATION_JSON_STRING, runCatching { JSONObject(normalSerializationJsonString) }.getOrElse { return null })

        return runCatching { JsonParser().parse(jsonObject.toString()) }.getOrNull()
    }

}

internal class JsonDeserializerForSealedClasses : JsonDeserializer<Any> {

    private val excludedClasses = mutableListOf<Class<*>>()

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

        val classFullName = jsonObject.optString(KEY_CLASS_FULL_NAME) ?: return null
        val jClass = runCatchingToNull { Class.forName(classFullName) } ?: return null
        val normalSerializationJsonObject = jsonObject.optJSONObject(
            KEY_NORMAL_SERIALIZATION_JSON_STRING
        ) ?: return null

        var normalDeserialization = runCatchingToNull { gson.fromJson(normalSerializationJsonObject.toString(), jClass) }

        val doubleCheckGson = generateDoubleCheckGson(jClass)
        if (normalDeserialization != normalSerializationJsonObject.toString().fromJson(doubleCheckGson)) {
            normalDeserialization = normalSerializationJsonObject.toString().fromJsonJava(jClass, doubleCheckGson)
        }

        return normalDeserialization
    }

}
