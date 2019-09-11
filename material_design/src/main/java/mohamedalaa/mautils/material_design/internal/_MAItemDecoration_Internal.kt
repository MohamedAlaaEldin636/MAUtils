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

package mohamedalaa.mautils.material_design.internal

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.core_kotlin.extensions.divRound
import mohamedalaa.mautils.core_kotlin.extensions.pairedIteration
import mohamedalaa.mautils.material_design.custom_classes.MAItemDecoration
import mohamedalaa.mautils.material_design.isBorderBottom
import mohamedalaa.mautils.material_design.isBorderLeft
import mohamedalaa.mautils.material_design.isBorderRight
import mohamedalaa.mautils.material_design.isBorderTop

/**
 * If isBorder then offset is 0 else suitable offset to maintain merged offsets isa.
 *
 * @param position position of item in [RecyclerView.Adapter] isa.
 */
internal fun MAItemDecoration.subItemOffsetIgnoreBorderMergeOffsetsVertical(
    layoutManager: LinearLayoutManager,
    position: Int
): Rect = subItemOffsetIgnoreBorderVertical(
    layoutManager,
    position,
    dividerDimenInPx
)

internal fun MAItemDecoration.subItemOffsetIgnoreBorderMergeOffsetsHorizontal(
    layoutManager: LinearLayoutManager,
    position: Int
): Rect = subItemOffsetIgnoreBorderMergeOffsetsVertical(layoutManager, position).apply {
    reverseLeftRightToTopBottomAndViceVersa()

    val half = dividerDimenInPx.divRound(2)
    left = if (layoutManager.isBorderLeft(position)) 0 else half
    right = if (layoutManager.isBorderRight(position)) 0 else half
}

internal fun MAItemDecoration.subItemOffsetIgnoreBorderNoMergeOffsetsVertical(
    layoutManager: LinearLayoutManager,
    position: Int
): Rect = subItemOffsetIgnoreBorderVertical(
    layoutManager,
    position,
    dividerDimenInPx.times(2)
)

internal fun MAItemDecoration.subItemOffsetIgnoreBorderNoMergeOffsetsHorizontal(
    layoutManager: LinearLayoutManager,
    position: Int
): Rect {
    val dividerDimenInPx = dividerDimenInPx.times(2)
    return subItemOffsetIgnoreBorderVertical(
        layoutManager,
        position,
        dividerDimenInPx
    ).apply {
        reverseLeftRightToTopBottomAndViceVersa()

        val half = dividerDimenInPx.divRound(2)
        left = if (layoutManager.isBorderLeft(position)) 0 else half
        right = if (layoutManager.isBorderRight(position)) 0 else half
    }
}

internal fun MAItemDecoration.subItemOffsetNoIgnoreBorderMergeOffsetsVertical(
    layoutManager: LinearLayoutManager,
    position: Int
): Rect = subItemOffsetNoIgnoreBorderVertical(
    layoutManager,
    position,
    dividerDimenInPx,
    true
)

internal fun MAItemDecoration.subItemOffsetNoIgnoreBorderMergeOffsetsHorizontal(
    layoutManager: LinearLayoutManager,
    position: Int
): Rect {
    val mergeOffsets = true
    return subItemOffsetNoIgnoreBorderVertical(
        layoutManager,
        position,
        dividerDimenInPx,
        mergeOffsets
    ).apply {
        reverseLeftRightToTopBottomAndViceVersa()

        val half = dividerDimenInPx.divRound(2)
        left = if (layoutManager.isBorderLeft(position) || mergeOffsets.not()) dividerDimenInPx else half
        right = if (layoutManager.isBorderRight(position) || mergeOffsets.not()) dividerDimenInPx else half
    }
}

internal fun MAItemDecoration.subItemOffsetNoIgnoreBorderNoMergeOffsetsVertical(
    layoutManager: LinearLayoutManager,
    position: Int
): Rect = subItemOffsetNoIgnoreBorderVertical(
    layoutManager,
    position,
    dividerDimenInPx,
    false
)

internal fun MAItemDecoration.subItemOffsetNoIgnoreBorderNoMergeOffsetsHorizontal(
    layoutManager: LinearLayoutManager,
    position: Int
): Rect {
    val mergeOffsets = false
    return subItemOffsetNoIgnoreBorderVertical(
        layoutManager,
        position,
        dividerDimenInPx,
        mergeOffsets
    ).apply {
        reverseLeftRightToTopBottomAndViceVersa()

        val half = dividerDimenInPx.divRound(2)
        left = if (layoutManager.isBorderLeft(position) || mergeOffsets.not()) dividerDimenInPx else half
        right = if (layoutManager.isBorderRight(position) || mergeOffsets.not()) dividerDimenInPx else half
    }
}

