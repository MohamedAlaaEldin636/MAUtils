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
sealed class NotifyAsReminderOrAction {

    companion object {
        const val APP_DEFAULT_SETTINGS = -2
        const val INFINITE_VALUE = -3
    }

    /**
     * - For alarms not actions isa.
     * - top importance level so sound and a popup isa.
     */
    object Notification : NotifyAsReminderOrAction()

    /**
     * - For alarms not actions isa.
     * - not just VIP like [Notification] but also has [android.app.Notification.Builder.setFullScreenIntent]
     * isa.
     *
     * @property volumePercent can be [APP_DEFAULT_SETTINGS] OR ranges from 1 to 100 inclusive isa.
     *
     * @property ringingDurationInSeconds can be either [APP_DEFAULT_SETTINGS], [INFINITE_VALUE]
     * OR the value of seconds isa.
     *
     * @property ringtoneLocation `null` means use app settings default isa,
     * which should be free to use for commercial annoying alarm set in this app
     * or anything else the user can add from the media database of android isa.
     */
    data class Alarm(
        var volumePercent: Int = APP_DEFAULT_SETTINGS,
        var ringingDurationInSeconds: Int = APP_DEFAULT_SETTINGS,
        var ringtoneLocation: String? = null,
        var useVibration: Boolean = true
    ) : NotifyAsReminderOrAction()

    /**
     * @property listOfTasks should never be empty isa.
     */
    sealed class Action : NotifyAsReminderOrAction() {

        abstract var listOfTasks: List<TaskReminderOrAction>

        data class NoNotificationNorAlarm(override var listOfTasks: List<TaskReminderOrAction>) : Action()

        data class WithNotification(override var listOfTasks: List<TaskReminderOrAction>) : Action()

        data class WithAlarm(
            override var listOfTasks: List<TaskReminderOrAction>,
            var alarm: Alarm
        ) : Action()

    }

}
