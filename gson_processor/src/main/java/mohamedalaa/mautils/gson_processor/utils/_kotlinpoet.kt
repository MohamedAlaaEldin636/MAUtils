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

import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.reflection.isInterface
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.jvmErasure

fun buildObject(objectName: String, propertySpec: PropertySpec): TypeSpec {
    return TypeSpec.objectBuilder(objectName)
        .addModifiers(KModifier.PUBLIC, KModifier.FINAL)

        .addProperty(propertySpec)

        .build()
}

/** @param init all classes available from all annotated classes isa. */
fun buildProperty(init: List<Class<*>>, propertyName: String = "propertyName"): PropertySpec {
    return PropertySpec.builder(propertyName, KotlinpoetUtils.parameterizedTypeName(List::class, Class::class, Any::class))
        .addModifiers(KModifier.PUBLIC, KModifier.FINAL)

        .init(init)

        .build()
}

fun PropertySpec.Builder.init(list: List<Class<*>>) = apply {
    val string = buildString {
        append("kotlin.collections.listOfNotNull(")

        for (index in 0 until list.size) {
            append("kotlin.runCatching { Class.forName(\"${list[index].name}\") }.getOrNull()")

            if (index != list.lastIndex) {
                append(",")
            }
        }

        append(")")
    }

    initializer(string)
}

fun PropertySpec.Builder.initListString(list: List<String>) = apply {
    val string = buildString {
        append("kotlin.collections.listOfNotNull(")

        for (index in 0 until list.size) {
            append("kotlin.runCatching { Class.forName(\"${list[index]}\") }.getOrNull()")

            if (index != list.lastIndex) {
                append(",")
            }
        }

        append(")")
    }

    initializer(string)
}
