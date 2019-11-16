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

package mohamedalaa.mautils.sample_java_only

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
//import mohamedalaa.mautils.sample_java_only.mautils_sharedPref.sharedPref_Settings_SetKeepScreenOn
/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 11/16/2019.
 *
 */
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class SSS {
    @Test
    fun generatedMethods() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val value = context.sharedPref_Settings_GetKeepScreenOn() // default is false isa.
        Assert.assertFalse(value)

        context.sharedPref_Settings_SetKeepScreenOn(true)
        Assert.assertTrue(
            context.sharedPref_Settings_GetKeepScreenOn()
        )
    }
}