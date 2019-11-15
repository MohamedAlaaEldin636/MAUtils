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

import mohamedalaa.mautils.core_kotlin.custom_classes.kclass_identifier.MAKClass
import org.junit.Test
import kotlin.test.assertEquals

class AnnotationCreation {

    /**
     * Creates instance of annotation at runtime and gets values of it isa.
     */
    @Test
    fun create_then_getValuesFromAnnotation() {
        val makClass = MAKClass.build(Boolean::class, true)

        getKClass(makClass).apply {
            assertEquals(first, Boolean::class)
            assertEquals(second, true)
        }
    }

    private fun getKClass(makClass: MAKClass) = makClass.kClass to makClass.nullable

}