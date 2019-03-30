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

package mohamedalaa.mautils.mautils.fake_data

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/15/2019.
 *
 */
data class CustomObject(val name: String = "Mohamed",
                        val age: Int = 22,
                        val address :String = "Not your business",
                        val friendsNames: List<String> = listOf("Gendy", "Omar", "Afrah"))

data class CustomWithTypeParam<T, R>(var element1: T? = null,
                                     var element2: R? = null,
                                     val name: String = "name",
                                     val anotherName: String? = null)

data class AnotherPair<out T, out R>(val element1: T? = null, val element2: R? = null)

data class AnotherPairNoOut<T, R>(val element1: T? = null, val element2: R? = null)