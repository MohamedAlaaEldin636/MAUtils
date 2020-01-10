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

import mohamedalaa.mautils.sample.gson.model.ConditionReminderOrAction

/**
 * - If more than 1 property is used than means an OR condition,
 * Ex. [isDismissed] == true && [snoozeNumberInSingleAlarm] is 3 then whichever happens
 * first will close this repeat step if no more repeat steps then alarm is turned off otherwise
 * check the next in the list isa.
 * - for [snoozeNumberOverall] decrement 1 by 1 on every snooze and update database
 * no need for another variable in the database to determine current progress isa.
 */
data class RepeatUntilPolicy(
    var isDismissed: Boolean = false,

    var snoozeNumberInSingleAlarm: Int = 0,
    var snoozeNumberOverall: Int = 0,

    var timing: ConditionReminderOrAction.Timing
)
