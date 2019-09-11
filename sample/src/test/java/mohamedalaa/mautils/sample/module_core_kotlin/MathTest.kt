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

package mohamedalaa.mautils.sample.module_core_kotlin

import mohamedalaa.mautils.core_kotlin.extensions.round
import org.junit.Test

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/21/2019.
 *
 */
class MathTest {

    @Test
    fun decimalPlacesRounding() {
        val num1 = 3.45.round(5)
        val num2 = 3.4555555555555.round(5)

        println(num1)
        println(num2)
    }

}