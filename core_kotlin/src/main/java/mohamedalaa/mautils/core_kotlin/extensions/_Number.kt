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

@file:JvmName("NumberUtils")

package mohamedalaa.mautils.core_kotlin.extensions

import kotlin.math.roundToInt

/**
 * @return true if `receiver` number is even, otherwise false.
 *
 * @see isOdd
 */
fun Number.isEven(): Boolean {
    return when (this) {
        is Int -> rem(2) == 0
        is Short -> rem(2) == 0
        is Byte -> rem(2) == 0
        is Float -> rem(2) == 0f
        is Double -> rem(2) == 0.0
        is Long -> rem(2) == 0L
        else -> throw RuntimeException("Not supported with class -> ${this.javaClass} isa.")
    }
}

/**
 * @return true if `receiver` number is odd, otherwise false.
 *
 * @see isEven
 */
fun Number.isOdd(): Boolean = isEven().not()

/**
 * @return true if `receiver` number is positive, otherwise false.
 *
 * @see isNegative
 */
fun Number.isPositive(): Boolean {
    return when (this) {
        is Int -> this >= 0
        is Short -> this >= 0
        is Byte -> this >= 0
        is Float -> this >= 0
        is Double -> this >= 0
        is Long -> this >= 0
        else -> throw RuntimeException("Not supported with class -> ${this.javaClass} isa.")
    }
}

/**
 * @return true if `receiver` number is negative, otherwise false.
 *
 * @see isPositive
 */
fun Number.isNegative(): Boolean = isPositive().not()

/**
 * rounds half up the division result so 3.divRound() == 2 instead of 3.div(2) == 1 as 1.5 is rounded to 2.
 */
fun Int.divRound(other: Int): Int = toFloat().div(other.toFloat()).roundToInt()
