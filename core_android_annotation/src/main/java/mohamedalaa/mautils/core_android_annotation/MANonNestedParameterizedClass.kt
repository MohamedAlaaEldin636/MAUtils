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

package mohamedalaa.mautils.core_android_annotation

import kotlin.reflect.KClass

/**
 * 1. Can be used for classes without type params isa like -> String::class
 * 2. Can be used for classes with non-nested type params isa like -> List::class, String::class
 */
annotation class MANonNestedParameterizedClass(
    vararg val value: KClass<*>
)