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

package mohamedalaa.mautils.mautils.module_mautils_gson

import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.mautils.model_for_testing.SpecialDataClass
import mohamedalaa.mautils.mautils.model_for_testing.SpecialIndirectSealedClass
import mohamedalaa.mautils.mautils.model_for_testing.SpecialSealedClass
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
    fun not_direct_sealed_like_subclass_class() {
        val o1 = SpecialDataClass(
            4,
            SpecialSealedClass.DataClass1(
                34.0,
                SpecialIndirectSealedClass.DataClass1(99)
            )
        )

        val json = o1.toJson()

        val r1 = json.fromJson<SpecialDataClass>()

        println(o1)
        println()
        println(json)
        println()
        println(r1)
    }

}