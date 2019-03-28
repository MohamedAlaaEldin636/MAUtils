@file:JvmName("GsonUtils")

package mohamedalaa.mautils.mautils_gson.java

import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import mohamedalaa.mautils.mautils_gson.generateGson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Converts `receiver` object to a JSON String OR null in case of any error isa.
 *
 * **Warning**
 *
 * If `receiver` has type parameters, then you have to use [GsonConverter.toJsonOrNullJava] isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [generateGson] isa.
 *
 * @return JSON String OR null if any problem occurs isa.
 *
 * @see toJsonJava
 * @see fromJsonOrNullJava
 */
@JvmOverloads
@JvmName("toJsonOrNull")
fun <E> E?.toJsonOrNullJava(gson: Gson? = null): String? = this?.run {
    val usedGson = gson ?: generateGson()

    try { usedGson.toJson(this) } catch (e: Exception) { null }
}

/**
 * Converts `receiver` object to a JSON String OR throws exception in case of any error isa.
 *
 * **Warning**
 *
 * If `receiver` has type parameters, then you have to use [GsonConverter.toJsonJava] isa.
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [generateGson] isa.
 *
 * @throws RuntimeException in case if any problem occurred while converting isa.
 *
 * @return JSON String OR throws exception if any problem occurs isa.
 *
 * @see toJsonOrNullJava
 * @see fromJsonJava
 */
@JvmOverloads
@JvmName("toJson")
fun <E> E?.toJsonJava(gson: Gson? = null): String = toJsonOrNullJava(gson)
    ?: throw RuntimeException("Cannot convert $this to JSON String")

/**
 * Converts `this JSON String` to object of type [elementClass], OR null in case of any error occurs isa.
 *
 * **Warning**
 *
 * If `receiver` has type parameters, then you have to use [GsonConverter.fromJsonOrNull] isa.
 *
 * @param elementClass class of the `receiver` to be used isa.
 * @param gson in case you want a special configuration for [Gson], Note default value used is [generateGson] isa.
 *
 * @return object of type <E> from given JSON String OR null if any problem occurs isa.
 *
 * @see fromJsonJava
 * @see toJsonOrNullJava
 */
@JvmOverloads
@JvmName("fromJsonOrNull")
fun <E> String?.fromJsonOrNullJava(elementClass: Class<E>, gson: Gson? = null): E? = this?.run {
    val usedGson = gson ?: generateGson()

    try { usedGson.fromJson(this, elementClass) } catch (e: Exception) { null }
}

/**
 * Converts `this JSON String` to object of type [elementClass], OR throws exception in case of any error occurs isa.
 *
 * **Warning**
 *
 * If `receiver` has type parameters, then you have to use [GsonConverter.fromJson] isa.
 *
 * @param elementClass class of the `receiver` to be used isa.
 * @param gson in case you want a special configuration for [Gson], Note default value used is [generateGson] isa.
 *
 * @return object of type <[E]> from given JSON String OR throws exception if any problem occurs isa.
 *
 * @throws RuntimeException in case if any problem occurred while converting isa.
 *
 * @see fromJsonOrNullJava
 * @see toJsonJava
 */
@JvmOverloads
@JvmName("fromJson")
fun <E> String?.fromJsonJava(elementClass: Class<E>, gson: Gson? = null): E = fromJsonOrNullJava(elementClass, gson)
    ?: throw RuntimeException("Cannot convert $this to $elementClass")

