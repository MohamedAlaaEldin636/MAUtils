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

package mohamedalaa.mautils.core_android_processor.extensions;

import com.squareup.kotlinpoet.ClassName;
import com.squareup.kotlinpoet.ParameterizedTypeName;
import com.squareup.kotlinpoet.TypeName;
import com.squareup.kotlinpoet.TypeNames;

import javax.lang.model.type.TypeMirror;

import kotlin.reflect.KClass;

@SuppressWarnings("unused")
public class KotlinpoetUtils {

    public static TypeName typeName(String fullName) {
        return ParameterizedTypeName.get(ClassName.bestGuess(fullName));
    }

    public static TypeName typeName(String packageName, String simpleName) {
        return ParameterizedTypeName.get(new ClassName(packageName, simpleName));
    }

    public static TypeName parameterizedTypeName(KClass receiver, KClass... typeParams) {
        return ParameterizedTypeName.get(receiver, typeParams);
    }

    public static TypeName parameterizedTypeName(ClassName receiver, ClassName... typeParams) {
        return ParameterizedTypeName.get(receiver, typeParams);
    }

    public static TypeName parameterizedTypeName(TypeName receiver, TypeName... typeParams) {
        return ParameterizedTypeName.get((ClassName) receiver, typeParams);
    }

    public static TypeName typeName(KClass kClass) {
        return TypeNames.get(kClass);
    }

    public static TypeName typeName1(TypeMirror mirror) {
        return TypeNames.get(mirror);
    }

    public static TypeName parameterizedTypeName(String receiverFullName, TypeName typeName) {
        return ParameterizedTypeName.get(ClassName.bestGuess(receiverFullName), typeName);
    }

    public static TypeName parameterizedTypeNameStar(String receiverFullName) {
        return ParameterizedTypeName.get(ClassName.bestGuess(receiverFullName), TypeNames.STAR);
    }

}
