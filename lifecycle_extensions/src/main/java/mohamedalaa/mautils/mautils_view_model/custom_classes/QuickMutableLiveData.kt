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

package mohamedalaa.mautils.mautils_view_model.custom_classes

import androidx.lifecycle.MutableLiveData

/**
 * more concise way to initialize [MutableLiveData] with an initial value
 *
 * **Example**
 * ```
 * // Instead of below code
 * val StringMutableLiveData = MutableLiveData<String>().apply { this.value = "Initial Value" }
 * // Use below one for initial values isa.
 * val StringQuickMutableLiveData = QuickMutableLiveData("Initial Value")
 * ```
 */
class QuickMutableLiveData<T>(value: T? = null): MutableLiveData<T>() {
    init {
        this.value = value
    }
}