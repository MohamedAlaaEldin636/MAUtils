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

package mohamedalaa.mautils.sample.shared_pref_core

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import mohamedalaa.mautils.shared_pref_core.sharedPrefClearAll
import mohamedalaa.mautils.shared_pref_core.sharedPrefHasKey
import mohamedalaa.mautils.shared_pref_core.sharedPrefRemoveKey
import mohamedalaa.mautils.shared_pref_core.sharedPrefSet
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class OtherOfSharedPrefs {

    private val fileName = "fileName"

    @Test
    fun clearAll_removeKey_hasKey() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // has key && remove key
        context.sharedPrefSet(fileName, "k1", "s")
        assertEquals(
            true,
            context.sharedPrefHasKey(fileName, "k1")
        )

        context.sharedPrefRemoveKey(fileName, "k1")
        assertEquals(
            false,
            context.sharedPrefHasKey(fileName, "k1")
        )

        // clear all
        context.sharedPrefSet(fileName, "k1", "s 111")
        context.sharedPrefSet(fileName, "k2", "s 222")
        assertEquals(
            2,
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE).all.size
        )
        context.sharedPrefClearAll(fileName)
        assertEquals(
            0,
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE).all.size
        )
    }

}
