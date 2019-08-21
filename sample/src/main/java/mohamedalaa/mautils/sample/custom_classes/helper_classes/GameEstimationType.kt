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

/** Indicates if the game is [MicroBola], [MiniBola], [FullBola], [CustomBola] isa. */
@MASealedAbstractOrInterface
open class GameEstimationType(
    var gameEstimationRoundsConfiguration: GameEstimationRoundsConfiguration
) {

    /** 5 normal, 0 fast Rounds isa. */
    object MicroBola : GameEstimationType(
        gameEstimationRoundsConfiguration = GameEstimationRoundsConfiguration(
            5,
            0,
            GameTrumpSuit.NoTrump,
            GameEstimationFastRoundsSort.DESC
        )
    )

    /** 5 normal, 5 fast Rounds isa. */
    object MiniBola : GameEstimationType(
        gameEstimationRoundsConfiguration = GameEstimationRoundsConfiguration(
            5,
            5,
            GameTrumpSuit.NoTrump,
            GameEstimationFastRoundsSort.DESC
        )
    )

    object FullBola : GameEstimationType(
        gameEstimationRoundsConfiguration = GameEstimationRoundsConfiguration(
            13,
            5,
            GameTrumpSuit.NoTrump,
            GameEstimationFastRoundsSort.DESC
        )
    )

    class CustomBola(
        var id: Int,
        var name: String,
        gameEstimationRoundsConfiguration: GameEstimationRoundsConfiguration
    ) : GameEstimationType(gameEstimationRoundsConfiguration) {
        override fun equals(other: Any?): Boolean {
            if (other !is CustomBola) return false

            if (id != other.id) {
                return false
            }

            if (name != other.name) {
                return false
            }

            if (gameEstimationRoundsConfiguration != other.gameEstimationRoundsConfiguration) {
                return false
            }

            return true
        }

        override fun hashCode(): Int = ObjectsCompat.hash(id, name, gameEstimationRoundsConfiguration.hashCode())

        override fun toString(): String {
            return "GameEstimationType.CustomBola(id=$id, name=$name, other=$gameEstimationRoundsConfiguration)"
        }

        fun copy(
            id: Int = this.id,
            name: String = this.name,
            gameEstimationRoundsConfiguration: GameEstimationRoundsConfiguration = this.gameEstimationRoundsConfiguration
        ): CustomBola {
            return CustomBola(id, name, gameEstimationRoundsConfiguration)
        }
    }

    override fun equals(other: Any?): Boolean {
        return when {
            this is MicroBola && other is MicroBola -> true
            this is MiniBola && other is MiniBola -> true
            this is FullBola && other is FullBola -> true
            this is CustomBola -> {
                val otherCustomBola = other as? CustomBola ?: return false
                otherCustomBola == this
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when {
            this is MicroBola -> ObjectsCompat.hashCode(MicroBola::class.java.name)
            this is MiniBola -> ObjectsCompat.hashCode(MiniBola::class.java.name)
            this is FullBola -> ObjectsCompat.hashCode(FullBola::class.java.name)
            else -> (this as? CustomBola)?.hashCode() ?: 0
        }
    }

}
