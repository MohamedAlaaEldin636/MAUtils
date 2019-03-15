package mohamedalaa.mautils.recycler_view.extensions.internal

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.custom_classes.RCItemDecoration
import mohamedalaa.mautils.recycler_view.extensions.isBorderBottom
import mohamedalaa.mautils.recycler_view.extensions.isBorderLeft
import mohamedalaa.mautils.recycler_view.extensions.isBorderRight
import mohamedalaa.mautils.recycler_view.extensions.isBorderTop

/**
 * "0" -> X
 * "1..∞" -> "a..∞"
 *
 * todo max 8 for now isa.
 */
private fun RCItemDecoration.zzz(layoutManager: GridLayoutManager, position: Int): Rect {
    val rect = Rect()

    // Left & right
    val (left, right) = when (layoutManager.spanCount) {
        0,1 -> return rect
        2 -> when (position) {
            0,1 -> {
                val x = getVar(layoutManager.spanCount, "x")

                if (position == 0) 0 to x else x to 0
            }
            else -> return rect
        }
        3 -> when (position) {
            0,2 -> {
                val x = getVar(layoutManager.spanCount, "x")

                if (position == 0) 0 to x else x to 0
            }
            1 -> {
                val a = getVar(layoutManager.spanCount, "a")

                a to a
            }
            else -> return rect
        }
        4 -> when (position) {
            0,3 -> {
                val x = getVar(layoutManager.spanCount, "x")

                if (position == 0) 0 to x else x to 0
            }
            1,2 -> {
                val a = getVar(layoutManager.spanCount, "a")
                val b = getVar(layoutManager.spanCount, "b")

                if (position == 1) a to b else b to a
            }
            else -> return rect
        }
        5 -> when (position) {
            0,4 -> {
                val x = getVar(layoutManager.spanCount, "x")

                if (position == 0) 0 to x else x to 0
            }
            1,3 -> {
                val a = getVar(layoutManager.spanCount, "a")
                val b = getVar(layoutManager.spanCount, "b")

                if (position == 1) a to b else b to a
            }
            2 -> {
                val c = getVar(layoutManager.spanCount, "c")

                c to c
            }
            else -> return rect
        }
        6 -> when (position) {
            0,5 -> {
                val x = getVar(layoutManager.spanCount, "x")

                if (position == 0) 0 to x else x to 0
            }
            1,4 -> {
                val a = getVar(layoutManager.spanCount, "a")
                val b = getVar(layoutManager.spanCount, "b")

                if (position == 1) a to b else b to a
            }
            2,3 -> {
                val c = getVar(layoutManager.spanCount, "c")
                val d = getVar(layoutManager.spanCount, "d")

                if (position == 2) c to d else d to c
            }
            else -> return rect
        }
        7 -> when (position) {
            0,6 -> {
                val x = getVar(layoutManager.spanCount, "x")

                if (position == 0) 0 to x else x to 0
            }
            1,5 -> {
                val a = getVar(layoutManager.spanCount, "a")
                val b = getVar(layoutManager.spanCount, "b")

                if (position == 1) a to b else b to a
            }
            2,4 -> {
                val c = getVar(layoutManager.spanCount, "c")
                val d = getVar(layoutManager.spanCount, "d")

                if (position == 2) c to d else d to c
            }
            3 -> {
                val e = getVar(layoutManager.spanCount, "e")

                e to e
            }
            else -> return rect
        }
        8 -> when (position) {
            0,7 -> {
                val x = getVar(layoutManager.spanCount, "x")

                if (position == 0) 0 to x else x to 0
            }
            1,6 -> {
                val a = getVar(layoutManager.spanCount, "a")
                val b = getVar(layoutManager.spanCount, "b")

                if (position == 1) a to b else b to a
            }
            2,5 -> {
                val c = getVar(layoutManager.spanCount, "c")
                val d = getVar(layoutManager.spanCount, "d")

                if (position == 2) c to d else d to c
            }
            3,4 -> {
                val e = getVar(layoutManager.spanCount, "e")
                val f = getVar(layoutManager.spanCount, "f")

                if (position == 3) e to f else f to e
            }
            else -> return rect
        }
        else -> return rect
    }

    return rect
}

