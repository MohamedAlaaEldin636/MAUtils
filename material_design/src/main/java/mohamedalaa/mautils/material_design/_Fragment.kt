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

@file:JvmName("FragmentUtils")

package mohamedalaa.mautils.material_design

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import mohamedalaa.mautils.core_android.buildBundle
import mohamedalaa.mautils.gson.buildBundleGson
import mohamedalaa.mautils.gson.buildBundleGsonForced
import mohamedalaa.mautils.material_design.dependencies_runtime_checks.moduleGsonExists

fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return

    val fragmentView = view ?: return

    val rootView = fragmentView.rootView
    imm.hideSoftInputFromWindow(rootView.windowToken, 0)
}

fun <F : Fragment> F.instanceWithArg(bundle: Bundle?): F
    = apply { arguments = bundle }

/**
 * to retrieve values see below Ex. for clarification isa
 * ```
 * fragment.arguments?.apply {
 *      val getterBundle = getterBundle()
 *      // ...
 * }
 * ```
 */
fun <F : Fragment> F.instanceWithArgBundle(vararg values: Any?): F
    = apply { arguments = buildBundle(*values) }

/**
 * **Warning**
 * Only works if you have implementation 'com.github.MohamedAlaaEldin636.MAUtils:gson:$mautils_version',
 * otherwise nothing will be put in [Fragment.setArguments] isa.
 */
fun <F : Fragment> F.instanceWithArgBundleGson(vararg values: Any?): F = apply {
    if (moduleGsonExists()) {
        arguments = buildBundleGson(*values)
    }
}

/**
 * **Warning**
 * Only works if you have implementation 'com.github.MohamedAlaaEldin636.MAUtils:gson:$mautils_version',
 * otherwise nothing will be put in [Fragment.setArguments] isa.
 */
fun <F : Fragment> F.instanceWithArgBundleGsonForced(vararg values: Any?): F = apply {
    if (moduleGsonExists()) {
        arguments = buildBundleGsonForced(*values)
    }
}
