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

package mohamedalaa.mautils.gson_processor.utils

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterizedTypeName
import javax.lang.model.element.Modifier

/** Generate public static final method that returns given [init] isa. */
fun buildMethodSpec(init: List<String>, methodName: String = "method1"): MethodSpec {
    return MethodSpec.methodBuilder(methodName)
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)

        .returns(ParameterizedTypeName.get(List::class.java, String::class.java))

        .init(init)

        .build()
}

fun MethodSpec.Builder.init(list: List<String>) = apply {
    addStatement("List<String> result = new \$T<>()", ClassName.get("java.util", "ArrayList"))

    for (item in list) {
        addStatement("result.add(\"$item\")")
    }

    addStatement("return result")
}