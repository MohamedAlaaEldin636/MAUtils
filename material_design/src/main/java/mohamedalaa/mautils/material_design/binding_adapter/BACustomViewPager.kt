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
import mohamedalaa.mautils.material_design.custom_classes.CustomViewPager

object BACustomViewPager {

    @JvmStatic
    @BindingAdapter("android:customViewPager_enableScrolling")
    fun customViewPagerEnableScrolling(customViewPager: CustomViewPager, enableScrolling: Boolean?) {
        customViewPager.isScrollingEnabled = enableScrolling ?: false
    }

}