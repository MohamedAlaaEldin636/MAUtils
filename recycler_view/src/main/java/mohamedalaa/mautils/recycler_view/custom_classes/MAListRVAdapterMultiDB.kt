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

import androidx.databinding.ViewDataBinding
import mohamedalaa.mautils.recycler_view.custom_classes.internal.ListRVAdapterCommonActions

/**
 * Same as [MAListRVAdapter] but provides [ViewDataBinding] instead of root view of item layout in [onBindViewHolder],
 * for more clarification See [MARVAdapterMultiDB].
 */
abstract class MAListRVAdapterMultiDB<E>(dataList: List<E>)
    : MARVAdapterMultiDB(), ListRVAdapterCommonActions<E> {

    final override val _dataList: MutableList<E> = dataList.toMutableList()

    val dataList: List<E>
        get() = _dataList

    // ---- Overridden fun

    final override fun getItemCount(): Int
        = _dataList.size

}