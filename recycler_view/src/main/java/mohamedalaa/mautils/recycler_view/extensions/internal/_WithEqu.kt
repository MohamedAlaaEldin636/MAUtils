package mohamedalaa.mautils.recycler_view.extensions.internal

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import mohamedalaa.mautils.recycler_view.custom_classes.RCItemDecoration

internal fun RCItemDecoration.subItemOffsetIgnoreBorderMergeOffsetsVertical3(
    layoutManager: GridLayoutManager,
    position: Int
): Rect {
    val rect = Rect()
    if (layoutManager.spanCount == 1) {
        return rect
    } // todo else if 2 kda isa

    val numOfVariables = layoutManager.spanCount.dec()
    val variables = List(numOfVariables) { if (it == 0) "x" else 'a'.plus(it.dec()).toString() }

    val xVariable = variables[0]
    val xEquations = variables.getAsPair(startIndex = 1).map {
        "${it.first}+${it.second ?: it.first}"
    }.toMutableList()

    // FullDimen Equations as well isa todo
    val fdEquations = variables.getAsPair().map {
        "${it.first}+${it.second ?: it.first}"
    }

    if (fdEquations.size == 1) {
        // todo
    }else {
        val baseSide = fdEquations[0]
        for (index in 1 until fdEquations.size) {
            val equation = baseSide + "=" + fdEquations[index]
            xEquations += equation.solveForOnlyTwoSides(xVariable).second.toEquation()
        }
        // By now all x = is done isa.
    }

    // map of "x" 5a, "b" = "2a"(term) --- so string key and term value isa.

    // todo adjust left right top and bottom isa.
    return rect
}

private fun <T> List<T>.getAsPair(startIndex: Int = 0): List<Pair<T, T?>> {
    val listOfPairs = mutableListOf<Pair<T, T?>>()
    var tempPair: Pair<T?, T?> = null to null
    for (index in startIndex until size) {
        when {
            tempPair.first == null -> {
                if (index == lastIndex) {
                    listOfPairs += elementAt(index) to null
                }else {
                    tempPair = elementAt(index) to null
                }
            }
            tempPair.second == null -> {
                listOfPairs += (tempPair.first ?: throw RuntimeException("Unexpected error code 382738ddj0329")) to elementAt(index)

                tempPair = null to null
            }
        }
    }

    return listOfPairs
}