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

package mohamedalaa.mautils.core_android.extensions

import android.widget.SearchView

typealias SearchView_OnQueryTextListener_Typealias = MASearchViewOnQueryTextListener.() -> Unit

/**
 * More concise & idiomatic way for using more than 1 fun interface, See example below.
 * ```
 * fun setupOnQueryTextListener(searchView: SearchView) {
 *      searchView.setOnQueryTextListenerMA {
 *          onQueryTextChange {
 *              // Your code here
 *
 *              true // Depends on what you wanna achieve
 *          } onQueryTextSubmit {
 *              // Your code here
 *
 *              false // Depends on what you wanna achieve
 *          }
 *      }
 * }
 * ```
 *
 * @see [SearchView.setOnQueryTextListenerMA]
 */
class MASearchViewOnQueryTextListener(listener: SearchView_OnQueryTextListener_Typealias?): SearchView.OnQueryTextListener {

    init {
        listener?.invoke(this)
    }

    private var _onQueryTextChange: ((String?) -> Boolean)? = null
    private var _onQueryTextSubmit: ((String?) -> Boolean)? = null

    override fun onQueryTextChange(newText: String?): Boolean
        = _onQueryTextChange?.invoke(newText) ?: false

    /** execute [action] when [onQueryTextChange] is triggered isa. */
    infix fun MASearchViewOnQueryTextListener.onQueryTextChange(action: ((String?) -> Boolean)?): MASearchViewOnQueryTextListener
        = apply { _onQueryTextChange = action }

    override fun onQueryTextSubmit(query: String?): Boolean
        = _onQueryTextSubmit?.invoke(query) ?: false

    /** execute [action] when [onQueryTextSubmit] is triggered isa. */
    infix fun MASearchViewOnQueryTextListener.onQueryTextSubmit(action: ((String?) -> Boolean)?): MASearchViewOnQueryTextListener
        = apply { _onQueryTextSubmit = action }

}