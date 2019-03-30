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

package mohamedalaa.mautils.recycler_view.custom_classes.internal

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.custom_classes.MARVAdapter

/**
 * This is internal for the library only.
 *
 * @see MARVAdapter todo others
 */
abstract class MABaseRVAdapter internal constructor (@LayoutRes private val layoutRes: Int? = null)
    : RecyclerView.Adapter<MABaseRVAdapter.ViewHolder>(), RVAdapterCommonActions {

    // ---- Abstract fun

    /**
     * @return layout resource for the item layout, Note this is tied to [getItemViewType]
     * so returning several layout resources will auto-change [getItemViewType] isa.
     */
    @LayoutRes open fun getLayoutRes(): Int = layoutRes ?: 0

    internal abstract fun internalOnBindViewHolder(holder: ViewHolder, position: Int)

    // ---- Overridden fun

    final override fun getItemViewType(position: Int): Int {
        return getLayoutRes()
    }

    final override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = internalOnBindViewHolder(holder, position)

    // ----- View Holder

    class ViewHolder(internal val any: Any): RecyclerView.ViewHolder(
        when (any) {
            is View -> any
            is ViewDataBinding -> any.root
            else -> throw RuntimeException("MAUtils Error Contact developer code view holder initialization")
        }
    )

}