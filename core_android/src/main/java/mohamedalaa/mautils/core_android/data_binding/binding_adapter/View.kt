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

package mohamedalaa.mautils.core_android.data_binding.binding_adapter

import android.graphics.PorterDuff
import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import mohamedalaa.mautils.core_android.setBackgroundTint

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 4/1/2019.
 *
 */
object View {

    @JvmStatic
    @BindingAdapter("app:view_setBackgroundTintColor")
    fun setBackgroundTintColor(view: View, @ColorInt color: Int?) {
        color?.apply { view.setBackgroundTint(color) }
    }

    @JvmStatic
    @BindingAdapter("app:view_setBackgroundTintPorterDuffMode")
    fun setBackgroundTintPorterDuffMode(view: View, porterDuffMode: PorterDuff.Mode?) {
        porterDuffMode?.apply { view.setBackgroundTint(porterDuffMode = porterDuffMode) }
    }

}