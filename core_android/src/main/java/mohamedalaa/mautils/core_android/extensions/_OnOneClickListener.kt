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

import android.view.View
import android.os.SystemClock
import androidx.databinding.BindingAdapter

abstract class OnOneClickListener : View.OnClickListener {

    companion object {
        private const val CLICK_THRESHOLD: Long = 1_000

        fun create(action: View.() -> Unit): OnOneClickListener = object : OnOneClickListener() {
            override fun onOneClick(view: View) {
                view.action()
            }
        }
    }

    private var lastClickTime: Long = 0

    final override fun onClick(view: View) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime > CLICK_THRESHOLD) {
            lastClickTime = currentTime
            onOneClick(view)
        }
    }

    abstract fun onOneClick(view: View)

}

@BindingAdapter("android:view_setOnOneClickListener")
fun View.setOnOneClickListener(listener: View.OnClickListener) {
    setOnClickListener(object : OnOneClickListener() {
        override fun onOneClick(view: View) {
            listener.onClick(view)
        }
    })
}

fun View.setOnOneClickListener(action: (View) -> Unit) {
    setOnClickListener(object : OnOneClickListener() {
        override fun onOneClick(view: View) {
            action(view)
        }
    })
}

fun <T> T.buildOnOneClickListener(action: T.(View) -> Unit) = object : OnOneClickListener() {
    override fun onOneClick(view: View) = action(view)
}
