package mohamedalaa.mautils.recycler_view.extensions.internal

import mohamedalaa.mautils.core_kotlin.isNegative

/* ==> LIMITATION
1- side of equation cannot have ++, +-, --
2- No parenthesis
*/

// ---- Internal Extension fun todo make all below as internal ext fun after done testing isa.

/**
 * Solves given equation(receiver) for the given [variable] having a coefficient of 1 OR 0
 * Ex. If equation was x+a=b+c Then result will be x=b+c-a isa.
 * Ex. x+a=b+c+x -> 0x=b+c-a
 *
 * @see removeZero
 * @see removeZeros
 * @see groupTerms
 */
fun String.solveForOnlyTwoSides(variable: Char = 'x'): Pair<Term, List<Term>> {
    val sidesList = split("=")
    if (sidesList.size != 2) {
        throw RuntimeException("Equation either has no equal sign OR has more than one equal sign")
    }else if (variable !in this) {
        throw RuntimeException("Variable is not in the equation")
    }
    val leftHandSide = sidesList[0]
    val rightHandSide = sidesList[1]

    val leftHandSideTerms = leftHandSide.toTerms().toMutableList()
    val rightHandSideTerms = rightHandSide.toTerms().toMutableList()

    // Move all non "x" to tight hand side
    val leftPairs = leftHandSideTerms.mapIndexedNotNull { index, term ->
        if (variable !in term.variables) {
            index to term.copy(numMultiplier = term.numMultiplier.times(-1f))
        }else {
            null
        }
    }.sortedByDescending { it.first }

    leftPairs.forEach {
        leftHandSideTerms.removeAt(it.first)

        rightHandSideTerms.add(it.second)
    }

    // Move all "x" to left hand side
    val rightPairs = rightHandSideTerms.mapIndexedNotNull { index, term ->
        if (variable in term.variables) {
            index to term.copy(numMultiplier = term.numMultiplier.times(-1f))
        }else {
            null
        }
    }.sortedByDescending { it.first }

    rightPairs.forEach {
        rightHandSideTerms.removeAt(it.first)

        leftHandSideTerms.add(it.second)
    }

    // group coefficients isa.
    val newLeftTerms = leftHandSideTerms.groupTerms().toMutableList()
    val newRightTerms = rightHandSideTerms.groupTerms().toMutableList()

    // Check validity of left terms is just 1 term of x variable only isa.
    val solveForTerm = newLeftTerms[0]
    when {
        newLeftTerms.size > 1 -> throw RuntimeException("Unexpected error contact developer newLeftTerms.size > 1 is true isa")
        solveForTerm.variables.size > 1 -> throw RuntimeException("More than one variable in a term is not supported")
        solveForTerm.variables[0] != variable -> throw RuntimeException("Unexpected error contact developer not exact variable")
    }

    // Make left term of variable x has coefficient of +ve 1 only or 0 isa.
    if (solveForTerm.numMultiplier.isNegative()) {
        newRightTerms.forEach {
            it.numMultiplier *= -1f
        }

        solveForTerm.numMultiplier *= -1f
    }
    if (solveForTerm.numMultiplier != 1f && solveForTerm.numMultiplier != 0f) {
        newRightTerms.forEach {
            it.numMultiplier /= solveForTerm.numMultiplier
        }

        solveForTerm.numMultiplier = 1f
    }

    return solveForTerm to newRightTerms
}

/**
 * Solves bunch of equations if can be substituted with each other see Example below isa
 * ```
 * var xEquations = listOf("a+b", "2b-a")
 * val fullDimen = 48f
 * var map = xEquations.solveAllVariables(listOf("x", "a", "b"), fullDimen, "x+a")
 * // Result is -> {a=12, x=36, b=24}
 *
 * // Note not always accurate with respect to fullDimen value
 * // since returned type is Map<String, **Int**>, However it is 100%
 * // accurate to conditions in given equations so a+b is indeed equals 2b-a
 *
 * xEquations = listOf("a+b", "2c", "b+c-a")
 * map = xEquations.solveAllVariables(listOf("x", "a", "b", "c"), fullDimen, "x+a")
 * // Result is -> {a=10, x=40, b=30, c=20}
 * ```
 *
 * `receiver` is list of equation sides Ex. a+b, 2b-a which all equals [variables].get(0) isa.
 *
 * @param variables All variables in equations
 * @param withResultValue additional solved equation this is the value of it isa, Ex. 48f
 * @param withResultGivenSide additional solved equation this is the variables side of it isa, Ex. x+a
 *
 * @return Map of all variables with actual values Ex. 'x' = 40 , 'a' = 30 etc... isa.
 *
 * @see [subItemOffsetIgnoreBorderMergeOffsetsVertical3]
 */
