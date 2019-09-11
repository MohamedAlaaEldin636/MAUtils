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
import androidx.fragment.app.commit
import kotlinx.android.synthetic.main.activity_open_source_licences.*
import mohamedalaa.mautils.core_android.extensions.logError
import mohamedalaa.mautils.core_android.extensions.postWithReceiver
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import mohamedalaa.mautils.gson.getterBundleGson
import mohamedalaa.open_source_licences.R
import mohamedalaa.open_source_licences.custom_classes.OpenSourceLicencesForActivityInterface
import mohamedalaa.open_source_licences.model.LicenceModel
import mohamedalaa.open_source_licences.model.OpenSourceLicencesModel
import mohamedalaa.open_source_licences.view.fragments.OpenSourceLicencesFragment

internal class OpenSourceLicencesActivity : AppCompatActivity(), OpenSourceLicencesFragment.Listener {

    private var openSourceLicencesModel: OpenSourceLicencesModel? = null
    private var licenceModelList: List<LicenceModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreIntent()
        openSourceLicencesModel?.styleRes?.apply { if (this != 0) setTheme(this) }
        setContentView(R.layout.activity_open_source_licences)

        // Hide action bar isa.
        supportActionBar?.hide()

        // Launch fragment, Note used dynamically for constructor vars isa.
        rootFrameLayout.postWithReceiver {
            if (supportFragmentManager.findFragmentById(R.id.rootFrameLayout) != null) return@postWithReceiver

            supportFragmentManager.commit {
                replace(
                    R.id.rootFrameLayout,
                    OpenSourceLicencesFragment(licenceModelList, openSourceLicencesModel)
                )
            }
        }
    }

    // ---- Private fun

    private fun restoreIntent() {
        // Intent
        val getterBundleGson = intent.extras!!.getterBundleGson()

        openSourceLicencesModel = getterBundleGson.get()
        licenceModelList = getterBundleGson.get()
    }

    // ---- Overriding OpenSourceLicencesFragment.Listener

    override fun onNavIconClick() = openSourceLicencesModel.performIfNotNull {
        if (openSourceLicencesForActivityInterfaceTreatNavIconAsOnBackPressed) {
            onBackPressed()
        }else if (openSourceLicencesForActivityInterfaceClassFullName.isNotEmpty()) {
            runCatching {
                val implementerClass = Class.forName(openSourceLicencesForActivityInterfaceClassFullName)

                implementerClass.getMethod(
                    OpenSourceLicencesForActivityInterface::onNavIconClick.name,
                    AppCompatActivity::class.java
                ).invoke(implementerClass.newInstance(), this@OpenSourceLicencesActivity)
            }
        }
    }

}
