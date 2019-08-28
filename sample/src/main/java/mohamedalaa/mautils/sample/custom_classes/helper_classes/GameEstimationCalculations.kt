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
import androidx.room.Entity
import androidx.room.PrimaryKey


/** no args constructor -> is the app default calculations isa. */
@Entity(tableName = "estimationCalculations")
data class GameEstimationCalculations(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    // Default cannot be used - Unique like id isa.
    var name: String = "DEFAULT_SLOT_NAME",

    var description: String? = null,

    /** null means no condition */
    var extraRoundsConditionKingSubKingScoreDiff: Int? = 13,

    /**
     * null means no condition, cannot be 1 since 1 zero king means he wins the game isa,
     * 2 means if 2 or more zero king add extra rounds according to [],
     * 3 means if 2 zero king then no extra rounds, only if there was 3 or more etcc... isa.
     */
    var extraRoundsConditionZeroKingsNumber: Int? = 2,

    var extraRoundsCycles: GameEstimationCalculationsExtraRoundsCycles = GameEstimationCalculationsExtraRoundsCycles.Fixed(
        1
    ),

    @Embedded(prefix = "roundsConfiguration_")
    var extraEstimationRoundsConfiguration: GameEstimationRoundsConfiguration = GameEstimationRoundsConfiguration(
        3,
        0,
        null,
        GameEstimationFastRoundsSort.RANDOM
    ),

    var gameMaxNoWinnersMultiplier: GameEstimationNoWinnerMultiplier = GameEstimationNoWinnerMultiplier.Unlimited,

    // includes normal dash - dash call - blind dash
    var maxNumberOfDashes: Int = 2,

    // Base Scores
    var roundWin: Int = 10,
    var roundWinNoTricksMultiplier: Int = 1,
    var roundLose: Int = 0,
    var roundLoseNoTricksMultiplier: Int = 1,

    // Additional Scores
    var onlyWin: Int = 10,
    var onlyLose: Int = 10,

    var withWin: Int = 10,
    var withLose: Int = 10,

    var riskWin: Int = 10,
    var riskLose: Int = 10,

    var regularDashUnderWin: Int = 10,
    var regularDashUnderLose: Int = 10,
    var regularDashOverWin: Int = 0,
    var regularDashOverLose: Int = 0,

    var dashCallUnderWin: Int = 23,
    var dashCallUnderLose: Int = 23,
    var dashCallOverWin: Int = 13,
    var dashCallOverLose: Int = 13,

    var blindDashUnderWin: Int = 90,
    var blindDashUnderLose: Int = 90,
    var blindDashOverWin: Int = 40,
    var blindDashOverLose: Int = 40,

    @Embedded(prefix = "trumpSuitCaller_")
    var gameCalculationsTrumpSuitCaller: GameEstimationCalculationsTrumpSuitCaller = GameEstimationCalculationsTrumpSuitCaller()
)
