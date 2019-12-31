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

package mohamedalaa.mautils.sample.gson

import android.os.Build
import mohamedalaa.mautils.core_kotlin.infoPrintLn
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class TestSuperClasses {

    /**
     * - gson serialization support superclass properties isa.
     */
    @Test
    fun f1() {
        val impl1 = ImplOfOpenClassA("", null).apply { def = "fioweijoifewjfowj" }
        val impl2 = ImplOfOpenClassA("", null).apply { def = "fioweijoifewjfowj" }
        assertEquals(impl1, impl2)

        val jImpl1 = impl1.toJson()
        val jImpl2 = impl2.toJson()
        assertEquals(jImpl1, jImpl2)
        infoPrintLn(jImpl1)

        val rImpl1 = jImpl1.fromJson<ImplOfOpenClassA>()
        val rImpl2 = jImpl2.fromJson<ImplOfOpenClassA>()
        assertEquals(rImpl1, impl1)
        assertEquals(rImpl2, impl2)
    }

}
