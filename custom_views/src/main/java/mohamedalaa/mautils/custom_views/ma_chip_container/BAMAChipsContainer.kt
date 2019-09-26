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
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import mohamedalaa.mautils.core_kotlin.extensions.throwRuntimeException
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty

/**
 * - Sets a listener that will be invoked when any of the chips changes it's checked state,
 * **Also note** it will be invoked once in case of Choice change of selection and won't be invoked
 * in case of Choice clicking on currently checked chip isa.
 *
 * - In case of Choice style it only notifies when checked is true, not when it is false isa.
 *
 * @see MAChipsContainer.title
 * @see MAChipsContainer.getLastCheckedChipsNames
 * @see setCurrentCheckedChipsNames
 */
@BindingAdapter("app:maChipsContainer_setOnChipCheckedChangeListener")
fun MAChipsContainer.setOnChipCheckedChangeListener(
    action: (maChipsContainer: MAChipsContainer, changedChipName: String, newValueIsChecked: Boolean) -> Unit
) {
    onChipCheckedChangeListener = object : MAChipsContainer.OnChipCheckedChangeListener {
        override fun afterChange(
            maChipsContainer: MAChipsContainer,
            changedChipName: String,
            newValueIsChecked: Boolean
        ) {
            action(maChipsContainer, changedChipName, newValueIsChecked)
        }
    }

    setCurrentCheckedChipsNamesAttrChanged(inverseBindingListener)
}

/**
 * Can be used as two-way data binding isa, See [getCurrentCheckedChipsNames]
 *
 * @see MAChipsContainer.setAllChipsAsChecked
 */
@BindingAdapter("app:checkedChipsNames")
fun MAChipsContainer.setCurrentCheckedChipsNames(names: List<String>) {
    // Only 1 selection for Choice isa.
    if (chipsStyle == ChipsStyle.Choice && names.size != 1) {
        throwRuntimeException("cannot use setCurrentCheckedChipsNames with style Choice EXCEPT with " +
            "1 string in the fun param of type List<String> isa.")
    }

    // Check infinite loop
    if (getCurrentCheckedChipsNames() != names) {
        for (chip in getAllChips()) {
            chip.isChecked = chip.text.toStringOrEmpty() in names
        }
    }
}

/**
 * Can be used as two-way data binding isa, See [getCurrentCheckedChipsNames]
 */
@BindingAdapter("app:checkedChipsNamesAttrChanged")
internal fun MAChipsContainer.setCurrentCheckedChipsNamesAttrChanged(inverseBindingListener: InverseBindingListener?) {
    // Listener
    this.inverseBindingListener = inverseBindingListener
    val listener = CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
        if (forceNotInvokeCheckedChangeListener) return@OnCheckedChangeListener

        val noChange = chipsStyle == ChipsStyle.Choice && isChecked.not()

        if (chipsStyle == ChipsStyle.Choice || chipsStyle == ChipsStyle.Filter) {
            onCheckedChangeListenerSetupsForChoiceAndFilterOnly(compoundButton, isChecked)
        }

        if (noChange.not()) {
            onChipCheckedChangeListener?.afterChange(this, compoundButton.text.toStringOrEmpty(), isChecked)
            inverseBindingListener?.onChange()
        }
    }

    // Setting the listener isa.
    for (chip in getAllChips()) {
        chip.setOnCheckedChangeListener(listener)
    }
}

/** @see setCurrentCheckedChipsNames */
@InverseBindingAdapter(attribute = "app:checkedChipsNames", event = "app:checkedChipsNamesAttrChanged")
fun MAChipsContainer.getCurrentCheckedChipsNames(): List<String> {
    return getAllChips().mapNotNull {
        if (it.isChecked) {
            it.text.toStringOrEmpty()
        }else {
            null
        }
    }.toList()
}
