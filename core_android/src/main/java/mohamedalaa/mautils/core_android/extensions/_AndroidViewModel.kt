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

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import mohamedalaa.mautils.core_android.custom_classes.dialog_fragments.MALoadingDialogFragment
import mohamedalaa.mautils.core_kotlin.extensions.delayFramePerSecond
import kotlin.coroutines.CoroutineContext

/**
 * - Launches [MALoadingDialogFragment] then call [before] with [coroutineContext], then dismiss [MALoadingDialogFragment]
 * then call [after] with [AndroidViewModel.viewModelScope] isa.
 *
 * - Perfect for usage of saving some data and you want to make user wait for the save to be finished
 * without interrupting or interacting with the application isa.
 *
 * @param fragmentManager [FragmentManager] to launch [MALoadingDialogFragment] isa.
 * @param coroutineContext [CoroutineContext] in which [before] block will run inside.
 * @param before block running while [MALoadingDialogFragment] is shown.
 * @param after block that runs after [MALoadingDialogFragment] is dismissed isa.
 */
fun <R> AndroidViewModel.beforeAndAfterDismissInLoadingDialog(
    fragmentManager: FragmentManager,
    coroutineContext: CoroutineContext = Dispatchers.Main,
    before: suspend CoroutineScope.() -> R?,
    after: suspend CoroutineScope.(R?) -> Unit
) {
    viewModelScope.launch {
        // Show dialog loading isa.
        val loadingFragment = MALoadingDialogFragment()
        loadingFragment.show(fragmentManager)

        val result: R? = withContext(coroutineContext) {
            before()
        }

        delayFramePerSecond()

        // dismiss loading dialog
        loadingFragment.dismiss()

        delayFramePerSecond()

        after(result)
    }
}

/** Same as [AndroidViewModel.getApplication] but used as property for more concise code isa. */
val AndroidViewModel.application: Application
    @JvmSynthetic get() = getApplication()

/** Same as [Context.getString] exists only for more concise code isa. */
@JvmSynthetic
fun AndroidViewModel.getString(@StringRes res: Int): String = application.getString(res)

/** Same as [Context.getString] exists only for more concise code isa. */
@JvmSynthetic
fun AndroidViewModel.getString(@StringRes res: Int, vararg args: Any): String = application.getString(res, *args)
