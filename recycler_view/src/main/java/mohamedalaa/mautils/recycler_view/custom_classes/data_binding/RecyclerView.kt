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

package mohamedalaa.mautils.recycler_view.custom_classes.data_binding

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import mohamedalaa.mautils.core_android.dpToPx
import mohamedalaa.mautils.recycler_view.custom_classes.MAItemDecoration
import mohamedalaa.mautils.recycler_view.extensions.forEachItemDecorations
import kotlin.math.roundToInt

/**
 * ## Notes
 * 1. Nullable params are meant to be like that since by this approach no boxing is mandatory.
 * 2. cannot have default param since defaults for data binding itself will override it.
 */
object RecyclerView {

    /**
     * - If [spanCount] == 0 or is null then it is [LinearLayoutManager], and [isStaggered] is ignored.
     * - else then according to [isStaggered] it is either [GridLayoutManager] OR [StaggeredGridLayoutManager] isa.
     * - In case of [StaggeredGridLayoutManager] then [reverseLayout] is not effective
     */
    @JvmStatic
    @BindingAdapter(
        "android:recyclerView_layoutManager_isStaggered",
        "android:recyclerView_layoutManager_isHorizontal",
        "android:recyclerView_layoutManager_spanCount",
        "android:recyclerView_layoutManager_reverseLayout",
        requireAll = false
    )
    fun setRecyclerViewLayoutManager(
        recyclerView: RecyclerView,
        isStaggered: Boolean?,
        isHorizontal: Boolean?,
        spanCount: Int?,
        reverseLayout: Boolean?
    ) {
        val context = recyclerView.context
        val orientation = if (isHorizontal == true) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
        val reverseLayoutNotNull = reverseLayout ?: false

        val layoutManager: RecyclerView.LayoutManager = if (spanCount != null && spanCount > 0) {
            if (isStaggered == true) {
                StaggeredGridLayoutManager(spanCount, orientation)
            }else {
                GridLayoutManager(context, spanCount, orientation, reverseLayoutNotNull)
            }
        }else {
            LinearLayoutManager(context, orientation, reverseLayoutNotNull)
        }

        recyclerView.layoutManager = layoutManager
    }

    @JvmStatic
    @BindingAdapter(
        "android:recyclerView_maItemDecoration_dividerColor",
        "android:recyclerView_maItemDecoration_dividerDimenInDp",
        "android:recyclerView_maItemDecoration_IgnoreBorder",
        "android:recyclerView_maItemDecoration_MergeOffsets",
        "android:recyclerView_maItemDecoration_SingleItemDivider",
        requireAll = false
    )
    fun setRecyclerViewMAItemDecoration(
        recyclerView: RecyclerView,
        @ColorInt dividerColor: Int?,
        @Px dividerDimenInDp: Int?,
        ignoreBorder: Boolean?,
        mergeOffsets: Boolean?,
        singleItemDivider: Boolean?

    ) {
        val itemDecoration = MAItemDecoration(
            dividerColor ?: Color.BLACK,
            recyclerView.context.dpToPx(dividerDimenInDp ?: 0).roundToInt(),
            ignoreBorder ?: true,
            mergeOffsets ?: true,
            singleItemDivider ?: false
        )

        var exists = false
        recyclerView.run {
            forEachItemDecorations {
                if (it == itemDecoration) {
                    exists = true

                    return@run
                }
            }
        }

        if (exists.not()) {
            recyclerView.addItemDecoration(itemDecoration)
        }
    }

}