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

package mohamedalaa.mautils.mautils.module_recycler_view

import org.junit.Test

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 3/13/2019.
 *
 */
class EquationsTest {

    @Test
    fun solve_forTwoSides() {
        /*// try elimination of x kda isa
        var equation = "x+a=b+c-x"
        //equation = "x+a=b+c+x" // x value is 0x isa.
        //equation = "x+a=b+c+a" // term a exists but is zero in numMultiplier

        equation.solveForOnlyTwoSides('x').apply {
            println(this)
            println()
            println(this.second.toEquation())
            println()
            println("x=${this.second.toEquation()}".solveForOnlyTwoSides())
            println()
            println("x+a=${this.second.toEquation()}".solveForOnlyTwoSides())
            println()
            println("x-0.5a=${this.second.toEquation()}".solveForOnlyTwoSides())
            println()
            //println("x-0.5a=${this.second.toEquation()}".solveForOnlyTwoSides().groupTerms())
            println("----")
        }*/
    }

    @Test
    fun solve_all_x_equations_isa() {
        /*var xEquations = listOf("a+b", "2b-a")

        val fullDimen = 48f
        var map = xEquations.solveAllVariables(listOf('x', 'a', 'b'), fullDimen, "x+a")

        println(map)

        xEquations = listOf("a+b", "2c", "b+c-a")
        map = xEquations.solveAllVariables(listOf('x', 'a', 'b', 'c'), fullDimen, "x+a")

        println(map)*/
    }

}