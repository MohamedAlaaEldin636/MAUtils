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

@file:JvmName("ListUtils")

package mohamedalaa.mautils.core_kotlin.extensions

/**
 * Adds [element] only if not inside `receiver`
 *
 * @return true if added, false otherwise
 */
fun <T> MutableList<T>.addIfNotInside(element: T): Boolean = (element !in this).apply {
    if (this) {
        add(element)
    }
}

// todo rename above to addDistinct afdal isa.
fun <T : Any> MutableList<T>.addIfNotNull(element: T?): Boolean {
    if (element != null) {
        add(element)
    }

    return element != null
}

/**
 * Moves element from index [fromIndex] to index [toIndex] isa.
 */
fun <E> MutableList<E>.move(fromIndex: Int, toIndex: Int) {
    val element = elementAt(fromIndex)

    removeAt(fromIndex)
    add(toIndex, element)
}

/**
 * Swap element at [firstIndex] with element at [secondIndex] isa.
 */
fun <E> MutableList<E>.swap(firstIndex: Int, secondIndex: Int) {
    val firstElement = elementAt(firstIndex)
    val secondElement = elementAt(secondIndex)

    add(firstIndex, secondElement)
    removeAt(firstIndex.inc())

    add(secondIndex, firstElement)
    removeAt(secondIndex.inc())
}