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
}
