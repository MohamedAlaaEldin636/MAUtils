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
import androidx.core.util.ObjectsCompat
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */

@MASealedAbstractOrInterface
@MARoomGsonTypeConverter
open class GameTeamType {
    object NoTeam : GameTeamType()

    @MASealedAbstractOrInterface
    sealed class WithTeam(
        var teamAColorInt: Int,
        var teamBColorInt: Int,
        var teamAPlayersIndices: List<Int>
    ) : GameTeamType() {

        class TwoVsTwo(
            teamAColorInt: Int = Color.WHITE,
            teamBColorInt: Int = Color.WHITE,
            teamAPlayersIndices: List<Int>
        ) : WithTeam(teamAColorInt, teamBColorInt, teamAPlayersIndices)

        class OneVsThree(
            teamAColorInt: Int = Color.WHITE,
            teamBColorInt: Int = Color.WHITE,
            teamAPlayersIndices: List<Int>
        ) : WithTeam(teamAColorInt, teamBColorInt, teamAPlayersIndices)

    }

    override fun equals(other: Any?): Boolean {
        return when {
            this is NoTeam && other is NoTeam -> true
            this is WithTeam && other is WithTeam -> {
                teamAColorInt == other.teamAColorInt
                    && teamBColorInt == other.teamBColorInt
                    && teamAPlayersIndices == other.teamAPlayersIndices
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is NoTeam -> ObjectsCompat.hashCode(NoTeam::class.java.name)
            is WithTeam -> ObjectsCompat.hash(
                WithTeam::class.java.name,
                teamAColorInt,
                teamBColorInt,
                teamAPlayersIndices
            )
            else -> 0
        }
    }

}