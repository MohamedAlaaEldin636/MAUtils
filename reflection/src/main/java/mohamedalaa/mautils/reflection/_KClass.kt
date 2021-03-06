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

@file:JvmName("KClassUtils")

package mohamedalaa.mautils.reflection

import kotlin.reflect.KClass

val KClass<*>.isObject: Boolean
    get() = this.objectInstance != null

val KClass<*>.isInterface: Boolean
    get() = java.isInterface

val KClass<*>.isPrimitive: Boolean
    get() = javaPrimitiveType != null