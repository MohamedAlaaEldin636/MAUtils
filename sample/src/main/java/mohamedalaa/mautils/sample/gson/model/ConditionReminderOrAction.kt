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

package mohamedalaa.mautils.sample.gson.model

import mohamedalaa.mautils.sample.gson.model.entity.PlaceGeofence
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
import java.util.*

@MASealedAbstractOrInterface
sealed class ConditionReminderOrAction {

    /**
     * ### Properties
     * - [placeGeofenceId]
     *
     * @property placeGeofenceId corresponds to if of [PlaceGeofence] isa.
     */
    data class Place(var placeGeofenceId: String) : ConditionReminderOrAction()

    /**
     * - only 1 of time var & 1 of date vars not null & others must be null isa.
     * - this approach is used instead of another nested sealed class since it will lead
     * to error converting to/from json isa.
     */
    sealed class Timing : ConditionReminderOrAction() {

        data class ExactTime(
            var hourFormat12: Int,
            var min: Int,
            var isAM: Boolean
        ) : Timing()

        data class WindowTime(
            var fromInclusive: ExactTime,
            var toInclusive: ExactTime
        ) : Timing()

        sealed class AbstractExactDate : Timing() {

            /**
             * @property days one or more of the days in [Calendar] class isa.
             */
            data class DaysOfWeek(var days: List<Int>) : AbstractExactDate()

            /**
             * @property days Ex. [13,14,15] isa.
             */
            data class DaysOfMonth(var days: List<Int>) : AbstractExactDate()

            data class StartMidEndOfMonth(
                var start: Boolean,
                var mid: Boolean,
                var midTendsToLowerRounding: Boolean,
                var end: Boolean
            ) : AbstractExactDate()

        }

        data class AbstractWindowDate(
            var fromInclusive: AbstractExactDate,
            var toInclusive: AbstractExactDate
        ) : Timing()

        data class ConcreteExactDate(
            var dayOfMonth: Int,
            var month: Int,
            var year: Int
        ) : Timing()

        data class ConcreteWindowDate(
            var fromInclusive: ConcreteExactDate,
            var toInclusive: ConcreteExactDate
        ) : Timing()

    }

    /**
     * @property detectedActivity one of constants of [DetectedActivity], Ex. [DetectedActivity.IN_VEHICLE] isa.
     *
     * @property useEnterTransitionType corresponds to [ActivityTransition.ACTIVITY_TRANSITION_ENTER] isa,
     * **And Note** this can be true along with [useExitTransitionType] as true as it makes more logic
     * when user wants a car enter/exit yes in coding it is not bitwise-OR but it does accepts
     * list so same result different approach so acceptable isa.
     * @property useExitTransitionType see [useEnterTransitionType] isa.
     */
    data class Activity(
        var detectedActivity: Int,

        var useEnterTransitionType: Boolean,
        var useExitTransitionType: Boolean
    ) : ConditionReminderOrAction()

}
