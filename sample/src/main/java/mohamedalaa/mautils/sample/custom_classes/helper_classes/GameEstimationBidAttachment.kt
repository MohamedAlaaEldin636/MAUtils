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
sealed class GameEstimationBidAttachment {

    object With : GameEstimationBidAttachment()

    data class Risk(val value: Int): GameEstimationBidAttachment()

    data class WithAndRisk(val value: Int): GameEstimationBidAttachment()

    override fun equals(other: Any?): Boolean {
        return when {
            this is With && other is With -> true
            this is Risk && other is Risk -> value == other.value
            this is WithAndRisk && other is WithAndRisk -> value == other.value
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when {
            this is With -> ObjectsCompat.hash(With::class.java.name)
            this is Risk -> ObjectsCompat.hash(Risk::class.java.name, value)
            this is WithAndRisk -> ObjectsCompat.hash(WithAndRisk::class.java.name, value)
            else -> 0
        }
    }

}