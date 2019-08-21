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
 * Indicates [GameTrumpSuit] of the bid winner isa.
 *
 * NT > Spades > Hearts > Diamonds > Clubs
 *
 * - Using [List.sortedBy] then null, NT, Spades, Hearts, Diamonds, Clubs is returned isa.
 *
 * ## VIP
 * 1. In case considering nullable [GameTrumpSuit] as Random, null always comes 1st in sorting so put that in mind isa.
 */
@MASealedAbstractOrInterface
open class GameTrumpSuit(val value: Int) : Comparable<GameTrumpSuit> {

    object NoTrump : GameTrumpSuit(1)
    object Spades : GameTrumpSuit(2)
    object Hearts : GameTrumpSuit(3)
    object Diamonds : GameTrumpSuit(4)
    object Clubs : GameTrumpSuit(5)

    override fun compareTo(other: GameTrumpSuit): Int
        = value - other.value

    override fun equals(other: Any?): Boolean {
        if (other is GameTrumpSuit) {
            return value == other.value
        }

        return false
    }

    override fun hashCode(): Int = ObjectsCompat.hashCode(value)

}
