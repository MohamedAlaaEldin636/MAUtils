@file:JvmName("GsonUtils")

package mohamedalaa.mautils.mautils_gson

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList
import kotlin.Exception
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.jvmErasure

/**
 * Converts `Any` to a JSON String so it can be used easily, Ex. on save instance state,
 * Shared pref, However do not use that approach for these examples unless the object is not huge,
 * OR null in case of any error isa.
 *
 * @return object as JSON String or null if any problem occurs
 *
 * @see toJsonString
 * @see fromJsonStringOrNull
 * @see fromJsonStringAsListOrNull
 * @see fromJsonStringAsListOrNullSafely
 */
fun Any?.toJsonStringOrNull(): String? {
    if (this == null) {
        return null
    }

    val gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .create()

    return try { gson.toJson(this) } catch (e: Exception) { null }
}

/**
 * Converts `Any` to a JSON String so it can be used easily, Ex. on save instance state,
 * Shared pref, However do not use that approach for these examples unless the object is not huge,
 * OR throws exception in case of any error isa.
 *
 * @return object as JSON String or throws exception if any problem occurs isa.
 *
 * @throws RuntimeException in case of any problem happened while converting isa, Ex. `receiver` was null.
 *
 * @see toJsonStringOrNull
 * @see fromJsonString
 * @see fromJsonStringAsList
 * @see fromJsonStringAsListSafely
 */
fun Any?.toJsonString(): String? = toJsonStringOrNull()
    ?: throw RuntimeException("Cannot convert $this to json string.")

/**
 * Converts `this JSON String` to <E> object, or null in case of any error isa,
 * Consider using [fromJsonStringAsListOrNull] OR [fromJsonStringAsListOrNullSafely], OR their
 * corresponding non null returns, in case if object is a list isa.
 *
 * @return object from given JSON String or null if any problem occurs isa.
 *
 * @see fromJsonString
 * @see fromJsonStringAsListOrNull
 * @see fromJsonStringAsListOrNullSafely
 */
inline fun <reified E> String?.fromJsonStringOrNull(): E? {
    if (isNullOrEmpty()) {
        return null
    }

    val gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .create()

    return try { gson.fromJson(this, E::class.java) } catch (e: Exception) { null }
}

/**
 * Converts `this JSON String` to <E> object or throws exception in case of any error isa,
 * Consider using [fromJsonStringAsListOrNull] OR [fromJsonStringAsListOrNullSafely], OR their
 * corresponding non null returns, in case if object is a list isa.
 *
 * @return object from given JSON String or throws exception if any problem occurs isa.
 *
 * @throws RuntimeException in case of any problem happened while converting isa.
 *
 * @see fromJsonStringOrNull
 * @see fromJsonStringAsListOrNull
 * @see fromJsonStringAsListOrNullSafely
 */
inline fun <reified E> String?.fromJsonString(): E = fromJsonStringOrNull<E>()
    ?: throw RuntimeException("Cannot convert $this to ${E::class.simpleName}")

/**
 * Converts `this JSON Array String` to List<E?> object, or null in case of any error isa,
 * Consider using [fromJsonStringOrNull] OR it's corresponding non null return,
 * in case if object isn't a list and is a custom object, that can be serialized using gson isa.
 *
 * **NOTE (Diff between `this` & [fromJsonStringAsListOrNull])**
 *
 * 1- `this` returns List<E?> so if a child had a problem in conversion it is replaced with null,
 * however the other approach returns the whole list as null instead isa.
 *
 * 2- either `this` is JSON Array with <E> as items directly or with JSON Object of <E>,
 * it will work correctly isa.
 *
 * @return List of <E?> object from given JSON String or null if any problem occurs isa.
 *
 * @see fromJsonStringAsListSafely
 * @see fromJsonString
 * @see fromJsonStringAsListOrNull
 */
inline fun <reified E> String?.fromJsonStringAsListOrNullSafely(): List<E?>? {
    if (isNullOrEmpty()) {
        return null
    }

    val list = ArrayList<E?>()
    try {
        val jsonArray = JSONArray(this)

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray[i]

            val genericObject = if (item is E) {
                jsonArray.get(i) as? E
            }else {
                val jsonObject = jsonArray.optJSONObject(i)

                var jsonStringItem: String? = null
                try {
                    jsonStringItem = jsonObject.toString()
                }catch (innerException: Exception) {
                    // Do nothing isa.
                }

                jsonStringItem.fromJsonStringOrNull<E>()
            }

            list.add(genericObject)
        }
    }catch (e: JSONException) {
        return null
    }

    return list
}

/**
 * Converts `this JSON Array String` to List<E?> object, or throws exception in case of any error isa,
 * Consider using [fromJsonString] OR it's corresponding non null return,
 * in case if object isn't a list and is a custom object, that can be serialized using gson isa.
 *
 * **NOTE (Diff between `this` & [fromJsonStringAsList])**
 *
 * 1- `this` returns List<E?> so if a child had a problem in conversion it is replaced with null,
 * however the other approach throws error instead isa.
 *
 * 2- either `this` is JSON Array with <E> as items directly or with JSON Object of <E>,
 * it will work correctly isa.
 *
 * @return List of <E?> object from given JSON String or throws exception if any problem occurs isa.
 *
 * @throws RuntimeException in case of any error happened while converting isa.
 *
 * @see fromJsonStringAsListOrNullSafely
 * @see fromJsonStringOrNull
 * @see fromJsonStringAsListOrNull
 */
