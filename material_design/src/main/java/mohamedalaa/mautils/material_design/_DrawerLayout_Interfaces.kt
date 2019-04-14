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

package mohamedalaa.mautils.material_design

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

typealias DrawerLayout_DrawerListener_Typealias = MADrawerLayoutDrawerListener.() -> Unit

class MADrawerLayoutDrawerListener(listener: DrawerLayout_DrawerListener_Typealias?): DrawerLayout.DrawerListener {

    init {
        listener?.invoke(this)
    }

    private var _onDrawerClosed: ((View) -> Unit)? = null
    private var _onDrawerOpened: ((View) -> Unit)? = null
    private var _onDrawerSlide: ((View, Float) -> Unit)? = null
    private var _onDrawerStateChanged: ((Int) -> Unit)? = null

    override fun onDrawerClosed(drawerView: View) {
        _onDrawerClosed?.invoke(drawerView)
    }

    infix fun MADrawerLayoutDrawerListener.onDrawerClosed(action: ((View) -> Unit)?): MADrawerLayoutDrawerListener
        = apply { _onDrawerClosed = action }

    override fun onDrawerOpened(drawerView: View) {
        _onDrawerOpened?.invoke(drawerView)
    }

    infix fun MADrawerLayoutDrawerListener.onDrawerOpened(action: ((View) -> Unit)?)
        = apply { _onDrawerOpened = action }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        _onDrawerSlide?.invoke(drawerView, slideOffset)
    }

    infix fun MADrawerLayoutDrawerListener.onDrawerSlide(action: ((View, Float) -> Unit)?)
        = apply { _onDrawerSlide = action }

    override fun onDrawerStateChanged(newState: Int) {
        _onDrawerStateChanged?.invoke(newState)
    }

    infix fun MADrawerLayoutDrawerListener.onDrawerStateChanged(action: ((Int) -> Unit)?)
        = apply { _onDrawerStateChanged = action }

}

/**
 * Using [listener] for [DrawerLayout.addDrawerListener] instead of regular [DrawerLayout.DrawerListener],
 * for more concise & idiomatic coding isa.
 *
 * @see [MADrawerLayoutDrawerListener]
 */
fun DrawerLayout.addDrawerListenerMA(listener: DrawerLayout_DrawerListener_Typealias?) {
    val genListener = MADrawerLayoutDrawerListener(listener)
    addDrawerListener(genListener)
}
