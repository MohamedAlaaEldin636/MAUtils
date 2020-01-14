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

package mohamedalaa.mautils.sample.core_kotlin

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import mohamedalaa.mautils.core_android.custom_classes.character_style.DrawableSpan
import mohamedalaa.mautils.core_android.custom_classes.dialog_fragments.MADialogFragment
import mohamedalaa.mautils.core_android.custom_classes.dialog_fragments.MALoadingDialogFragment
import mohamedalaa.mautils.core_android.extensions.*
import mohamedalaa.mautils.core_kotlin.extensions.contains
import mohamedalaa.mautils.core_kotlin.extensions.format
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import org.junit.Test

class MainEntry {

    private abstract class AbstractActivity : AppCompatActivity() {
        override fun onSaveInstanceState(bundle: Bundle) {
            bundle.addValues(
                33
            )

            val getterBundle = bundle.getterBundle()
            val int = getterBundle.get<Int>()
            val string = getterBundle.get<String>()
            val boolean = getterBundle.get<Boolean>()

            bundle.addValuesWithKeys(
                "key1" to 32,
                "key2" to true // etc...
            )

            super.onSaveInstanceState(bundle)
            // hh thhty
        }
    }

    @Test
    fun f1(
        spannable: Spannable,
        drawable: Drawable,
        activity: AppCompatActivity,
        viewModel: AndroidViewModel
    ) = activity.performIfNotNull {
        spannable[0..4] = DrawableSpan(drawable)

        buildBundle(
            33
        ).apply {
            //putString()
        }

        val loadingDialog = MALoadingDialogFragment()
        loadingDialog.show(supportFragmentManager)
        // do some work
        loadingDialog.dismiss()

        // also note in case you wanna use a code that needs to run in the background
        // and/or run a suspend function and auto dismiss when done checkout the below extension fun
        viewModel.beforeAndAfterDismissInLoadingDialog(supportFragmentManager, Dispatchers.IO, before = {
            // code while showing the loading dialog with CoroutineScope.
        }, after = {
            // code after the dismiss of the dialog runs on Dispatchers.MAIN.
        })

    }

    private class SomeDialogFragment : MADialogFragment(
        widthIsMatchPrent = true,
        heightIsMatchPrent = false,
        windowGravity = Gravity.CENTER,
        canceledOnTouchOutside = false
    )

    private fun mustReturnUnitAndWantLessPossibleBrackets(int: Int?) = int.performIfNotNull { // this: Int
        // ...
    }

    private fun abc() {
        val list: List<Int>? = null
        if (3 in list) {

        }
        System.currentTimeMillis().format(
            "d MMM, yyyy"
        )
    }

}
