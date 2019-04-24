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

package mohamedalaa.mautils.sample.module_mautils_gson

import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.sample.assertEquality
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GsonTestV3 {

    @Test
    fun trial_with_annotation_isa() {
        println("hello".toJson())
        println()
        println("PRE bye".toJson())
        println()
        println("bye".toJson())
    }

    @Test
    fun toAndFrom_withSealedClass() {
        val o1 = ParentUsingBoth(
            UsingBoth(
                NoArgsSealedClass.NoSingleton,
                WithArgsSealedClass.WithDataClass(
                    false
                ),
                35f
            ),
            WithArgsSealedClass.WithDataClass(false),
            984,
            ImplSomeInterface(12, "s as string"),
            ImplAbstractClass(602, true)
        )
        val j1 = o1.toJson()
        val r1 = j1.fromJson<ParentUsingBoth>()

        println("########")
        println(o1)
        println()
        println(j1)
        println()
        println(r1)
        println()

        assertEquality(o1, r1)
    }

    @Test
    fun direct_sealedClass() {
        val o1: WithArgsSealedClass =
            WithArgsSealedClass.WithDataClass(true)
        val j1 = o1.toJson()
        val r1 = j1.fromJson<WithArgsSealedClass>()

        println(o1)
        println()
        println(j1)
        println()
        println(r1)

        assertEquality(o1, r1)
    }

}