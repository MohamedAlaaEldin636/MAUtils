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

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @param lifecycleOwner [LifecycleOwner] used to observe the `receiver` value changes isa.
 *
 * @return if [LiveData.getValue] is not-null then it returns it immediately otherwise it suspends
 * until it become non null isa, so **CAUTION** use it if you are sure that after even long time
 * it will be non-null or this will be suspended forever isa.
 *
 * @see observeUnique
 * @see observeOldAndNew
 */
@MainThread
suspend fun <T> LiveData<T>.getNotNullOrSuspendUntilNotNullValue(lifecycleOwner: LifecycleOwner): T {
    return value ?: suspendCoroutine { continuation ->
        val observer = object : Observer<T?> {
            override fun onChanged(value: T?) {
                if (value == null) return

                this@getNotNullOrSuspendUntilNotNullValue.removeObserver(this)

                continuation.resume(value)
            }
        }
        observe(lifecycleOwner, observer)
    }
}

/**
 * - Invokes [onChangeBlock] only if last value isn't the same as the value in [Observer.onChanged] isa.
 *
 * @see observeOldAndNew
 * @see getNotNullOrSuspendUntilNotNullValue
 */
fun <T> LiveData<T>.observeUnique(lifecycleOwner: LifecycleOwner, onChangeBlock: (T?) -> Unit) {
    observe(lifecycleOwner, object : Observer<T?> {
        private var isInitialInvocation: Boolean = true
        private var lastValue: T? = null

        override fun onChanged(it: T?) {
            if (isInitialInvocation || lastValue != it) {
                isInitialInvocation = false

                lastValue = it
                onChangeBlock(it)
            }
        }
    })
}

/**
 * Observe `receiver` only once so [onChangeBlock] is only invoked once followed by [LiveData.removeObserver] isa.
 *
 * @see observeOnceNotNullValue
 * @see observeUnique
 * @see observeOldAndNew
 * @see getNotNullOrSuspendUntilNotNullValue
 */
fun <T> LiveData<T?>.observeOnce(lifecycleOwner: LifecycleOwner, onChangeBlock: (T?) -> Unit) {
    observe(lifecycleOwner, object : Observer<T?> {
        override fun onChanged(it: T?) {
            onChangeBlock(it)

            removeObserver(this)
        }
    })
}

/**
 * Same as [observeOnce] **BUT** only if [Observer.onChanged] has a non-null param otherwise
 * we wait till next invocation and once a non-null value param found then [onChangeBlock] is called
 * then [LiveData.removeObserver] is called immediately after it isa.
 *
 * @see observeUnique
 * @see observeOldAndNew
 * @see getNotNullOrSuspendUntilNotNullValue
 */
fun <T> LiveData<T?>.observeOnceNotNullValue(lifecycleOwner: LifecycleOwner, onChangeBlock: (T) -> Unit) {
    observe(lifecycleOwner, object : Observer<T?> {
        override fun onChanged(it: T?) {
            onChangeBlock(it ?: return)

            removeObserver(this)
        }
    })
}

/**
 * - Same as [LiveData.observe] but instead of given only the value of [Observer.onChanged] fun
 * it as well gives you the value before it, so `old` and `new` values isa.
 * - Note initial value of `old` value will be value of [LiveData.getValue] on invocation of this function isa.
 *
 * @see observeUnique
 * @see getNotNullOrSuspendUntilNotNullValue
 */
fun <T> LiveData<T>.observeOldAndNew(owner: LifecycleOwner, onChangedAction: (old: T?, new: T?) -> Unit) {
    observe(owner, object : Observer<T> {
        private var oldValue: T? = this@observeOldAndNew.value

        @Synchronized
        override fun onChanged(newValue: T?) {
            onChangedAction(oldValue, newValue)

            oldValue = newValue
        }
    })
}
