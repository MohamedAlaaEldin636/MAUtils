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

import android.content.Context
import androidx.core.util.ObjectsCompat
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter


@MASealedAbstractOrInterface
@MARoomGsonTypeConverter
open class GameEstimationNoWinnerMultiplier {
    object Unlimited : GameEstimationNoWinnerMultiplier()

    object NoMultiplier : GameEstimationNoWinnerMultiplier()

    /** @param value min value for this is 2 isa. */
    class MaxMultiplier(val value: Int = 2) : GameEstimationNoWinnerMultiplier()

    override fun equals(other: Any?): Boolean {
        return when {
            this is Unlimited && other is Unlimited -> true
            this is NoMultiplier && other is NoMultiplier -> true
            this is MaxMultiplier && other is MaxMultiplier -> this.value == other.value
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is Unlimited -> -1
            is NoMultiplier -> -2
            is MaxMultiplier -> ObjectsCompat.hashCode(this.value)
            else -> 0
        }
    }

}

@JvmOverloads
fun GameEstimationNoWinnerMultiplier?.toReadableString(context: Context, fallback: String = ""): String {
    return when (this) {
        is GameEstimationNoWinnerMultiplier.Unlimited -> "context.getString(R.string.unlimited)"
        is GameEstimationNoWinnerMultiplier.NoMultiplier -> "context.getString(R.string.no_multiplier)"
        is GameEstimationNoWinnerMultiplier.MaxMultiplier -> "context.getString(R.string.var_colon_var,context.getString(R.string.max_multiplier),value.toString())"
        else -> fallback
    }
}

infix fun GameEstimationNoWinnerMultiplier?.equalsObject(other: GameEstimationNoWinnerMultiplier?): Boolean {
    return when {
        this is GameEstimationNoWinnerMultiplier.Unlimited && other is GameEstimationNoWinnerMultiplier.Unlimited -> true
        this is GameEstimationNoWinnerMultiplier.NoMultiplier && other is GameEstimationNoWinnerMultiplier.NoMultiplier -> true
        this is GameEstimationNoWinnerMultiplier.MaxMultiplier && other is GameEstimationNoWinnerMultiplier.MaxMultiplier ->
            value == other.value
        else -> false
    }
}