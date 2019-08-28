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
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
import mohamedalaa.mautils.sample.model_for_testing.IntMap

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */

@MASealedAbstractOrInterface
sealed class GameRoundState {

    class Bidding(
        /** Used for forward icon in round, so if all calls are valid Then auto go to calculating from bidding isa. */
        var autoNextRoundState: Boolean = true,
        /** in case [autoNextRoundState] is false, below become true if next round is valid to go, so icon tint become green isa. */
        var isCurrentlyValid: Boolean = false
    ): GameRoundState() {

        fun copy(
            autoNextRoundState: Boolean = this.autoNextRoundState,
            isCurrentlyValid: Boolean = this.isCurrentlyValid
        ): Bidding {
            return Bidding(
                autoNextRoundState, isCurrentlyValid
            )
        }

    }

    /**
     * @property isAutoCompleteTricksValid only if sum of non null actual tricks == 13 so the rest will take 0 actal tricks isa,
     * or the null actual tricks all can be made right resulting in all players having 13 actual tricks as a sum isa.
     * @property gameRisk Ex. + 6, x2, - 10
     */
    class Calculating(
        /** Used for forward icon in round, so if all calls are valid Then auto go to calculating from bidding isa. */
        var autoNextRoundState: Boolean,
        /** in case [autoNextRoundState] is false, below become true if next round is valid to go, so icon tint become green isa. */
        var isCurrentlyValid: Boolean,
        var isAutoCompleteTricksValid: Boolean,
        var gameRisk: String
    ) : GameRoundState() {

        fun copy(
            autoNextRoundState: Boolean = this.autoNextRoundState,
            isCurrentlyValid: Boolean = this.isCurrentlyValid,
            isAutoCompleteTricksValid: Boolean = this.isAutoCompleteTricksValid,
            gameRisk: String = this.gameRisk
        ): Calculating {
            return Calculating(
                autoNextRoundState, isCurrentlyValid, isAutoCompleteTricksValid, gameRisk
            )
        }

    }

    /**
     * @property ranksSnapshot See property [scoresSnapshot]
     * @property scoresSnapshot scores including this round so after calculating total scores via [GameAbstract.calculateScoresPlusPreScores]
     * including this round Put the values here for more clarification if the game is in this point [Finished]
     * (end of the game isa) then [GameAbstract.scores] == [GameAbstract.calculateScoresPlusPreScores] isa,
     * to know zero round scores (initial scores) see [GameAbstract.preScores] isa.
     */
    class Finished(
        /** Used for forward icon in round, so if all calls are valid Then auto go to calculating from bidding isa. */
        var autoNextRoundState: Boolean,
        var gameRisk: String,
        var noWinners: Boolean,
        var ranksSnapshot: IntMap<GameRank>,
        var scoresSnapshot: IntMap<Int>,
        var onlyWinnerIndex: Int?,
        var onlyLoserIndex: Int?
    ) : GameRoundState() {

        fun copy(
            autoNextRoundState: Boolean = this.autoNextRoundState,
            gameRisk: String = this.gameRisk,
            noWinners: Boolean = this.noWinners,
            ranksSnapshot: IntMap<GameRank> = this.ranksSnapshot,
            scoresSnapshot: IntMap<Int> = this.scoresSnapshot,
            onlyWinnerIndex: Int? = this.onlyWinnerIndex,
            onlyLoserIndex: Int? = this.onlyLoserIndex
        ): Finished {
            return Finished(
                autoNextRoundState, gameRisk, noWinners, ranksSnapshot, scoresSnapshot, onlyWinnerIndex, onlyLoserIndex
            )
        }

    }

    override fun equals(other: Any?): Boolean {
        if (other !is GameRoundState) return false

        return when {
            this is Bidding && other is Bidding -> listOf(
                autoNextRoundState == other.autoNextRoundState,
                isCurrentlyValid == other.isCurrentlyValid
            ).allTrue()
            this is Calculating && other is Calculating -> listOf(
                autoNextRoundState == other.autoNextRoundState,
                isCurrentlyValid == other.isCurrentlyValid,
                isAutoCompleteTricksValid == other.isAutoCompleteTricksValid,
                gameRisk == other.gameRisk
            ).allTrue()
            this is Finished && other is Finished -> listOf(
                autoNextRoundState == other.autoNextRoundState,
                gameRisk == other.gameRisk,
                noWinners == other.noWinners,
                ranksSnapshot == other.ranksSnapshot,
                scoresSnapshot == other.scoresSnapshot,
                onlyWinnerIndex == other.onlyWinnerIndex,
                onlyLoserIndex == other.onlyLoserIndex
            ).allTrue()
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is Bidding -> ObjectsCompat.hash(
                autoNextRoundState,
                isCurrentlyValid
            )
            is Calculating -> ObjectsCompat.hash(
                autoNextRoundState,
                isCurrentlyValid,
                isAutoCompleteTricksValid,
                gameRisk
            )
            is Finished -> ObjectsCompat.hash(
                autoNextRoundState,
                gameRisk,
                noWinners,
                ranksSnapshot,
                scoresSnapshot,
                onlyWinnerIndex,
                onlyLoserIndex
            )
        }
    }

}