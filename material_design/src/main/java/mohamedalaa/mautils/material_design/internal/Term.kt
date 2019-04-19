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

/**
 * @param variables Ex. [x, y] OR emptyList so just see numMultiplier isa.
 * @param numMultiplier Ex. 3.0, -2.4 etc... isa.
 */
internal data class Term(val variables: List<Char>, var numMultiplier: Float) {

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