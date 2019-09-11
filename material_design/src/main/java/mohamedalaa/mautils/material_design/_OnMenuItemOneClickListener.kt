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

import android.os.SystemClock
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

abstract class OnMenuItemOneClickListener : Toolbar.OnMenuItemClickListener {

    private companion object {
        private const val CLICK_THRESHOLD: Long = 1_000
    }

    private var lastClickTime: Long = 0

    final override fun onMenuItemClick(item: MenuItem?): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime > CLICK_THRESHOLD) {
            lastClickTime = currentTime
            return onMenuItemOneClick(item)
        }

        return false
    }

    /**
     * This method will be invoked when a menu item is clicked if the item itself did
     * not already handle the event.
     *
     * @param item [MenuItem] that was clicked
     * @return `true` if the event was handled, `false` otherwise.
     */
    abstract fun onMenuItemOneClick(item: MenuItem?): Boolean

}

@BindingAdapter("android:toolbar_setOnMenuItemOneClickListener")
fun Toolbar.setOnMenuItemOneClickListener(listener: Toolbar.OnMenuItemClickListener) {
    setOnMenuItemClickListener(object : OnMenuItemOneClickListener() {
        override fun onMenuItemOneClick(item: MenuItem?): Boolean
            = listener.onMenuItemClick(item)
    })
}

/**
 * @see OnMenuItemOneClickListener.onMenuItemOneClick
 */
fun Toolbar.setOnMenuItemOneClickListener(action: (MenuItem?) -> Boolean) {
    setOnMenuItemClickListener(object : OnMenuItemOneClickListener() {
        override fun onMenuItemOneClick(item: MenuItem?): Boolean
            = action(item)
    })
}

fun <T> T.buildOnMenuItemOneClickListener(action: T.(MenuItem?) -> Boolean) = object : OnMenuItemOneClickListener() {
    override fun onMenuItemOneClick(item: MenuItem?): Boolean = action(item)
}
