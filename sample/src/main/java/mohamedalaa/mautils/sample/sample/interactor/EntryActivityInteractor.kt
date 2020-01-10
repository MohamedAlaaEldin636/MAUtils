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

package mohamedalaa.mautils.sample.sample.interactor

import android.content.Context
import android.widget.SearchView
import mohamedalaa.mautils.core_android.extensions.MASearchViewOnQueryTextListenerBuilder
import mohamedalaa.mautils.core_android.extensions.toast
import java.lang.ref.WeakReference

class EntryActivityInteractor {

    /*
    1. color not white isa.
    2. not clickable so actually nothing happened isa.
     */
    fun onToolbarSearchViewOnQueryTextListener(context: Context) = fun MASearchViewOnQueryTextListenerBuilder.() {
        val weakRef = WeakReference(context)

        onQueryTextChange {
            weakRef.get()?.toast("$it")

            false
        }
    }

}
