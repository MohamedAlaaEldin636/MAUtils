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

import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */
/** Either [King], [SubKing], [SubKooz] OR [Kooz] */
@MASealedAbstractOrInterface
open class GameRank(val value: Int) : Comparable<GameRank> {

    object King : GameRank(4)
    object SubKing : GameRank(3)
    object SubKooz : GameRank(2)
    object Kooz : GameRank(1)

    override fun compareTo(other: GameRank): Int
        = value - other.value

    override fun equals(other: Any?): Boolean {
        return if (other !is GameRank) {
            false
        }else {
            value == other.value
        }
    }

    override fun hashCode(): Int = value.hashCode()

}