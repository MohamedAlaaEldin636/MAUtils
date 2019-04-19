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
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.material_design.custom_classes.internal.RVInternalCommonAdapter

/**
 * **Usage**
 *
 * - Quick build for Simple [RecyclerView.Adapter], see differences below isa
 * ```
 * // ==> More Concise Approach
 * class MyRecyclerViewAdapter(private val map: Map<String, Int>) : RVCommonAdapter() {
 *
 *      override fun getLayoutRes(): Int
 *          = if (/*Some Condition*/) R.layout.my_rc_item_1 else R.layout.my_rc_item_2
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          // ...
 *      }
 *
 *      override fun getItemCount(): Int = map.size
 *
 * }
 *
 * // ==> In case only 1 layout is used, better add it in constructor isa.
 * class MyRecyclerViewAdapter(private val map: Map<String, Int>) : RVCommonAdapter(R.layout.my_rc_item) {
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          // ...
 *      }
 *
 *      override fun getItemCount(): Int = map.size
 *
 * }
 *
 * // ==> Old Approach
 * class MyRecyclerViewAdapter2(private val map: Map<String, Int>)
 *      : RecyclerView.Adapter<MyRecyclerViewAdapter2.MyViewHolder>() {
 *
 *      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
 *          val inflater = LayoutInflater.from(parent.context)
 *
 *          val view = inflater.inflate(R.layout.my_rc_item, parent, false)
 *
 *          return MyViewHolder(view)
 *      }
 *
 *      override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
 *          // ...
 *      }
 *
 *      override fun getItemCount(): Int = map.size
 *
 *      // ----- View Holder
 *
 *      class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
 *
 * }
 * ```
 * - [getLayoutRes] is tied to [RecyclerView.Adapter.getItemViewType] so you don't need to worry
 * about item types, just make your condition checking in [getLayoutRes] and it will auto
 * generate item view types isa.
 *
 * @param layoutRes better use this instead of [getLayoutRes] if you have single item type isa.
 *
 * @see RVListCommonAdapter
 */
abstract class RVCommonAdapter(@LayoutRes layoutRes: Int? = null) : RVInternalCommonAdapter(layoutRes) {

    public override fun changeData(changeAction: () -> Unit) {
        super.changeData(changeAction)
    }

    public override fun removeItemAt(position: Int, removeAction: (Int) -> Unit) {
        super.removeItemAt(position, removeAction)
    }

    public override fun insertItemAt(position: Int, insertAction: (Int) -> Unit) {
        super.insertItemAt(position, insertAction)
    }

}