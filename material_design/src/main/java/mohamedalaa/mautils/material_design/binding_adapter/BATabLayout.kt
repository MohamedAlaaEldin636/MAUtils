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

package mohamedalaa.mautils.material_design.binding_adapter

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

/** android:tabLayout_ */
object BATabLayout {


    @JvmStatic
    @BindingAdapter("android:tabLayout_viewPager")
    fun setupWithViewPager(tabLayout: TabLayout, viewPager: ViewPager?) {
        viewPager?.apply { tabLayout.setupWithViewPager(this) }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:tabLayout_selectedTabPosition", event = "android:tabLayout_selectedTabPositionAttrChanged")
    fun getTabLayoutSelectedTabPosition(tabLayout: TabLayout): Int {
        return tabLayout.selectedTabPosition
    }

    @JvmStatic
    @BindingAdapter("android:tabLayout_selectedTabPosition", "android:tabLayout_selectedTabPositionAttrChanged", requireAll = false)
    fun setTabLayoutSelectedTabPosition(tabLayout: TabLayout, position: Int, inverseBindingListener: InverseBindingListener?) {
        // Listener
        val listener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                inverseBindingListener?.onChange()
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {}

            override fun onTabUnselected(p0: TabLayout.Tab?) {}
        }
        val oldListener = ListenerUtil.trackListener(tabLayout, listener, tabLayout.id)
        if (oldListener != null) {
            tabLayout.removeOnTabSelectedListener(oldListener)
        }
        tabLayout.addOnTabSelectedListener(listener)

        // Infinite loop check todo tb ma necall el static fun hna bta3et el inverse ashal wala balash laykon infinite loop isa ?!
        if (tabLayout.selectedTabPosition == position) {
            return
        }

        // Action
        tabLayout.getTabAt(position)?.select()
    }

}