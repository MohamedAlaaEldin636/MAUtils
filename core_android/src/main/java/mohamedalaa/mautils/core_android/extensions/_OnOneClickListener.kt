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

/**
 * Interface definition for a callback to be invoked when a view is clicked **ONLY** if last click
 * has passed [clickThresholdInMillis] which has a default value of [DEFAULT_CLICK_THRESHOLD_IN_MILLIS] isa.
 *
 * @property clickThresholdInMillis max time in millis which doesn't permit invocation of [onOneClick]
 * except when last click passes it isa.
 */
abstract class OnOneClickListener(
    private var clickThresholdInMillis: Long = DEFAULT_CLICK_THRESHOLD_IN_MILLIS
) : View.OnClickListener {

    companion object {
        /** Constant value of 1_000L which is 1 second isa. */
        private const val DEFAULT_CLICK_THRESHOLD_IN_MILLIS: Long = 1_000 // 1 sec
    }

    private var lastClickTime: Long = 0

    /** Instead override [onOneClick] isa. */
    final override fun onClick(view: View) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastClickTime > clickThresholdInMillis) {
            lastClickTime = currentTime
            onOneClick(view)
        }
    }

    /** Called when a view has been clicked only if satisfies the condition of [clickThresholdInMillis] isa. */
    abstract fun onOneClick(view: View)

}

/**
 * - Uses [View.setOnClickListener] with [OnOneClickListener] where [OnOneClickListener.onOneClick]
 * invokes the given [listener]'s [View.OnClickListener.onClick] function isa.
 * - Also you can use this in XML with `DataBinding` name of attribute is `view_setOnOneClickListener` isa.
 */
@BindingAdapter("view_setOnOneClickListener")
fun View.setOnOneClickListener(listener: View.OnClickListener) {
    setOnClickListener(object : OnOneClickListener() {
        override fun onOneClick(view: View) {
            listener.onClick(view)
        }
    })
}

/**
 * - Uses [View.setOnClickListener] with [OnOneClickListener] where [OnOneClickListener.onOneClick]
 * invokes the given [action] isa.
 *
 * @see setOnOneClickListener
 */
fun View.setOnOneClickListener(action: (View) -> Unit) {
    setOnClickListener(object : OnOneClickListener() {
        override fun onOneClick(view: View) {
            action(view)
        }
    })
}
