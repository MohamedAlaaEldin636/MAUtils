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

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import mohamedalaa.mautils.core_android.extensions.logError
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 1/10/2020.
 *
 */
object BAEditText {

    @JvmStatic
    @InverseBindingAdapter(attribute = "editText_textTwoWay", event = "editText_textTwoWayAttrChanged")
    fun getTextTwoWay(editText: EditText): CharSequence? {
        return editText.text.apply {
            logError("getTextTwoWay() is invoked el7.\neditText.text $this")
        }
    }

    // implement InverseBindingListener msln kda isa.
    @JvmStatic
    @BindingAdapter("editText_textTwoWay", "editText_textTwoWayAttrChanged", requireAll = false)
    fun setTextTwoWay(editText: EditText, textTwoWay: CharSequence?, inverseBindingListener: InverseBindingListener?) {
        // Listener
        val currentTextTwoWay: String = textTwoWay.toStringOrEmpty()
        val listener = TextWatcherImpl(
            currentTextTwoWay,
            inverseBindingListener
        )

        val oldListener = ListenerUtil.trackListener(editText, listener, editText.id)
        if (oldListener != null) {
            editText.removeTextChangedListener(oldListener)
        }
        editText.addTextChangedListener(listener)

        val editTextCurrentText: String = editText.text.toStringOrEmpty()
        logError("setTextTwoWay() is invoked el7. ${editTextCurrentText == currentTextTwoWay}, $editTextCurrentText == $currentTextTwoWay") // todo log also in onChange of vm isa.
        if (editTextCurrentText == currentTextTwoWay) {
            return
        }

        // todo called it here isa.
        listener.isChangedFromBindingAdapterAnnotation = true
        editText.setText(currentTextTwoWay)
    }

    internal class TextWatcherImpl(
        currentTextTwoWay: String,
        private val inverseBindingListener: InverseBindingListener?
    ) : TextWatcher {

        private var observerValue: String = currentTextTwoWay

        // todo might solve was programmatic or from xml
        // but still how to remove the twice invoke da isa ?!
        private var isChangedProgrammatically: Boolean = false

        // todo call this one isa.
        var isChangedFromBindingAdapterAnnotation = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val paramValue: String = s.toStringOrEmpty()
            isChangedProgrammatically = paramValue == observerValue

            observerValue = paramValue
        }

        override fun afterTextChanged(s: Editable?) {
            // only prob if in case programmatically change SAME value then no change happen isa.
            // another is change same value xml isa -> cuz then it is considered programmatically isa.

            // -> maybe consider the getValue if has anything to do with it isa... and communicate via
            // special tag with special id isa...
            // todo also see kdoc of inversebindginadapter and see 7war el ObserverField<> da kda isa.
            // AND INVERSE BINDING LISTENER AS WELL ISA.
            if (isChangedProgrammatically.not()) {
                inverseBindingListener?.apply {
                    logError("InverseBindingListener.onChange() is invoked el7.\ns -> $s")
                    onChange()
                }
            }else {
                if (isChangedFromBindingAdapterAnnotation) {
                    isChangedFromBindingAdapterAnnotation = false
                }else {
                    // track log first isa.
                    logError("Changed from xml by same value so should call onChane() isa")

                    // changed from xml by same value isa....

                    // 1. having weak ref of edit text isa.
                    // 2. before this and above on change,
                    //      setTag of it is an action by programmatically or by xml isa. TODO this isa.
                    // and maybe add in xml a way of enabling it so that if false
                    // no need to set tag msh da meory da wala eh isa.
                    inverseBindingListener?.onChange()
                }
            }
        }
    }

}
