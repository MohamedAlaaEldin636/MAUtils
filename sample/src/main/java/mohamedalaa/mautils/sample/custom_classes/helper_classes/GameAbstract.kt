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
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter
import mohamedalaa.mautils.sample.model_for_testing.IntMap

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */
abstract class GameAbstract(
    var preScores: GamePreScores? = null,

    var gameMechanism: GameMechanism = GameMechanism.ORGANIZED,

    var gameCreationType: GameCreationType = GameCreationType.LOCAL,

    @MARoomGsonTypeConverter
    var players: IntMap<GamePlayer> = buildGameMap { GamePlayer(it.inc()) },
    @MARoomGsonTypeConverter
    var ranks: IntMap<GameRankWrapper> = buildGameMap { GameRankWrapper() },
    @MARoomGsonTypeConverter
    var scores: IntMap<Int> = buildGameMap { 0 },
    @MARoomGsonTypeConverter
    var rounds: MutableList<GameRound> = mutableListOf(),

    var currentRoundNumber: Int = 1,

    var gameTeamType: GameTeamType = GameTeamType.NoTeam,

    var timeGameStarted: Long = System.currentTimeMillis(),
    var timeGameFinished: Long? = null, // used to determine duration isa.

    var gameState: GameState = GameState.IN_PROGRESS,

    var isFavourite: Boolean = false
) {

    override fun equals(other: Any?): Boolean {
        if (other !is GameAbstract) return false

        return listOf(
            preScores == other.preScores,

            gameMechanism == other.gameMechanism,

            gameCreationType == other.gameCreationType,

            players == other.players,
            ranks == other.ranks,
            scores == other.scores,
            rounds == other.rounds,

            currentRoundNumber == other.currentRoundNumber,

            gameTeamType == other.gameTeamType,

            timeGameStarted == other.timeGameStarted,
            timeGameFinished == other.timeGameFinished,

            gameState == other.gameState,
            isFavourite == other.isFavourite
        ).allTrue()
    }

    override fun hashCode(): Int {
        return ObjectsCompat.hash(
            preScores,

            gameMechanism,

            gameCreationType,

            players,
            ranks,
            scores,
            rounds,

            currentRoundNumber,

            gameTeamType,

            timeGameStarted,
            timeGameFinished,

            gameState,
            isFavourite
        )
    }

    /**
     * Copies all values in `this` into [other] isa, Ex. other.preScores = preScores
     */
    fun copyThisToOther(other: GameAbstract) {
        other.preScores = preScores

        other.gameMechanism = gameMechanism

        other.gameCreationType = gameCreationType

        other.players = players
        other.ranks = ranks
        other.scores = scores
        other.rounds = rounds

        other.currentRoundNumber = currentRoundNumber

        other.gameTeamType = gameTeamType

        other.timeGameStarted = timeGameStarted
        other.timeGameFinished = timeGameFinished

        other.gameState = gameState
        other.isFavourite = isFavourite
    }

}