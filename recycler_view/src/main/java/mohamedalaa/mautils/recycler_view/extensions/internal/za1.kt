package mohamedalaa.mautils.recycler_view.extensions.internal

// receiver is equation isa.
private fun solveFor(solveForVar: String = "x", equation: String = "x+a=b+c") {
    val sidesList = equation.split("=")
    val leftHandSide = sidesList[0]
    val rightHandSide = sidesList[1]

    val leftHandSideTerms: List<Term>
    val rightHandSideTerms: List<Term>
}
