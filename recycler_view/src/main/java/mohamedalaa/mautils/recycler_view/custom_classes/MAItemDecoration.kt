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

package mohamedalaa.mautils.recycler_view.custom_classes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.extensions.internal.*

/**
 * **Notes**
 *
 * 1- Whenever you want to change any property use [RecyclerView.invalidateItemDecorations]
 * afterwards so that changes take effect isa.
 *
 * @param singleItemDivider true means if [RecyclerView] has only 1 item, it will still draw
 * divider, and surely after considering [ignoreBorder] & [mergeOffsets] values isa.
 */
class MAItemDecoration(@ColorInt dividerColor: Int = Color.BLACK,
                       @Px var dividerDimenInPx: Int = 0,
                       var ignoreBorder: Boolean = true,
                       var mergeOffsets: Boolean = true,
                       var singleItemDivider: Boolean = false
) : RecyclerView.ItemDecoration() {

    // ---- Properties

    @ColorInt var dividerColor = dividerColor
        set(value) {
            paint.color = value
            field = value
        }

    // ---- Private / Internal properties

    internal val paint = Paint().apply {
        color = dividerColor
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    // ---- Overridden fun

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view).run { if (this == RecyclerView.NO_POSITION) 0 else this }
        val adapter = parent.adapter
        val layoutManager = parent.layoutManager

        val rect: Rect = when {
            adapter == null || (singleItemDivider.not() && adapter.itemCount < 2) -> Rect()
            layoutManager is LinearLayoutManager -> {
                val isHorizontal = layoutManager.orientation != LinearLayoutManager.VERTICAL
                when {
                    ignoreBorder && mergeOffsets -> if(isHorizontal) {
                        subItemOffsetIgnoreBorderMergeOffsetsHorizontal(layoutManager, position)
                    }else {
                        subItemOffsetIgnoreBorderMergeOffsetsVertical(layoutManager, position)
                    }
                    ignoreBorder -> if (isHorizontal) {
                        subItemOffsetIgnoreBorderNoMergeOffsetsHorizontal(layoutManager, position)
                    }else {
                        subItemOffsetIgnoreBorderNoMergeOffsetsVertical(layoutManager, position)
                    }
                    mergeOffsets -> if (isHorizontal) {
                        subItemOffsetNoIgnoreBorderMergeOffsetsHorizontal(layoutManager, position)
                    }else {
                        subItemOffsetNoIgnoreBorderMergeOffsetsVertical(layoutManager, position)
                    }
                    // Else both booleans are false isa.
                    else -> if (isHorizontal) {
                        subItemOffsetNoIgnoreBorderNoMergeOffsetsHorizontal(layoutManager, position)
                    }else {
                        subItemOffsetNoIgnoreBorderNoMergeOffsetsVertical(layoutManager, position)
                    }
                }
            }
            else -> Rect()
        }

        outRect.set(rect)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter
        val layoutManager = parent.layoutManager
        when {
            adapter == null || (singleItemDivider.not() && adapter.itemCount < 2) -> Rect()
            layoutManager is LinearLayoutManager -> {
                val firstVisible = layoutManager.findFirstVisibleItemPosition().apply { if (this == RecyclerView.NO_POSITION) return }
                val lastVisible = layoutManager.findLastVisibleItemPosition().apply { if (this == RecyclerView.NO_POSITION) return }

                val fullDimen = if (mergeOffsets) dividerDimenInPx else dividerDimenInPx.times(2)
                subOnDraw(canvas, parent, layoutManager, firstVisible, lastVisible, fullDimen, ignoreBorder)
            }
        }
    }

}