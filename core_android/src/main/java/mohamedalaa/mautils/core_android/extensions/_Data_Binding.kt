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

@file:JvmName("DataBindingUtils")

package mohamedalaa.mautils.core_android.extensions

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * - Retrieves the binding responsible for the given `receiver`, If `receiver` is not a binding layout root,
 * its parents will be searched for the binding. If there is no binding, `null` will be returned.
 */
fun <VDB : ViewDataBinding> View.findBindingOrNull(): VDB?
    = DataBindingUtil.findBinding(this)

/** Same as [findBindingOrNull] but instead of returning `null` [RuntimeException] is thrown instead isa. */
fun <VDB : ViewDataBinding> View.findBinding(): VDB
    = findBindingOrNull() ?: throw RuntimeException("Cannot find corresponding view data binding.")
