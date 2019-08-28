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
        if (src.toJson(doubleCheckGson) != normalSerializationJsonString) {
            println()
            println("in serialization isa -> ${src::class.java}")
            normalSerializationJsonString = src.toJson(doubleCheckGson)
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
            println("ENTEREEEEEED")
            normalDeserialization = normalSerializationJsonObject.toString().fromJsonJava(jClass, doubleCheckGson)
        }

        println("##############################")
        println("$jClass \n-> $normalDeserialization \n-> $normalSerializationJsonObject \n.")
        runCatching {
            //println(normalSerializationJsonObject.toString().fromJson(Class.forName("mohamedalaa.mautils.sample.custom_classes.helper_classes.GameTrumpSuit"), doubleCheckGson))
        }.getOrElse {
            println("smalllllll throwable isa -> $it")
        }
        println("##############################")

        /*additionalChecks(doubleCheckGson, normalDeserialization, normalSerializationJsonObject, jClass).apply {
            if (first) {
                normalDeserialization = second
            }
        }*/

        return normalDeserialization
    }

    /**
     * @return [Pair.first] if true then assign [normalDeserialization] to [Pair.second]
     * otherwise do nothing isa.
     */
    private fun additionalChecks(
        doubleCheckGson: Gson,
        normalDeserialization: Any?,
        normalSerializationJsonObject: JSONObject,
        jClass: Class<*>
    ): Pair<Boolean, Any?> {
        var jsonString = normalSerializationJsonObject.toString()
        while (KEY_CLASS_FULL_NAME in jsonString && KEY_NORMAL_SERIALIZATION_JSON_STRING in jsonString) {
            val indexOfClassFullName = jsonString.indexOf(KEY_CLASS_FULL_NAME)
            if (indexOfClassFullName < 0) break

            val colonIndex = jsonString.lastIndexOf(":", indexOfClassFullName)
            val lastQuoteIndex = jsonString.lastIndexOf("\"", colonIndex)
            val startQuoteIndex = jsonString.lastIndexOf("\"", lastQuoteIndex.dec())

            val key = jsonString.substring(startQuoteIndex.inc(), lastQuoteIndex)

            // todo deserialization work isa.
            /*println("key $key")
            println("whole $jsonString - $normalSerializationJsonObject")
            runCatching {
                println("has next -> ${normalSerializationJsonObject.keys().hasNext()}")
                normalSerializationJsonObject.keys().forEach {
                    println("it $it")
                }
                println(normalSerializationJsonObject.get(key)::class.java)
            }.getOrElse { println("small throwable $it") }
            normalSerializationJsonObject.optJSONObject(key)?.apply {
                deserializeSingleJsonObject(this)
            }*/

            jsonString = jsonString.substring(indexOfClassFullName + KEY_CLASS_FULL_NAME.length)
        }

        if (jsonString == normalSerializationJsonObject.toString()) {
            return false to null
        }else {
            //println()
            //println("$normalSerializationJsonObject ==== $jsonString")
        }



        return try {
            val jsonObj1 = normalSerializationJsonObject.getJSONObject("gameTarneebTypeTrumpSuit")
            val classFullName1 = jsonObj1.optString(KEY_CLASS_FULL_NAME) ?: return false to null
            val jClass1 = runCatchingToNull { Class.forName(classFullName1) } ?: return false to null
            val normalSerializationJsonObject1 = jsonObj1.optJSONObject(
                KEY_NORMAL_SERIALIZATION_JSON_STRING
            ) ?: return false to null

            val normalDeserialization1 = runCatchingToNull { doubleCheckGson.fromJson(normalSerializationJsonObject1.toString(), jClass1) }

            jClass.superclass?.getDeclaredField("gameTarneebTypeTrumpSuit")?.apply {
                //println("jClass $jClass - jClass1 $jClass1")
                isAccessible = true
                set(normalDeserialization, normalDeserialization1)
            }

            true to normalDeserialization
        }catch (throwable: Throwable) {
            //println("Throwable -> $throwable")

            false to null
        }
    }

    private fun deserializeSingleJsonObject(
        jsonObject: JSONObject
    ) {
        //val classFullName = jsonObject.optString(KEY_CLASS_FULL_NAME) ?: return

        // todo what if itself has iside it another one isa...
        // todo try gowa el determine da ne7ot el parent thing class kda isa.
    }

}
