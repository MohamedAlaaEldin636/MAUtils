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

import com.google.gson.internal.`$Gson$Preconditions`
import com.google.gson.internal.`$Gson$Types`
import mohamedalaa.mautils.gson.java.GsonConverter
import java.io.Serializable
import java.lang.reflect.*

/**
 * - Eliminates any wildcard to it's first upper or lower bound isa.
 */
internal fun GsonConverter.Companion.canonicalize(type: Type): Type {
    return when (type) {
        is Class<*> -> if (type.isArray) {
            GenericArrayTypeImpl(
                canonicalize(
                    type.componentType as Type
                )
            )
        }else {
            type
        }
        is ParameterizedType -> ParameterizedTypeImpl(
            type.ownerType,
            type.rawType,
            type.actualTypeArguments.toList()
        )
        is GenericArrayType -> GenericArrayTypeImpl(
            type.genericComponentType
        )
        is WildcardType -> type.convertToTypeOrNull()!!/*WildcardTypeImpl(type.upperBounds, type.lowerBounds)*/
        else -> type
    }
}

/**
 * - Eliminates any wildcard to it's first upper or lower bound isa.
 */
internal fun GsonConverter.Companion.canonicalizeOrNull(type: Type?): Type? = runCatching {
    canonicalize(type ?: return null)
}.getOrNull()

private class GenericArrayTypeImpl(private val componentType: Type) : GenericArrayType, Serializable {

    companion object {
        private const val serialVersionUID: Long = 0
    }

    override fun getGenericComponentType(): Type {
        return componentType
    }

    override fun equals(other: Any?): Boolean {
        return (other is GenericArrayType
            && `$Gson$Types`.equals(this, other))
    }

    override fun hashCode(): Int {
        return componentType.hashCode()
    }

    override fun toString(): String {
        return `$Gson$Types`.typeToString(componentType) + "[]"
    }

}

/** convert to any non-wildcard type even for type params if parameterized type isa. */
fun WildcardType.convertToTypeOrNull(): Type? {
    val parameterizedType = upperBounds.firstOrNull() ?: lowerBounds.firstOrNull() ?: return null
    if (parameterizedType !is ParameterizedType) return GsonConverter.canonicalize(parameterizedType)

    val ownerType = parameterizedType.ownerType?.run { GsonConverter.canonicalize(this) }
    val rawType = GsonConverter.canonicalize(parameterizedType.rawType)
    val typeArguments = parameterizedType.actualTypeArguments.mapNotNull {
        if (it is WildcardType) it.convertToTypeOrNull() else GsonConverter.canonicalize(it)
    }

    return ParameterizedTypeImpl(
        ownerType,
        rawType,
        typeArguments
    )
}

private class ParameterizedTypeImpl(
    ownerType: Type?,
    rawType: Type,
    typeArguments: List<Type>
) : ParameterizedType, Serializable {

    private val ownerType: Type?
    private val rawType: Type
    private val typeArguments: List<Type>

    companion object {
        private const val serialVersionUID: Long = 0
    }

    init {
        // require an owner type if the raw type needs it isa.
        if (rawType is Class<*>) {
            val isStaticOrTopLevelClass = Modifier.isStatic(rawType.modifiers)
                || rawType.enclosingClass == null

            // throws exception if false isa.
            `$Gson$Preconditions`.checkArgument(ownerType != null || isStaticOrTopLevelClass)
        }
        this.ownerType = if (ownerType == null) null else GsonConverter.canonicalize(ownerType)
        this.rawType = GsonConverter.canonicalize(rawType)

        this.typeArguments = typeArguments.map {
            `$Gson$Preconditions`.checkNotNull(it)
            `$Gson$Preconditions`.checkArgument(it !is Class<*> || !it.isPrimitive)
            GsonConverter.canonicalize(it)
        }
    }

    override fun getActualTypeArguments(): Array<Type> {
        return typeArguments.toTypedArray()
    }

    override fun getRawType(): Type {
        return rawType
    }

    override fun getOwnerType(): Type? {
        return ownerType
    }

    override fun equals(other: Any?): Boolean {
        return (other is ParameterizedType
            && `$Gson$Types`.equals(this, other))
    }

    override fun hashCode(): Int {
        return (actualTypeArguments.contentHashCode()
            xor rawType.hashCode()
            xor (ownerType?.hashCode() ?: 0))
    }

    override fun toString(): String {
        val length = typeArguments.size
        if (length == 0) {
            return `$Gson$Types`.typeToString(rawType)
        }
        val stringBuilder = StringBuilder(30 * (length + 1))
        stringBuilder.append(`$Gson$Types`.typeToString(rawType)).append("<")
            .append(`$Gson$Types`.typeToString(typeArguments[0]))
        for (i in 1 until length) {
            stringBuilder.append(", ").append(`$Gson$Types`.typeToString(typeArguments[i]))
        }
        return stringBuilder.append(">").toString()
    }

}
