/*
 * Copyright (c) 2019 Mohamed Alaa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

@file:JvmName("GridLayoutManagerUtils")

package mohamedalaa.mautils.material_design

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
