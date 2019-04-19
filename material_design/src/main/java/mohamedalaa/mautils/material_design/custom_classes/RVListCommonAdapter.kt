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

package mohamedalaa.mautils.material_design.custom_classes

import androidx.annotation.LayoutRes
import mohamedalaa.mautils.material_design.custom_classes.internal.RVInternalCommonAdapter

/**
 * Same as [RVCommonAdapter] but facilitates methods in it for **list** of items case and even add more,
 * 1. [RVCommonAdapter.removeItemAt] is replaced with [removeItemAt] using [dataList],
 * as well as replacing [RVCommonAdapter.insertItemAt], [RVCommonAdapter.changeData].
 * 2. auto handle for [getItemCount] using [dataList]
 * 3. Adds [moveItem] fun which internally remove then insert using corresponding fun isa.
 *
 * **Example of extending this class**
 * ```
 * class MyRecyclerViewAdapter(namesList: List<String>)
 *      : RVListCommonAdapter<String>(namesList) {
 *
 *      override fun getLayoutRes(): Int
 *          = if (/*Some Condition*/) R.layout.my_rc_item_1 else R.layout.my_rc_item_2
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          // ...
 *      }
 *
 * }
 *
 * // Then to call insert, remove, move, or change whole data it is easy
 * // without coding any line of code.
 * myRecyclerViewAdapter.removeItemAt(index)
 * myRecyclerViewAdapter.insertItemAt(index)
 * myRecyclerViewAdapter.changeData(index)
 * myRecyclerViewAdapter.moveItem(index)
 * ```
 *
 * @param dataList list of items in the recycler view adapter.
 * @param layoutRes better use this instead of [getLayoutRes] if you have single item type isa.
 */
abstract class RVListCommonAdapter<E>(
    dataList: List<E>?,
    @LayoutRes layoutRes: Int? = null
) : RVInternalCommonAdapter(layoutRes) {

    // Private properties

    private var _dataList: MutableList<E>? = dataList?.toMutableList()

    // Public properties

    val dataList: List<E>?
        get() = _dataList

    // Open overridden fun

    override fun getItemCount(): Int = _dataList?.size ?: 0

    // Public fun

    /** Same as [RVCommonAdapter.changeData], but does the work in [RVListCommonAdapter.dataList] for you isa. */
    fun changeData(dataList: List<E>?) = super.changeData {
        if (dataList != null) {
            _dataList?.clear()

            _dataList?.addAll(dataList)
        }else {
            _dataList = null
        }
    }

    /** Same as [RVCommonAdapter.removeItemAt] */
    fun removeItemAt(position: Int) = super.removeItemAt(position) {
        _dataList?.removeAt(position)
    }

    /** Same as [RVCommonAdapter.insertItemAt] */
    fun insertItemAt(position: Int, element: E) = super.insertItemAt(position) {
        _dataList?.add(position, element)
    }

    /**
     * Moves an item from [fromPosition] to [toPosition] using [removeItemAt] then [insertItemAt]
     *
     * @see [changeData]
     */
    fun moveItem(fromPosition: Int, toPosition: Int) {
        val element = dataList?.elementAt(fromPosition) ?: return

        removeItemAt(fromPosition)
        insertItemAt(toPosition, element)
    }

}