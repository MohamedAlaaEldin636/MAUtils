package mohamedalaa.mautils.recycler_view.extensions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// todo what if layout was reversed

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

/** @return row and col as pair each starts from 1 to positive infinity isa. */
fun GridLayoutManager.rowAndColumn(position: Int): Pair<Int, Int> {
    val row = position.rem(spanCount).inc()
    val col = position.div(spanCount).inc()

    return if (orientation == LinearLayoutManager.VERTICAL) col to row else row to col
}

/** @return position of item in [RecyclerView.Adapter] isa. */
fun GridLayoutManager.positionFromRowAndCol(row: Int, col: Int): Int {
    val isVertical = orientation == LinearLayoutManager.VERTICAL

    var fromRow = row.dec()
    var fromCol = col.dec().times(spanCount)
    if (isVertical) {
        val temp = fromRow
        fromRow = fromCol
        fromCol = temp
    }

    return fromRow + fromCol
}
