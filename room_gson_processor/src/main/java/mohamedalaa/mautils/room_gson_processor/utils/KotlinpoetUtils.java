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

package mohamedalaa.mautils.room_gson_processor.utils;

import com.squareup.kotlinpoet.ClassName;
import com.squareup.kotlinpoet.ParameterizedTypeName;
import com.squareup.kotlinpoet.TypeName;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 4/27/2019.
 */
public class KotlinpoetUtils {

    public static TypeName parameterizedTypeName(ClassName receiver, ClassName... typeParams) {
        return ParameterizedTypeName.get(receiver, typeParams);
    }

    public static TypeName parameterizedTypeName(ClassName receiver, TypeName... typeParams) {
        return ParameterizedTypeName.get(receiver, typeParams);
    }

    public static TypeName parameterizedTypeName(String receiver, String... typeParams) {
        ClassName[] array = new ClassName[typeParams.length];
        for (int i = 0; i < typeParams.length; i++) {
            array[i] = ClassName.bestGuess(typeParams[i]);
        }

        return parameterizedTypeName(ClassName.bestGuess(receiver), array);
    }

}
