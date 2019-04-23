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

package mohamedalaa.mautils.sample.module_gson_processor.root

import mohamedalaa.mautils.core_kotlin.addIfNotNull
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaType

val Type.asClassOrNull: Class<*>?
    get() = runCatching { Class.forName(typeName) }.getOrNull()

val Type.asClass: Class<*>
    get() = asClassOrNull ?: throw RuntimeException("Cannot get class from $this")

val String?.asClassOrNull: Class<*>?
    get() = runCatching { Class.forName(this ?: "error") }.getOrNull()

/** Adds all classes in type params even nested ones, Ex. Pair<Game<SomeThing>, Int> returns [Pair, Game, SomeThing, Int] isa. */
fun Class<*>.allTypeParamClassesAndNested(exclude: List<Class<*>> = emptyList(), distinct: Boolean = true): List<Class<*>> {
    val list = mutableListOf<Class<*>>()

    typeParameters.forEach {
        for (type in it.bounds) {
            list += type.allTypeParamClasses(true, distinct)
        }
    }

    list.removeAll(exclude)
    return if (distinct) list.distinct() else list
}

private fun Type.allTypeParamClasses(includeSelf: Boolean, distinct: Boolean = true): List<Class<*>> {
    val list = mutableListOf<Class<*>>()

    if (this is ParameterizedType) {
        list += this.allTypeParamClasses(true, distinct)
    }else if (includeSelf){
        list.addIfNotNull(this.asClassOrNull)
    }

    return if (distinct) list.distinct() else list
}

private fun ParameterizedType.allTypeParamClasses(includeSelf: Boolean, distinct: Boolean = true): List<Class<*>> {
    val list = mutableListOf<Class<*>>()

    if (includeSelf) {
        list.addIfNotNull(typeName.takeWhile { it != '<' }.asClassOrNull)
    }

    actualTypeArguments.forEach {
        if (it is ParameterizedType) {
            list += it.allTypeParamClasses(true, distinct)
        }else {
            list.addIfNotNull(it.asClassOrNull)
        }
    }

    return if (distinct) list.distinct() else list
}

fun Class<*>.allDeclaredPropsWithAllTypeParamAndNested(
    exclude: List<Class<*>> = emptyList(),
    distinct: Boolean = true,
    checkTypeParams: Boolean,
    checkNested: Boolean
): List<Class<*>> {
    val list = mutableListOf<Class<*>>()

    kotlin.declaredMemberProperties.forEach {
        list += when {
            checkTypeParams && checkNested -> {
                val tempList1 = it.returnType.javaType.allTypeParamClasses(true, distinct)
                val tempList2 = mutableListOf<Class<*>>()
                tempList1.forEach { innerIt ->
                    tempList2 += innerIt.allDeclaredPropsWithAllTypeParamAndNested(
                        exclude, distinct, checkTypeParams, checkNested
                    )
                }

                tempList1 + tempList2
            }
            checkTypeParams -> {
                it.returnType.javaType.allTypeParamClasses(true, distinct)
            }
            checkNested -> {
                val jClass = it.returnType.javaType.ignoreParameterizedTypeOrNull()?.asClass

                if (jClass == null) {
                    emptyList()
                }else {
                    listOf(jClass) + jClass.allDeclaredPropsWithAllTypeParamAndNested(
                        exclude, distinct, checkTypeParams, checkNested
                    )
                }
            }
            else -> {
                val jClass = it.returnType.javaType.ignoreParameterizedTypeOrNull()?.asClass

                if (jClass == null) emptyList() else listOf(jClass)
            }
        }
    }

    list.removeAll(exclude)
    return if (distinct) list.distinct() else list
}

private fun Type.ignoreParameterizedTypeOrNull()
    = if (this is ParameterizedType) typeName.takeWhile { it != '<' }.asClassOrNull else this.asClassOrNull

fun List<Class<*>>.allDeclaredPropsWithAllTypeParamAndNested(
    exclude: List<Class<*>> = emptyList(),
    distinct: Boolean = true,
    checkTypeParams: Boolean,
    checkNested: Boolean
): List<Class<*>> {
    val list = mutableListOf<Class<*>>()

    forEach {
        list += it.allDeclaredPropsWithAllTypeParamAndNested(exclude, distinct, checkTypeParams, checkNested)
    }

    list.removeAll(exclude)
    return if (distinct) list.distinct() else list
}
