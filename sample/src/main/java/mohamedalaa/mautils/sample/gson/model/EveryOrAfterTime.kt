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

import mohamedalaa.mautils.sample.gson.model.ConditionReminderOrAction.Timing.AbstractExactDate.StartMidEndOfMonth
import mohamedalaa.mautils.sample.gson.model.repeat.RepeatPolicy

/**
 * - There can be more than 1 property not `0` isa, indicating repeat every 2 month + 5 days isa,
 * instead of 65 days isa.
 *
 * @property day just means after 24 hours isa.
 *
 * @property week just means after 7 days isa.
 *
 * @property month just means after 30 days isa, so in case you want infinite repeat of an alarm
 * at start exactly start of every month then use for a condition [StartMidEndOfMonth.start] == true
 * && for a repeat [RepeatPolicy.SameConditions.repeatTimes] == `null` isa.
 */
data class EveryOrAfterTime(
    var min: Int = 0,
    var hour: Int = 0,

    var day: Int = 0,

    var week: Int = 0,

    var month: Int = 0,

    var year: Int = 0
)
