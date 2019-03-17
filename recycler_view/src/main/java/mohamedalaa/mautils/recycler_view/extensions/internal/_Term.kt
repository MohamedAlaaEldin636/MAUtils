package mohamedalaa.mautils.recycler_view.extensions.internal

import mohamedalaa.mautils.core_kotlin.nearestIndexOf
import mohamedalaa.mautils.core_kotlin.sumByFloat
import kotlin.math.absoluteValue

/**
 * Converts [List]<[Term]> to String as equation side
 * Ex. given b+c-a result -> +1.0b+1.0c-1.0a isa.
 */
internal fun List<Term>.toEquation(): String
    = joinToString(separator = "") { "${if (it.numMultiplier >= 0) "+" else "-"}${it.numMultiplier.absoluteValue}${it.variables.joinToString(separator = "")}" }

/**
 * Substitute values in [solutions] with `receiver` terms then groups them to single term isa.
 *
 * Ex. a+c+2a (c = -4a) -> -1a
 */
internal fun List<Term>.substituteAndGroupToSingleTerm(solutions: Map<Char, Term>): Term {
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

/**
 * @return Same as [contains] but instead of true the matched variable is returned instead
 * OR `null` if no matching is possible isa.
 */
internal fun Term.containsAndGet(variables: List<Char>): Char? {
    for (variable in this.variables) {
        if (variable in variables) {
            return variable
        }
    }

    return null
}

/**
 * Substitutes [fromVariable] with [Term.variables] in [to] isa.
 *
 * Ex. equation side a+c (c, 2a) Becomes a+2a isa.
 *
 * @see groupTerms
 * @see removeZeros
 */
internal fun Term.substituteVariables(fromVariable: Char, to: Term): Term {
    return run {
        if (fromVariable in this.variables) {
            if (this.variables.size != 1) {
                // If needed in future, then just substitute variable in old list and merge both isa.
                throw RuntimeException("Unsupported era for multi variables in a term isa.")
            }

            this.copy(variables = to.variables, numMultiplier = this.numMultiplier.times(to.numMultiplier))
        }else {
            this
        }
    }
}

/**
 * @return Same as [contains] but instead of true the matched variable is returned instead
 * OR `null` if no matching is possible isa.
 */
internal fun List<Term>.containsAndGet(variables: List<Char>): Char? {
    forEach {
        for (variable in it.variables) {
            if (variable in variables) {
                return variable
            }
        }
    }

    return null
}

/** @return true if any char in [variables] is inside any [Term.variables] of `receiver` isa. */
internal operator fun List<Term>.contains(variables: List<Char>): Boolean {
    forEach {
        for (variable in it.variables) {
            if (variable in variables) {
                return true
            }
        }
    }

    return false
}

/**
 * Substitutes [fromVariable] with [Term.variables] in [to] isa.
 *
 * Ex. equation side a+c (c, 2a) Becomes a+2a isa.
 *
 * @see groupTerms
 * @see removeZeros
 */
internal fun List<Term>.substituteVariables(fromVariable: Char, to: Term): List<Term> {
    return map {
        if (fromVariable in it.variables) {
            // If needed in future, then just substitute variable in old list and merge both isa.
            if (it.variables.size != 1) {
                throw RuntimeException("Unsupported operation for multi variables in a term isa.")
            }

            it.copy(variables = to.variables, numMultiplier = it.numMultiplier.times(to.numMultiplier))
        }else {
            it
        }
    }
}

/** Return a list Eliminating all terms with [Term.numMultiplier] == 0, so might return empty list isa. */
internal fun List<Term>.removeZeros(): List<Term>
    = mapNotNull { it.removeZero() }

/** @return same given `receiver` or `null` if [Term.numMultiplier] is 0 isa. */
internal fun Term.removeZero(): Term?
    = if (numMultiplier == 0f) null else this

/**
 * Converts equation side with 1 or more terms into [List]<[Term]> isa.
 *
 * @see [Term.convertToTerm]
 */
internal fun String.toTerms(): List<Term> {
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

/**
 * Transform x+b+3x To 4x+b isa.
 *
 * @see removeZero
 * @see removeZeros
 */
internal fun List<Term>.groupTerms(): List<Term> {
    val variablesMap = groupBy { it.variables }
    if (variablesMap.size == size) {
        return this
    }

    return variablesMap.map {
        if (it.value.size > 1) {
            val numMultiplier = it.value.sumByFloat { term -> term.numMultiplier }

            it.value[0].copy(numMultiplier = numMultiplier)
        }else {
            it.value[0]
        }
    }
}
