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

package mohamedalaa.mautils.sample.module_gson_processor

import mohamedalaa.mautils.gson.toJson
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.reflect.full.declaredMemberProperties

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/3/2019.
 *
 */
@RunWith(RobolectricTestRunner::class)
class Generation {

    @Test
    fun gson_serial() {
        "hello".toJson()
    }

    @Test
    fun objReflection() {
        val kClass = Class.forName("mohamedalaa.mautils.sample.AAAA").kotlin
        println(kClass.declaredMemberProperties.toList()[0].call(kClass.objectInstance))

        /*println(Class.forName("mohamedalaa.mautils.sample.AAAA")
            .kotlin.declaredMemberProperties.toList()[0].call(AAAA))*/

        /*val z = AAAA::class.declaredMemberProperties.toList()[0].getter.call(AAAA)

        println(z)*/
    }

    @Test
    fun gen_list_class() {
        val list = listOf(String::class.java, CharSequence::class.java, TopSealedClass::class.java)
        val string = buildString {
            append("kotlin.collections.listOfNotNull(")

            for (index in 0 until list.size) {
                append("kotlin.runCatching { Class.forName(${list[index]}.name) }.getOrNull()")

                if (index != list.lastIndex) {
                    append(",")
                }
            }

            append(")")
        }

        println(string)
    }

}

object AAAA {
    val int: Int = 9
}