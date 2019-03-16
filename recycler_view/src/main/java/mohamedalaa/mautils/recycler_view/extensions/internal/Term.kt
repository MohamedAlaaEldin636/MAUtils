package mohamedalaa.mautils.recycler_view.extensions.internal

/** todo make it internal after making all it's extension fun as internal after done with testing isa.
 * @param variables Ex. [x, y] OR emptyList so just see numMultiplier isa.
 * @param numMultiplier Ex. 3.0, -2.4 etc... isa.
 */
data class Term(val variables: List<Char>, var numMultiplier: Float) {

    companion object {

        private const val NUMBERS_SIGNS_DOT = "-+0123456789."

        /**
         * Converts 1 term in string to [Term] isa.
         *
         * Sign will be taken from 1st char of given [string] or +ve as a fallback,
         * see Examples below for more clarification isa.
         *
         * +3.0x -> Term([ x ], 3.0)
         * 3.9x -> Term([ x ], 3.9)
         * -3.9x -> Term([ x ], -3.9)
         */
        fun convertToTerm(string: String): Term {
            val variables = string.filter { it !in NUMBERS_SIGNS_DOT }.map { it }

            val numMultiplier = string.filter { it in NUMBERS_SIGNS_DOT }.run {
                when (this) {
                    "", "+" -> "1"
                    "-" -> "-1"
                    else -> this
                }
            }.toFloat()

            return Term(variables, numMultiplier)
        }
    }

}