@file:JvmName("GridLayoutManagerUtils")

package mohamedalaa.mautils.recycler_view.extensions

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @return row and col as pair each starts from 1 to positive infinity isa.
 *
 * @see positionFromRowAndCol
 */
fun GridLayoutManager.rowAndColumn(position: Int): Pair<Int, Int> {
    val row = position.rem(spanCount).inc()
    val col = position.div(spanCount).inc()

    return if (orientation == LinearLayoutManager.VERTICAL) col to row else row to col
}

/**
 * @return position of item in [RecyclerView.Adapter] given [row] & [col] isa.
 *
 * @see rowAndColumn
 */
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
