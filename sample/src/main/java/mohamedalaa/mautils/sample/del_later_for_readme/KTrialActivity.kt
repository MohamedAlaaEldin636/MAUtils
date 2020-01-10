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

package mohamedalaa.mautils.sample.del_later_for_readme

import android.animation.AnimatorSet
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import mohamedalaa.mautils.core_android.extensions.hideKeyboardFrom
import mohamedalaa.mautils.core_android.extensions.showKeyboardFor

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 3/20/2019.
 *
 */
class KTrialActivity : AppCompatActivity() {

    private val context = this
    private lateinit var editText: EditText

    fun f1() {
        // Hide keyboard
        context.hideKeyboardFrom(editText/*, false optional clearFocus param*/)

        // Force Show keyboard
        context.showKeyboardFor(editText/*, false optional requestFocus param*/)
    }

    private lateinit var animatorSet: AnimatorSet

    private fun f2() {
        //val rootViewOfLayout = inflateLayout(R.layout.layout/*, parent optional viewGroup, false optional attachToParent*/)
    }

}