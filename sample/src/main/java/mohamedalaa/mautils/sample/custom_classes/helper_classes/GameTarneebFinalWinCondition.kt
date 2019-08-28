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

@MASealedAbstractOrInterface
@MARoomGsonTypeConverter
open class GameTarneebFinalWinCondition(var reachedScore: Int) {

    class TeamScore(reachedScore: Int) : GameTarneebFinalWinCondition(reachedScore)

    class IndividualScore(reachedScore: Int, var otherTeamMemberScoreEqualOrMore: Int?) : GameTarneebFinalWinCondition(reachedScore)

    override fun equals(other: Any?): Boolean {
        return when {
            this is TeamScore && other is TeamScore -> this.reachedScore == other.reachedScore
            this is IndividualScore && other is IndividualScore -> {
                this.reachedScore == other.reachedScore && this.otherTeamMemberScoreEqualOrMore == other.otherTeamMemberScoreEqualOrMore
            }
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is TeamScore -> ObjectsCompat.hashCode(this.reachedScore)
            is IndividualScore -> ObjectsCompat.hash(this.reachedScore, this.otherTeamMemberScoreEqualOrMore)
            else -> 0
        }
    }

}
