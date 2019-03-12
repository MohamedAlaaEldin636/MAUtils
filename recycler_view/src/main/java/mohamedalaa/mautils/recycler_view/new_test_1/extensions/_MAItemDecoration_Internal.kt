package mohamedalaa.mautils.recycler_view.new_test_1.extensions

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import mohamedalaa.mautils.recycler_view.extensions.isBorderBottom
import mohamedalaa.mautils.recycler_view.extensions.isBorderLeft
import mohamedalaa.mautils.recycler_view.extensions.isBorderRight
import mohamedalaa.mautils.recycler_view.extensions.isBorderTop
import mohamedalaa.mautils.recycler_view.new_test_1.MAItemDecoration

internal fun MAItemDecoration.subItemOffsetIgnoreBorderMergeOffsetsVertical(
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

    val totalCountOfY = yCount.plus(xCount.times(2))

    val yDistance = Math.round(horzTotalGapsDimen.toFloat().div(totalCountOfY.toFloat()))
    val xDistance = yDistance.times(2)

    val isBorderLeft = layoutManager.isBorderLeft(position)
    val isBorderRight = layoutManager.isBorderRight(position)
    val (left, right) = when {
        //isBorderLeft && isBorderRight -> Cannot happen unless spanCount == 1 which is handled above isa.
        isBorderLeft -> 0 to xDistance
        isBorderRight-> xDistance to 0
        else -> yDistance to yDistance
    }

    // -- Top & Bottom -- //
    val isBorderTop = layoutManager.isBorderTop(position)
    val isBorderBottom = layoutManager.isBorderBottom(position)
    val (top, bottom) = when {
        isBorderTop && isBorderBottom -> 0 to 0
        isBorderTop -> 0 to xDistance
        isBorderBottom -> xDistance to 0
        else -> yDistance to yDistance
    }

    rect.set(left, top, right, bottom)
    return rect
}