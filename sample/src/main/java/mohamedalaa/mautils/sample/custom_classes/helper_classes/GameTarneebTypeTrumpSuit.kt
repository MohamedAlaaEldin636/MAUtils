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
open class GameTarneebTypeTrumpSuit {

    /**
     * @param withBid true means determined by player with the bid, false means after a bid is chosen
     * the bid winner chooses the trump suit isa.
     */
    class DeterminedByPlayer(var withBid: Boolean) : GameTarneebTypeTrumpSuit()

    /** null [trumpSuit] means random trump suit isa. */
    class Fixed(var trumpSuit: GameTrumpSuit?) : GameTarneebTypeTrumpSuit()

    /** false means if last card was hearts then result is diamond isa. */
    class LastCard(var sameColorSuit: Boolean) : GameTarneebTypeTrumpSuit()

    override fun equals(other: Any?): Boolean {
        return when {
            this is DeterminedByPlayer && other is DeterminedByPlayer -> withBid == other.withBid
            this is Fixed && other is Fixed -> this.trumpSuit == other.trumpSuit
            this is LastCard && other is LastCard -> this.sameColorSuit == other.sameColorSuit
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is DeterminedByPlayer -> ObjectsCompat.hashCode(withBid)
            is Fixed -> ObjectsCompat.hashCode(this.trumpSuit.hashCode())
            is LastCard -> ObjectsCompat.hashCode(this.sameColorSuit)
            else -> 0
        }
    }

}