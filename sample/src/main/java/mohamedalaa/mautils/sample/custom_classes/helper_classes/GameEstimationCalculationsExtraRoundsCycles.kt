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
open class GameEstimationCalculationsExtraRoundsCycles {

    object None : GameEstimationCalculationsExtraRoundsCycles()

    class Fixed(var number: Int) : GameEstimationCalculationsExtraRoundsCycles()

    object Unlimited : GameEstimationCalculationsExtraRoundsCycles()

    override fun equals(other: Any?): Boolean {
        return when {
            this is None && other is None -> true
            this is Fixed && other is Fixed -> this.number == other.number
            this is Unlimited && other is Unlimited -> true
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is None -> -1
            is Fixed -> ObjectsCompat.hashCode(this.number)
            is Unlimited -> -2
            else -> 0
        }
    }

}

@JvmOverloads
fun GameEstimationCalculationsExtraRoundsCycles?.toReadableString(context: Context, fallback: String = "Unknown"): String {
    return when (this) {
        is GameEstimationCalculationsExtraRoundsCycles.None -> "context.getString(R.string.none)"
        is GameEstimationCalculationsExtraRoundsCycles.Fixed -> "context.getString(R.string.fixed_brackets_var, number.toString())"
        is GameEstimationCalculationsExtraRoundsCycles.Unlimited -> "context.getString(R.string.unlimited)"
        else -> fallback
    }
}

infix fun GameEstimationCalculationsExtraRoundsCycles.equalsObject(other: GameEstimationCalculationsExtraRoundsCycles): Boolean {
    return when {
        this is GameEstimationCalculationsExtraRoundsCycles.None && other is GameEstimationCalculationsExtraRoundsCycles.None -> true
        this is GameEstimationCalculationsExtraRoundsCycles.Fixed && other is GameEstimationCalculationsExtraRoundsCycles.Fixed ->
            number == other.number
        this is GameEstimationCalculationsExtraRoundsCycles.Unlimited && other is GameEstimationCalculationsExtraRoundsCycles.Unlimited -> true
        else -> false
    }
}
