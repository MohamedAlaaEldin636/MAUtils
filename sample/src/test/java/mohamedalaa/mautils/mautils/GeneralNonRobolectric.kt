package mohamedalaa.mautils.mautils

import org.junit.Test

class GeneralNonRobolectric {

    @Test
    fun forEach_startIndex() {
        /*(0..10).forEach(5) {
            println(it)
        }*/

        println()

        (0..10).drop(5).forEach { println(it) }
        val range = (0..10)
    }

    @Test
    fun signedString() {
        val numF = -3.4f

        println(numF)
        println(numF.toString())
        println(numF.toString().toFloat())
        println("-3.0f".toFloat())
    }

}