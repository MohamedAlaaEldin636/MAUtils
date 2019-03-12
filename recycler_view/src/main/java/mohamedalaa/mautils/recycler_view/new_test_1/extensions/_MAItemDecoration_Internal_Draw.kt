package mohamedalaa.mautils.recycler_view.new_test_1.extensions

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.custom_classes.RCItemDecoration
import mohamedalaa.mautils.recycler_view.extensions.internal.addRightPath
import mohamedalaa.mautils.recycler_view.extensions.internal.addTopPath
import mohamedalaa.mautils.recycler_view.extensions.internal.addTopRightCornerPath
import mohamedalaa.mautils.recycler_view.extensions.isBorderRight
import mohamedalaa.mautils.recycler_view.extensions.isBorderTop
import mohamedalaa.mautils.recycler_view.new_test_1.MAItemDecoration

// todo wait child.width is fine but gaps are NOT
internal fun MAItemDecoration.subDrawGridIgnoreBorderAndMergeOffsetsVertical(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: GridLayoutManager,
    firstVisible: Int,
    lastVisible: Int
) {
    /** Number of offsets counted horizontally isa */
    val horzNumberOfGaps = layoutManager.spanCount.dec().apply { if (this == 0) return}
    val horzTotalGapsDimen = fullDimen * horzNumberOfGaps

    // -- Left & Right -- //
    val xCount = 2 // each x == 2y
    val yCount = layoutManager.spanCount.minus(xCount).times(2)

    val totalCountOfY = yCount.plus(xCount.times(2))

    val yDistance = Math.round(horzTotalGapsDimen.toFloat().div(totalCountOfY.toFloat()))
    val xDistance = yDistance.times(2)





    /*val bounds = Rect()
    for (position in firstVisible..lastVisible) {
        val isBorderTop = layoutManager.isBorderTop(position)
        val isBorderRight = layoutManager.isBorderRight(position)

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
    }*/
}