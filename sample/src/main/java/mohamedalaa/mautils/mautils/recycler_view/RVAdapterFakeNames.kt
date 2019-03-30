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

package mohamedalaa.mautils.mautils.recycler_view

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.my_rc_item.view.*
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRVAdapter
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRVAdapterDB
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRVAdapterMultiDB

class RVAdapterFakeNames(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRVAdapter<String>(dataList) {

    override fun getLayoutRes(): Int
        = if (layoutManager.orientation == LinearLayoutManager.VERTICAL) R.layout.my_rc_item else R.layout.my_rc_item_hz

    override fun onBindViewHolder(itemView: View, position: Int) {
        itemView.textView.text = dataList[position]

        itemView.setOnClickListener {
            removeItemAt(position)
        }
    }

}

class RVAdapterFakeNamesDB22(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRVAdapterDB<String, ViewDataBinding>(dataList, R.layout.my_rc_item) {

    override fun onBindViewHolder(binding: ViewDataBinding, position: Int) {

    }

}

class RVAdapterFakeNamesDB(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : MAListRVAdapterMultiDB<String>(dataList) {

    override fun getLayoutRes(): Int {
        return super.getLayoutRes()
    }

    override fun onBindViewHolder(binding: ViewDataBinding, position: Int) {

    }

}