// ---- Private fun

/**
 * If isBorder then offset is 0 else suitable offset to maintain [dividerDimenInPx] between items isa.
 *
 * @param position position of item in [RecyclerView.Adapter] isa.
 */
private fun subItemOffsetIgnoreBorderVertical(
    layoutManager: LinearLayoutManager,
    position: Int,
    dividerDimenInPx: Int
): Rect {
    val spanCount = if (layoutManager is GridLayoutManager) layoutManager.spanCount else 1

    val rect = Rect()

    // Top & Bottom isa
    rect.top = if (layoutManager.isBorderTop(position)) 0 else dividerDimenInPx.divRound(2)
    rect.bottom = if (layoutManager.isBorderBottom(position)) 0 else dividerDimenInPx.divRound(2)

    // Left & Right isa.
    val isBorderLeft = layoutManager.isBorderLeft(position)
    val isBorderRight = layoutManager.isBorderRight(position)
    if (spanCount == 1) {
        return rect
    }else if (spanCount == 2) {
        rect.left = if (isBorderLeft) 0 else dividerDimenInPx.divRound(2)
        rect.right = if (isBorderRight) 0 else dividerDimenInPx.divRound(2)

        return rect
    }

    val numOfVariables = spanCount.dec()
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
    val solvedVariablesMap = xEquations.solveAllVariables(variables, dividerDimenInPx.toFloat(), baseSide)

    val remPosition = position.rem(spanCount)
    val palindromeResult = (0 until spanCount).palindromeToMin(remPosition)
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
 * If isBorder then offset is [dividerDimenInPx] else suitable offset to maintain [dividerDimenInPx]
 * or it's doubled value between items according to [mergeOffsets] isa.
 *
 * @param position position of item in [RecyclerView.Adapter] isa.
 */
private fun subItemOffsetNoIgnoreBorderVertical(
    layoutManager: LinearLayoutManager,
    position: Int,
    dividerDimenInPx: Int,
    mergeOffsets: Boolean
): Rect {
    val spanCount = if (layoutManager is GridLayoutManager) layoutManager.spanCount else 1

    val rect = Rect()

    // Top & Bottom isa
    rect.top = if (layoutManager.isBorderTop(position) || mergeOffsets.not()) dividerDimenInPx else dividerDimenInPx.divRound(2)
    rect.bottom = if (layoutManager.isBorderBottom(position) || mergeOffsets.not()) dividerDimenInPx else dividerDimenInPx.divRound(2)

    // Left & Right isa.
    if (spanCount == 1) {
        rect.left = dividerDimenInPx
        rect.right = dividerDimenInPx

        return rect
    }

    val variables = List(spanCount) { if (it == 0) 'x' else 'a'.plus(it.dec()) }

    val xVariable = variables[0]
    val xEquations = variables.drop(1).pairedIteration().map {
        if (mergeOffsets) {
            "${it.first}+${it.second ?: it.first}"
        }else {
            "0.5${it.first}+0.5${it.second ?: it.first}"
        }
    }.toMutableList()

    // Item Full Width Equations as well isa.
    val fwEquations = variables.pairedIteration().map {
        "${it.first}+${it.second ?: it.first}"
    }

    val baseSide = fwEquations[0]
    for (index in 1 until fwEquations.size) {
        val equation = baseSide + "=" + fwEquations[index]
        xEquations += equation.solveForOnlyTwoSides(xVariable).second.toEquation()
    }
    val withResultValue = dividerDimenInPx.toFloat()
    val solvedVariablesMap = xEquations.solveAllVariables(variables, withResultValue, xVariable.toString())

    val remPosition = position.rem(spanCount)
    val palindromeResult = (0 until spanCount).palindromeToMin(remPosition)
    val pairedVariables = mutableListOf<Char>().apply { addAll(variables) }.pairedIteration()
    val (left, right) = when (palindromeResult) {
        null -> {
            val variable = pairedVariables[remPosition].first

            val offset = solvedVariablesMap[variable] ?: throw RuntimeException("Unexpected error contact dev 3024309392")
            offset to offset
        }
        else -> {
            val (varLeft, varRight) = pairedVariables[palindromeResult.second]

            val (offsetLeft, offsetRight) = solvedVariablesMap[varLeft] to solvedVariablesMap[varRight]

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

private fun Rect.reverseLeftRightToTopBottomAndViceVersa() {
    set(top, left, bottom, right)
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
