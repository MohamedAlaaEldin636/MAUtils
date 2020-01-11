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

import android.graphics.Color
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import androidx.annotation.ColorInt
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableField
import mohamedalaa.mautils.core_android.data_binding.binding_adapter.BAEditText
import mohamedalaa.mautils.core_android.extensions.*
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import mohamedalaa.mautils.material_design.*
import java.lang.ref.WeakReference

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
    @BindingAdapter("toolbar_menuRes",
        "toolbar_onMenuItemClickListener",
        "toolbar_onNavigationIconClickListener",
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

    /**
     * ### Usage
     * - Used to setup [Toolbar.getMenu] with [Menu.setupActionViewForSearchView] isa.
     * - Note you can use two-way data binding approach in addition to [maSearchViewOnQueryTextListenerBuilderBlock]
     * if you want, But commonly it's either the [maSearchViewOnQueryTextListenerBuilderBlock] listener
     * OR the two-way data binding approach but both are provided for full customization isa,
     * **Also Note** two-way data binding is for [SearchView.text] only isa.
     * - Other params in this fun corresponds to all other in [Menu.setupActionViewForSearchView]
     * to support full xml customization isa.
     *
     * ### CAUTION
     * - when using two-way use it with below code because dynamic change will trigger observer
     * to be invoked twice isa.
     * viewModel.mutableLiveDataSearchQuery.observeOldAndNew(this) { old, new ->
     *      // Read CharSequence doc as it says equals has unknown result isa.
     *      if (old.toStringOrNull() == new.toStringOrNull()) return@observeOldAndNew
     *
     *      logError("changed text isa is -> $new")
     * }
     */
    @JvmStatic
    @BindingAdapter(
        "toolbar_setupActionViewForSearchView_searchViewText",
        "toolbar_setupActionViewForSearchView_searchViewTextAttrChanged",

        "toolbar_setupActionViewForSearchView_maSearchViewOnQueryTextListenerBuilderBlock",

        "toolbar_setupActionViewForSearchView_imageViewsTintColor",
        "toolbar_setupActionViewForSearchView_searchViewTextColor",
        "toolbar_setupActionViewForSearchView_searchViewHintTextColor",
        "toolbar_setupActionViewForSearchView_searchViewHintText",

        "toolbar_setupActionViewForSearchView_onCloseListener",

        requireAll = false
    )
    fun setupActionViewForSearchView(
        toolbar: Toolbar,

        searchViewText: CharSequence?,
        inverseBindingListener: InverseBindingListener?,

        maSearchViewOnQueryTextListenerBuilderBlock: (MASearchViewOnQueryTextListenerBuilder.() -> Unit)?,

        @ColorInt imageViewsTintColor: Int?,
        @ColorInt searchViewTextColor: Int?,
        @ColorInt searchViewHintTextColor: Int?,
        searchViewHintText: CharSequence?,

        onCloseListener: (() -> Boolean)?
    ) {
        // sometimes if `postWithReceiver` not used like below code and changes to menu of toolbar
        // was done as well by binding adapter or done after this invocation then all colors are
        // cleared and sometimes functionality as well, so below code is better and doesn't show
        // any ui blinking of color changes for example isa.
        toolbar.postWithReceiver {
            val listener = maSearchViewOnQueryTextListenerBuilderBlock?.asListener()
            menu.setupActionViewForSearchView(
                searchViewText = searchViewText,
                onQueryTextListener = {
                    onQueryTextChange {
                        inverseBindingListener?.onChange()

                        listener?.onQueryTextChange(it) ?: false
                    }

                    onQueryTextSubmit {
                        listener?.onQueryTextSubmit(it) ?: false
                    }
                },

                imageViewsTintColor = imageViewsTintColor ?: Color.WHITE,
                searchViewTextColor = searchViewTextColor ?: Color.WHITE,
                searchViewHintTextColor = searchViewHintTextColor ?: Color.WHITE.addColorAlpha(0.75f),
                searchViewHintText = searchViewHintText,

                onCloseListener = onCloseListener
            )
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "toolbar_setupActionViewForSearchView_searchViewText", event = "toolbar_setupActionViewForSearchView_searchViewTextAttrChanged")
    fun getToolbarMenuItemSearchViewText(toolbar: Toolbar): CharSequence? {
        val searchView = toolbar.menu.firstNestedActionViewIsInstanceOrNull<SearchView>()
            ?: return null

        return searchView.text
    }

}