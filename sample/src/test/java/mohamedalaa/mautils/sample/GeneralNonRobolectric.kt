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

import org.junit.Test

class GeneralNonRobolectric {

    @Test
    fun forEach_startIndex() {
        /*(0..10).forEach(5) {
            println(it)
        }*/

        println()

        (0..10).drop(5).forEach { println(it) }
        val range = (0..10)
    }

    @Test
    fun signedString() {
        val numF = -3.4f

        println(numF)
        println(numF.toString())
        println(numF.toString().toFloat())
        println("-3.0f".toFloat())
    }

}