private fun RCItemDecoration.getVar(spanCount: Int, key: String): Int {
    return when (spanCount) {
        2 -> fullDimen.divRound(2)
        3 -> {
            val a = fullDimen.divRound(3)
            val x = a.times(2)

            if (key == "x") x else a
        }
        4 -> {
            val a = fullDimen.divRound(4)
            val b = a.times(2)
            val x = a.times(3)

            when(key) {
                "x" -> x
                "a" -> a
                else -> b
            }
        }
        5 -> {
            val a = fullDimen.divRound(5)
            val b = a.times(3)
            val c = a.times(2)
            val x = a.times(4)

            when(key) {
                "x" -> x
                "a" -> a
                "b" -> b
                else -> c
            }
        }
        6 -> {
            val a = fullDimen.divRound(6)
            val b = a.times(4)
            val c = a.times(2)
            val d = a.times(3)
            val x = a.times(5)

            when(key) {
                "x" -> x
                "a" -> a
                "b" -> b
                "c" -> c
                else -> d
            }
        }
        7 -> {
            val a = fullDimen.divRound(7)
            val b = a.times(5)
            val c = a.times(2)
            val d = a.times(4)
            val e = a.times(3)
            val x = a.times(6)

            when(key) {
                "x" -> x
                "a" -> a
                "b" -> b
                "c" -> c
                "d" -> d
                else -> e
            }
        }
        8 -> {
            val a = fullDimen.divRound(8)
            val b = a.times(6)
            val c = a.times(2)
            val d = a.times(5)
            val e = a.times(3)
            val f = a.times(4)
            val x = a.times(7)

            when(key) {
                "x" -> x
                "a" -> a
                "b" -> b
                "c" -> c
                "d" -> d
                "e" -> e
                else -> f
            }
        }
        else -> 0
    }
}

private fun Int.divRound(other: Int): Int
    = Math.round(this.toFloat().div(other.toFloat()))

internal fun RCItemDecoration.subItemOffsetIgnoreBorderMergeOffsetsVertical2(
    layoutManager: GridLayoutManager,
    position: Int
): Rect {
    val rect = Rect()

    val a = fullDimen.div(5)
    val (left, right) = when (val index = position.rem(5)) {
        0 -> 0 to a.times(4)
        1 -> a to a.times(3)
        2 -> a.times(2) to a.times(2)
        3 -> a.times(3) to a
        4 -> a.times(4) to 0
        else -> 0 to 0
    }

    val top = fullDimen
    val bottom = 0






    /** Number of offsets counted horizontally isa */
    /*val horzNumberOfGaps = layoutManager.spanCount.dec().apply { if (this == 0) return rect }
    val horzTotalGapsDimen = fullDimen * horzNumberOfGaps

    // -- Left & Right -- //
    val xCount = 2 // each x == 2y
    val yCount = layoutManager.spanCount.minus(xCount).times(2)

    val totalCountOfY = yCount.plus(xCount)

    val yDistance = Math.round(horzTotalGapsDimen.toFloat().div(totalCountOfY.toFloat()))

    val isBorderLeft = layoutManager.isBorderLeft(position)
    val isBorderRight = layoutManager.isBorderRight(position)
    val (left, right) = when {
        //isBorderLeft && isBorderRight -> Cannot happen unless spanCount == 1 which is handled above isa.
        isBorderLeft -> 0 to yDistance
        isBorderRight-> yDistance to 0
        else -> yDistance to yDistance
    }

    // -- Top & Bottom -- //
    val isBorderTop = layoutManager.isBorderTop(position)
    val isBorderBottom = layoutManager.isBorderBottom(position)
    val (top, bottom) = when {
        isBorderTop && isBorderBottom -> 0 to 0
        isBorderTop -> 0 to yDistance
        isBorderBottom -> yDistance to 0
        else -> yDistance to yDistance
    }*/

    rect.set(left, top, right, bottom)
    return rect
}

