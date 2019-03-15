package mohamedalaa.mautils.recycler_view.extensions.internal

import kotlin.math.absoluteValue

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
fun String.solveForOnlyTwoSides(variable: String = "x", substitutableVariables: Pair<String, Term>? = null): Pair<Term, List<Term>> {
    val sidesList = split("=")
    if (sidesList.size != 2) {
        throw RuntimeException("Equation either has no equal sign OR has more than one equal sign")
    }else if (variable !in this) {
        throw RuntimeException("Variable is not in the equation")
    }
    val leftHandSide = sidesList[0]
    val rightHandSide = sidesList[1]

    val leftHandSideTerms = leftHandSide.toTerms()
        .run {
            if (substitutableVariables != null) {
                substituteVariables(substitutableVariables.first, substitutableVariables.second)
            }else {
                this
            }
        }
        .toMutableList()
    val rightHandSideTerms = rightHandSide.toTerms()
        .run {
            if (substitutableVariables != null) {
                substituteVariables(substitutableVariables.first, substitutableVariables.second)
            }else {
                this
            }
        }
        .toMutableList()

    // Move all non "x" to tight hand side
    val leftPairs = leftHandSideTerms.mapIndexedNotNull { index, term ->
        if (variable !in term.variables) {
            index to term.copy(signIsPositive = term.signIsPositive.not())
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
            index to term.copy(signIsPositive = term.signIsPositive.not())
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
        solveForTerm.variables.size > 1 -> throw RuntimeException("more than one variable in a term is not supported")
        solveForTerm.variables[0] != variable -> throw RuntimeException("Unexpected error contact developer not exact variable")
    }

    // Make left term of variable x has coefficient of +ve 1 only isa.
    if (solveForTerm.signIsPositive.not()) {
        newRightTerms.forEach {
            it.signIsPositive = it.signIsPositive.not()
        }

        solveForTerm.signIsPositive = true
    }
    if (solveForTerm.numMultiplier != 1f && solveForTerm.numMultiplier != 0f) {
        val tempMultiplier = solveForTerm.numMultiplier
        newRightTerms.forEach {
            it.numMultiplier /= tempMultiplier
        }

        solveForTerm.numMultiplier = 1f
    }

    return solveForTerm to newRightTerms
}

/**
 * Converts [List]<[Term]> to String as equation side
 * Ex. given b+c-a result -> +1.0b+1.0c-1.0a isa.
 */
fun List<Term>.toEquation(): String
    = joinToString(separator = "") { "${if (it.signIsPositive) "+" else "-"}${it.numMultiplier}${it.variables.joinToString(separator = "")}" }

/**
 * Solves bunch of equations if can be substituted with each other see Example below isa
 * ```
 * var xEquations = listOf("a+b", "2b-a")
 * val fullDimen = 48f
 * var map = xEquations.solveAllFor(listOf("x", "a", "b"), fullDimen, "x+a")
 * // Result is -> {a=12, x=36, b=24}
 *
 * // Note not always accurate with respect to fullDimen value
 * // since returned type is Map<String, **Int**>, However it is 100%
 * // accurate to conditions in given equations so a+b is indeed equals 2b-a
 *
 * xEquations = listOf("a+b", "2c", "b+c-a")
 * map = xEquations.solveAllFor(listOf("x", "a", "b", "c"), fullDimen, "x+a")
 * // Result is -> {a=10, x=40, b=30, c=20}
 * ```
 *
 * `receiver` is list of equation sides Ex. a+b, 2b-a which all equals [variables].get(0) isa.
 *
 * @param variables All variables in equations
 * @param withResultValue additional solved equation this is the value of it isa, Ex. 48f
 * @param withResultGivenSide additional solved equation this is the variables side of it isa, Ex. x+a
 *
 * @return Map of all variables with actual values Ex. "x" = 40 , "a" = 30 etc... isa.
 *
 * @see [subItemOffsetIgnoreBorderMergeOffsetsVertical3]
 */
fun List<String>.solveAllFor(variables: List<String>, withResultValue: Float, withResultGivenSide: String): Map<String, Int> {
    val solutions = mutableMapOf<String, Term>()

    val xVariable = variables[0]
    val aVariable = variables[1]
    var counterForVariables = 2

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
                val newSolveForVariable: String = kotlin.run {
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
            solutions[xVariable] = "$xVariable=${this[0]}".solveForOnlyTwoSides(xVariable).second.performSubstitutionAndGrouping(solutions)

            val knownVariableTerm: Term = "z=$withResultGivenSide".solveForOnlyTwoSides("z").second.performSubstitutionAndGrouping(solutions)
            val coefficient = knownVariableTerm.numMultiplier * (if (knownVariableTerm.signIsPositive) 1f else (-1f))

            val realMap = mutableMapOf<String, Int>()

            // a variable value isa.
            val baseVariable = knownVariableTerm.variables[0]
            val baseVariableValue = Math.round(withResultValue.div(coefficient))
            realMap[baseVariable] = baseVariableValue

            variables.forEach {
                if (baseVariable == it) {
                    return@forEach
                }

                val multiplier = Math.round(solutions[it]?.signedCoefficient ?: throw RuntimeException("code 902830292023902"))

                realMap[it] = multiplier.times(baseVariableValue)
            }

            if (realMap.size != variables.size) {
                throw RuntimeException("code 980fhiw7382823")
            }

            return realMap
        }

        // todo what if span count is 1,2 or 3
        listOfEquationSides = bEquationsSides.map { it.toEquation() }
        counterForVariables++
    } while (keepLooping)

    throw RuntimeException("End of loop code 3827398792")
}

private val Term.signedCoefficient
    get() = numMultiplier * (if (signIsPositive) 1f else (-1f))

