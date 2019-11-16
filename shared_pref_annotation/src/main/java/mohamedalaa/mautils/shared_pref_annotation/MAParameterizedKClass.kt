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

package mohamedalaa.mautils.shared_pref_annotation

import kotlin.reflect.KClass

/**
 * ### Features
 *
 * - Used to describe the class with types params and with nullability in mind isa.
 * - In case non-nullable class/typeParams use [nonNullKClasses] isa.
 *      ```
 *      // Kotlin -> [Set]<[Int]>
 *      MAParameterizedKClass.build(
 *          arrayOf(
 *              Set::class, Int::class
 *          )
 *      )
 *      // Java -> [Set]<[Float]>
 *      MAParameterizedKClass.Builder.build(
 *          new Class<?>[]{Set.class, Float.class}
 *      );
 *      ```
 * - In case need nullability use [maKClass] isa.
 *      ```
 *      // Kotlin -> [Set]<[Int]?>
 *      MAParameterizedKClass.build(
 *          maKClass = *arrayOf(
 *              MAKClass.build(Set::class),
 *              MAKClass.build(Int::class, true)
 *          )
 *      )
 *      ```
 */
annotation class MAParameterizedKClass(
    val nonNullKClasses: Array<KClass<*>> = [],
    vararg val maKClass: MAKClass = []
)