fun List<String>.solveAllVariables(variables: List<Char>, withResultValue: Float, withResultGivenSide: String): Map<Char, Int> {
    val solutions = mutableMapOf<Char, Term>()

    val xVariable = variables[0]
    val aVariable = variables[1]
    var counterForVariables = 2

    if (variables.size == 2) {
        solutions[xVariable] = this[0].toTerms().substituteAndGroupToSingleTerm(solutions)

        val knownVariableTerm: Term = withResultGivenSide.toTerms().substituteAndGroupToSingleTerm(solutions)

        val realMap = mutableMapOf<Char, Int>()

        // a variable value isa.
        val baseVariable = knownVariableTerm.variables[0]
        val baseVariableValue = Math.round(withResultValue.div(knownVariableTerm.numMultiplier))
        realMap[baseVariable] = baseVariableValue

        variables.forEach {
            if (baseVariable == it) {
                return@forEach
            }

            val multiplier = Math.round(solutions[it]?.numMultiplier ?: throw RuntimeException("code 902830292023902"))

            realMap[it] = multiplier.times(baseVariableValue)
        }

        if (realMap.size != variables.size) {
            throw RuntimeException("code 980fhiw7382823")
        }

        return realMap
    }

    val keepLooping = true
    var listOfEquationSides = this
    do {
        // Get all `b` equations isa.
        val solveForVariable = variables[counterForVariables] // loop here isa
        val baseSide = listOfEquationSides[0]
        var bEquationsSides: List<List<Term>> = listOfEquationSides.drop(1).mapNotNull {
            val pair = "$baseSide=$it".solveForOnlyTwoSides(solveForVariable)

            if (pair.first.removeZero() != null) {
                pair.second.removeZeros()
            }else {
                val fallbackVariables = variables.subList(counterForVariables.inc(), variables.size)
                val newSolveForVariable: Char = kotlin.run {
                    fallbackVariables.forEach { fallback ->
                        if (pair.second.any { innerIt -> fallback in innerIt.variables }) {
                            return@run fallback
                        }
                    }

                    throw RuntimeException("Not enough fallbackVariables")
                }

                val newPair = "$baseSide=$it".solveForOnlyTwoSides(newSolveForVariable).second.removeZeros()
                if (newPair.size == 1 && newPair[0].variables[0] == aVariable) {
                    solutions[newSolveForVariable] = newPair[0]
                }

                null
            }
        }

        // if solutions not empty then start substituting isa.
        if (solutions.isNotEmpty()) {
            bEquationsSides = bEquationsSides.map {
                val fromVariable = it.containsAndGet(solutions.keys.toList())
                val toTerm = solutions[fromVariable]

                if (fromVariable != null && toTerm != null) {
                    it.substituteVariables(fromVariable, toTerm).groupTerms()
                }else {
                    it
                }
            }
        }

        // if bEquationSides can be solutions Ex. b = 2a
        bEquationsSides.forEach {
            if (it.size == 1 && it[0].variables[0] == aVariable) {
                solutions[solveForVariable] = it[0]
            }
        }

        // if solutions are done isa.
        if (solutions.size == variables.size.minus(2)) {
            // 1- solve for x respect to a isa then return all isa.
            // 2- or instead of return add param for FullDimen as float or int with x and a so solve for a with real num and others as well isa
            solutions[xVariable] = this[0].toTerms().substituteAndGroupToSingleTerm(solutions)

            val knownVariableTerm: Term = withResultGivenSide.toTerms().substituteAndGroupToSingleTerm(solutions)

            val realMap = mutableMapOf<Char, Int>()

            // a variable value isa.
            val baseVariable = knownVariableTerm.variables[0]
            val baseVariableValue = Math.round(withResultValue.div(knownVariableTerm.numMultiplier))
            realMap[baseVariable] = baseVariableValue

            variables.forEach {
                if (baseVariable == it) {
                    return@forEach
                }

                val multiplier = Math.round(solutions[it]?.numMultiplier ?: throw RuntimeException("code 902830292023902"))

                realMap[it] = multiplier.times(baseVariableValue)
            }

            if (realMap.size != variables.size) {
                throw RuntimeException("code 980fhiw7382823")
            }

            return realMap
        }

        listOfEquationSides = bEquationsSides.map { it.toEquation() }
        counterForVariables++
    } while (keepLooping)

    throw RuntimeException("End of loop code 3827398792")
}