inline fun <reified E> String?.fromJsonStringAsListSafely(): List<E?> = fromJsonStringAsListOrNullSafely()
    ?: throw RuntimeException("Cannot convert $this to List<${E::class.simpleName}>")

/**
 * Converts `this JSON Array String` to List<E> object, or null in case of any error isa,
 * Consider using [fromJsonStringOrNull] OR it's corresponding non null return,
 * in case if object isn't a list and is a custom object, that can be serialized using gson isa.
 *
 * **NOTE (Diff between `this` & [fromJsonStringAsListOrNullSafely])**
 *
 * 1- the other approach returns List<E?> so if a child had a problem in conversion it is replaced with null,
 * however `this` returns the whole list as null instead isa.
 *
 * 2- either `this` is JSON Array with <E> as items directly or with JSON Object of <E>,
 * it will work correctly isa.
 *
 * @return List of <E> object from given JSON String or null if any problem occurs isa.
 *
 * @see fromJsonStringAsListSafely
 * @see fromJsonString
 * @see fromJsonStringAsList
 */
inline fun <reified E> String?.fromJsonStringAsListOrNull(): List<E>? {
    if (isNullOrEmpty()) {
        return null
    }else if (null is E) {
        @Suppress("UNCHECKED_CAST")
        return fromJsonStringAsListOrNullSafely<E?>() as? List<E>?
    }

    val list = ArrayList<E>()
    try {
        val jsonArray = JSONArray(this)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.optJSONObject(i)

            var jsonStringItem: String? = null
            try {
                jsonStringItem = jsonObject.toString()
            }catch (innerException: Exception) {
                // Do nothing isa.
            }

            val genericObject = jsonStringItem.fromJsonStringOrNull<E>() ?: return null

            list.add(genericObject)
        }
    }catch (e: JSONException) {
        return null
    }

    return list
}

/**
 * Converts `this JSON Array String` to List<E> object, or throws exception in case of any error isa,
 * Consider using [fromJsonString] OR it's corresponding non null return,
 * in case if object isn't a list and is a custom object, that can be serialized using gson isa.
 *
 * **NOTE (Diff between `this` & [fromJsonStringAsListSafely])**
 *
 * 1- the other approach returns List<E?> so if a child had a problem in conversion it is replaced with null,
 * however `this` throws error instead isa.
 *
 * 2- either `this` is JSON Array with <E> as items directly or with JSON Object of <E>,
 * it will work correctly isa.
 *
 * @return List of <E> object from given JSON String or throws exception if any problem occurs isa.
 *
 * @throws RuntimeException in case of any error happened while converting isa.
 *
 * @see fromJsonStringAsListOrNullSafely
 * @see fromJsonStringOrNull
 * @see fromJsonStringAsListOrNull
 */
inline fun <reified E> String?.fromJsonStringAsList(): List<E> = fromJsonStringAsListOrNull()
    ?: throw RuntimeException("Cannot convert $this to List<${E::class.simpleName}>")

// todo

/**
 * Converts `this JSON String` to <E> object, or null in case of any error isa,
 * Consider using [fromJsonStringAsListOrNull] OR [fromJsonStringAsListOrNullSafely], OR their
 * corresponding non null returns, in case if object is a list isa.
 *
 * @return object from given JSON String or null if any problem occurs isa.
 *
 * @see fromJsonString
 * @see fromJsonStringAsListOrNull
 * @see fromJsonStringAsListOrNullSafely
 */
inline fun <reified E> String?.trialOneFromJson(): E? {
    if (isNullOrEmpty()) {
        return null
    }

    val gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .enableComplexMapKeySerialization()
        .create()

    val collectionType = object : TypeToken<E>(){}.type

    return try { gson.fromJson(this, collectionType/*E::class.java*/) } catch (e: Exception) { null }
}

/**
 * Converts `Any` to a JSON String so it can be used easily, Ex. on save instance state,
 * Shared pref, However do not use that approach for these examples unless the object is not huge,
 * OR null in case of any error isa.
 *
 * @return object as JSON String or null if any problem occurs
 *
 * @see toJsonString
 * @see fromJsonStringOrNull
 * @see fromJsonStringAsListOrNull
 * @see fromJsonStringAsListOrNullSafely
 */
inline fun <reified E> E?.trialOneToJsonStringOrNull(): String? {
    if (this == null) {
        return null
    }

    val gson = GsonBuilder()
        .serializeNulls()
        .setLenient()
        .enableComplexMapKeySerialization()
        .create()

    val collectionType = object : TypeToken<E>(){}.type

    return try { gson.toJson(this, collectionType) } catch (e: Exception) { null }
}

