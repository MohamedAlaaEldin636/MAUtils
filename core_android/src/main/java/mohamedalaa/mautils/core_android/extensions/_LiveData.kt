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
