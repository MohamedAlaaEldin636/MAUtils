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

import com.google.gson.reflect.TypeToken
import org.junit.Test

class AAAAAAAAA {

    inline fun <reified Q> f1(q: Q? = null) {
        f2(Q::class.java, object : TypeToken<Q>(){}.type.toString())
    }

    fun f2(javaClass: Class<*>, s: String) {
        println()
        println("javaClass $s")
        println("javaClass == Set::class.java ${javaClass == Set::class.java}, ")
        println("javaClass == List::class.java ${javaClass == List::class.java}, ")
        println("javaClass == Array<Float>::class.java ${javaClass == Array<Float>::class.java}, ")
        println("javaClass.componentType == Float::class.java ${javaClass.componentType == Float::class.java}, ")
        println("javaClass == Array<String>::class.java ${javaClass == Array<String>::class.java}, ") // both true isa.
        println("javaClass == Array<String?>::class.java ${javaClass == Array<String?>::class.java}, ")
    }

    @Test
    fun f1() {
        f1<Set<Int>>()
        f1<List<Int>>()
        f1<Array<Int>>()
        f1(arrayOf(4f))
        f1(listOf(3, 3).toTypedArray()) // this is Array<Int> not intArray isa.
        f1(floatArrayOf(5f))
        f1(arrayOf(""))
        f1(arrayOf("", null, "s"))
    }

}