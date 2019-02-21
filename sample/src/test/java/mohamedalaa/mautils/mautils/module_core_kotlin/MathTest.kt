package mohamedalaa.mautils.mautils.module_core_kotlin

import mohamedalaa.mautils.core_kotlin.round
import org.junit.Test

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/21/2019.
 *
 */
class MathTest {

    @Test
    fun decimalPlacesRounding() {
        val num1 = 3.45.round(5)
        val num2 = 3.4555555555555.round(5)

        println(num1)
        println(num2)
    }

}