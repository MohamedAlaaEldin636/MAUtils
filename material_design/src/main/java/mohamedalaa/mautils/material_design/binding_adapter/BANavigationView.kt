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
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.navigation.NavigationView
import mohamedalaa.mautils.core_android.extensions.findBindingOrNull
import mohamedalaa.mautils.core_android.extensions.layoutInflater
import mohamedalaa.mautils.core_kotlin.extensions.pairedIteration

object BANavigationView {

    /**
     * ### How to use
     *
     * To add BR ids put them in any class as constants Ex. const val BR_viewModel = BR.viewModel
     *
     * then in xml use below code
     *
     * ```
     * <import type="kotlin.collections.CollectionsKt"/>
     * <import type="...YourConstantClass"/>
     * <variable name="viewModel" ... />
     * ```
     * android:navigationView_setHeaderViewPairedVariables
     * ="@{CollectionsKt.listOf(YourConstantClass.BR_viewModel, viewModel, YourConstantClass.BR_presenter, presenter)}"
     */
    @JvmStatic
    @BindingAdapter("android:navigationView_setHeaderViewLayoutRes", "android:navigationView_setHeaderViewPairedVariables", requireAll = false)
    fun addHeaderViewAndVariables(
        navigationView: NavigationView,
        headerViewLayoutRes: Int?,
        pairedVariables: List<*>?
    ) {
        // Inflate layout
        val (binding: ViewDataBinding, addView: Boolean) = (if (headerViewLayoutRes != null) {
            DataBindingUtil.inflate<ViewDataBinding>(
                navigationView.context.layoutInflater, headerViewLayoutRes, navigationView, false
            )?.run { this to true }
        }else {
            navigationView.getHeaderView(0).findBindingOrNull<ViewDataBinding>()?.run { this to false }
        }) ?: return

        // Assign variables
        pairedVariables?.pairedIteration()?.forEach {
            val id = (it.first as? Int) ?: return@forEach
            val any = it.second ?: return@forEach

            binding.setVariable(id, any)
        }

        // Add header view
        if (addView) {
            navigationView.addHeaderView(binding.root)
        }
    }

    @JvmStatic
    @BindingAdapter("android:navigationView_ItemSelectedListener")
    fun setNavigationViewItemSelectedListener(navigationView: NavigationView, listener: NavigationView.OnNavigationItemSelectedListener?) {
        navigationView.setNavigationItemSelectedListener(listener)
    }

}