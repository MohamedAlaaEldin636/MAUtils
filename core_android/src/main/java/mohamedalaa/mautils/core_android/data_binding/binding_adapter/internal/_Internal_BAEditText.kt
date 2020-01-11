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

package mohamedalaa.mautils.core_android.data_binding.binding_adapter.internal

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import mohamedalaa.mautils.core_android.R
import mohamedalaa.mautils.core_android.data_binding.binding_adapter.BAEditText
import mohamedalaa.mautils.core_android.data_binding.binding_adapter.BAEditText.setTextTwoWay
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import java.lang.ref.WeakReference

/**
 * ### Usage
 * - Used to ensure [setTextTwoWay] to happen as intended isa.
 *
 * ### Requirement
 * 1. [BindingAdapter] fun that uses this class should set [isChangedFromBindingAdapterAnnotation]
 * to true before calling [EditText.setText] isa.
 * 2. Should only by used by [BAEditText.setTextTwoWay] isa.
 */
internal class TextWatcherImpl(
    editText: EditText,
    currentTextTwoWay: String,
    private val inverseBindingListener: InverseBindingListener?,
    private val enableDistinguishChangeCauser: Boolean
) : TextWatcher {

    private val weakRefEditText: WeakReference<EditText>? = if (enableDistinguishChangeCauser) {
        WeakReference(editText)
    }else {
        null
    }

    private var observerValue: String = currentTextTwoWay

    private var isChangedProgrammatically: Boolean = false

    var isChangedFromBindingAdapterAnnotation = false

    init {
        if (enableDistinguishChangeCauser) {
            editText.setTag(
                R.id.editText_textTwoWay_isUserInputChangeNotDynamicallyChanged,
                false
            )
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val paramValue: String = s.toStringOrEmpty()
        isChangedProgrammatically = paramValue == observerValue

        observerValue = paramValue
    }

    override fun afterTextChanged(s: Editable?) {
        if (isChangedProgrammatically.not()) {
            inverseBindingListener?.apply {
                if (enableDistinguishChangeCauser.not()) {
                    onChange()

                    return
                }

                // Changed from xml to `different` value isa.
                weakRefEditText?.get()?.setTag(
                    R.id.editText_textTwoWay_isUserInputChangeNotDynamicallyChanged,
                    true
                )

                onChange()

                weakRefEditText?.get()?.setTag(
                    R.id.editText_textTwoWay_isUserInputChangeNotDynamicallyChanged,
                    false
                )
            }
        }else {
            if (isChangedFromBindingAdapterAnnotation) {
                isChangedFromBindingAdapterAnnotation = false
            }else {
                if (enableDistinguishChangeCauser.not()) {
                    inverseBindingListener?.onChange()

                    return
                }

                // Changed from xml to `same` value isa.
                weakRefEditText?.get()?.setTag(
                    R.id.editText_textTwoWay_isUserInputChangeNotDynamicallyChanged,
                    true
                )

                inverseBindingListener?.onChange()

                weakRefEditText?.get()?.setTag(
                    R.id.editText_textTwoWay_isUserInputChangeNotDynamicallyChanged,
                    false
                )
            }
        }
    }
}
