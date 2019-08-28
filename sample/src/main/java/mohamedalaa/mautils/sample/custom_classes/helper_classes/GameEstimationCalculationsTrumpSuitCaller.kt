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

package mohamedalaa.mautils.sample.custom_classes.helper_classes

import androidx.room.Embedded

/** [Int] indicates value of score, [Boolean] indicate include round win/lose score */
data class GameEstimationCalculationsTrumpSuitCaller(

    @Embedded(prefix = "zero_")
    val zero: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(),
    @Embedded(prefix = "one_")
    val one: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(),
    @Embedded(prefix = "two_")
    val two: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(),
    @Embedded(prefix = "three_")
    val three: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(),

    @Embedded(prefix = "four_")
    val four: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        20,
        20
    ),
    @Embedded(prefix = "five_")
    val five: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        20,
        20
    ),
    @Embedded(prefix = "six_")
    val six: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        20,
        20
    ),
    @Embedded(prefix = "seven_")
    val seven: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        20,
        20
    ),

    @Embedded(prefix = "eight_")
    val eight: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        64,
        32,
        includeTricksScoreWin = false,
        includeTricksScoreLose = false
    ),
    @Embedded(prefix = "nine_")
    val nine: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        81,
        40,
        includeTricksScoreWin = false,
        includeTricksScoreLose = false
    ),
    @Embedded(prefix = "ten_")
    val ten: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        100,
        50,
        includeTricksScoreWin = false,
        includeTricksScoreLose = false
    ),

    @Embedded(prefix = "eleven_")
    val eleven: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        121,
        60,
        includeTricksScoreWin = false,
        includeTricksScoreLose = false
    ),
    @Embedded(prefix = "twelve_")
    val twelve: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        144,
        72,
        includeTricksScoreWin = false,
        includeTricksScoreLose = false
    ),
    @Embedded(prefix = "thirteen_")
    val thirteen: GameEstimationCalculationsSpecificToCaller = GameEstimationCalculationsSpecificToCaller(
        169,
        84,
        includeTricksScoreWin = false,
        includeTricksScoreLose = false
    )
)