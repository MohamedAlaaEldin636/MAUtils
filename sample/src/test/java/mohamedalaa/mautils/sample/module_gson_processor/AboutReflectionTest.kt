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

package mohamedalaa.mautils.sample.module_gson_processor

import mohamedalaa.mautils.sample.module_gson_processor.root.getAllEligibleClassesFromAnnotation
import org.junit.Test

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/2/2019.
 *
 */
class AboutReflectionTest {

    /** prints listOfClassesToBeAnnotated isa. */
    @Test
    fun annotation_nesting_and_typeParams() {
        val listOfClassesToBeAnnotated =
            getAllEligibleClassesFromAnnotation(
                BaseForReflection::class.qualifiedName.toString(),

                includeSelf = true,
                checkSelfTypeParams = false,

                checkDeclaredProperties = false,
                checkNestedDeclaredProperties = true,
                checkAllDeclaredPropertiesTypeParams = true,

                excludeInterfaces = false, // interfaces
                excludeAbstractClasses = false,
                excludeSealedClasses = false,

                excludeClasses = *arrayOf()
            )

        listOfClassesToBeAnnotated.forEach(::println)
    }

}