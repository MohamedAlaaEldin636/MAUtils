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
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

object Toolbar {

    @JvmStatic
    @BindingAdapter("app:toolbar_menuRes",
        "app:toolbar_onMenuItemClickListener",
        "app:toolbar_onNavigationIconClickListener",
        requireAll = false)
    fun setupToolbar(
        toolbar: Toolbar,
        @MenuRes menuRes: Int?,
        onMenuItemClickListener: Toolbar.OnMenuItemClickListener?,
        onNavigationIconClickListener: View.OnClickListener?
    ) {
        menuRes?.apply { toolbar.inflateMenu(this) }

        onMenuItemClickListener?.apply { toolbar.setOnMenuItemClickListener(this) }

        onNavigationIconClickListener?.apply { toolbar.setNavigationOnClickListener(this) }
    }

}