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
import mohamedalaa.mautils.sample.model_for_testing.IntMap

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */

@MASealedAbstractOrInterface
@MARoomGsonTypeConverter
open class GamePreScores(val scores: IntMap<Int>) {

    class Manual(scores: IntMap<Int>) : GamePreScores(scores)

    /**
     * Extended means from another game/s isa.
     *
     * **Notes**
     * 1. if [gamesIds] min [List.size] is 1 and if so then [GameAbstract.players] names can be the d=same as default value,
     * if more then can be same if all gamesIds have names in same order and same profiles (guest or profile) isa,
     * else then like any regular game just Player 1 and so on isa, diff is scores are locked with the values isa.
     * 2. [scores] represents sum of all [gamesIds] scores See [GameAbstract.scores] isa.
     */
    class Extended(
        scores: IntMap<Int>,
        val gamesIds: List<Int>
    ) : GamePreScores(scores)

    override fun equals(other: Any?): Boolean {
        return when {
            this is Manual && other is Manual -> this.scores == other.scores
            this is Extended && other is Extended -> {
                this.scores == other.scores && this.gamesIds == other.gamesIds
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is Manual -> ObjectsCompat.hash(Manual::class.java.name, scores)
            is Extended -> ObjectsCompat.hash(Extended::class.java.name, scores, gamesIds)
            else -> 0
        }
    }

}