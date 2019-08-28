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
sealed class GameTarneebCalculationTeamDynamicValue {

    class NumberOfTricks(var multiplier: Int) : GameTarneebCalculationTeamDynamicValue()

    class BidValue(var multiplier: Int) : GameTarneebCalculationTeamDynamicValue()

    class FixedValue(var value: Int) : GameTarneebCalculationTeamDynamicValue()

    override fun equals(other: Any?): Boolean {
        return when {
            this is NumberOfTricks && other is NumberOfTricks -> this.multiplier == other.multiplier
            this is BidValue && other is BidValue -> this.multiplier == other.multiplier
            this is FixedValue && other is FixedValue -> this.value == other.value
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is NumberOfTricks -> ObjectsCompat.hash(NumberOfTricks::class.java.name, multiplier)
            is BidValue -> ObjectsCompat.hash(BidValue::class.java.name, multiplier)
            is FixedValue -> ObjectsCompat.hash(FixedValue::class.java.name, value)
        }
    }

}