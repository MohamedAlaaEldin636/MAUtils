package mohamedalaa.mautils.recycler_view.new_test_1.extensions

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.core_kotlin.divRound
import mohamedalaa.mautils.core_kotlin.pairedIteration
import mohamedalaa.mautils.recycler_view.extensions.internal.solveAllVariables
import mohamedalaa.mautils.recycler_view.extensions.internal.solveForOnlyTwoSides
import mohamedalaa.mautils.recycler_view.extensions.internal.toEquation
import mohamedalaa.mautils.recycler_view.extensions.isBorderBottom
import mohamedalaa.mautils.recycler_view.extensions.isBorderLeft
import mohamedalaa.mautils.recycler_view.extensions.isBorderRight
import mohamedalaa.mautils.recycler_view.extensions.isBorderTop
import mohamedalaa.mautils.recycler_view.new_test_1.MAItemDecoration

/**
 * todo probs
 * removing 1st item makes it magnified ?!
 */

/**
 * If isBorder then offset is 0 else suitable offset to maintain merged offsets isa.
 *
 * @param position position of item in [RecyclerView.Adapter] isa.
 */
internal fun MAItemDecoration.subItemOffsetIgnoreBorderMergeOffsetsVertical(
    layoutManager: GridLayoutManager,
    position: Int
): Rect = subItemOffsetIgnoreBorderVertical(layoutManager, position, fullDimen)

internal fun MAItemDecoration.subItemOffsetIgnoreBorderNoMergeOffsetsVertical(
    layoutManager: GridLayoutManager,
    position: Int
): Rect = subItemOffsetIgnoreBorderVertical(layoutManager, position, fullDimen.times(2))

internal fun MAItemDecoration.subItemOffsetNoIgnoreBorderMergeOffsetsVertical(
    layoutManager: GridLayoutManager,
    position: Int
): Rect {

    return Rect()
}

internal fun MAItemDecoration.subItemOffsetNoIgnoreBorderNoMergeOffsetsVertical(
    layoutManager: GridLayoutManager,
    position: Int
): Rect {
    return Rect()
}

// ---- Private fun

/**
 * If isBorder then offset is 0 else suitable offset to maintain [fullDimen] between items isa.
 *
 * @param position position of item in [RecyclerView.Adapter] isa.
 */
private fun subItemOffsetIgnoreBorderVertical(
    layoutManager: GridLayoutManager,
    position: Int,
    fullDimen: Int
): Rect {
    val rect = Rect()

    // Top & Bottom isa
    rect.top = if (layoutManager.isBorderTop(position)) 0 else fullDimen.divRound(2)
    rect.bottom = if (layoutManager.isBorderBottom(position)) 0 else fullDimen.divRound(2)

    // Left & Right isa.
    val isBorderLeft = layoutManager.isBorderLeft(position)
    val isBorderRight = layoutManager.isBorderRight(position)
    if (layoutManager.spanCount == 1) {
        return rect
    }else if (layoutManager.spanCount == 2) {
        rect.left = if (isBorderLeft) 0 else fullDimen.divRound(2)
        rect.right = if (isBorderRight) 0 else fullDimen.divRound(2)

        return rect
    }

    val numOfVariables = layoutManager.spanCount.dec()
    val variables = List(numOfVariables) { if (it == 0) 'x' else 'a'.plus(it.dec()) }

    val xVariable = variables[0]
    val xEquations = variables.drop(1).pairedIteration().map {
        "${it.first}+${it.second ?: it.first}"
    }.toMutableList()

    // FullDimen Equations as well isa.
    val fdEquations = variables.pairedIteration().map {
        "${it.first}+${it.second ?: it.first}"
    }

    val baseSide = fdEquations[0]
    for (index in 1 until fdEquations.size) {
        val equation = baseSide + "=" + fdEquations[index]
        xEquations += equation.solveForOnlyTwoSides(xVariable).second.toEquation()
    }
    val solvedVariablesMap = xEquations.solveAllVariables(variables, fullDimen.toFloat(), baseSide)

    val remPosition = position.rem(layoutManager.spanCount)
    val palindromeResult = (0 until layoutManager.spanCount).palindromeToMin(remPosition)
    val withZeroVariablesPaired = mutableListOf('0').apply { addAll(variables) }.pairedIteration()
    val (left, right) = when (palindromeResult) {
        null -> {
            val variable = withZeroVariablesPaired[remPosition].first

            val offset = solvedVariablesMap[variable] ?: throw RuntimeException("Unexpected error contact dev 3024309392")
            offset to offset
        }
        else -> {
            val (varLeft, varRight) = withZeroVariablesPaired[palindromeResult.second]

            val (offsetLeft, offsetRight) = when (varLeft) {
                '0' -> 0 to solvedVariablesMap[varRight]
                else -> solvedVariablesMap[varLeft] to solvedVariablesMap[varRight]
            }

            if (offsetLeft == null || offsetRight == null) {
                throw RuntimeException("Unexpected error contact dev 30392")
            }

            if (palindromeResult.first) offsetRight to offsetLeft else offsetLeft to offsetRight
        }
    }

    rect.left = left
    rect.right = right

    return rect
}

/**
 * [IntRange.step] Must be 1 isa.
 * [IntRange.start] Must be 0 isa.
 *
 * See Examples below for more clarification isa.
 *
 * 0, 1, 2 -> 0 false to itself & 1 null & 2 true to 0
 * 0, 1, 2, 3 -> 0 false to itself & 1 false to itself & 2 true to 1 & 3 true to 0
 */
private fun IntRange.palindromeToMin(value: Int): Pair<Boolean, Int>? {
    val centerValue = last.toFloat().div(2)

    val floatedValue = value.toFloat()
    return when {
        floatedValue == centerValue -> null
        floatedValue > centerValue -> {
            val diff = floatedValue.minus(centerValue)

            true to centerValue.minus(diff).toInt()
        }
        else -> false to value
    }
}
