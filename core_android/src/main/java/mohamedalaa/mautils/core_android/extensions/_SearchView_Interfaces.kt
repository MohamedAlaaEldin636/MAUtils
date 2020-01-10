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

/**
 * Converts `receiver`'s block to [SearchView.OnQueryTextListener] in case you like to override
 * specific functions on an existing `receiver` block isa.
 */
fun (MASearchViewOnQueryTextListenerBuilder.() -> Unit).asListener(): SearchView.OnQueryTextListener {
    return MASearchViewOnQueryTextListenerBuilder.getListener(this)
}

/**
 * - Builder for [SearchView.OnQueryTextListener] Used by [setOnQueryTextListenerMA] isa.
 *
 * ### Functions
 * - [onQueryTextChange]
 * - [onQueryTextSubmitAction]
 *
 * @see [SearchView.setOnQueryTextListenerMA]
 */
class MASearchViewOnQueryTextListenerBuilder private constructor() {

    private var onQueryTextChangeAction: (String?) -> Boolean = { false }
    private var onQueryTextSubmitAction: (String?) -> Boolean = { false }

    companion object {

        /** @return [SearchView.OnQueryTextListener] with modifications made in [listener] isa. */
        internal fun getListener(listener: MASearchViewOnQueryTextListenerBuilder.() -> Unit): ListenerImpl {
            return MASearchViewOnQueryTextListenerBuilder().apply(listener).run {
                ListenerImpl(
                    onQueryTextChangeAction,
                    onQueryTextSubmitAction
                )
            }
        }

    }

    /**
     * Same as [SearchView.OnQueryTextListener.onQueryTextChange] where return value is [action] isa.
     */
    @Suppress("unused") // Unused receiver but needed to be extension function for IDE style isa.
    fun MASearchViewOnQueryTextListenerBuilder.onQueryTextChange(action: (String?) -> Boolean) {
        onQueryTextChangeAction = action
    }

    /**
     * Same as [SearchView.OnQueryTextListener.onQueryTextSubmit] where return value is [action] isa.
     */
    @Suppress("unused") // Unused receiver but needed to be extension function for IDE style isa.
    fun MASearchViewOnQueryTextListenerBuilder.onQueryTextSubmit(action: (String?) -> Boolean) {
        onQueryTextSubmitAction = action
    }

    // ----- Classes

    internal class ListenerImpl(
        private val onQueryTextChangeAction: (String?) -> Boolean,
        private val onQueryTextSubmitAction: (String?) -> Boolean
    ) : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean =
            onQueryTextChangeAction(newText)
        override fun onQueryTextSubmit(query: String?): Boolean =
            onQueryTextSubmitAction(query)
    }

}
