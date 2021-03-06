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

@file:JvmName("DrawableUtils")

package mohamedalaa.mautils.core_android.extensions

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Uses [Drawable.setColorFilter], while [tintCompat] Uses [DrawableCompat],
 * so if the drawable has `colorFilter` use this one instead isa.
 *
 * @param porterDuffMode Tinting mode used, default is [PorterDuff.Mode.DST_ATOP]
 * @param mutate if you want to invoke [Drawable.mutate] before applying [Drawable.setColorFilter],
 * default is false isa.
 *
 * @see [tintCompat]
 */
@JvmOverloads
fun Drawable?.tintColorFilter(@ColorInt color: Int, porterDuffMode: PorterDuff.Mode = PorterDuff.Mode.DST_IN, mutate: Boolean = false) {
    this?.apply {
        if (mutate) {
            this.mutate().colorFilter = PorterDuffColorFilter(color, porterDuffMode)
        }else {
            this.colorFilter = PorterDuffColorFilter(color, porterDuffMode)
        }
    }
}

/**
 * Uses [DrawableCompat], while [tintColorFilter] Uses [Drawable.setColorFilter],
 * so if the drawable has colorFilter use [tintColorFilter] instead isa.
 *
 * @see tintColorFilter
 */
fun Drawable?.tintCompat(@ColorInt color: Int, mode: PorterDuff.Mode? = null) {
    if (this == null) return
    mode?.apply { DrawableCompat.setTintMode(this@tintCompat, this) }
    DrawableCompat.setTint(this, color)
}
