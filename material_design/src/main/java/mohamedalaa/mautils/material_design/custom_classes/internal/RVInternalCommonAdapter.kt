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

package mohamedalaa.mautils.material_design.custom_classes.internal

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.core_android.extensions.inflateLayout
import mohamedalaa.mautils.material_design.custom_classes.RVCommonAdapter
import mohamedalaa.mautils.material_design.custom_classes.RVListCommonAdapter

/**
 * DO NOT use this instead use [RVCommonAdapter] OR [RVListCommonAdapter]
 *
 * @param layoutRes either `this param` or [getLayoutRes] should be used isa.
 */
abstract class RVInternalCommonAdapter @PublishedApi internal constructor (@LayoutRes private val layoutRes: Int? = null)
    : RecyclerView.Adapter<RVInternalCommonAdapter.ViewHolder>() {

    // Abstract fun

    abstract fun onBindViewHolder(itemView: View, position: Int)

    // Open fun

    /**
     * @return layout resource used in [onCreateViewHolder] for the item layout,
     * Note this is tied to [getItemViewType] so returning several layout resources
     * will auto-change [getItemViewType] unless it's overridden by you isa.
     */
    @LayoutRes open fun getLayoutRes(): Int = layoutRes ?: 0

    // Open overridden fun

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.inflateLayout(getLayoutRes(), parent)

        return ViewHolder(
            view
        )
    }

    override fun getItemViewType(position: Int): Int
        = getLayoutRes()

    // Final overridden fun

    final override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = onBindViewHolder(holder.itemView, position)

    // Internal open fun

    /**
     * Changes whole data by executing [changeAction] then calling [RecyclerView.Adapter.notifyDataSetChanged] afterwards isa.
     */
    internal open fun changeData(changeAction: () -> Unit) {
        changeAction()

        notifyDataSetChanged()
    }

    /**
     * Remove Item at [position] via [removeAction] then uses [notifyItemRemoved]
     */
    internal open fun removeItemAt(position: Int, removeAction: (Int) -> Unit) {
        removeAction(position)

        notifyItemRemoved(position)

        notifyItemRangeChanged(0, this.itemCount, java.lang.Boolean.FALSE)
    }

    /**
     * Insert Item at [position] via [insertAction] then uses [notifyItemInserted]
     */
    internal open fun insertItemAt(position: Int, insertAction: (Int) -> Unit) {
        insertAction(position)

        notifyItemInserted(position)

        notifyItemRangeChanged(0, this.itemCount, java.lang.Boolean.FALSE)
    }

    // Classes

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}