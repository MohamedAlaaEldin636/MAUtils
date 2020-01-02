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

package mohamedalaa.mautils.gson

import java.lang.reflect.Field

fun Class<*>.declaredFieldsForSuperclassesOnly(initialList: List<Field> = emptyList()): List<Field> {
    // ignore interfaces isa. ( has no baking fields isa. )
    val superclass = superclass
    val list = (superclass ?: return initialList).declaredFields.filterNotNull()
    return superclass.declaredFieldsForSuperclassesOnly(initialList + list)
}

fun Class<*>.getDeclaredFieldsForSuperclassesOnly(name: String): Field? {
    val allFields = declaredFieldsForSuperclassesOnly()
    return allFields.firstOrNull {
        it.name == name
    }
}
