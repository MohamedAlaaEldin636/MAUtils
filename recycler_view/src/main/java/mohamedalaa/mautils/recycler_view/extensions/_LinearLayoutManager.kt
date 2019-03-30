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

@file:JvmName("LinearLayoutManagerUtils")

package mohamedalaa.mautils.recycler_view.extensions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * @return true if [index] has border from left isa.
 *
 * Ex. imagine vertical [GridLayoutManager] with [GridLayoutManager.getSpanCount] == 2
 *
 * Then indices 0, 2, 4, etc... has left border isa.
 */
fun LinearLayoutManager.isBorderLeft(index: Int): Boolean {
    val spanCount = if (this is GridLayoutManager) spanCount else 1

    return if (orientation == LinearLayoutManager.VERTICAL) {
        index.rem(spanCount) == 0
    }else {
        index in (0 until spanCount)
    }
}

/** @return true if [index] has border from top, to read an Ex. See [isBorderLeft] isa. */
fun LinearLayoutManager.isBorderTop(index: Int): Boolean {
    val spanCount = if (this is GridLayoutManager) spanCount else 1

    return if (orientation == LinearLayoutManager.VERTICAL) {
        index in (0 until spanCount)
    }else {
        index.rem(spanCount) == 0
    }
}

/** @return true if [index] has border from right, to read an Ex. See [isBorderLeft] isa. */
fun LinearLayoutManager.isBorderRight(index: Int): Boolean {
    val spanCount = if (this is GridLayoutManager) spanCount else 1

    return if (orientation == LinearLayoutManager.VERTICAL) {
        index.inc().rem(spanCount) == 0
    }else {
        var breakIteration = false

        val seed = itemCount.dec()
        val lastColumnIndices = if (seed.rem(spanCount) == 0) listOf(seed) else generateSequence(seed) {
            if (breakIteration) {
                null
            }else {
                val nextIndex = it.dec()

                when {
                    nextIndex < 0 -> null
                    nextIndex.rem(spanCount) == 0 -> {
                        breakIteration = true

                        nextIndex
                    }
                    else -> nextIndex
                }
            }
        }.toList()

        index in lastColumnIndices
    }
}

/** @return true if [index] has border from bottom, to read an Ex. See [isBorderLeft] isa. */
fun LinearLayoutManager.isBorderBottom(index: Int): Boolean {
    val spanCount = if (this is GridLayoutManager) spanCount else 1

    return if (orientation == LinearLayoutManager.VERTICAL) {
        var breakIteration = false

        val seed = itemCount.dec()
        val lastColumnIndices = if (seed.rem(spanCount) == 0) listOf(seed) else generateSequence(seed) {
            if (breakIteration) {
                null
            }else {
                val nextIndex = it.dec()

                when {
                    nextIndex < 0 -> null
                    nextIndex.rem(spanCount) == 0 -> {
                        breakIteration = true

                        nextIndex
                    }
                    else -> nextIndex
                }
            }
        }.toList()

        index in lastColumnIndices
    }else {
        index.inc().rem(spanCount) == 0
    }
}