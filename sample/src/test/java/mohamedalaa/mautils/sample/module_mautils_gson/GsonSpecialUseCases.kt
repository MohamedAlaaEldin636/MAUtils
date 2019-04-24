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
import mohamedalaa.mautils.sample.model_for_testing.*
import mohamedalaa.mautils.test_core.TestingLog
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/4/2019.
 *
 */
@RunWith(RobolectricTestRunner::class)
class GsonSpecialUseCases {

    @Test
    fun justPrint2() {
        TestingLog.e("dede222222")
    }

    @Test
    fun justPrint() {
        TestingLog.e("dede")
    }

    @Test
    fun nested_sealed_class() {
        val o1 = NestedSealedClassDataClass(
            NestedSealedClassParent.SealedClass1.Data1(
                789
            ),
            56.6
        )

        val j1 = o1.toJson()

        val r1 = j1.fromJson<NestedSealedClassDataClass>()

        println(o1)
        println()
        println(j1)
        println()
        println(r1)

        assertEquality(o1, r1)
    }

    @Test
    fun normal_class() {
        val o1 = SpecialIndirectSealedClass.DataClass1(99)

        val j1 = o1.toJson()

        val r1 = j1.fromJson<SpecialIndirectSealedClass.DataClass1>()

        assertEquality(o1, r1)
    }

    @Test
    fun not_direct_sealed_like_subclass_class() {
        val o1 = SpecialDataClass(
            4,
            SpecialSealedClass.DataClass1(
                34.0,
                SpecialIndirectSealedClass.DataClass1(
                    99
                )
            )
        )

        println(o1)
        println()

        val json = o1.toJson()

        println(json)
        println()

        val r1 = json.fromJson<SpecialDataClass>()

        println(r1)
        println()

        assertEquality(o1, r1)
    }

    @Test
    fun just_custom_with_sealed() {
        val o1 = SpecialJust(
            5,
            SpecialIndirectSealedClass.DataClass1(99)
        )

        val j1 = o1.toJson()

        val r1 = j1.fromJson<SpecialJust>()

        println("-")
        println(o1)
        println("-")
        println(j1)
        println("-")
        println(r1)
        println()

        assertEquality(o1, r1)
    }

}