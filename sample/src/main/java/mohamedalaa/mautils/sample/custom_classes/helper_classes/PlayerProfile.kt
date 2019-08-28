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

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */

@Entity(tableName = "players")
data class PlayerProfile(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String = "Unknown player name (hardcoded string)",

    var imagePath: String? = null,

    var firstLetterAsImageColorInt: Int = Color.parseColor("#536DFE"),

    @MARoomGsonTypeConverter
    var gamesEstimationKingIds: List<Int> = emptyList(),
    var gamesEstimationSubKingIds: List<Int> = emptyList(),
    var gamesEstimationSubKoozIds: List<Int> = emptyList(),
    var gamesEstimationKoozIds: List<Int> = emptyList(),
    var gamesEstimationWinIds: List<Int> = emptyList(),
    var gamesEstimationLoseIds: List<Int> = emptyList(),
    var gamesEstimationTieIds: List<Int> = emptyList(),
    var gameEstimationInProgressIds: List<Int> = emptyList(),

    var gamesTarneebWinIds: List<Int> = emptyList(),
    var gamesTarneebLoseIds: List<Int> = emptyList(),
    var gamesTarneebTieIds: List<Int> = emptyList(),
    var gameTarneebInProgressIds: List<Int> = emptyList()
)