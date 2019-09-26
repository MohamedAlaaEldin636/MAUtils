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

import android.widget.CompoundButton
import androidx.core.view.children
import com.google.android.material.chip.Chip
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty

internal fun MAChipsContainer.getAllChips() = children.filterIsInstance<Chip>()

/**
 * ### When to use isa.
 * - Ensure style is [ChipsStyle.Choice] or [ChipsStyle.Filter] only to call this fun isa.
 * - Even if user specifies checked listener ensure calling this fun if possible isa.
 *
 * ### Benefits of usage isa.
 * - Ensures only 1 selection in case of [ChipsStyle.Choice] isa.
 * - Updates [MAChipsContainer.lastCheckedChipsNames] isa.
 */
internal fun MAChipsContainer.onCheckedChangeListenerSetupsForChoiceAndFilterOnly(
    checkedChipCompoundButton: CompoundButton,
    isChecked: Boolean
) {
    // 1 selection only for Choice isa.
    if (chipsStyle == ChipsStyle.Choice) {
        if (isChecked) {
            uncheckOtherChips(checkedChipCompoundButton.text.toStringOrEmpty())
        }else if (children.filterIsInstance<Chip>().all { it.isChecked.not() }) {
            checkedChipCompoundButton.isChecked = true
        }
    }

    // Change if lastCheckedChipsNames is different than current selection isa.
    val currentChipsNames = getCurrentCheckedChipsNames()
    if (currentChipsNames != betweenCurrentAndLastCheckedChipsNames) {
        if (betweenCurrentAndLastCheckedChipsNames != lastCheckedChipsNames) {
            lastCheckedChipsNames = betweenCurrentAndLastCheckedChipsNames
            betweenCurrentAndLastCheckedChipsNames = currentChipsNames
        }else {
            betweenCurrentAndLastCheckedChipsNames = currentChipsNames
        }
    }
}

// ---- Private fun

private fun MAChipsContainer.uncheckOtherChips(recentCheckedChipText: String) {
    for (child in children) {
        if (child is Chip) {
            if (child.text.toStringOrEmpty() != recentCheckedChipText) {
                child.isChecked = false
            }
        }
    }
}
