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

package mohamedalaa.mautils.mautils

import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BaseTest {

    sealed class BidAttachment {

        object With : BidAttachment()
        data class Risk(val value: Int): BidAttachment()

    }

    data class Contain22(
        val risk: BidAttachment.Risk,
        val with: BidAttachment.With?,
        val int: Int
    )

    @Test
    fun normalClass() {
        val o1 = Contain22(BidAttachment.Risk(33), BidAttachment.With, 6)
        val j1 = o1.toJson()
        val r1 = j1.fromJson<Contain22>()

        println("########")
        println(o1)
        println(j1)
        println(r1)
        println()
    }

}