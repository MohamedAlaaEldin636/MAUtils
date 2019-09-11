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

package mohamedalaa.mautils.core_kotlin.custom_classes

data class MutablePair<A, B>(
    var first: A,
    var second: B
)

fun <A, B> Pair<A, B>.toMutablePair() = MutablePair(first, second)

fun <A, B> MutablePair<A, B>.toPair() = Pair(first, second)

infix fun <A, B> A.mutablePair(other: B) = MutablePair(this, other)