/**
 * ## Description
 * Used only if your Object that needs to be converted to/from JSON-String has type parameters isa,
 *
 * otherwise consider using [String.fromJsonOrNullJava], [String.toJsonOrNullJava], [String.fromJsonJava]
 * OR [String.toJsonJava] isa.
 *
 * Used by java developers OR kotlin developers if and only if has non-invariant nested type parameters,
 *
 * See [mohamedalaa.mautils.mautils_gson.fromJsonOrNull] for more clarification isa.
 * ## How to use
 * ```
 * CustomWithTypeParam<CustomObject, Integer> customWithTypeParam = new CustomWithTypeParam<>();
 * String jsonString = new GsonConverter<CustomWithTypeParam<CustomObject, Integer>>(){}.toJsonOrNullJava(customWithTypeParam);
 * CustomWithTypeParam<CustomObject, Integer> fromJsonObject
 *      = new GsonConverter<CustomWithTypeParam<CustomObject, Integer>>(){}.fromJsonOrNullJava(jsonString);
 * // by now -> customWithTypeParam == fromJsonObject isa.
 * ```
 *
 * @param gson in case you want a special configuration for [Gson], Note default value used is [generateGson] isa.
 */
abstract class GsonConverter<E>(private val gson: Gson? = null) {

    /**
     * Converts [element] object to a JSON String OR null in case of any error isa,
     *
     * Even if type [element] has type parameter isa, Note if [element] has no type parameters
     * Use [String.toJsonOrNullJava] instead, since it's more concise syntax isa.
     *
     * For **How to use example** See [GsonConverter]
     *
     * @param element object to convert to JSON-String isa.
     *
     * @return JSON String or null if any problem occurs isa.
     */
    fun toJsonOrNull(element: E?): String? {
        val type = getSuperclassTypeParameter(javaClass)

        return element.toJsonOrNullJavaPrivate(type, gson)
    }

    /**
     * Converts `receiver` object to a JSON String OR throws exception in case of any error isa,
     *
     * Even if type [element] has type parameter isa, Note if [element] has no type parameters
     * Use [String.toJsonOrNullJava] instead, since it's more concise syntax isa.
     *
     * For **How to use example** See [GsonConverter]
     *
     * @param element object to convert to JSON-String isa.
     *
     * @throws RuntimeException in case of any error while converting isa.
     *
     * @return JSON String or throws exception if any problem occurs isa.
     */
    fun toJson(element: E?): String = toJsonOrNull(element)
        ?: throw RuntimeException("Cannot convert $element to JSON String")

    /**
     * Converts [json] to object of type [E], or null in case of any error occurs isa,
     *
     * Even if type [E] has type parameter isa, Note if [E] has no type parameters
     * Use [String.fromJsonOrNullJava] instead, since it's more concise syntax isa.
     *
     * For **How to use example** See [GsonConverter]
     *
     * @return object of type <E> from given JSON String or null if any problem occurs isa.
     */
    fun fromJsonOrNull(json: String?): E? {
        val type = getSuperclassTypeParameter(javaClass)

        return json.fromJsonOrNullJavaPrivate<E>(type, gson)
    }

    /**
     * Converts [json] to object of type [E], or throws exception in case of any error occurs isa,
     *
     * Even if type [E] has type parameter isa, Note if [E] has no type parameters
     * Use [String.fromJsonOrNullJava] instead, since it's more concise syntax isa.
     *
     * For **How to use example** See [GsonConverter]
     *
     * @throws RuntimeException in case of any error while converting isa.
     *
     * @return object of type <E> from given JSON String or throws exception if any problem occurs isa.
     */
    fun fromJson(json: String?): E = fromJsonOrNull(json)
        ?: throw RuntimeException("Cannot convert $json to <E>")

    private fun getSuperclassTypeParameter(subclass: Class<*>): Type {
        val superclass = subclass.genericSuperclass
        if (superclass is Class<*>) {
            throw RuntimeException("Missing type parameter isa.")
        }

        val parameterizedType = superclass as ParameterizedType
        return `$Gson$Types`.canonicalize(parameterizedType.actualTypeArguments[0])
    }

}

private fun <E> E?.toJsonOrNullJavaPrivate(type: Type, gson: Gson?): String? = this?.run {
    val usedGson = gson ?: generateGson()

    try { usedGson.toJson(this, type) } catch (e: Exception) { null }
}

private fun <E> String?.fromJsonOrNullJavaPrivate(type: Type, gson: Gson?): E? = this?.run {
    val usedGson = gson ?: generateGson()

    try { usedGson.fromJson(this, type) } catch (e: Exception) { null }
}

