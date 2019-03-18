package mohamedalaa.mautils.recycler_view.new_test_1.extensions

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.new_test_1.MAItemDecoration

fun MAItemDecoration.subOnDrawIgnoreBorderMergeOffsets(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) = subOnDraw(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen, true)

fun MAItemDecoration.subOnDrawIgnoreBorderNoMergeOffsets(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) = subOnDraw(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen.times(2), true)

fun MAItemDecoration.subOnDrawNoIgnoreBorderMergeOffsets(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) = subOnDraw(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen, false)

fun MAItemDecoration.subOnDrawNoIgnoreBorderNoMergeOffsets(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) = subOnDraw(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen.times(2), false)

// ---- Private fun

private fun MAItemDecoration.subOnDraw(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int,
    fullDimen: Int,
    ignoreBorder: Boolean
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

        if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
            val additionalDimen = if (ignoreBorder) 0 else this.fullDimen

            val top = bounds.bottom - child.height - fullDimen - additionalDimen
            val bottom = top.plus(fullDimen)
            val rect1 = Rect(parent.left, top, parent.right, bottom)

            val left = bounds.right.minus(layoutManager.getRightDecorationWidth(child))
            val right = left.plus(fullDimen)
            val rect2 = Rect(
                left,
                bottom,
                right,
                bottom.plus(child.height).plus(additionalDimen)
            )

            canvas.drawRect(rect1, paint)
            canvas.drawRect(rect2, paint)
        }else {
            val top = bounds.bottom - layoutManager.getBottomDecorationHeight(child)
            val bottom = top.plus(fullDimen)
            val rect1 = Rect(parent.left, top, parent.right, bottom)

            val left = bounds.left + layoutManager.getLeftDecorationWidth(child) - fullDimen
            val right = left.plus(fullDimen)
            val rect2 = Rect(
                left,
                parent.top,
                right,
                parent.bottom
            )

            canvas.drawRect(rect1, paint)
            canvas.drawRect(rect2, paint)
        }
    }
}
