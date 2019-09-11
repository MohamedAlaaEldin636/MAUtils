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

inline fun <reified DF : DialogFragment> Fragment.showDialogFragment(
    fragmentManager: FragmentManager = childFragmentManager,
    args: Bundle? = null
) {
    DF::class.java.newInstance().instanceWithArg(args).show(
        fragmentManager, DF::class.java.simpleName + dialogFragmentTagSuffix
    )
}

inline fun <reified DF : DialogFragment> FragmentActivity.showDialogFragment(
    fragmentManager: FragmentManager = supportFragmentManager,
    args: Bundle? = null
) {
    DF::class.java.newInstance().instanceWithArg(args).show(
        fragmentManager, DF::class.java.simpleName + dialogFragmentTagSuffix
    )
}

inline fun <reified DF : DialogFragment> DF.show(fragmentManager: FragmentManager) {
    show(fragmentManager, DF::class.java.simpleName + dialogFragmentTagSuffix)
}

@PublishedApi
internal val dialogFragmentTagSuffix = "_DialogFragmentTag"
