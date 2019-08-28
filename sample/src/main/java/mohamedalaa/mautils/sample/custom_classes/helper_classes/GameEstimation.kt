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

import androidx.core.util.ObjectsCompat
import androidx.room.Embedded

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */
class GameEstimation(
    var roundsLeft: Int = 0,

    var gameEstimationType: GameEstimationType = GameEstimationType.FullBola,

    @Embedded(prefix = "estimationCalculations_")
    var gameEstimationCalculations: GameEstimationCalculations = GameEstimationCalculations(),

    var zeroKingEnabled: Boolean = false
) : GameAbstract() {

    override fun equals(other: Any?): Boolean {
        if (other !is GameEstimation) return false

        return super.equals(other) && listOf(
            roundsLeft == other.roundsLeft,
            gameEstimationType == other.gameEstimationType,
            gameEstimationCalculations == other.gameEstimationCalculations,
            zeroKingEnabled == other.zeroKingEnabled
        ).allTrue()
    }

    override fun hashCode(): Int {
        return ObjectsCompat.hash(
            super.hashCode(),
            roundsLeft,
            gameEstimationType,
            gameEstimationCalculations,
            zeroKingEnabled
        )
    }

    fun copy(
        roundsLeft: Int = this.roundsLeft,
        gameEstimationType: GameEstimationType = this.gameEstimationType,
        gameEstimationCalculations: GameEstimationCalculations = this.gameEstimationCalculations,
        zeroKingEnabled: Boolean = this.zeroKingEnabled
    ): GameEstimation {
        return GameEstimation(
            roundsLeft, gameEstimationType, gameEstimationCalculations, zeroKingEnabled
        ).apply {
            this@GameEstimation.copyThisToOther(this)
        }
    }

}
