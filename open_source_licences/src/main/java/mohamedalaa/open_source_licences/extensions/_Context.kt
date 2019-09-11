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

package mohamedalaa.open_source_licences.extensions

import android.content.Context
import mohamedalaa.mautils.core_android.extensions.startActivity
import mohamedalaa.mautils.gson.buildBundleGsonForced
import mohamedalaa.open_source_licences.custom_classes.Constants
import mohamedalaa.open_source_licences.model.LicenceModel
import mohamedalaa.open_source_licences.model.OpenSourceLicencesModel
import mohamedalaa.open_source_licences.view.OpenSourceLicencesActivity

/**
 * @see Constants.APACHE_2_0_LICENCE_NAME
 */
fun Context.startActivityForOpenSourceLicences(
    licenceModelList: List<LicenceModel>,
    makeChangesBlock: (OpenSourceLicencesModel.() -> Unit)? = null
) {
    startActivity<OpenSourceLicencesActivity>(buildBundleGsonForced(
        OpenSourceLicencesModel(this).apply { if (makeChangesBlock != null) makeChangesBlock() },
        licenceModelList
    ))
}
