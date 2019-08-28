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
import androidx.room.Entity
import androidx.room.PrimaryKey
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */

/**
 * - [GameTarneebMinTotalBids.minTotalBids] in [minBidForPlayer] has always a max value of 13 isa.
 *
 * @param minBidForPlayer it varies from 1 player to another in same round since it depend on his/her score isa,
 * Also note that [GameTarneebMinTotalBids.forMaxScoreOf] is included isa.
 */
@MASealedAbstractOrInterface // todo this is the problem if commented then it is ok isa.
@MARoomGsonTypeConverter
open class GameTarneebType(
    @Embedded(prefix = "minBidForPlayer_")
    var minBidForPlayer: GameTarneebMinTotalBids,

    var gameTarneebTypeTrumpSuit: GameTarneebTypeTrumpSuit,

    var callAfterPassEnabled: Boolean,
    var doubleEnabled: Boolean,
    var reDoubleEnabled: Boolean,

    /** if min total bids condition is not met, then redeal cards isa, if null then no condition exists isa. */
    @Embedded(prefix = "minTotalBids_")
    var minTotalBids: GameTarneebMinTotalBids?,

    var finalWinCondition: GameTarneebFinalWinCondition
) {

    override fun toString(): String {
        return listOf(
            minBidForPlayer,
            gameTarneebTypeTrumpSuit,
            callAfterPassEnabled,
            doubleEnabled,
            reDoubleEnabled,
            minTotalBids,
            finalWinCondition
        ).joinToString(", ")
    }

    /**
     * **Rules**
     * - Bid cannot have trump suit
     * - Bid winner declare the Trump Suit
     * - Next bid MUST be higher than previous one isa.
     * - if Pass no Call in same round
     * - Scores counted NOT individually
     * - Win round when sum of team tricks equal or more than the estimated number of tricks isa.
     *
     * **Scoring**
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
     * **Final Win**
     * - When Combined team scores are 31 or more
     */
    object TarneebOriginal : GameTarneebType(
        minBidForPlayer = GameTarneebMinTotalBids(7),
        gameTarneebTypeTrumpSuit = GameTarneebTypeTrumpSuit.DeterminedByPlayer(false),
        callAfterPassEnabled = false,
        doubleEnabled = false,
        reDoubleEnabled = false,
        minTotalBids = null,
        finalWinCondition = GameTarneebFinalWinCondition.TeamScore(31)
    )

    /**
     * **Rules**
     * - Trump Suit is the opposite suit of same color suit of last dealt card.
     * - Calls are MANDATORY So No Pass is available isa.
     * - If total bids in round < 11 then restart round isa.
     * - Scores counted individually
     * - Win round when you get same or more of estimated tricks isa.
     *
     * **Scoring**
     * - When win round + value of bid else - value of bid
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
     * **Final Win**
     * - One of the team members is 41 or more && 2nd one is Zero or above isa.
     */
    object TarneebSyrian41 : GameTarneebType(
        minBidForPlayer = GameTarneebMinTotalBids(2),
        gameTarneebTypeTrumpSuit = GameTarneebTypeTrumpSuit.LastCard(false),
        callAfterPassEnabled = false,
        doubleEnabled = false,
        reDoubleEnabled = false,
        minTotalBids = GameTarneebMinTotalBids(
            11,
            null,
            0,
            0
        ),
        finalWinCondition = GameTarneebFinalWinCondition.IndividualScore(41, 0)
    )

    /**
     * **Rules**
     * - Bid MUST have trump suit
     * - Next bid Can be same number of tricks but bigger [GameTrumpSuit] value isa.
     * - if Pass you can re-call it is ok isa.
     * - There can be doubled and re-doubles isa.
     * - Scores counted NOT individually
     * - Bidding ends after Bidder next opponent say pass while other is in pass isa.
     * - Win round when sum of team tricks equal or more than the estimated number of tricks isa.
     *
     * **Scoring**
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
     * **Final Win**
     * - When Combined team scores are 41 or more
     */
    object TarneebEgyptian : GameTarneebType(
        minBidForPlayer = GameTarneebMinTotalBids(7),
        gameTarneebTypeTrumpSuit = GameTarneebTypeTrumpSuit.DeterminedByPlayer(true),
        callAfterPassEnabled = true,
        doubleEnabled = true,
        reDoubleEnabled = true,
        minTotalBids = null,
        finalWinCondition = GameTarneebFinalWinCondition.TeamScore(41)
    )

    /**
     * **Rules**
     * - Trump Suit is ALWAYS [GameTrumpSuit.Hearts] isa.
     * - Calls are MANDATORY So No Pass is available isa.
     * - When a player's point total is 30-39, his/her minimum bid becomes three.
     *   When a player reaches 40 points, his/her minimum bid becomes 4.
     *   If the player reaches 50 points, his/her minimum bid becomes 5, etc... isa.
     * - If max player score < 30 && total bids < 11 -> Restart round
     *   If max player score < 40 && total bids < 12 -> Restart round
     *   and so on isa.
     * - Scores counted individually
     * - Win round when you get same or more of estimated tricks isa.
     *
     * **Scoring**
     * - When win round + value of bid else - value of bid
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
     * **Final Win**
     * - One of the team members is 41 or more && 2nd one is Zero or above isa.
     */
    object Tarneeb400 : GameTarneebType(
        minBidForPlayer = GameTarneebMinTotalBids(
            2,
            29,
            1,
            11
        ),
        gameTarneebTypeTrumpSuit = GameTarneebTypeTrumpSuit.Fixed(GameTrumpSuit.Hearts),
        callAfterPassEnabled = false,
        doubleEnabled = false,
        reDoubleEnabled = false,
        /** so for 2 to 29 min is 11, 30 - 39 min is 12 etc... isa. */
        minTotalBids = GameTarneebMinTotalBids(
            11,
            29,
            1,
            10
        ),
        finalWinCondition = GameTarneebFinalWinCondition.IndividualScore(41, 0)
    )

    /**
     * In UI make copy from [TarneebEgyptian] and others isa, and even other custom tarneeb
     * in [EntityGameTarneebCalculation] using name in it isa.
     */
    @Entity(tableName = "customTarneebList")
    class TarneebCustom(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        var name: String,

        minBidForPlayer: GameTarneebMinTotalBids,

        gameTarneebTypeTrumpSuit: GameTarneebTypeTrumpSuit,

        callAfterPassEnabled: Boolean,
        doubleEnabled: Boolean,
        reDoubleEnabled: Boolean,

        minTotalBids: GameTarneebMinTotalBids?,

        finalWinCondition: GameTarneebFinalWinCondition
    ) : GameTarneebType(minBidForPlayer, gameTarneebTypeTrumpSuit, callAfterPassEnabled, doubleEnabled, reDoubleEnabled, minTotalBids, finalWinCondition) {

        override fun equals(other: Any?): Boolean {
            if (other !is TarneebCustom) return false

            return listOf(
                id == other.id,
                name == other.name,
                minBidForPlayer == other.minBidForPlayer,
                gameTarneebTypeTrumpSuit == other.gameTarneebTypeTrumpSuit,
                callAfterPassEnabled == other.callAfterPassEnabled,
                doubleEnabled == other.doubleEnabled,
                reDoubleEnabled == other.reDoubleEnabled,
                minTotalBids == other.minTotalBids,
                finalWinCondition == other.finalWinCondition
            ).allTrue()
        }

        override fun hashCode(): Int = ObjectsCompat.hash(
            id,
            name,
            minBidForPlayer,
            gameTarneebTypeTrumpSuit.hashCode(),
            callAfterPassEnabled,
            doubleEnabled,
            reDoubleEnabled,
            minTotalBids,
            finalWinCondition
        )

        override fun toString(): String {
            return listOf(
                id,
                name,
                minBidForPlayer,
                gameTarneebTypeTrumpSuit,
                callAfterPassEnabled,
                doubleEnabled,
                reDoubleEnabled,
                minTotalBids,
                finalWinCondition
            ).joinToString(", ") { it.toString() }
        }
    }

    override fun equals(other: Any?): Boolean {
        return when {
            this is TarneebOriginal && other is TarneebOriginal -> true
            this is TarneebSyrian41 && other is TarneebSyrian41 -> true
            this is TarneebEgyptian && other is TarneebEgyptian -> true
            this is Tarneeb400 && other is Tarneeb400 -> true
            this is TarneebCustom -> {
                val otherTarneebCustom = other as? TarneebCustom ?: return false
                otherTarneebCustom == this
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is TarneebOriginal -> ObjectsCompat.hashCode(TarneebOriginal::class.java.name)
            is TarneebSyrian41 -> ObjectsCompat.hashCode(TarneebSyrian41::class.java.name)
            is TarneebEgyptian -> ObjectsCompat.hashCode(TarneebEgyptian::class.java.name)
            is Tarneeb400 -> ObjectsCompat.hashCode(Tarneeb400::class.java.name)
            else -> (this as? TarneebCustom)?.hashCode() ?: 0
        }
    }

}
