package mohamedalaa.mautils.recycler_view.new_test_1.extensions

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.extensions.isBorderBottom
import mohamedalaa.mautils.recycler_view.extensions.isBorderLeft
import mohamedalaa.mautils.recycler_view.extensions.isBorderRight
import mohamedalaa.mautils.recycler_view.extensions.isBorderTop
import mohamedalaa.mautils.recycler_view.new_test_1.MAItemDecoration

fun MAItemDecoration.subOnDrawIgnoreBorderMergeOffsetsVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) = subOnDrawIgnoreBorderVertical(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen)

fun MAItemDecoration.subOnDrawIgnoreBorderNoMergeOffsetsVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) = subOnDrawIgnoreBorderVertical(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen.times(2))

fun MAItemDecoration.subOnDrawNoIgnoreBorderMergeOffsetsVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) = subOnDrawNoIgnoreBorderVertical(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen)

fun MAItemDecoration.subOnDrawNoIgnoreBorderNoMergeOffsetsVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) = subOnDrawNoIgnoreBorderVertical(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen.times(2))

// ---- Private fun

private fun MAItemDecoration.subOnDrawIgnoreBorderVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int,
    fullDimen: Int
) {
    val list = mutableListOf<Path>()

    for (position in firstVisible..lastVisible) {
        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
        val bounds = Rect()
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val isBorderTop = layoutManager.isBorderTop(position)
        val isBorderBottom = layoutManager.isBorderBottom(position)
        val isBorderLeft = layoutManager.isBorderLeft(position)
        val isBorderRight = layoutManager.isBorderRight(position)

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
            addTopRightCornerPath(list, child, bounds, layoutManager)
        }
        if (!isBorderTop && !isBorderLeft) {
            addTopLeftCornerPath(list, child, bounds, layoutManager)
        }
        if (!isBorderBottom && !isBorderRight) {
            addBottomRightCornerPath(list, child, bounds, layoutManager)
        }
        if (!isBorderBottom && !isBorderLeft) {
            addBottomLeftCornerPath(list, child, bounds, layoutManager)
        }
    }

    list.forEach {
        canvas.drawPath(it, paint)
    }

    // Last item draw check isa.
    if (layoutManager.itemCount.rem(layoutManager.spanCount) != 0
        && lastVisible == layoutManager.itemCount.dec()) {

        val child = parent.getChildAt(lastVisible.minus(firstVisible))

        val bounds = Rect()
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val top = bounds.bottom - child.height - fullDimen
        val left = parent.left

        val rect1 = Rect(left, top, parent.right, top.plus(fullDimen))
        val rect2 = Rect(
            bounds.right.minus(layoutManager.getRightDecorationWidth(child)),
            top.plus(fullDimen),
            bounds.right.minus(layoutManager.getRightDecorationWidth(child)).plus(fullDimen),
            top.plus(fullDimen).plus(child.height)
        )

        canvas.drawRect(rect1, paint)
        canvas.drawRect(rect2, paint)
    }
}

private fun MAItemDecoration.subOnDrawNoIgnoreBorderVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int,
    fullDimen: Int
) {
    val list = mutableListOf<Path>()

    for (position in firstVisible..lastVisible) {
        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
        val bounds = Rect()
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        addTopPath(list, child, bounds, layoutManager)
        addBottomPath(list, child, bounds, layoutManager)
        addRightPath(list, child, bounds, layoutManager)
        addLeftPath(list, child, bounds, layoutManager)
        addTopRightCornerPath(list, child, bounds, layoutManager)
        addTopLeftCornerPath(list, child, bounds, layoutManager)
        addBottomRightCornerPath(list, child, bounds, layoutManager)
        addBottomLeftCornerPath(list, child, bounds, layoutManager)
    }

    list.forEach {
        canvas.drawPath(it, paint)
    }

    // Last item draw check isa.
    if (layoutManager.itemCount.rem(layoutManager.spanCount) != 0
        && lastVisible == layoutManager.itemCount.dec()) {

        val child = parent.getChildAt(lastVisible.minus(firstVisible))

        val bounds = Rect()
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val top = bounds.bottom - child.height - fullDimen - this.fullDimen // todo additional full dimen isa
        val bottom = top.plus(fullDimen)
        val rect1 = Rect(parent.left, top, parent.right, bottom)

        val left = bounds.right.minus(layoutManager.getRightDecorationWidth(child))
        val right = left.plus(fullDimen)
        val rect2 = Rect(
            left,
            bottom,
            right,
            bottom.plus(child.height).plus(this.fullDimen) // todo plus additional full dimen isa.
        )

        canvas.drawRect(rect1, paint)
        canvas.drawRect(rect2, paint)
    }
}