private fun List<Term>.performSubstitutionAndGrouping(solutions: Map<String, Term>): Term {
    val terms = map {
        val fromVariable = it.containsAndGet(solutions.keys.toList())
        val toTerm = solutions[fromVariable]

        if (fromVariable != null && toTerm != null) {
            it.substituteVariables(fromVariable, toTerm)
        }else {
            it
        }
    }.groupTerms()

    if (terms.size != 1) {
        throw RuntimeException("Substitution didn't lead to a single variable isa.")
    }

    return terms[0]
}

private fun Term.containsAndGet(variables: List<String>): String? {
    for (variable in this.variables) {
        if (variable in variables) {
            return variable
        }
    }

    return null
}

private fun Term.substituteVariables(fromVariable: String, to: Term): Term {
    return run {
        if (fromVariable in this.variables) {
            if (this.variables.size != 1) {
                throw RuntimeException("Unsupported era for multi variables in a term isa.")
            }

            val newSign = this.signIsPositive == to.signIsPositive
            this.copy(variables = to.variables, numMultiplier = this.numMultiplier.times(to.numMultiplier), signIsPositive = newSign)
        }else {
            this
        }
    }
}

private fun List<Term>.containsAndGet(variables: List<String>): String? {
    forEach {
        for (variable in it.variables) {
            if (variable in variables) {
                return variable
            }
        }
    }

    return null
}

private operator fun List<Term>.contains(variables: List<String>): Boolean {
    forEach {
        for (variable in it.variables) {
            if (variable in variables) {
                return true
            }
        }
    }

    return false
}

// ---- Private Extension fun

private fun List<Term>.substituteVariables(fromVariable: String, to: Term): List<Term> {
    return map {
        if (fromVariable in it.variables) {
            if (it.variables.size != 1) {
                throw RuntimeException("Unsupported era for multi variables in a term isa.")
            }

            val newSign = it.signIsPositive == to.signIsPositive
            it.copy(variables = to.variables, numMultiplier = it.numMultiplier.times(to.numMultiplier), signIsPositive = newSign)
        }else {
            it
        }
    }
}

/** Eliminates all terms with [Term.numMultiplier] == 0, so might return empty list isa. */
private fun List<Term>.removeZeros(): List<Term>
    = mapNotNull { it.removeZero() }

/** @return same given `receiver` or null if [Term.numMultiplier] is 0 isa. */
private fun Term.removeZero(): Term?
    = if (numMultiplier == 0f) null else this

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the collection.
 */
inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

private fun String.nearestIndexOf(vararg string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int? {
    val indices = string.mapNotNull { indexOfOrNull(it, startIndex, ignoreCase) }

    return indices.min()
}

private fun String.indexOfOrNull(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): Int?
    = indexOf(string, startIndex, ignoreCase).run { if (this == -1) null else this }

private fun String.toTerms(): List<Term> {
    // Returns +x and index of next + or - relative to receiver or null if there is no more isa.
    val seedFun: () -> Pair<String, Int?> = {
        when(val nearestIndexOf = nearestIndexOf("+", "-")) {
            null -> "+$this" to null
            0 -> {
                when (val nextNearestIndexOf = nearestIndexOf("+", "-", startIndex = 1)) {
                    null -> this to null
                    else -> substring(0, nextNearestIndexOf) to nextNearestIndexOf
                }
            }
            else -> {
                "+${substring(0, nearestIndexOf)}" to nearestIndexOf
            }
        }
    }

    val nextFun: (Pair<String, Int?>) -> Pair<String, Int?>? = {
        it.second?.run {
            when (val nextNearestIndexOf = nearestIndexOf("+", "-", startIndex = this.inc())) {
                null -> substring(this) to null
                else -> substring(this, nextNearestIndexOf) to nextNearestIndexOf
            }
        }
    }

    return generateSequence(seedFun, nextFun)
        .map { it.first }
        .map { Term.convertToTerm(it) }
        .toList()
        .groupTerms()
}

private fun List<Term>.groupTerms(): List<Term> {
    val variablesMap = groupBy { it.variables }
    if (variablesMap.size == size) {
        return this
    }

    return variablesMap.map {
        if (it.value.size > 1) {
            val numMultiplier = it.value.sumByFloat { term ->
                if (term.signIsPositive) term.numMultiplier else ( -1 * term.numMultiplier )
            }

            val signIsPositive = numMultiplier >= 0

            it.value[0].copy(numMultiplier = numMultiplier.absoluteValue, signIsPositive = signIsPositive)
        }else {
            it.value[0]
        }
    }
}

// ----- Data Classes

/**
 * @param variables Ex. [x,y] OR emptyList so just see numMultiplier isa.
 * @param numMultiplier Ex. 3 OR 3/7 OR 0.75, so not always can be float
 * @param signIsPositive true means positive
 */
data class Term(val variables: List<String>,
                var numMultiplier: Float,
                var signIsPositive: Boolean = true) {

    companion object {
        /** Sign will be taken from 1st char of given [stringOfTerm] of +ve as a fallback isa. */
        fun convertToTerm(stringOfTerm: String): Term {
            val signIsPositive = stringOfTerm[0] != '-'
            val stringWithoutSign = if (stringOfTerm[0] in listOf('+', '-')) {
                stringOfTerm.substring(1)
            } else {
                stringOfTerm
            }

            val numMultiplier = stringWithoutSign.filter { it in "0123456789." }.run { if (isEmpty()) "1" else this }.toFloat()

            val variables = stringWithoutSign.filterNot { it in "0123456789." }.map { it.toString() }

            return Term(variables, numMultiplier, signIsPositive)
        }
    }

}
