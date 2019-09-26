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

package mohamedalaa.mautils.custom_views.ma_chip_container

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import mohamedalaa.mautils.custom_views.R

/** Setting to null will change visibility to gone, and not null makes it visible isa. */
@BindingAdapter("app:title")
fun MAChipsContainer.setTitle(title: CharSequence?) {
    val titleTextView = findViewById<TextView>(R.id.ma_chips_container_title) ?: return

    titleTextView.isVisible = title != null
    titleTextView.text = title

    this.title = title
}

/** Setting to null will change visibility to gone, and not null makes it visible isa. */
@BindingAdapter("app:subtitle")
fun MAChipsContainer.setSubtitle(subtitle: CharSequence?) {
    val subTitleTextView = findViewById<TextView>(R.id.ma_chips_container_subtitle) ?: return

    subTitleTextView.isVisible = subtitle != null
    subTitleTextView.text = subtitle

    this.subtitle = subtitle
}
