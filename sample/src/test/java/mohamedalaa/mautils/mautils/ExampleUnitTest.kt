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

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import mohamedalaa.mautils.mautils.fake_data.CustomObject
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam
import mohamedalaa.mautils.test_core.TestingLog
import org.junit.Test

import org.junit.Assert.*
import java.io.IOException
import kotlin.test.assertTrue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        assertTrue { true }

        TestingLog.e("hello")
    }

    @Test
    fun quick1() {
        println(3.5f.toString())
        println(3f.toString())
        println(3.0f.toString())
    }

    @Test
    fun mapsEqualityTest() {
        val map1 = mapOf("a1" to "", "a2" to "", "a3" to "")
        val map2 = mapOf("a1" to "", "a2" to "", "a3" to "")
        val map22 = mapOf("a1" to "", "a3" to "")
        val map3 = mapOf("a1" to 4, "a3" to 9)

        bbb1111(map1, map2, map3)       // true false       -> done el7
        bbb1111(map1, map22, map3)      // false false      -> done el7
    }

    private fun bbb1111(map1: Map<String, String>, map2: Map<String, String>, map3: Map<String, Int>) {
        println(map1 == map2)
        println()
        println(map2 == map3)
        println()
    }

    @Test
    fun showClassesIsa() {
        showClassIsa<Int>()
        showClassIsa<Boolean>()
        showClassIsa<String>()
        showClassIsa<CustomObject>()
        showClassIsa<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, Int>>>()
    }

    private inline fun <reified T> showClassIsa() {
        println(T::class)
        println(Int::class)
        println(Boolean::class)
    }

    @Test
    fun a12() {
        println("name is mido".indexOf("name"))
    }

    fun deuwhd() {
        val ta = object : TypeAdapter<Int>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Int?) {

            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): Int? {
                return null
            }
        }
    }
}
