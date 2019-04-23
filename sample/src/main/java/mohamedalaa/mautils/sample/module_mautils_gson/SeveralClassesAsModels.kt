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

package mohamedalaa.mautils.sample.module_mautils_gson

import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

@MASealedAbstractOrInterface
sealed class NoArgsSealedClass {

    data class NoDataClass(val bool: Boolean?) : NoArgsSealedClass()

    object NoSingleton : NoArgsSealedClass()

}

@MASealedAbstractOrInterface
sealed class WithArgsSealedClass {

    object WithSingleton : WithArgsSealedClass()

    data class WithDataClass(val bool: Boolean?) : WithArgsSealedClass()

}

data class UsingBoth(
    val noArgsSealedClass: NoArgsSealedClass,
    val withArgsSealedClass: WithArgsSealedClass,
    val float: Float
)

data class ParentUsingBoth(
    val usingBoth: UsingBoth,
    val withArgsSealedClass: WithArgsSealedClass,
    val int: Int,
    val implSomeInterface: ImplSomeInterface,
    val implAbstractClass: ImplAbstractClass
)

@MASealedAbstractOrInterface
interface SomeInterface {
    val someInterfaceInt: Int
}

class ImplSomeInterface(override val someInterfaceInt: Int, s: String) :
    SomeInterface {

    val string: String = s

}

@MASealedAbstractOrInterface
abstract class AbstractClass(val abstractClassInt: Int)

class ImplAbstractClass(int: Int, val b: Boolean) : AbstractClass(int)