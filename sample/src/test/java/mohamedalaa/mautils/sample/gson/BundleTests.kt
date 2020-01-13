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
import mohamedalaa.mautils.gson.buildBundleGson
import mohamedalaa.mautils.gson.getterBundleGson
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.sample.gson.model.entity.ReminderOrAction
import mohamedalaa.mautils.sample.gson.open_classes.BaseComplexClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class BundleTests : BaseComplexClass() {

    @Test
    fun f1() {
        val bundle = buildBundleGson(
            "string",
            this.customClass,
            33
        )

        val getterBundle = bundle.getterBundleGson()
        val string = getterBundle.get<String>()
        val customClass = getterBundle.get<ReminderOrAction>()
        val int = getterBundle.get<Int>()

        assertEquals("string", string)
        assertEquals(customClass, this.customClass)
        assertEquals(int, 33)
    }

}
