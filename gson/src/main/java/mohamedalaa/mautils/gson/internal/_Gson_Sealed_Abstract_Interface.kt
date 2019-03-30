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
import org.json.JSONObject
import java.lang.reflect.Type

private val gson = GsonBuilder()
    .serializeNulls()
    .setLenient()
    .enableComplexMapKeySerialization()
    .create()

private const val KEY_CLASS_FULL_NAME = "KEY_CLASS_FULL_NAME"
private const val KEY_NORMAL_SERIALIZATION_JSON_STRING = "KEY_NORMAL_SERIALIZATION_JSON_STRING"

private inline fun <R> runCatchingToNull(block: () -> R): R?
    = runCatching { block() }.getOrNull()

internal class JsonSerializerForSealedClasses : JsonSerializer<Any> {

    override fun serialize(
        src: Any?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        if (src == null) {
            return null
        }

        val normalSerializationJsonString = runCatchingToNull { gson.toJson(src, src::class.java) } ?: return null

        val jsonObject = JSONObject()
        jsonObject.put(KEY_CLASS_FULL_NAME, src.javaClass.name)
        jsonObject.put(KEY_NORMAL_SERIALIZATION_JSON_STRING, normalSerializationJsonString)

        return JsonParser().parse(jsonObject.toString())
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

        val classFullName = jsonObject.optString(KEY_CLASS_FULL_NAME) ?: return null
        val jClass = runCatchingToNull { Class.forName(classFullName) } ?: return null
        val normalSerializationJsonString = jsonObject.optString(
            KEY_NORMAL_SERIALIZATION_JSON_STRING
        ) ?: return null

        return runCatchingToNull { gson.fromJson(normalSerializationJsonString, jClass) }
    }

}