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

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */

/** Either [NormalBidding] OR [FastBidding] which enforce a [GameTrumpSuit] isa. */
@MASealedAbstractOrInterface
sealed class GameRoundBiddingType {

    object NormalBidding : GameRoundBiddingType()

    class FastBidding(val gameTrumpSuit: GameTrumpSuit) : GameRoundBiddingType()

    override fun equals(other: Any?): Boolean {
        if (other !is GameRoundBiddingType) return false

        return when {
            this is NormalBidding && other is NormalBidding -> true
            this is FastBidding && other is FastBidding -> gameTrumpSuit == other.gameTrumpSuit
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is NormalBidding -> ObjectsCompat.hashCode(NormalBidding::class.java.name)
            is FastBidding -> ObjectsCompat.hash(
                FastBidding::class.java.name,
                gameTrumpSuit
            )
        }
    }

}