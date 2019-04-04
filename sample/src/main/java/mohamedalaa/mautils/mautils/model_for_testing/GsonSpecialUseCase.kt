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

package mohamedalaa.mautils.mautils.model_for_testing

import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

data class SpecialDataClass(
    var int: Int,
    var specialSealedClass: SpecialSealedClass
)

data class SpecialJust(
    val int: Int,
    val specialIndirectSealedClass: SpecialIndirectSealedClass
)

@MASealedAbstractOrInterface
sealed class SpecialSealedClass {
    object Object1 : SpecialSealedClass()

    data class DataClass1(
        var double: Double,
        var specialIndirectSealedClass: SpecialIndirectSealedClass
    ) : SpecialSealedClass()
}

@MASealedAbstractOrInterface
sealed class SpecialIndirectSealedClass {
    object Object1 : SpecialIndirectSealedClass()

    data class DataClass1(val int: Int) : SpecialIndirectSealedClass()
}

@MASealedAbstractOrInterface
sealed class NestedSealedClassParent {

    //@MASealedAbstractOrInterface // optional
    sealed class SealedClass1 : NestedSealedClassParent() {
        object Object1 : NestedSealedClassParent.SealedClass1()

        data class Data1(val int: Int) : NestedSealedClassParent.SealedClass1()
    }

    object Object1 : NestedSealedClassParent()

}

data class NestedSealedClassDataClass(
    val nestedSealedClassParent: NestedSealedClassParent,
    val double: Double
)

