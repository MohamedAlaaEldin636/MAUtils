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

@file:JvmName("MenuUtils")

package mohamedalaa.mautils.core_android.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.annotation.ColorInt
import androidx.core.text.buildSpannedString
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import mohamedalaa.mautils.core_android.R
import mohamedalaa.mautils.core_android.custom_classes.character_style.DrawableSpan
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull

/**
 * @return index of [menuItem] in this `receiver` OR null if not found isa.
 *
 * @see Menu.firstOrNull
 */
fun Menu.indexOfOrNull(menuItem: MenuItem): Int? {
    forEachIndexed { index, item ->
        if (menuItem == item) {
            return index
        }
    }

    return null
}

/**
 * @return first [MenuItem] matching given [predicate] isa.
 *
 * @see Menu.indexOfOrNull
 * @see Menu.firstNestedOrNull
 */
inline fun Menu.firstOrNull(predicate: (MenuItem) -> Boolean): MenuItem? {
    forEach {
        if (predicate(it)) {
            return it
        }
    }

    return null
}

/**
 * @return all [MenuItem]s even nested ones inside [SubMenu] and if [excludeSubMenuItems] is `true` then
 * we exclude any [MenuItem] which returns `true` from [MenuItem.hasSubMenu] isa.
 *
 * @see mohamedalaa.mautils.core_android.extensions.add
 * @see setupActionViewForSearchView
 */
fun Menu.getAllNestedMenuItems(excludeSubMenuItems: Boolean = false): List<MenuItem> {
    val items = mutableListOf<MenuItem>()

    forEach {
        val excludeThisItem = excludeSubMenuItems && it.hasSubMenu()
        if (excludeThisItem.not()) {
            items += it
        }

        items += it.subMenu?.getAllNestedMenuItems(excludeSubMenuItems).orEmpty()
    }

    return items
}

/**
 * loops through each item in [getAllNestedMenuItems] with given [excludeSubMenuItems] and
 * for each iteration [block] is executed for it isa.
 *
 * @see mohamedalaa.mautils.core_android.extensions.add
 * @see Menu.firstNestedOrNull
 * @see setupActionViewForSearchView
 */
inline fun Menu.forEachNested(excludeSubMenuItems: Boolean = false, block: (MenuItem) -> Unit) {
    for (item in getAllNestedMenuItems(excludeSubMenuItems)) {
        block(item)
    }
}

/**
 * loops through each item in [getAllNestedMenuItems] and if [MenuItem] returns `true` from
 * [predicate] then it will be returned, otherwise returns `null` isa.
 *
 * @see mohamedalaa.mautils.core_android.extensions.add
 * @see Menu.forEachNested
 * @see Menu.getAllNestedMenuItems
 * @see Menu.firstOrNull
 * @see Menu.firstNestedActionViewIsInstanceOrNull
 */
inline fun Menu.firstNestedOrNull(predicate: (MenuItem) -> Boolean): MenuItem? {
    for (item in getAllNestedMenuItems(false)) {
        if (predicate(item)) return item
    }

    return null
}

/**
 * loops through each item in [getAllNestedMenuItems] and if [MenuItem]'s [MenuItem.getActionView]
 * is instance of [R] then return that [R] instance, otherwise returns `null` isa.
 *
 * @param R any class with [View] as upper bound needed to be retrieved as [MenuItem.getActionView] isa.
 *
 * @see Menu.firstNestedOrNull
 */
inline fun <reified R : View> Menu.firstNestedActionViewIsInstanceOrNull(): R? {
    for (item in getAllNestedMenuItems(false)) {
        val actionView = item.actionView

        if (actionView is R) return actionView
    }

    return null
}

/**
 * - Invokes [Menu.add] for each item in [titles] isa.
 *
 * @see forEachNested
 * @see getAllNestedMenuItems
 * @see addWithIds
 * @see setupActionViewForSearchView
 */
fun Menu.add(vararg titles: CharSequence) {
    for (title in titles) add(title)
}

/**
 * - For each item in [idsAndTitles] adds a new [MenuItem] in `receiver` with [MenuItem.getItemId]
 * as [Pair.first] && [MenuItem.getTitle] as [Pair.second] isa.
 *
 * @see add
 * @see addWithIconsDrawableRes
 */
fun Menu.addWithIds(vararg idsAndTitles: Pair<Int, CharSequence>) {
    for ((id, title) in idsAndTitles) {
        add(Menu.NONE, id, Menu.NONE, title)
    }
}

