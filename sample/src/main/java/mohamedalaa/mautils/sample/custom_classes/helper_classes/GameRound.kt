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

import mohamedalaa.mautils.sample.model_for_testing.IntMap

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */
data class GameRound (
    var roundNumber: Int = 1,
    var playersTricks: IntMap<GameTrickAbstract> = buildGameMap { GameEstimationTrick() },
    var gameRoundState: GameRoundState = GameRoundState.Bidding(),
    var gameRoundBiddingType: GameRoundBiddingType = GameRoundBiddingType.NormalBidding,
    var isDoubledAdditionally: Boolean = false
) {

    companion object {

        fun newBiddingRoundForEstimation(
            roundNumber: Int,
            autoNextRoundState: Boolean,
            gameEstimationType: GameEstimationType,
            gameEstimationCalculations: GameEstimationCalculations
        ): GameRound {
            val fastRoundTrumpSuit: GameTrumpSuit? = getFastRoundTrumpSuitIfPossible(
                gameEstimationType, gameEstimationCalculations, roundNumber
            )

            return GameRound(
                roundNumber,
                buildGameMap {
                    GameEstimationTrick()
                },
                GameRoundState.Bidding(
                    autoNextRoundState = autoNextRoundState,
                    isCurrentlyValid = false
                ),
                if (fastRoundTrumpSuit == null) {
                    GameRoundBiddingType.NormalBidding
                }else {
                    GameRoundBiddingType.FastBidding(fastRoundTrumpSuit)
                },
                false
            )
        }

        fun newBiddingRoundForTarneeb(
            roundNumber: Int,
            autoNextRoundState: Boolean,
            gameTarneebType: GameTarneebType
        ): GameRound {
            val trumpSuit = gameTarneebType.gameTarneebTypeTrumpSuit.run {
                if (this is GameTarneebTypeTrumpSuit.Fixed) {
                    this.trumpSuit ?: GameTrumpSuit.allAscending().random()
                }else {
                    null
                }
            }

            return GameRound(
                roundNumber,
                buildGameMap {
                    GameTarneebTrick(
                        gameTrumpSuit = trumpSuit
                    )
                },
                GameRoundState.Bidding(
                    autoNextRoundState = autoNextRoundState,
                    isCurrentlyValid = false
                ),
                GameRoundBiddingType.NormalBidding,
                false
            )
        }

        private fun getFastRoundTrumpSuitIfPossible(
            gameEstimationType: GameEstimationType,
            gameEstimationCalculations: GameEstimationCalculations,
            roundNumber: Int
        ) : GameTrumpSuit? = GameTrumpSuit.NoTrump
        /*
        {
            val (mainRoundsNormalRoundsNo, mainRoundsFastRoundsNo) = gameEstimationType.gameEstimationRoundsConfiguration.run {
                normalRoundsNumber to fastRoundsNumber
            }
            val mainRoundsTotalNo = mainRoundsNormalRoundsNo + mainRoundsFastRoundsNo

            // -- Inside Main Rounds -- //

            if (roundNumber <= mainRoundsTotalNo) {
                return if (roundNumber <= mainRoundsNormalRoundsNo) null else {
                    val fastRoundNumber = roundNumber - mainRoundsNormalRoundsNo

                    gameEstimationType.gameEstimationRoundsConfiguration.fastRoundTrumpSuit(fastRoundNumber)
                }
            }

            // -- Inside Extra Rounds -- //

            val (extraRoundsNormalRoundsNo, extraRoundsFastRoundsNo) = gameEstimationCalculations.extraEstimationRoundsConfiguration.run {
                normalRoundsNumber to fastRoundsNumber
            }
            val extraRoundsTotalNo = extraRoundsNormalRoundsNo + extraRoundsFastRoundsNo

            val roundNumberExcludingMainRounds = roundNumber - mainRoundsTotalNo
            val currentExtraRoundNumber = roundNumberExcludingMainRounds % extraRoundsTotalNo

            return if (currentExtraRoundNumber <= extraRoundsNormalRoundsNo) null else {
                val fastRoundNumber = currentExtraRoundNumber - extraRoundsNormalRoundsNo

                gameEstimationCalculations.extraEstimationRoundsConfiguration.fastRoundTrumpSuit(fastRoundNumber)
            }
        }
         */

    }

}