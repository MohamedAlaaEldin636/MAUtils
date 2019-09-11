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

package mohamedalaa.mautils.sample.mautils_core_android

import android.content.Intent
import mohamedalaa.mautils.core_android.extensions.buildBundle
import mohamedalaa.mautils.core_android.extensions.getterBundle
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/23/2019.
 *
 */
@RunWith(RobolectricTestRunner::class)
class IntentTest {

    @Test
    fun using_buildBundle() {
        val intent = Intent().apply { putExtras(
            buildBundle(
                3,
                "ss",
                true
            )
        ) }

        val getterBundle = intent.getterBundle()
        println("${getterBundle.get<Int>()}, ${getterBundle.get<String>()}, ${getterBundle.get<Boolean>()}")
    }

}