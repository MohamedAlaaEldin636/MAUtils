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

package mohamedalaa.mautils.recycler_view.custom_classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import mohamedalaa.mautils.recycler_view.custom_classes.internal.MABaseRVAdapter

/**
 * Same as [MARVAdapterDB] but with change in being able to override [getLayoutRes] to provide
 * as many item view types as you want.
 *
 * [ViewDataBinding] is the param provided in [onBindViewHolder], so make your check
 * in that fun to see to which layout res it corresponds to.
 */
abstract class MARVAdapterMultiDB : MABaseRVAdapter() {

    // ---- Abstract fun

    /**
     * Same as [MARVAdapter.onBindViewHolder] but provides [binding]
     * as param instead of layout root view for quick access to it isa.
     */
    abstract fun onBindViewHolder(binding: ViewDataBinding, position: Int)

    // ---- Overridden fun

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, getLayoutRes(), parent, false)

        return ViewHolder(binding)
    }

    @Suppress("UNCHECKED_CAST")
    final override fun internalOnBindViewHolder(holder: ViewHolder, position: Int)
        = onBindViewHolder(holder.any as ViewDataBinding, position)
}