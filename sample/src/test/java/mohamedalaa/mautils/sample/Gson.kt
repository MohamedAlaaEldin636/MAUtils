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

package mohamedalaa.mautils.sample

import androidx.test.core.app.ApplicationProvider
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.open_source_licences.model.OpenSourceLicencesModel
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class Gson {

    @Test
    fun openSourceLicencesModel() {
        val o1 = OpenSourceLicencesModel(ApplicationProvider.getApplicationContext()).apply {
            assetsFolderPath = "mido"
        }

        val j1 = o1.toJson()

        val r1 = j1.fromJson<OpenSourceLicencesModel>()

        assertEquals(o1.assetsFolderPath, r1.assetsFolderPath)
    }

}