package mohamedalaa.mautils.mautils.module_recycler_view

import mohamedalaa.mautils.recycler_view.extensions.internal.solveAllFor
import mohamedalaa.mautils.recycler_view.extensions.internal.solveForOnlyTwoSides
import mohamedalaa.mautils.recycler_view.extensions.internal.toEquation
import org.junit.Test

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 3/13/2019.
 *
 */
class EquationsTest {

    @Test
    fun solve_forTwoSides() {
        // try elimination of x kda isa
        var equation = "x+a=b+c-x"
        //equation = "x+a=b+c+x" // x value is 0x isa.
        //equation = "x+a=b+c+a" // term a exists but is zero in numMultiplier

        equation.solveForOnlyTwoSides("x").apply {
            println(this)
            println()
            println(this.second.toEquation())
            println()
            println("x=${this.second.toEquation()}".solveForOnlyTwoSides())
            println()
            println("x+a=${this.second.toEquation()}".solveForOnlyTwoSides())
        }
    }

    @Test
    fun solve_all_x_equations_isa() {
        var xEquations = listOf("a+b", "2b-a")

        val fullDimen = 48f
        var map = xEquations.solveAllFor(listOf("x", "a", "b"), fullDimen, "x+a")

        println(map)

        xEquations = listOf("a+b", "2c", "b+c-a")
        map = xEquations.solveAllFor(listOf("x", "a", "b", "c"), fullDimen, "x+a")

        println(map)
    }

}