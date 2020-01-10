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

package mohamedalaa.mautils.core_android.extensions

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * - Creates [DF] with the no-args constructor then adds [args] to it using [instanceWithArg] then
 * invokes [DialogFragment.show] with the given [fragmentManager] isa.
 * - Tag provided is the [DF]'s class [Class.getSimpleName] + [dialogFragmentTagSuffix] isa.
 *
 * @param DF [DialogFragment] class isa.
 *
 * @param fragmentManager manager to launch the [DialogFragment] isa.
 * @param args bundle to be set to [DF] via [DialogFragment.setArguments] isa.
 *
 * @see FragmentActivity.showDialogFragment
 */
inline fun <reified DF : DialogFragment> Fragment.showDialogFragment(
    fragmentManager: FragmentManager = childFragmentManager,
    args: Bundle? = null
) {
    DF::class.java.newInstance().instanceWithArg(args).show(
        fragmentManager, DF::class.java.simpleName + dialogFragmentTagSuffix
    )
}

/**
 * Same as [Fragment.showDialogFragment] but for [FragmentActivity] as a `receiver` isa.
 */
inline fun <reified DF : DialogFragment> FragmentActivity.showDialogFragment(
    fragmentManager: FragmentManager = supportFragmentManager,
    args: Bundle? = null
) {
    DF::class.java.newInstance().instanceWithArg(args).show(
        fragmentManager, DF::class.java.simpleName + dialogFragmentTagSuffix
    )
}

/**
 * - Invokes [DialogFragment.show] with Tag [DF]'s class [Class.getSimpleName] + [dialogFragmentTagSuffix] isa,
 * So more concise code for showing the [DF] in case you don't care about the tag isa,
 * Think of it as an overloaded function to [DialogFragment.show] isa.
 */
inline fun <reified DF : DialogFragment> DF.show(fragmentManager: FragmentManager) {
    show(fragmentManager, DF::class.java.simpleName + dialogFragmentTagSuffix)
}

/** const string with value -> "_DialogFragmentTag" */
@PublishedApi
internal const val dialogFragmentTagSuffix = "_DialogFragmentTag"
