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
import mohamedalaa.mautils.mautils.R

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */

data class GameTarneebMinTotalBids(
    var minTotalBids: Int,

    var forMaxScoreOf: Int? = null,

    var increaseMinTotalBidsBy: Int = 0,

    var whenMaxScoreIncreasedBy: Int = 0
) {

    /**
     * Either -> Fixed ( 5 ) or 0 for 20 ( increase by 1 for 10 )
     */
    fun toReadableStringForMinBidForPlayer(context: Context): String = context.run {
        ""
    }

}
