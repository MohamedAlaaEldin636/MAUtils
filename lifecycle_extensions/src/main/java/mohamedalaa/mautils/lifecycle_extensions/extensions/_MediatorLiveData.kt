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

@file:JvmName("MediatorLiveDataUtils")

package mohamedalaa.mautils.lifecycle_extensions.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Observes the given [LiveData] and whenever a change occur to it, the [block]
 *
 * fun will be executed If [MediatorLiveData.getValue] is not null,
 *
 * Then [MediatorLiveData.setValue] will be used so if there was a change in [block] for [T]
 *
 * a refresh will be sent to `receiver`'s observer isa.
 *
 * - **Notes**
 * 1. you can change data of mediator object according to change of liveDate with the [block] isa.
 * 2. If `receiver`'s value got from [MediatorLiveData.getValue] is null then no change
 * will happen, and the block won't be executed isa.
 *
 * - **Example**
 * ```
 * data class Person(val name: String, val age: Int)
 *
 * // Inside yourViewModel.kt
 * personMediatorLiveData.addSourceAndSetSafely(nameLiveData) {
 *      this.name = it
 * }
 * personMediatorLiveData.addSourceAndSetSafely(ageLiveData) {
 *      age = it
 * }
 * // Now Just observe personMediatorLiveData in activity and whenever age or name live data changes
 * // the new value of person live data will be sent to the activity isa.
 * ```
 */
fun <T, E> MediatorLiveData<T>.addSourceAndSet(source: LiveData<E>, block: T.(E) -> Unit) {
    addSource(source) {
        this.value?.apply {
            this@addSourceAndSet.value = apply { block(this, it) }
        }
    }
}