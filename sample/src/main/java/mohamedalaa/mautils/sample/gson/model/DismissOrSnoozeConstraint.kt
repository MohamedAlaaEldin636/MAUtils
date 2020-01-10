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

import android.hardware.Sensor
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

/**
 * - This is done only for notifyAsAlarm isa.
 * - backup pattern/password for any alarm constraint is in AppSettings isa.
 * - [canUseBackupAfterTimeInSeconds] can be `0` so backup immediately and can be `null` meaning
 * you can never use AppSettings backup to dismiss/snooze this alarm as the only way is by
 * this class's constraint isa.
 */
@MASealedAbstractOrInterface
sealed class DismissOrSnoozeConstraint {

    abstract var canUseBackupAfterTimeInSeconds: Int?

    /** for Snooze constraint only isa. */
    data class LikeDismissConstraint(
        override var canUseBackupAfterTimeInSeconds: Int? = 60
    ) : DismissOrSnoozeConstraint()

    data class QuestionAndAnswer(
        var question: String,
        var answer: String,
        override var canUseBackupAfterTimeInSeconds: Int? = 60
    ) : DismissOrSnoozeConstraint()

    /**
     * - Use this library isa https://github.com/itsxtt/pattern-lock
     */
    data class Pattern(
        var patternAsJsonString: String,
        override var canUseBackupAfterTimeInSeconds: Int? = 60
    ) : DismissOrSnoozeConstraint()

    data class PasswordText(
        var password: String,
        override var canUseBackupAfterTimeInSeconds: Int? = 60
    ) : DismissOrSnoozeConstraint()

    /** Using sensor isa. */
    data class WalkStepsActivity(
        var atLeastValue: Int,
        override var canUseBackupAfterTimeInSeconds: Int? = 60
    ) : DismissOrSnoozeConstraint()

    /**
     * - VIP needs backup since accuracy of a sensor might change so again we need a pattern/password
     * as a backup for any of the constraints here including pattern/password isa,
     * but u can as well set min duration for using the backup ex. only use backup after 1 min isa.
     * - Use only one of these properties isa.
     * - Get altitude by this method isa, [Link](https://developer.android.com/reference/android/hardware/SensorManager.html#getAltitude(float,%20float))
     * Since GPS altitude might not be accurate isa.
     * - Ex. for [atLeastValue] stand on bed and set this value so to wake up u must be standing on
     * bed isa.
     * - Ex. for [atMostValue] set it on ground floor so you ensure you are down of the apartment isa.
     */
    data class SeaLevelActivity(
        var atLeastValue: Int?,
        var atMostValue: Int?,
        override var canUseBackupAfterTimeInSeconds: Int? = 60
    ) : DismissOrSnoozeConstraint()

    /**
     * - Indicating the usage of [Sensor.TYPE_LIGHT] (DO NOT forget to check it's existence) isa.
     * - percent is used since sensors can get me only maxRangeValue isa.
     *
     * @property atLeastPercent ranges from 0 to 100 where if 70 is used then trigger occurs
     * if 70 or more isa.
     */
    data class AmbientBrightness(
        var atLeastPercent: Int,
        override var canUseBackupAfterTimeInSeconds: Int? = 60
    ) : DismissOrSnoozeConstraint()

    // https://firebase.google.com/docs/ml-kit/detect-faces
    // to-do smiley face with eye open wide see firebase ml-kit as image or for 10 seconds isa.
    // smile for 10 seconds isa.
    // if still in beta fallback to -> https://console.cloud.google.com/apis/library/vision.googleapis.com?filter=category%3Amachine-learning&id=957e5f12-b03d-4788-9f56-bcdd4dd51f5a&project=coding-learning-daily-tasks isa.


    // https://developer.android.com/training/location/receive-location-updates
    /**
     * - To calculate distance between 2 points isa, check -> [android.location.Location.distanceBetween] isa.
     *
     * @property radiusRangeInMeters ranges from 1 to 10 isa.
     *
     * @property durationStillInAreaInSeconds can be `0` so once enter alarm is marked as done
     * or can be snoozed and marked as done isa, also max is 10 minutes so 10 * 60 isa.
     */
    data class Location(
        var latitude: Double,
        var longitude: Double,
        var radiusRangeInMeters: Int,
        var durationStillInAreaInSeconds: Int,
        override var canUseBackupAfterTimeInSeconds: Int? = 60
    ) : DismissOrSnoozeConstraint()

    // https://developer.android.com/training/wearables/apps/voice
    // to-do speech recognition say specific thing

    // to-do in future isa, other unlocks fingerprint, face lock etc... isa. -> can be combination of 2 things isa or more isa.
    // ex. say something so tts say ` step unloked 1 more to go isa khosh pattern etc... isa.

}
