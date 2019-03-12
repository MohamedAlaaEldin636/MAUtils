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
