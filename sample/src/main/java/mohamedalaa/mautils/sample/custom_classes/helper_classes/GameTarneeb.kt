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

class GameTarneeb(
    var gameTarneebType: GameTarneebType = GameTarneebType.TarneebEgyptian,

    @Embedded(prefix = "entityGameTarneebCalculation_")
    var entityGameTarneebCalculation: EntityGameTarneebCalculation = EntityGameTarneebCalculation(
        0, "Default - Individual", null, true, GameTarneebCalculation.Individual(), null
    )
) : GameAbstract(
    gameTeamType = GameTeamType.WithTeam.TwoVsTwo(teamAPlayersIndices = listOf(0, 1))
) {

    override fun equals(other: Any?): Boolean {
        if (other !is GameTarneeb) return false

        return super.equals(other) && listOf(
            gameTarneebType == other.gameTarneebType,
            entityGameTarneebCalculation == other.entityGameTarneebCalculation
        ).allTrue()
    }

    override fun hashCode(): Int {
        return ObjectsCompat.hash(
            super.hashCode(),
            gameTarneebType,
            entityGameTarneebCalculation
        )
    }

    fun copy(
        gameTarneebType: GameTarneebType = this.gameTarneebType,
        entityGameTarneebCalculation: EntityGameTarneebCalculation = this.entityGameTarneebCalculation
    ): GameTarneeb {
        return GameTarneeb(
            gameTarneebType, entityGameTarneebCalculation
        ).apply {
            this@GameTarneeb.copyThisToOther(this)
        }
    }

}
