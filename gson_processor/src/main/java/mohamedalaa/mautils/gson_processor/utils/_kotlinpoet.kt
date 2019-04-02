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

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import mohamedalaa.mautils.reflection.isInterface
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.jvmErasure

fun buildProperty1(classFullName: String) {
    val propertySpec = PropertySpec.builder("propertyName", String::class)
        .addModifiers(KModifier.PUBLIC, KModifier.FINAL)
        //.initializer()
        .build()
}

fun PropertySpec.Builder.init(classFullName: String) = apply {
    val kClass = kotlin.runCatching { Class.forName(classFullName).kotlin }.getOrNull() ?: return@apply

    // in case include self, include unlimited nesting isa.
    //val listOfKClass: List<KClass<*>>
    var string: String = ""
    kClass.declaredMemberProperties.forEach {
        //it.returnType.asTypeName()
        it.returnType.jvmErasure.qualifiedName

        it.returnType
        it.returnType.asTypeName()

        //kClass.instan

        it.returnType.classifier?.createType(

        )

        kotlin.jvm.internal.ClassReference(Class.forName("")).createType(

        ).javaType

        // create
    }


    initializer("")
}

val KClass<*>.isAvailableForMASealedAbstractOrInterfaceAnnotation
    get() = isSealed || isAbstract || isInterface