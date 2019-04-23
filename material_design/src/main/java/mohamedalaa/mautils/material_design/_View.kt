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

@file:JvmName("ViewUtils")

package mohamedalaa.mautils.material_design

import android.view.View
import androidx.appcompat.widget.TooltipCompat

/**
 * Exactly as [TooltipCompat.setTooltipText] but ext fun
 */
fun View.setTooltipTextCompat(text: CharSequence?)
    = TooltipCompat.setTooltipText(this, text)

/** set only 1 padding border instead of using [View.setPadding] */
var View.paddingLeftCompat
    get() = paddingLeft
    set(value) = setPadding(value, paddingTop, paddingRight, paddingBottom)

/** set only 1 padding border instead of using [View.setPadding] */
var View.paddingRightCompat
    get() = paddingRight
    set(value) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

/** set only 1 padding border instead of using [View.setPadding] */
var View.paddingTopCompat
    get() = paddingTop
    set(value) = setPadding(paddingLeft, value, paddingRight, paddingBottom)

/** set only 1 padding border instead of using [View.setPadding] */
var View.paddingBottomCompat
    get() = paddingBottom
    set(value) = setPadding(paddingLeft, paddingTop, paddingRight, value)