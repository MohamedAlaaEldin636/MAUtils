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

import android.graphics.Color
import mohamedalaa.mautils.core_android.addColorAlpha
import mohamedalaa.mautils.core_android.alphaAsFloat
import mohamedalaa.mautils.core_android.isNearToBlack
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/1/2019.
 *
 */
@RunWith(RobolectricTestRunner::class)
class ColorTest {

    @Test
    fun range_of_color_alpha() {
        println(Color.alpha(Color.BLACK))
        println(Color.alpha(Color.BLACK.addColorAlpha(0f)))
        println()
        println(Color.BLACK.alphaAsFloat)
        println(Color.BLACK.addColorAlpha(0f).alphaAsFloat)
    }

    @Test
    fun test_luminance() {
        println(Color.BLUE.isNearToBlack())
        println(Color.BLUE.addColorAlpha(0f).isNearToBlack())
        println(Color.LTGRAY.isNearToBlack())
        println(Color.DKGRAY.isNearToBlack())
    }

}