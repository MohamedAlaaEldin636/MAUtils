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

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */


class GameTarneebTrick(
    // Bidding
    var gameTrumpSuit: GameTrumpSuit? = null,

    // Calculating
    var gameTrickResult: GameTarneebTrickResult? = null, // Also for Finished

    var double: Boolean = false,

    var redouble: Boolean = false
) : GameTrickAbstract() {

    override fun equals(other: Any?): Boolean {
        if (other !is GameTarneebTrick) return false

        return super.equals(other) && listOf(
            gameTrumpSuit == other.gameTrumpSuit,
            gameTrickResult == other.gameTrickResult
        ).allTrue()
    }

    override fun hashCode(): Int {
        return ObjectsCompat.hash(
            super.hashCode(),
            gameTrumpSuit,
            gameTrickResult
        )
    }

    override fun toString(): String {
        return toStringLikeDataClass(
            super.toString(),
            gameTrumpSuit,
            gameTrickResult
        )
    }

    fun copy(
        gameTrumpSuit: GameTrumpSuit? = this.gameTrumpSuit,
        gameTrickResult: GameTarneebTrickResult? = this.gameTrickResult
    ) : GameTarneebTrick {
        return GameTarneebTrick(
            gameTrumpSuit, gameTrickResult
        ).apply {
            this@GameTarneebTrick.copyThisToOther(this)
        }
    }

}