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

package mohamedalaa.mautils.core_kotlin

import kotlin.math.roundToInt

/**
 * @return true if number is even
 *
 * @see [Int.isOdd]
 */
fun Int.isEven(): Boolean = rem(2) == 0

/**
 * @return true if number is even
 *
 * @see [Float.isOdd]
 */
fun Float.isEven(): Boolean = rem(2) == 0F

/**
 * @return true if number is even
 *
 * @see [Long.isOdd]
 */
fun Long.isEven(): Boolean = rem(2) == 0L

/**
 * @return true if number is even
 *
 * @see [Double.isOdd]
 */
fun Double.isEven(): Boolean = rem(2) == 0.0

/**
 * @return true if number is odd
 *
 * @see [Int.isEven]
 */
fun Int.isOdd(): Boolean = isEven().not()

/**
 * @return true if number is odd
 *
 * @see [Float.isEven]
 */
fun Float.isOdd(): Boolean = isEven().not()

/**
 * @return true if number is odd
 *
 * @see [Long.isEven]
 */
fun Long.isOdd(): Boolean = isEven().not()

/**
 * @return true if number is odd
 *
 * @see [Double.isEven]
 */
fun Double.isOdd(): Boolean = isEven().not()

/** @return true if `receiver` number is positive, otherwise false. */
fun Number.isPositive(): Boolean {
    return when (this) {
        is Int -> this >= 0
        is Short -> this >= 0
        is Byte -> this >= 0
        is Float -> this >= 0
        is Double -> this >= 0
        is Long -> this >= 0
        else -> throw RuntimeException("Not supported Number.isPositive() with class -> ${this.javaClass} isa.")
    }
}

/** @return true if `receiver` number is negative, otherwise false. */
fun Number.isNegative(): Boolean = isPositive().not()

/** rounds the division result so 3.divRound() == 2 instead of 3.div(2) == 1 as 1.5 is rounded to 2 */
fun Int.divRound(other: Int): Int
    = toFloat().div(other.toFloat()).roundToInt()