internal fun RCItemDecoration.subItemOffsetIgnoreBorderMergeOffsetsVertical(
    layoutManager: GridLayoutManager,
    position: Int
): Rect {
    val rect = Rect()

    /** Number of offsets counted horizontally isa */
    val horzNumberOfGaps = layoutManager.spanCount.dec().apply { if (this == 0) return rect }
    val horzTotalGapsDimen = fullDimen * horzNumberOfGaps

    // -- Left & Right -- //
    val xCount = 2 // each x == 2y
    val yCount = layoutManager.spanCount.minus(xCount).times(2)

    val totalCountOfY = yCount.plus(xCount)

    val yDistance = Math.round(horzTotalGapsDimen.toFloat().div(totalCountOfY.toFloat()))

    val isBorderLeft = layoutManager.isBorderLeft(position)
    val isBorderRight = layoutManager.isBorderRight(position)
    val (left, right) = when {
        //isBorderLeft && isBorderRight -> Cannot happen unless spanCount == 1 which is handled above isa.
        isBorderLeft -> 0 to yDistance
        isBorderRight-> yDistance to 0
        else -> yDistance to yDistance
    }

    // -- Top & Bottom -- //
    val isBorderTop = layoutManager.isBorderTop(position)
    val isBorderBottom = layoutManager.isBorderBottom(position)
    val (top, bottom) = when {
        isBorderTop && isBorderBottom -> 0 to 0
        isBorderTop -> 0 to yDistance
        isBorderBottom -> yDistance to 0
        else -> yDistance to yDistance
    }

    rect.set(left, top, right, bottom)
    return rect
}

internal fun RCItemDecoration.subDrawGridIgnoreBorderAndMergeOffsetsVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) {
    val bounds = Rect()
    for (position in firstVisible..lastVisible) {
        val isBorderTop = layoutManager.isBorderTop(position)
        val isBorderRight = layoutManager.isBorderRight(position)
        if (isBorderTop && isBorderRight) {
            continue
        }

        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)
        Log.e("Check2", "position -> $position, width -> ${child.width}")

        val list = mutableListOf<Path>()

        when {
            isBorderTop -> addRightPath(list, child, bounds, layoutManager)
            isBorderRight -> addTopPath(list, child, bounds, layoutManager)
            else -> {
                addRightPath(list, child, bounds, layoutManager)
                addTopPath(list, child, bounds, layoutManager)
                addTopRightCornerPath(list, child, bounds)
            }
        }

        list.forEach {
            canvas.drawPath(it, paint)
        }
    }
}

internal fun RCItemDecoration.subDrawGridIgnoreBorderAndNoMergeOffsets(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) {
    val bounds = Rect()
    for (position in firstVisible..lastVisible) {
        val isBorderTop = layoutManager.isBorderTop(position)
        val isBorderBottom = layoutManager.isBorderBottom(position)
        val isBorderRight = layoutManager.isBorderRight(position)
        val isBorderLeft = layoutManager.isBorderLeft(position)

        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val list = mutableListOf<Path>()

        if (!isBorderTop) {
            addTopPath(list, child, bounds, layoutManager)
        }
        if (!isBorderBottom) {
            addBottomPath(list, child, bounds, layoutManager)
        }
        if (!isBorderRight) {
            addRightPath(list, child, bounds, layoutManager)
        }
        if (!isBorderLeft) {
            addLeftPath(list, child, bounds, layoutManager)
        }
        if (!isBorderTop && !isBorderRight) {
            addTopRightCornerPath(list, child, bounds)
        }
        if (!isBorderTop && !isBorderLeft) {
            addTopLeftCornerPath(list, child, bounds)
        }
        if (!isBorderBottom && !isBorderRight) {
            addBottomRightCornerPath(list, child, bounds)
        }
        if (!isBorderBottom && !isBorderLeft) {
            addBottomLeftCornerPath(list, child, bounds)
        }

        list.forEach {
            canvas.drawPath(it, paint)
        }
    }
}

