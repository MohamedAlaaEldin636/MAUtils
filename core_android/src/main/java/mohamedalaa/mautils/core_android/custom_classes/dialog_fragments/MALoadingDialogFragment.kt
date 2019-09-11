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

package mohamedalaa.mautils.core_android.custom_classes.dialog_fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.lifecycle.AndroidViewModel
import mohamedalaa.mautils.core_android.R
import mohamedalaa.mautils.core_android.extensions.*

/**
 * Shows loading [ProgressBar] until you dismiss the dialog isa.
 *
 * @see AndroidViewModel.beforeAndAfterDismissInLoadingDialog
 */
class MALoadingDialogFragment(
    @ColorInt private var backgroundColor: Int? = null,
    private var tryingToDismissToastMsg: String? = null,
    @StyleRes private var styleRes: Int = 0
) : MADialogFragment(heightIsMatchPrent = true, canceledOnTouchOutside = false) {

    internal companion object {
        private val PREFIX: String = MALoadingDialogFragment::class.java.name

        private val KEY_BACKGROUND_COLOR = "${PREFIX}_" + "KEY_BACKGROUND_COLOR"
        private val KEY_TRYING_TO_DISMISS_TOAST_MSG = "${PREFIX}_" + "KEY_TRYING_TO_DISMISS_TOAST_MSG"
        private val KEY_STYLE_RES = "${PREFIX}_" + "KEY_STYLE_RES"
    }

    init {
        arguments = (arguments ?: Bundle()).apply {
            addValuesWithKeys(
                KEY_BACKGROUND_COLOR to backgroundColor,
                KEY_TRYING_TO_DISMISS_TOAST_MSG to tryingToDismissToastMsg,
                KEY_STYLE_RES to styleRes
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.apply {
            backgroundColor = getOrNull(KEY_TRYING_TO_DISMISS_TOAST_MSG)
            tryingToDismissToastMsg = getOrNull(KEY_TRYING_TO_DISMISS_TOAST_MSG)
            styleRes = getOrNull(KEY_TRYING_TO_DISMISS_TOAST_MSG) ?: 0
        }

        if (styleRes != 0) {
            setStyle(STYLE_NORMAL, styleRes)
        }
    }

    override fun Window.onCreateDialogWindowChanges() {
        val backgroundColor = (backgroundColor ?: (context?.getColorFromRes(R.color.black_alpha_25)) ?: Color.parseColor("#40000000"))
        setBackgroundDrawable(backgroundColor.toDrawable())
    }

    override fun onBackPressed() {
        context?.toast(tryingToDismissToastMsg ?: getString(R.string.loading_please_wait), duration = Toast.LENGTH_LONG)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_loading, container, false)
    }

}

