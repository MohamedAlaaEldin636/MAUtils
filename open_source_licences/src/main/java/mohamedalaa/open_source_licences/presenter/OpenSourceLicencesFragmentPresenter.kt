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

package mohamedalaa.open_source_licences.presenter

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import mohamedalaa.mautils.core_android.extensions.beforeAndAfterDismissInLoadingDialog
import mohamedalaa.mautils.core_android.extensions.getNotNullOrSuspendUntilNotNullValue
import mohamedalaa.mautils.core_android.extensions.startActivity
import mohamedalaa.mautils.gson.buildBundleGson
import mohamedalaa.mautils.gson.forceUsingJsonInBundle
import mohamedalaa.open_source_licences.model.LicenceModel
import mohamedalaa.open_source_licences.view.DetailedOpenSourceActivity
import mohamedalaa.open_source_licences.view.fragments.OpenSourceLicencesFragment

internal class OpenSourceLicencesFragmentPresenter {

    fun launchDetailedOpenSourceActivity(fragment: OpenSourceLicencesFragment, licenceModel: LicenceModel) {
        val context = fragment.context ?: return
        val licencesNameAndContent = fragment.viewModel.mutableLiveDataLicencesNameAndContent.value

        val launchBlock: Context.(licenseContent: String) -> Unit = { licenseContent ->
            context.startActivity<DetailedOpenSourceActivity>(buildBundleGson(
                fragment.openSourceLicencesModel.forceUsingJsonInBundle(),
                licenceModel.forceUsingJsonInBundle(),
                licenseContent
            ))
        }

        if (licencesNameAndContent == null) {
            // Still loading isa.
            fragment.viewModel.beforeAndAfterDismissInLoadingDialog(fragment.childFragmentManager, Dispatchers.Main, before = {
                val list = fragment.viewModel.mutableLiveDataLicencesNameAndContent.getNotNullOrSuspendUntilNotNullValue(fragment)

                delay(3000)

                list.first { it.first == licenceModel.licenceName }.second
            }, after = {
                it?.apply { context.launchBlock(it) }
            })
        }else {
            val licenseContent = licencesNameAndContent.first { it.first == licenceModel.licenceName }.second
            context.launchBlock(licenseContent)
        }
    }

}
