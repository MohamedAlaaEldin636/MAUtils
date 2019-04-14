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

package mohamedalaa.mautils.mautils.kfunctions

import org.junit.Test
import kotlin.reflect.KFunction

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/12/2019.
 *
 */
class PassingAndConverting {

    @Test
    fun test1() {
        val annFun1 = { println("Invoked isa") }

        //m1(annFun1 as KFunction<*>)
        m1(::m2)
    }

    private fun m1(kFunction: KFunction<*>) {
        println("names isa are -> ${kFunction.name} + ${::m2.name}")

        @Suppress("UNCHECKED_CAST")
        val f1 = kFunction as? () -> Unit

        f1?.invoke() ?: println("was null")
    }

    private fun m2() {
        println("in m2 isa")
    }

}