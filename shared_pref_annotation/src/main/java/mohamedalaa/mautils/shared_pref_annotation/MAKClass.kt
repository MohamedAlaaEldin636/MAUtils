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

@file:JvmName("MAKClass")

package mohamedalaa.mautils.shared_pref_annotation

import kotlin.reflect.KClass

/**
 * Represents a [KClass] via [kClass] param with being `null` or non-Null via [nullable] param isa.
 *
 * @see MAParameterizedKClass
 */
annotation class MAKClass(
    val kClass: KClass<*>,
    val nullable: Boolean = false
)
