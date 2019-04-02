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

package mohamedalaa.mautils.mautils.module_gson_processor.root

import mohamedalaa.mautils.reflection.isInterface
import kotlin.reflect.KClass

fun getAllEligibleClassesFromAnnotation(
    annotatedClassFullName: String,

    includeSelf: Boolean,
    checkSelfTypeParams: Boolean,

    checkDeclaredProperties: Boolean,
    checkNestedDeclaredProperties: Boolean,
    checkAllDeclaredPropertiesTypeParams: Boolean,

    excludeInterfaces: Boolean,
    excludeAbstractClasses: Boolean,
    excludeSealedClasses: Boolean,
    vararg excludeClasses: Class<*>
): List<Class<*>> {
    if (excludeInterfaces && excludeAbstractClasses && excludeSealedClasses) {
        return emptyList()
    }

    val list = mutableListOf<Class<*>>()

    // Include Self
    val annotatedClass = runCatching { Class.forName(annotatedClassFullName) }.getOrNull() ?: return emptyList()
    if (includeSelf) {
        list += annotatedClass
    }

    // Check Self Type Param
    if (checkSelfTypeParams) {
        list += annotatedClass.allTypeParamClassesAndNested(list, true)
    }

    // Check Declared Properties
    if (checkDeclaredProperties) {
        list += list.allDeclaredPropsWithAllTypeParamAndNested(
            list, true, checkAllDeclaredPropertiesTypeParams, checkNestedDeclaredProperties
        )
    }

    // Exclude duplications && any other exclusion conditions isa.
    return list.distinct().run {
        if (excludeInterfaces.not() && excludeAbstractClasses.not() && excludeSealedClasses.not() && excludeClasses.isEmpty()) {
            this
        }else {
            this.filter {
                val kClass = it.kotlin
                if (excludeInterfaces && kClass.isInterface) {
                    return@filter false
                }
                if (excludeAbstractClasses && kClass.isAbstract) {
                    return@filter false
                }
                if (excludeSealedClasses && kClass.isSealed) {
                    return@filter false
                }
                if (it in excludeClasses) {
                    return@filter false
                }

                true
            }
        }
    }.filter { it.kotlin.isAvailableForMASealedAbstractOrInterfaceAnnotation }
}

val KClass<*>.isAvailableForMASealedAbstractOrInterfaceAnnotation: Boolean
    get() = isSealed || isAbstract || isInterface
