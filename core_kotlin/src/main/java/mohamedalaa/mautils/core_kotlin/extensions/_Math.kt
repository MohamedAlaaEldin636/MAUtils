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

@file:JvmName("MathUtils")

package mohamedalaa.mautils.core_kotlin.extensions

import java.math.BigDecimal

/**
 * Rounds to the nearest [decimalPlace] count, with roundingMode == [BigDecimal.ROUND_HALF_UP]
 * ```
 * val value1 = 0.33333334f.round(3)
 * val value2 = 0.66666667f.round(3)
 * println(value1) // 0.333
 * println(value2) // 0.667
 * ```
 *
 * @see Double.round
 */
fun Float.round(decimalPlace: Int): Float {
    var bigDecimal = BigDecimal(this.toString())
    bigDecimal = bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bigDecimal.toFloat()
}

/**
 * Rounds to the nearest [decimalPlace] count, with roundingMode == [BigDecimal.ROUND_HALF_UP]
 *
 * @see Float.round
 */
fun Double.round(decimalPlace: Int): Float {
    var bigDecimal = BigDecimal(this.toString())
    bigDecimal = bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bigDecimal.toFloat()
}