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

package mohamedalaa.mautils.sample.gson.model.repeat

import mohamedalaa.mautils.sample.gson.model.MATime
import mohamedalaa.mautils.sample.gson.model.EveryOrAfterTime
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

/**
 * - Repeat alarm for an exact number of times or until specific criteria in [RepeatUntilPolicy]
 * is met then go to next [RepeatPolicy] in the list if no more exist then turn off the alarm isa.
 */
@MASealedAbstractOrInterface
sealed class RepeatPolicy {

    /**
     * @property repeatTimes at least is `1` or `null` for infinite times isa.
     *
     * @property repeatUntilPolicy used only if [repeatTimes] is `null` indicating infinite repetition isa.
     */
    data class SameConditions(
        var repeatTimes: Int?,
        var currentRepeatedTimes: Int = 0,
        var repeatUntilPolicy: RepeatUntilPolicy? = null
    ) : RepeatPolicy()

    /**
     * Ex. after reminder is invoked you wanna re-invoke it every 3 hrs after last invoke isa.
     *
     * @param lastNotifiedTime used in case we wanna show in UI alarm as item you set it will ring
     * at specific time not acc. to conditions since repeat is WithNewConditions isa,
     * so calculating using this + the [everyTime] properties isa.
     */
    data class WithNewConditions(
        var lastNotifiedTime: MATime,
        var everyTime: EveryOrAfterTime,
        var repeatUntilPolicy: RepeatUntilPolicy? = null
    ) : RepeatPolicy()

    /**
     * Used only in case of [List]<[RepeatPolicy]> && can't be the first repeat policy for sure,
     * && [step] must be between 1 inclusive and the step before this one inclusive as well,
     * Also note 1 indicates (indexInList + 1) isa.
     */
    data class BackToAnotherRepeatPolicyStep(var step: Int) : RepeatPolicy()

}
