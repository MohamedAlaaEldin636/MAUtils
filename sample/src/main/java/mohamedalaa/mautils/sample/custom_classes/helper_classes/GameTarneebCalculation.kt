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
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */

@MASealedAbstractOrInterface
@MARoomGsonTypeConverter
sealed class GameTarneebCalculation {

    /**
     * ```
     * | 2 to 4         | same value so for 2 -> +2 |
     * | -------------- | ------------------------- |
     * | 5 to 8         | value * 2 so for 6 -> +12 |
     * | -------------- | ------------------------- |
     * | 9              | value * 3 so for 9 -> +27 |
     * | -------------- | ------------------------- |
     * | 10 or more     | win game or -40           |
     * ```
     *
     * winning exceptions if winMultiplier == null ( in UI type "Win Whole Game" ) isa.
     */
    class Individual(
        @MARoomGsonTypeConverter
        var calculationList: List<GameTarneebCalculationIndividual> = listOf(
            GameTarneebCalculationIndividual(0, 1, 1, null, null),
            GameTarneebCalculationIndividual(1, 1, 1, null, null),
            GameTarneebCalculationIndividual(2, 1, 1, null, null),
            GameTarneebCalculationIndividual(3, 1, 1, null, null),
            GameTarneebCalculationIndividual(4, 1, 1, null, null),

            GameTarneebCalculationIndividual(5, 2, 2, null, null),
            GameTarneebCalculationIndividual(6, 2, 2, null, null),
            GameTarneebCalculationIndividual(7, 2, 2, null, null),
            GameTarneebCalculationIndividual(8, 2, 2, null, null),

            GameTarneebCalculationIndividual(9, 3, 3, null, null),

            GameTarneebCalculationIndividual(10, null, null, null, 40),
            GameTarneebCalculationIndividual(11, null, null, null, 40),
            GameTarneebCalculationIndividual(12, null, null, null, 40),
            GameTarneebCalculationIndividual(13, null, null, null, 40)
        )
    ) : GameTarneebCalculation() {
        companion object;

        override fun equals(other: Any?): Boolean {
            if (other !is Individual) return false

            return calculationList == other.calculationList
        }

        override fun hashCode(): Int = ObjectsCompat.hash(*calculationList.toTypedArray())
    }

    /**
     * - Win (was bidder)
     *      No. of tricks
     *      OR 16 if got 13 while didn't bid for it
     *      OR 26 if bid 13
     * - Win (was NOT bidder)
     *      No. of tricks
     * - Lose (was bidder)
     *      Bid value
     *      OR 16 if bid was 13
     * - Lose (was NOT bidder)
     *      Zero
     *
     * ==> UI should be
     * // Win (bidder)
     *
     * /* Exceptions */
     *
     * // Win (Not Bidder) etc... isa.
     *
     * @param exceptions not null values are prior to null ones isa, since null ones indicate any value isa,
     * Also there cannot be null for both estimated- & actual-tricks isa.
     */
    class Team(
        var win: GameTarneebCalculationTeamDynamicValue = GameTarneebCalculationTeamDynamicValue.NumberOfTricks(1),

        var winNotBidder: GameTarneebCalculationTeamDynamicValue = GameTarneebCalculationTeamDynamicValue.NumberOfTricks(1),

        var lose: GameTarneebCalculationTeamDynamicValue = GameTarneebCalculationTeamDynamicValue.BidValue(1),

        var loseNotBidder: GameTarneebCalculationTeamDynamicValue = GameTarneebCalculationTeamDynamicValue.FixedValue(0),

        @MARoomGsonTypeConverter
        var exceptions: Set<GameTarneebCalculationTeamException> = setOf(
            GameTarneebCalculationTeamException(
                estimatedTricks = null,
                actualTricks = 13,

                isWinner = true,
                isBidder = true,
                score = GameTarneebCalculationTeamDynamicValue.FixedValue(16)
            ),
            GameTarneebCalculationTeamException(
                estimatedTricks = 13,
                actualTricks = 13,

                isWinner = true,
                isBidder = true,
                score = GameTarneebCalculationTeamDynamicValue.FixedValue(26)
            ),
            GameTarneebCalculationTeamException(
                estimatedTricks = null,
                actualTricks = 13,

                isWinner = false,
                isBidder = true,
                score = GameTarneebCalculationTeamDynamicValue.FixedValue(16)
            )
        )
    ) : GameTarneebCalculation() {
        companion object;

        override fun equals(other: Any?): Boolean {
            if (other !is Team) return false

            return listOf(
                win == other.win,
                winNotBidder == other.winNotBidder,
                lose == other.lose,
                loseNotBidder == other.loseNotBidder,
                exceptions == other.exceptions
            ).allTrue()
        }

        override fun hashCode(): Int = ObjectsCompat.hash(
            win,
            winNotBidder,
            lose,
            loseNotBidder,
            exceptions
        )
    }

}
