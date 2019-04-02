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

package mohamedalaa.mautils.mautils.module_gson_processor

class With1<I, K : Pair<I, Int>> where I : CharSequence, I : List<String> {

}

class With2<T : CharSequence> {

}

data class BaseForReflection<I>(
    val int: Int,
    val topSealedClass: TopSealedClass,
    val float: Float,
    val subBase: SubBase,
    val complexPair: Pair<
        List<
            Pair<
                SubBase,
                Pair<TopSealedClass, AnotherSubBase>
                >
            >,
        Pair<
            Pair<Int, Float>, Double
            >
        >
) where I : Interface, I : AnotherAbstractClass

sealed class TopSealedClass

sealed class SealedClass

abstract class AbstractClass

interface Interface

data class SubBase(
    val double: Double,
    val sealedClass: SealedClass,
    val abstractClass: AbstractClass,
    val interface1: Interface
)

abstract class AnotherAbstractClass

data class AnotherSubBase(
    val long: Long,
    val anotherAbstractClass: AnotherAbstractClass
)

