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

import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

@MASealedAbstractOrInterface
sealed class SnoozePolicy {

    object AccToGlobalAppSettings : SnoozePolicy()

    /** Means just ring once then either repeat or declare as missed if not marked as done isa. */
    object NoAutoNorManual : SnoozePolicy()

    /**
     * @property snoozeSleepDurationInMinutes varies between 1 and 120 min isa, Note can be < notifyAs
     * ringingDuration since [snoozeSleepDurationInMinutes] starts after manual snooze is clicked
     * or finish of ringing duration for auto snooze isa.
     *
     * @property maxNumberOfAutoSnoozes can be `0` meaning no auto snoozes, & can be infinite using
     * `null` and can be some number if some number then after
     * that number the alarm will be declared as missed and then checks repeat policy isa.
     */
    data class Custom(
        var snoozeSleepDurationInMinutes: Int = 10,
        var maxNumberOfAutoSnoozes: Int? = null,

        var disableManualSnoozing: Boolean = false
    ) : SnoozePolicy()

}
