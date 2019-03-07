package mohamedalaa.mautils.recycler_view.extensions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

// todo what if layout was reversed

/**
 * @return true if [index] has border from left isa.
 *
 * Ex. imagine vertical [GridLayoutManager] with [GridLayoutManager.getSpanCount] == 2
 *
 * Then indices 0, 2, 4, etc... has left border isa.
 */
fun GridLayoutManager.isBorderLeft(index: Int): Boolean {
    return if (orientation == LinearLayoutManager.VERTICAL) {
        index.rem(spanCount) == 0
    }else {
        index in (0 until spanCount)
    }
}

/** @return true if [index] has border from top, to read an Ex. See [isBorderLeft] isa. */
fun GridLayoutManager.isBorderTop(index: Int): Boolean {
    return if (orientation == LinearLayoutManager.VERTICAL) {
        index in (0 until spanCount)
    }else {
        index.rem(spanCount) == 0
    }
}

/** @return true if [index] has border from right, to read an Ex. See [isBorderLeft] isa. */
fun GridLayoutManager.isBorderRight(index: Int): Boolean {
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
fun GridLayoutManager.isBorderBottom(index: Int): Boolean {
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
