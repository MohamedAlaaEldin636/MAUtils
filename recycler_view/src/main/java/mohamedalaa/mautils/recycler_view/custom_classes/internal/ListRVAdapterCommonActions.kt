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

import mohamedalaa.mautils.recycler_view.custom_classes.MARVAdapter

@Suppress("PropertyName")
internal interface ListRVAdapterCommonActions<E> : RVAdapterCommonActions {

    val _dataList: MutableList<E>

    // ---- Public fun

    /** Same as [MARVAdapter.changeData] isa. */
    fun changeData(dataList: List<E>) = super.changeData {
        this._dataList.clear()
        this._dataList.addAll(dataList)
    }

    /** Same as [MARVAdapter.removeItemAt] */
    fun removeItemAt(position: Int) = super.removeItemAt(position) {
        _dataList.removeAt(position)
    }

    /** Same as [MARVAdapter.insertItemAt] */
    fun insertItemAt(position: Int, element: E) = super.insertItemAt(position) {
        _dataList.add(position, element)
    }

    /**
     * Moves an item from [fromPosition] to [toPosition] using [removeItemAt] then [insertItemAt]
     *
     * @see [changeData]
     */
    fun moveItem(fromPosition: Int, toPosition: Int) {
        val element = _dataList.elementAt(fromPosition)

        removeItemAt(fromPosition)
        insertItemAt(toPosition, element)
    }

}