internal fun RCItemDecoration.subDrawGridNoIgnoreBorderAndMergeOffsetsVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) {
    val bounds = Rect()
    for (position in firstVisible..lastVisible) {
        val isBorderBottom = layoutManager.isBorderBottom(position)
        val isBorderLeft = layoutManager.isBorderLeft(position)

        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val list = mutableListOf<Path>()

        addTopPath(list, child, bounds, layoutManager)
        addRightPath(list, child, bounds, layoutManager)
        addTopRightCornerPath(list, child, bounds)

        when {
            isBorderLeft && isBorderBottom -> {
                addLeftPath(list, child, bounds, layoutManager)
                addTopLeftCornerPath(list, child, bounds)

                addBottomPath(list, child, bounds, layoutManager)
                addBottomLeftCornerPath(list, child, bounds)
                addBottomRightCornerPath(list, child, bounds)
            }
            isBorderLeft -> {
                addLeftPath(list, child, bounds, layoutManager)
                addTopLeftCornerPath(list, child, bounds)
            }
            isBorderBottom -> {
                addBottomPath(list, child, bounds, layoutManager)
                addBottomRightCornerPath(list, child, bounds)
            }
        }

        list.forEach {
            canvas.drawPath(it, paint)
        }
    }
}

internal fun RCItemDecoration.subDrawGridNoIgnoreBorderAndNoMergeOffsets(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) {
    val bounds = Rect()
    for (position in firstVisible..lastVisible) {
        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val list = mutableListOf<Path>()

        addTopPath(list, child, bounds, layoutManager)
        addBottomPath(list, child, bounds, layoutManager)
        addRightPath(list, child, bounds, layoutManager)
        addLeftPath(list, child, bounds, layoutManager)

        addTopRightCornerPath(list, child, bounds)
        addTopLeftCornerPath(list, child, bounds)
        addBottomRightCornerPath(list, child, bounds)
        addBottomLeftCornerPath(list, child, bounds)

        list.forEach {
            canvas.drawPath(it, paint)
        }
    }
}

internal fun RCItemDecoration.subDrawGridIgnoreBorderAndMergeOffsetsHorizontal(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) {
    val bounds = Rect()
    for (position in firstVisible..lastVisible) {
        val isBorderTop = layoutManager.isBorderTop(position)
        val isBorderLeft = layoutManager.isBorderLeft(position)
        if (isBorderTop && isBorderLeft) {
            continue
        }

        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val list = mutableListOf<Path>()

        when {
            isBorderTop -> addLeftPath(list, child, bounds, layoutManager)
            isBorderLeft -> addTopPath(list, child, bounds, layoutManager)
            else -> {
                addTopPath(list, child, bounds, layoutManager)
                addLeftPath(list, child, bounds, layoutManager)
                addTopLeftCornerPath(list, child, bounds)
            }
        }

        list.forEach {
            canvas.drawPath(it, paint)
        }
    }
}

internal fun RCItemDecoration.subDrawGridNoIgnoreBorderAndMergeOffsetsHorizontal(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) {
    val bounds = Rect()
    for (position in firstVisible..lastVisible) {
        val isBorderBottom = layoutManager.isBorderBottom(position)
        val isBorderRight = layoutManager.isBorderRight(position)

        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val list = mutableListOf<Path>()

        addTopPath(list, child, bounds, layoutManager)
        addLeftPath(list, child, bounds, layoutManager)
        addTopLeftCornerPath(list, child, bounds)

        when {
            isBorderRight && isBorderBottom -> {
                addRightPath(list, child, bounds, layoutManager)
                addTopRightCornerPath(list, child, bounds)

                addBottomPath(list, child, bounds, layoutManager)
                addBottomRightCornerPath(list, child, bounds)
                addBottomLeftCornerPath(list, child, bounds)
            }
            isBorderRight -> {
                addRightPath(list, child, bounds, layoutManager)
                addTopRightCornerPath(list, child, bounds)
            }
            isBorderBottom -> {
                addBottomPath(list, child, bounds, layoutManager)
                addBottomLeftCornerPath(list, child, bounds)
            }
        }

        list.forEach {
            canvas.drawPath(it, paint)
        }
    }
}
