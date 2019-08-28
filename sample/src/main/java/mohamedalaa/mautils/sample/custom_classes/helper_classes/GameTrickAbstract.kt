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

@MASealedAbstractOrInterface
abstract class GameTrickAbstract(
    // Bidding
    var estimatedTricks: Int? = null,

    // Calculating
    var actualTricks: Int? = null
) {

    override fun equals(other: Any?): Boolean {
        if (other !is GameTrickAbstract) return false

        return estimatedTricks == other.estimatedTricks && actualTricks == other.actualTricks
    }

    override fun hashCode(): Int {
        return ObjectsCompat.hash(
            estimatedTricks,
            actualTricks
        )
    }

    override fun toString(): String {
        return toStringLikeDataClass(
            estimatedTricks,
            actualTricks
        )
    }

    /**
     * Copies all values in `this` into [other] isa, Ex. other.actualTricks = actualTricks
     */
    fun copyThisToOther(other: GameTrickAbstract) {
        other.estimatedTricks = estimatedTricks
        other.actualTricks = actualTricks
    }

}