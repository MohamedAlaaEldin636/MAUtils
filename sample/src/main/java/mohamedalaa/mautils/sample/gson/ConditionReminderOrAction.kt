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

@file:Suppress("unused", "KDocUnresolvedReference")

package mohamedalaa.mautils.sample.gson

import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

open class OpenClassA(
    var abc: Boolean,
    var def: String
)

data class ImplOfOpenClassA(
    var a: String,
    var b: Long?
) : OpenClassA(true, "")

/**
 * Used as complex class to test using gson isa,
 * note the sealed class property inside this class [condition1]
 * and even it exists it can be serialized/deserialized isa.
 */
data class ReminderOrAction(
    var dwede: String,
    var id: Int,
    var condition1: ConditionReminderOrAction,
    var condition2: ConditionReminderOrAction,
    var sasas: Boolean,
    var a: Double,
    val fl: Float
) : OpenClassA(true, "")

@MASealedAbstractOrInterface
sealed class ConditionReminderOrAction {

    /**
     * ### Properties
     * - [placeGeofenceId]
     *
     * @property placeGeofenceId corresponds to if of [PlaceGeofence] isa.
     */
    data class Place(var placeGeofenceId: Int) : ConditionReminderOrAction()

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

            data class DaysOfWeek(var days: List<Int>) : AbstractExactDate()

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
            var toInclusive: AbstractExactDate,
            /** JUST for a worse case isa. */
            var obj : SomeObjectAsWellIsa
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

    object SomeObjectAsWellIsa : ConditionReminderOrAction()

}
