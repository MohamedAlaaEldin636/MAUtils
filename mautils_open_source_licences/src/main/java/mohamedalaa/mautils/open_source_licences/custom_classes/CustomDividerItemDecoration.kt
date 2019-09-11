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

package mohamedalaa.mautils.open_source_licences.custom_classes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.core_android.extensions.dpToPx

/**
 * Same as [DividerItemDecoration], but without divider after last index isa.
 *
 * **Note**
 *
 * If [dividerDrawable] is null, then [dividerColor] and [dividerDimenInPx] must be not null isa,
 * however if that happens a fallback to 1 dp with [Color.BLACK] will be used isa.
 *
 * @param dividerDimenInPx if null then [Drawable.getIntrinsicHeight] or [Drawable.getIntrinsicWidth]
 * is used according to [orientationIsVertical], along with [dividerDrawable] isa.
 */
class CustomDividerItemDecoration(context: Context,
                                  private val dividerDrawable: Drawable? = null,
                                  @ColorInt private var dividerColor: Int? = null,
                                  @Px private var dividerDimenInPx: Int? = null,
                                  private val orientationIsVertical: Boolean = true)
    : RecyclerView.ItemDecoration() {

    private val dimenInPx: Int

    private val colorDrawable = dividerColor?.run { ColorDrawable(this) }

    init {
        if (dividerDrawable == null && dividerColor == null) {
            dividerColor = Color.BLACK

            if (dividerDimenInPx == null) {
                dividerDimenInPx = context.dpToPx(1).toInt()
            }
        }

        dimenInPx = if (dividerDrawable != null) {
            if (orientationIsVertical) dividerDrawable.intrinsicHeight else dividerDrawable.intrinsicWidth
        }else {
            dividerDimenInPx ?: context.dpToPx(1).toInt()
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) = when {
        parent.layoutManager == null -> Unit
        orientationIsVertical -> drawVertical(canvas, parent)
        else -> drawHorizontal(canvas, parent)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        // hide the divider for the last child
        parent.adapter.apply {
            when {
                (this != null && position == this.itemCount.dec()) -> outRect.setEmpty()
                orientationIsVertical -> outRect.set(0, 0, 0, dimenInPx)
                else -> outRect.set(0, 0, dimenInPx, 0)
            }
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        val (left, right) = if (parent.clipToPadding) {
            val tempLeft = parent.paddingLeft
            val tempRight = parent.width - parent.paddingRight
            canvas.clipRect(tempLeft, parent.paddingTop, tempRight, parent.height - parent.paddingBottom)

            tempLeft to tempRight
        }else {
            0 to parent.width
        }

        val bounds = Rect()
        for (index in 0 until parent.childCount.dec()) {
            val child = parent.getChildAt(index)

            parent.layoutManager?.getDecoratedBoundsWithMargins(child, bounds)

            val bottom = bounds.bottom + Math.round(child.translationY)
            val top = bottom - dimenInPx

            dividerDrawable?.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            } ?: colorDrawable?.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }

        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        val (top, bottom) = if (parent.clipToPadding) {
            val tempTop = parent.paddingTop
            val tempBottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, tempTop, parent.width - parent.paddingRight, tempBottom)

            tempTop to tempBottom
        }else {
            0 to parent.height
        }

        val bounds = Rect()
        for (index in 0 until parent.childCount.dec()) {
            val child = parent.getChildAt(index)

            parent.layoutManager?.getDecoratedBoundsWithMargins(child, bounds)

            val right = bounds.right + Math.round(child.translationX)
            val left = right - dimenInPx

            dividerDrawable?.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            } ?: colorDrawable?.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }

        canvas.restore()
    }

}