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

package mohamedalaa.mautils.sample.custom_views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import mohamedalaa.mautils.core_android.extensions.logError
import mohamedalaa.mautils.gson.addValuesGsonForced
import mohamedalaa.mautils.gson.getterBundleGson
import mohamedalaa.mautils.lifecycle_extensions.extensions.getAndroidViewModelWithFactory
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.mautils.databinding.ActivityCustomViewsMainBinding
import mohamedalaa.mautils.sample.custom_views.presenter.CustomViewsMainActivityPresenter
import mohamedalaa.mautils.sample.custom_views.view_model.CustomViewsMainActivityViewModel

class CustomViewsMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomViewsMainBinding
    private lateinit var viewModel: CustomViewsMainActivityViewModel

    private var checkedNames = emptyList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!::binding.isInitialized) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_views_main)
        }

        checkedNames = listOf(getString(R.string.chip_5))
        savedInstanceState?.getterBundleGson()?.apply {
            checkedNames = getOrNull() ?: emptyList()
        }

        if (!::viewModel.isInitialized) {
            viewModel = getAndroidViewModelWithFactory(
                checkedNames
            )
        }

        binding.viewModel = viewModel
        binding.presenter = CustomViewsMainActivityPresenter()
        binding.lifecycleOwner = this

        // Observe live data in view model isa.
        viewModel.mutableLiveDataCheckedChipsNames.observe(this, Observer {
            checkedNames = it.orEmpty()
            logError("VIEWMODEL old checked names -> ${binding.maChipsContainer.getLastCheckedChipsNames()}," +
                "\nnew checked names isa -> $it")
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.addValuesGsonForced(
            checkedNames
        )

        super.onSaveInstanceState(outState)
    }
}
