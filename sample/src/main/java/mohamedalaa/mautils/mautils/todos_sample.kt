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

import android.graphics.Color
import androidx.core.os.bundleOf
import java.util.*

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/18/2019.
 *
 * todo
 * 1- new module of getBySelfOrListOrArrayOrLazy for baseImplementation
 *
 * 2- new module for licences and notes etc.... , privacy isa, and maybe other about app, dev
 *
 * todo general
 * 1- put licence for your own code isa.
 * 2- put licences and notes in sample isa.
 */

val a = 5

private fun z1(any: Any, ss: String) {
    //bundleOf()
    val a1 = ss::length

    //any::
    val z1 = ::a
    z1.name
    z1.get()
    val z = ::a.name

    val strs = listOf("a", "bc", "def")
    println(strs.map(String::length)) // [1,2,3]
}

private fun z1(iterable: Iterable<*>, queue: Queue<*>) {
    iterable.count()

    //queue.offer()
    //queue.peek()
    //queue.poll()

    //listOf(3).indexof
}

private fun z2(color: Int) {
    /*Color.luminance(Color.RED)
    ColorContez*/
}
