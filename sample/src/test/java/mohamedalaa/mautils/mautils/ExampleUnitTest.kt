package mohamedalaa.mautils.mautils

import mohamedalaa.mautils.mautils.fake_data.CustomObject
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam
import org.junit.Test

import org.junit.Assert.*
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
}
