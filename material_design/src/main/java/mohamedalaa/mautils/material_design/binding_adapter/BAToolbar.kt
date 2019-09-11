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
import androidx.annotation.ColorInt
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import mohamedalaa.mautils.core_android.extensions.postWithReceiver
import mohamedalaa.mautils.material_design.findNavIconViewOrNull
import mohamedalaa.mautils.material_design.setIconsTint
import mohamedalaa.mautils.material_design.setTooltipTextCompat

/**
 * ## List of Other bindingAdapters in extension functions isa.
 *
 * 1. Currently none isa.
 */
object BAToolbar {

    /** @param useViewPost if true then I will use [postWithReceiver] before [setIconsTint] isa. */
    @JvmStatic
    @BindingAdapter(
        "mautils:toolbar_setIconsTint",
        "mautils:toolbar_setIconsTint_changeTitleTextColor",
        "mautils:toolbar_setIconsTint_useViewPost",
        requireAll = false
    )
    fun setIconsTint(toolbar: Toolbar, @ColorInt color: Int, changeTitleTextColor: Boolean? = null, useViewPost: Boolean? = null) {
        if (useViewPost == true) {
            toolbar.postWithReceiver {
                toolbar.setIconsTint(color, changeTitleTextColor ?: false)
            }
        }else {
            toolbar.setIconsTint(color, changeTitleTextColor ?: false)
        }
    }

    @JvmStatic
    @BindingAdapter("mautils:toolbar_menuRes",
        "mautils:toolbar_onMenuItemClickListener",
        "mautils:toolbar_onNavigationIconClickListener",
        requireAll = false)
    fun setupMenuAndIconsClicks(
        toolbar: Toolbar,
        @MenuRes menuRes: Int?,
        onMenuItemClickListener: Toolbar.OnMenuItemClickListener?,
        onNavigationIconClickListener: View.OnClickListener?
    ) {
        menuRes?.apply { toolbar.menu.clear();  toolbar.inflateMenu(this) }

        onMenuItemClickListener?.apply { toolbar.setOnMenuItemClickListener(this) }

        onNavigationIconClickListener?.apply { toolbar.setNavigationOnClickListener(this) }
    }

    @JvmStatic
    @BindingAdapter("mautils:toolbar_setNavIconContentDescriptionAndTooltipText")
    fun setNavIconContentDescriptionAndTooltipText(toolbar: Toolbar, contentDescription: String?) {
        toolbar.findNavIconViewOrNull()?.apply {
            toolbar.navigationContentDescription = contentDescription
            setTooltipTextCompat(contentDescription)
        }
    }

}