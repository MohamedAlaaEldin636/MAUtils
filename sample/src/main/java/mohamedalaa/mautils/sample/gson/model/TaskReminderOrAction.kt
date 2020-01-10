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

/**
 * - this approach of abstract property is used to benefit from the data class functions
 * & to ensure base property for all subclasses isa.
 */
@MASealedAbstractOrInterface
sealed class TaskReminderOrAction {

    abstract var initialDelayInSeconds: Int

    data class SilentMode(
        var turnOn: Boolean,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    data class Wifi(
        var turnOn: Boolean,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    data class MobileData(
        var turnOn: Boolean,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    data class GPS(
        var turnOn: Boolean,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    data class AirplaneMode(
        var turnOn: Boolean,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    data class BatterySaveMode(
        var turnOn: Boolean,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    data class TextToSpeech(
        var text: String,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    /**
     * @property durationInSeconds cane be `null` to be infinite duration meaning it won't stop except
     * when user explicitly stops it isa.
     */
    data class Record(
        var isSecret: Boolean,
        var durationInSeconds: Int? = null,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    /**
     * @property durationInSeconds cane be `null` to be infinite duration meaning it won't stop except
     * when user explicitly stops it isa.
     */
    data class Video(
        var isSecret: Boolean,
        var durationInSeconds: Int? = null,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    /** @property durationInSeconds cane be `null` to be infinite duration isa. */
    data class Vibrate(
        var durationInSeconds: Int?,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    /**
     * @property idOfStopwatch refers to a stopwatch in the Multiple stopwatches nav menu item in
     * nav view isa, But note it can be null indicating start a new one isa.
     *
     * @property titleOfStopwatch can be `null` indicating title will be Created from a reminder isa.
     */
    data class StartStopwatch(
        var idOfStopwatch: Int?,
        var titleOfStopwatch: String?,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

    /**
     * - Either [idOfTimer] or [durationOfTimerInSeconds] has to be non-null and the other has to
     * be null isa, Ex. in case duration is not null this means a newly created timer isa.
     *
     * @see StartStopwatch
     */
    data class StartTimer(
        var idOfTimer: Int?,
        var titleOfTimer: String?,
        var durationOfTimerInSeconds: Int?,
        override var initialDelayInSeconds: Int = 0
    ) : TaskReminderOrAction()

}
