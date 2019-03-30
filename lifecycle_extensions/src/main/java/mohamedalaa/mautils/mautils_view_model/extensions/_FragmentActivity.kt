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

@file:JvmName("FragmentActivityUtils")

package mohamedalaa.mautils.mautils_view_model.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Same as [ViewModelProviders.of] `receiver` then [ViewModelProvider.get]<[VM]>
 * but is more simplified version of it isa.
 *
 * @see [Fragment.getViewModel]
 */
inline fun <reified VM : ViewModel> FragmentActivity.getViewModel()
    = ViewModelProviders.of(this).get(VM::class.java)
