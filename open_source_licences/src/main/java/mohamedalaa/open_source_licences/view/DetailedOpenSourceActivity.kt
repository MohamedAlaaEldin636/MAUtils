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

package mohamedalaa.open_source_licences.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import mohamedalaa.mautils.core_android.extensions.launchLink
import mohamedalaa.mautils.core_android.extensions.postWithReceiver
import mohamedalaa.mautils.gson.getterBundleGson
import mohamedalaa.open_source_licences.R
import mohamedalaa.open_source_licences.databinding.ActivityDetailedOpenSourceBinding
import mohamedalaa.open_source_licences.model.LicenceModel
import mohamedalaa.open_source_licences.model.OpenSourceLicencesModel

internal class DetailedOpenSourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailedOpenSourceBinding>(this, R.layout.activity_detailed_open_source)

        // Hide action bar isa.
        supportActionBar?.hide()

        // Restore intent
        val getterBundleGson = intent.extras!!.getterBundleGson()

        val openSourceLicencesModel = getterBundleGson.get<OpenSourceLicencesModel>()
        val licencesModel = getterBundleGson.get<LicenceModel>()
        val licencesContent = getterBundleGson.get<String>()
        val modifiedLicenceContent = licencesModel.preLicenseContentSpecificToOpenSource.run {
            if (isEmpty()) return@run licencesContent

            joinToString("\n").trim() + "\n\n$licencesContent"
        }

        // Assign binding isa.
        binding.model = openSourceLicencesModel
        binding.openSourceName = licencesModel.openSourceName
        binding.author = licencesModel.listOfAuthorsNames.joinToString(", ")
        binding.licenceContent = modifiedLicenceContent
        binding.lifecycleOwner = this

        // Setup xml isa.
        binding.toolbar.postWithReceiver {
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                launchLink(licencesModel.openSourceLink, createIntentChooser = true)

                true
            }
        }
    }
}
