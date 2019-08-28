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


@Entity(tableName = "gamesIds")
class Game(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @Embedded(prefix = "gameEstimation_")
    var gameEstimation: GameEstimation? = GameEstimation(),

    @Embedded(prefix = "gameTarneeb_")
    var gameTarneeb: GameTarneeb? = null
) {

    override fun equals(other: Any?): Boolean {
        if (other !is Game) return false

        return listOf(
            id == other.id,
            gameEstimation == other.gameEstimation,
            gameTarneeb == other.gameTarneeb
        ).allTrue()
    }

    override fun hashCode(): Int {
        return ObjectsCompat.hash(
            id,
            gameEstimation,
            gameTarneeb
        )
    }

    fun copy(
        id: Int = this.id,
        gameEstimation: GameEstimation? = this.gameEstimation?.copy(),
        gameTarneeb: GameTarneeb? = this.gameTarneeb?.copy()
    ): Game {
        return Game(id, gameEstimation, gameTarneeb)
    }

}