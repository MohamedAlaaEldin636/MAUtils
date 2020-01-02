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

package mohamedalaa.mautils.gson.del_later

import java.io.Serializable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType

/**
 * - MUST HAVE 1 UPPER BOUND ISA.
 */
fun WildcardType.convertToTypeOrNullIsa(): Type? {
    val parameterizedType = upperBounds.firstOrNull() ?: return null
    if (parameterizedType !is ParameterizedType) return parameterizedType

    val ownerType = parameterizedType.ownerType
    val rawType = parameterizedType.rawType
    val typeArguments = parameterizedType.actualTypeArguments.mapNotNull {
        if (it is WildcardType) it.convertToTypeOrNullIsa() else it
    }

    return ParamTypeImpl(ownerType, rawType, typeArguments)
}

/**
 * todo be sure 100% that the [rawType] can be parameterized and the [typeArguments] are valid types isa.
 */
class ParamTypeImpl(ownerType: Type?, rawType: Type, typeArguments: List<Type>) : ParameterizedType/*Serializable*/ {

    private val privateOwnerType: Type? = ownerType?.privateCanonicalize()
    private val privateRawType: Type = rawType.privateCanonicalize()
    private val privateTypeArguments: List<Type> = typeArguments.map {
        it.privateCanonicalize()
    }

    override fun getOwnerType(): Type? = privateOwnerType

    override fun getRawType(): Type = privateRawType

    override fun getActualTypeArguments(): Array<Type> = privateTypeArguments.toTypedArray()

}

private fun Type.privateCanonicalize(): Type {
    // todo
    return this
}

/*
  private static final class ParameterizedTypeImpl implements ParameterizedType, Serializable {
    private final Type ownerType;
    private final Type rawType;
    private final Type[] typeArguments;

    public ParameterizedTypeImpl(Type ownerType, Type rawType, Type... typeArguments) {
      // require an owner type if the raw type needs it
      if (rawType instanceof Class<?>) {
        Class<?> rawTypeAsClass = (Class<?>) rawType;
        boolean isStaticOrTopLevelClass = Modifier.isStatic(rawTypeAsClass.getModifiers())
            || rawTypeAsClass.getEnclosingClass() == null;
        checkArgument(ownerType != null || isStaticOrTopLevelClass);
      }

      this.ownerType = ownerType == null ? null : canonicalize(ownerType);
      this.rawType = canonicalize(rawType);
      this.typeArguments = typeArguments.clone();
      for (int t = 0, length = this.typeArguments.length; t < length; t++) {
        checkNotNull(this.typeArguments[t]);
        checkNotPrimitive(this.typeArguments[t]);
        this.typeArguments[t] = canonicalize(this.typeArguments[t]);
      }
    }

    public Type[] getActualTypeArguments() {
      return typeArguments.clone();
    }

    public Type getRawType() {
      return rawType;
    }

    public Type getOwnerType() {
      return ownerType;
    }

    @Override public boolean equals(Object other) {
      return other instanceof ParameterizedType
          && $Gson$Types.equals(this, (ParameterizedType) other);
    }

    @Override public int hashCode() {
      return Arrays.hashCode(typeArguments)
          ^ rawType.hashCode()
          ^ hashCodeOrZero(ownerType);
    }

    @Override public String toString() {
      int length = typeArguments.length;
      if (length == 0) {
        return typeToString(rawType);
      }

      StringBuilder stringBuilder = new StringBuilder(30 * (length + 1));
      stringBuilder.append(typeToString(rawType)).append("<").append(typeToString(typeArguments[0]));
      for (int i = 1; i < length; i++) {
        stringBuilder.append(", ").append(typeToString(typeArguments[i]));
      }
      return stringBuilder.append(">").toString();
    }

    private static final long serialVersionUID = 0;
  }

 */