/**
 * - For each item in [titlesAndIconsDrawableRes] adds a new [MenuItem] in `receiver` with [MenuItem.getTitle]
 * as [Pair.first] && [MenuItem.getIcon] as [Pair.second] isa.
 *
 * @see addWithIds
 */
fun Menu.addWithIconsDrawableRes(vararg titlesAndIconsDrawableRes: Pair<CharSequence, Int>) {
    for ((title, drawableRes) in titlesAndIconsDrawableRes) {
        add(Menu.NONE, Menu.NONE, Menu.NONE, title).apply {
            setIcon(drawableRes)
        }
    }
}

/**
 * - Use it only if you want to setup [SearchView] inside `receiver` with below setups isa.
 * 1. All [ImageView]s tinted with [imageViewsTintColor].
 * 2. changes color for text with [searchViewTextColor] && for hint with [searchViewHintTextColor].
 * 3. can have your own hint instead of the default value by using [searchViewHintText] isa.
 *
 * ### Notes
 * - Searching for [SearchView] in `receiver` is done by [Menu.firstNestedActionViewIsInstanceOrNull] isa.
 *
 * @param searchViewHintText if `null` or empty then a default search icon 🔍 with
 * [R.string.type_here_3_dots_ellipsized] will be used instead isa.
 * @param searchViewText `null` means [SearchView.isIconified] and [searchViewText] won't be used
 * otherwise even if empty means it is fully visible and we set the query text as [searchViewText] isa,
 * useful in case you want your search to start with specific query isa, Also note we don't call
 * [EditText.setText] unless * [searchViewText] != [EditText.getText] ( [EditText] here means
 * text inside [SearchView] isa. )
 *
 * @param onCloseListener used if not-null when [SearchView.OnCloseListener.onClose] is invoked isa.
 * @param onQueryTextListener uses [setOnQueryTextListenerMA] on [SearchView] isa.
 *
 * @return `true` if found [MenuItem.getActionView] `as` [SearchView] && found non-null [Context]
 * from [context] OR [SearchView.getContext], `false` otherwise isa.
 *
 * @see Menu.firstNestedActionViewIsInstanceOrNull
 */
// @ForMe any change in flow ex. listener after setting text or check setting text before setting it
// needs to check mohamedalaa.mautils.material_design.binding_adapter.BAToolbar#setupActionViewForSearchView
// firstly as it depends on two-way data binding isa.
fun Menu.setupActionViewForSearchView(
    context: Context? = null,

    @ColorInt imageViewsTintColor: Int = Color.WHITE,
    @ColorInt searchViewTextColor: Int = Color.WHITE,
    @ColorInt searchViewHintTextColor: Int = Color.WHITE.addColorAlpha(0.75f),
    searchViewHintText: CharSequence? = null,
    searchViewText: CharSequence? = null,

    onCloseListener: (() -> Boolean)? = null,
    onQueryTextListener: MASearchViewOnQueryTextListenerBuilder.() -> Unit
): Boolean {
    val searchView = firstNestedActionViewIsInstanceOrNull<SearchView>() ?: return false

    (context ?: searchView.context ?: return false).performIfNotNull {

        // for search and close icons to be colored isa.
        searchView.setIconsTint(imageViewsTintColor)

        // used to change underline color isa.
        searchView.forEachNested { view ->
            view.setBackgroundTintCompat(searchViewTextColor, PorterDuff.Mode.DST_ATOP)
        }

        searchView.setOnQueryTextListenerMA(onQueryTextListener)
        onCloseListener?.apply {
            searchView.setOnCloseListener {
                this()
            }
        }

        searchView.firstNestedViewIsInstanceOrNull<EditText> {
            setTextColor(searchViewTextColor)

            if (searchViewHintText.isNullOrEmpty()) {
                val hintDrawable = getDrawableFromResOrNull(R.drawable.ic_search_black_24dp, searchViewHintTextColor)
                hint = buildSpannedString {
                    if (hintDrawable == null) {
                        append(getString(R.string.type_here_3_dots_ellipsized))

                        return@buildSpannedString
                    }

                    append("? ${getString(R.string.type_here_3_dots_ellipsized)}")

                    this[0] = DrawableSpan(hintDrawable)
                }
            }else {
                hint = searchViewHintText
            }
            setHintTextColor(searchViewHintTextColor)

            imeOptions = EditorInfo.IME_ACTION_SEARCH or EditorInfo.IME_FLAG_NO_FULLSCREEN

            searchView.isIconified = searchViewText == null
            if (text != searchViewText && searchViewText != null) {
                setText(searchViewText)
                setSelectionToLastChar(true)
            }
        }
    }

    return true
}
