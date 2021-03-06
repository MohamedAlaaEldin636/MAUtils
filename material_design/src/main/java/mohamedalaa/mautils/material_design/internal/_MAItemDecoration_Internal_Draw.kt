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

package mohamedalaa.mautils.material_design.internal

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.material_design.custom_classes.MAItemDecoration
import kotlin.math.min

internal fun MAItemDecoration.subOnDraw(
    canvas: Canvas,
    parent: RecyclerView,
    layoutManager: LinearLayoutManager,
    firstVisible: Int,
    lastVisible: Int,
    fullDimen: Int,
    ignoreBorder: Boolean
) {
    val spanCount = if (layoutManager is GridLayoutManager) layoutManager.spanCount else 1

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
    if (layoutManager.itemCount.rem(spanCount) != 0
        && lastVisible == layoutManager.itemCount.dec()) {

        val child = parent.getChildAt(lastVisible.minus(firstVisible))

        val bounds = Rect()
        layoutManager.getDecoratedBoundsWithMargins(child, bounds)

        val additionalDimen = if (ignoreBorder) 0 else this.dividerDimenInPx
        val (rect1, rect2) = if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
            val top = bounds.top + layoutManager.getTopDecorationHeight(child) - fullDimen
            val bottom = top.plus(fullDimen)
            val rect1 = Rect(parent.left, top, parent.right, bottom)

            val left = bounds.right.minus(layoutManager.getRightDecorationWidth(child))
            val right = left.plus(fullDimen)
            val rect2 = Rect(
                left,
                min(parent.top, bottom),
                right,
                min(parent.bottom, bounds.bottom - layoutManager.getBottomDecorationHeight(child) + additionalDimen)
            )

            rect1 to rect2
        }else {
            val top = bounds.bottom - layoutManager.getBottomDecorationHeight(child)
            val bottom = top.plus(fullDimen)
            val rect1 = Rect(
                parent.left,
                top,
                min(parent.right, bounds.right - layoutManager.getRightDecorationWidth(child) + additionalDimen),
                bottom
            )

            val left = bounds.left + layoutManager.getLeftDecorationWidth(child) - fullDimen
            val right = left.plus(fullDimen)
            val rect2 = Rect(
                left,
                parent.top,
                right,
                parent.bottom
            )

            rect1 to rect2
        }

        canvas.drawRect(rect1, paint)
        canvas.drawRect(rect2, paint)
    }
}
