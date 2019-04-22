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

import androidx.core.view.GravityCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import androidx.drawerlayout.widget.DrawerLayout
import mohamedalaa.mautils.material_design.MADrawerLayoutDrawerListener
import mohamedalaa.mautils.material_design.R

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 4/14/2019.
 *
 */
object BADrawerLayout {

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:drawerLayout_openDrawer", event = "android:drawerLayout_openDrawerAttrChanged")
    fun getDrawerLayoutDrawerWithGravity(drawerLayout: DrawerLayout): Boolean? {
        val gravity = drawerLayout.getTag(R.id.drawer_layout_drawer_gravity_tag_id) as? Int

        return gravity?.run { drawerLayout.isDrawerOpen(gravity) } ?: false
    }

    @JvmStatic
    @BindingAdapter("android:drawerLayout_openDrawer", "android:drawerLayout_drawerGravity", "android:drawerLayout_openDrawerAttrChanged", requireAll = false)
    fun setDrawerLayoutDrawerWithGravity(drawerLayout: DrawerLayout, open: Boolean?, gravityCompatInt: Int?, inverseBindingListener: InverseBindingListener?) {
        val gravity = gravityCompatInt ?: GravityCompat.START

        // Tag
        drawerLayout.setTag(R.id.drawer_layout_drawer_gravity_tag_id, gravity)

        // Listener
        val listener = MADrawerLayoutDrawerListener {
            onDrawerClosed {
                inverseBindingListener?.onChange()
            } onDrawerOpened {
                inverseBindingListener?.onChange()
            }
        }
        val oldListener = ListenerUtil.trackListener(drawerLayout, listener, drawerLayout.id)
        if (oldListener != null) {
            drawerLayout.removeDrawerListener(oldListener)
        }
        drawerLayout.addDrawerListener(listener)

        if (drawerLayout.isDrawerOpen(gravity) == open) {
            return
        }

        if (open == true) {
            drawerLayout.openDrawer(gravity)
        }else {
            drawerLayout.closeDrawer(gravity)
        }
    }

}