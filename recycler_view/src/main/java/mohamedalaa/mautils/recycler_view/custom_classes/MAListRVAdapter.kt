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

import androidx.annotation.LayoutRes
import mohamedalaa.mautils.recycler_view.custom_classes.internal.ListRVAdapterCommonActions

/**
 * Same as [MARVAdapter] but facilitates methods in it for **list** of items case and even add more,
 * 1. [MARVAdapter.removeItemAt] is replaced with [removeItemAt] using [dataList],
 * as well as replacing [MARVAdapter.insertItemAt], [MARVAdapter.changeData].
 * 2. auto handle for [getItemCount] using [dataList]
 * 3. Adds [moveItem] fun which internally remove then insert using corresponding fun isa.
 *
 * **Example of extending this class**
 * ```
 * class MyRecyclerViewAdapter(namesList: List<String>)
 *      : MAListRVAdapter<String>(namesList) {
 *
 *      override fun getLayoutRes(): Int
 *          = if (layoutManager.orientation == LinearLayoutManager.VERTICAL) R.layout.my_rc_item else R.layout.my_rc_item_hz
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          itemView.setOnClickListener {
 *              removeItemAt(position)
 *          }
 *      }
 *
 * }
 *
 * // Then to call insert, remove, move, or change whole data it is easy
 * // without coding any line of code.
 * myRecyclerViewAdapter.removeItemAt(index)
 * ```
 *
 * @param dataList list of items in the recycler view adapter.
 * @param layoutRes better use this instead of [getLayoutRes] if you have single item type isa.
 */
abstract class MAListRVAdapter<E>(dataList: List<E>, @LayoutRes layoutRes: Int? = null)
    : MARVAdapter(layoutRes), ListRVAdapterCommonActions<E> {

    final override val _dataList: MutableList<E> = dataList.toMutableList()

    val dataList: List<E>
        get() = _dataList

    // ---- Overridden fun

    final override fun getItemCount(): Int
        = _dataList.size

}