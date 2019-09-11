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

package mohamedalaa.mautils.core_kotlin.extensions

infix fun <A, B, C> Pair<A, B>.triple(third: C) = Triple(first, second, third)

fun <A, B> Triple<A, A, A>.map(transformation: (A) -> B): Triple<B, B, B>
    = Triple(transformation(first), transformation(second), transformation(third))
