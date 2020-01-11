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

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import mohamedalaa.mautils.core_android.R
import mohamedalaa.mautils.core_android.data_binding.binding_adapter.internal.TextWatcherImpl
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty

object BAEditText {

    /**
     * Used as [InverseBindingAdapter] for [setTextTwoWay] isa.
     */
    @JvmStatic
    @InverseBindingAdapter(attribute = "editText_textTwoWay", event = "editText_textTwoWayAttrChanged")
    fun getTextTwoWay(editText: EditText): CharSequence? {
        return editText.text
    }

    /**
     * ### This approach ensures 2 important things when using two-way data binding for EditText's text isa.
     * 1. In case of a dynamic change the observer will be invoked once only.
     * 2. On any change you can distinguish between if it is a user change or a dynamic change (opt-in feature).
     *
     * ### How to use
     * In Xml
     * ```
     * editText_textTwoWay="@={viewModel.mutableLiveDataEditText1}"
     * editText_textTwoWay_enableDistinguishChangeCauser="@{true}"
     * ```
     * Then to observe
     * ```
     * viewModel.mutableLiveDataEditText1.observe(this, Observer {
     *      val isUserInputChangeNotDynamicallyChanged = editText.getTag(
     *          R.id.editText_textTwoWay_isUserInputChangeNotDynamicallyChanged
     *      ) as? Boolean
     *      val toastMsg = when (isUserInputChangeNotDynamicallyChanged) {
     *          true -> {
     *              // changed by user isa.
     *              "User Change"
     *          }
     *          false -> {
     *              // changed dynamically isa.
     *              "Dynamic Change"
     *          }
     *          null -> {
     *              // If you did opt-in, Then should never happen, but just in case, Handle it as you want here isa.
     *              "UNKNOWN Change"
     *          }
     *      }
     *
     *      toast(toastMsg) // Same as Toast(...).show()
     * })
     * ```
     *
     * ### Why this approach
     * - In case of using regular approach android:text=@={viewModel.liveDataEditTextValue},
     *
     * And to observe changes you used in you activity `viewModel.liveDataEditTextValue.observe(lifecycleOwner, observerBlock)`
     *
     * Then instead of user changing the value by typing on keyboard what If the you(developer) wants
     * to make a change by using viewModel.liveDataEditTextValue.value = "Some Value",
     *
     * Then observerBlock will be triggered twice, surely you can at that point check last value and
     * ignore this one, But what if you care about changing same value then you will never know
     *
     * And even more critical case what if you want at this point to know whether it is a change made
     * by the user OR by you so how to know that too,
     *
     * Here is why we use the above approach instead
     * ```
     * editText_textTwoWay="@={viewModel.liveDataEditTextValue}"
     * editText_textTwoWay_enableDistinguishChangeCauser="@{true}"
     * ```
     *
     * - [enableDistinguishChangeCauser] is made opt-in Since No need to add a tag to a `View`
     * if it won't be used isa.
     *
     * ### CAUTION
     * - You should never change the tag of [editText] with the key [R.id.editText_textTwoWay_isUserInputChangeNotDynamicallyChanged]
     * it is just intended to be used as [EditText.getTag] only isa.
     */
    @JvmStatic
    @BindingAdapter(
        "editText_textTwoWay",
        "editText_textTwoWayAttrChanged",

        "editText_textTwoWay_enableDistinguishChangeCauser",

        requireAll = false
    )
    fun setTextTwoWay(
        editText: EditText,

        textTwoWay: CharSequence?,
        inverseBindingListener: InverseBindingListener?,

        enableDistinguishChangeCauser: Boolean?
    ) {
        // Listener
        val currentTextTwoWay: String = textTwoWay.toStringOrEmpty()
        val listener = TextWatcherImpl(
            editText,
            currentTextTwoWay,
            inverseBindingListener,
            enableDistinguishChangeCauser ?: false
        )

        val oldListener = ListenerUtil.trackListener(editText, listener, editText.id)
        if (oldListener != null) {
            editText.removeTextChangedListener(oldListener)
        }
        editText.addTextChangedListener(listener)

        val editTextCurrentText: String = editText.text.toStringOrEmpty()
        if (editTextCurrentText == currentTextTwoWay) {
            return
        }

        // Let listener knows we are about to call `setText()` isa.
        listener.isChangedFromBindingAdapterAnnotation = true
        editText.setText(currentTextTwoWay)
    }

}
