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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.my_rc_item.view.*
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.material_design.custom_classes.RVListCommonAdapter

class ListFakeNamesAdapter(dataList: List<String>, var layoutManager: LinearLayoutManager)
    : RVListCommonAdapter<String>(dataList.toMutableList()) {

    override fun getLayoutRes(): Int
        = if (layoutManager.orientation == LinearLayoutManager.VERTICAL) R.layout.my_rc_item else R.layout.my_rc_item_hz

    override fun onBindViewHolder(itemView: View, position: Int) {
        itemView.textView.text = dataList?.getOrNull(position)

        itemView.setOnClickListener {
            removeItemAt(position)
        }
    }

}