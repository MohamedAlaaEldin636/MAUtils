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

package mohamedalaa.mautils.sample.gson

import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

object ObjectClass

data class TestDataClassA(
    var string: String,
    var int: Int,
    var testSealedClassA1: TestSealedClassA,
    var testSealedClassA2: TestSealedClassA
)

@MASealedAbstractOrInterface
sealed class TestSealedClassA {

    object ObjectA : TestSealedClassA()

    data class DataClassA(
        var string: String,
        var int: Int
    ) : TestSealedClassA()

}
