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

package mohamedalaa.mautils.gson;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import mohamedalaa.mautils.core_kotlin.Del_LaterKt;

public abstract class TestGsonConverterB<E> {

    @Nullable
    private Gson gson;

    public TestGsonConverterB(){}
    public TestGsonConverterB(@Nullable Gson gson) {
        this.gson = gson;
    }

    @Nullable
    public String toJsonOrNull(@Nullable E element) {
        Type type = getSuperclassTypeParameter(getClass());

        return toJsonOrNullJavaPrivate(element, type, gson);
    }

    @NonNull
    public String toJson(@Nullable E element) {
        String jsonString = toJsonOrNull(element);

        if (jsonString != null) return jsonString;

        throw new RuntimeException("Cannot convert " + element + " to JSON String");
    }

    @Nullable
    public E fromJsonOrNull(@Nullable String json) {
        Type type = getSuperclassTypeParameter(getClass());


        return fromJsonOrNullJavaPrivate(json, type, gson);
    }

    @NonNull
    public E fromJson(@Nullable String json) {
        E value = fromJsonOrNull(json);

        if (value != null) return value;

        throw new RuntimeException("Cannot convert " + json + " to <E>");
    }

    @NonNull
    private Type getSuperclassTypeParameter(@NonNull Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter isa.");
        }

        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        if (parameterizedType == null) {
            throw new RuntimeException("type parameter is null isa.");
        }
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    @Nullable
    private String toJsonOrNullJavaPrivate(@Nullable E element, @NonNull Type type, @Nullable Gson gson) {
        @NonNull Gson usedGson;
        if (gson != null) {
            usedGson = _GsonKt.addTypeAdapters(gson);
        }else  {
            usedGson = _GsonKt.getPrivateGeneratedGson();
        }

        try {
            return usedGson.toJson(element, type);
        }catch (Exception e) {
            return null;
        }
    }

    @Nullable
    private E fromJsonOrNullJavaPrivate(@Nullable String json, @NonNull Type type, @Nullable Gson gson) {
        @NonNull Gson usedGson;
        if (gson != null) {
            usedGson = _GsonKt.addTypeAdapters(gson);
        }else  {
            usedGson = _GsonKt.getPrivateGeneratedGson();
        }

        try {
            return usedGson.fromJson(json, type);
        }catch (Exception e) {
            return null;
        }
    }

}
