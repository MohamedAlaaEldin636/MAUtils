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

import android.view.View
import androidx.databinding.BindingAdapter
import mohamedalaa.mautils.material_design.setTooltipTextCompat

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 4/14/2019.
 *
 */
object View {

    /**
     * If true sets tooltip text as [View.getContentDescription] if false then clears it isa.
     */
    @JvmStatic
    @BindingAdapter("app:view_enableTooltipText")
    fun tooltipTextAsContentDescription(view: View, enable: Boolean?) {
        view.setTooltipTextCompat(if (enable == true) view.contentDescription else null)
    }
}