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

package mohamedalaa.mautils.sample.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import mohamedalaa.mautils.core_android.extensions.*
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import mohamedalaa.mautils.lifecycle_extensions.extensions.getAndroidViewModelWithFactory
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.mautils.databinding.ActivityEntryBinding
import mohamedalaa.mautils.sample.sample.interactor.EntryActivityInteractor
import mohamedalaa.mautils.sample.sample.view_model.EntryActivityViewModel

class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding
    private lateinit var viewModel: EntryActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!::binding.isInitialized) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_entry)
        }

        if (!::viewModel.isInitialized) {
            viewModel = getAndroidViewModelWithFactory()
        }

        // Assign binding isa.
        binding.viewModel = viewModel
        binding.interactor = EntryActivityInteractor()
        binding.lifecycleOwner = this

        // Additional setups isa.
        toolbarSetup()

        editText1Setup()

        editText2Setup()
    }

    private fun toolbarSetup() {
        binding.materialButton1.setOnOneClickListener {
            viewModel.mutableLiveDataSearchQuery.value = "hello"
        }
        viewModel.mutableLiveDataSearchQuery.observeOldAndNew(this) { old, new ->
            // Read CharSequence doc as it says equals has unknown result isa.
            if (old.toStringOrNull() == new.toStringOrNull()) return@observeOldAndNew

            logError("changed text isa is -> $new")
        }
    }

    private fun editText1Setup() {
        binding.editText1MaterialButton.setOnOneClickListener {
            viewModel.mutableLiveDataEditText1.value = "hello"
        }
        viewModel.mutableLiveDataEditText1.observe(this, Observer {
            val isUserInputChangeNotDynamicallyChanged = binding.editText1
                .getTag(R.id.editText_textTwoWay_isUserInputChangeNotDynamicallyChanged) as? Boolean
            val toastMsg = when (isUserInputChangeNotDynamicallyChanged) {
                true -> {
                    "User Change"
                }
                false -> {
                    "Dynamic Change"
                }
                null -> {
                    "UNKNOWN Change"
                }
            }

            toast(toastMsg)
        })
    }

    /** Used to check keyboard dynamic manipulations isa. */
    private fun editText2Setup() {
        binding.editText2ShowMaterialButton.setOnOneClickListener {
            showKeyboardFor(binding.editText2) // todo not working maybe need requestFocus isa. tell in kdoc isa.
        }
        binding.editText2HideMaterialButton.setOnOneClickListener {
            hideKeyboardFrom(binding.editText2)
        }
        binding.editText2GlobalIsShownMaterialButton.setOnOneClickListener {
            toast("isKeyboardShown can't be known isa.}")
        }
        binding.editText2GlobalHideMaterialButton.setOnOneClickListener {
            hideKeyboard()
        }
    }

}
