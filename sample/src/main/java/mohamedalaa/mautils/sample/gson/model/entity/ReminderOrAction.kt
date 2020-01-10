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

package mohamedalaa.mautils.sample.gson.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import mohamedalaa.mautils.sample.gson.model.repeat.ChainedRepeatPolicy
import mohamedalaa.mautils.sample.gson.model.styled_string.MAStyledString
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter
import mohamedalaa.mautils.sample.gson.model.*

/**
 * ### Properties
 * - [id]
 * - [useLocalTiming]
 * - [creationDate]
 * - [chatId]
 * - [backupReminderOrAction]
 * - [title]
 * - [additionalNotes]
 * - [isFavorite]
 * - [isHidden]
 * - [conditions]
 * - [chainedRepeatPolicy]
 * - [snoozePolicyAsJsonString]
 * - [trashTimeInMillis]
 * - [isGentleAlarm]
 * - [priorNotificationTimeInMillis]
 * - [dismissConstraintAsJsonString]
 * - [snoozeConstraintAsJsonString]
 *
 * @property chatId `empty` in case for self not for individual or group and if not empty then it
 * refers to id inside firebase realtime-database isa.
 *
 * @property title max 250 chars isa.
 * @property additionalNotes max 1000 chars isa.
 *
 * @property conditions MUST never be empty isa,
 * **ALSO VIP** inner list is && relation while outer is || relation and there cannot be
 * 2 same types of && relation ex. cannot say ring when enter some place and enter another place
 * at same time it does not make sense isa.
 *
 * @property trashTimeInMillis `null` means not inside trash, else date is used for permanent
 * deletion schedule by a class extending [ListenableWorker] isa.
 *
 * @property isGentleAlarm `null` means Acc. to global app settings else true/false to indicate
 * gradually increase ring volume or not (Used only in case notify as alarm not as notification nor action) isa.
 *
 * @property turnOffConstraintAsJsonString In case showing list of alarms and just wanna turn it off once isa.
 */
@Entity(
    tableName = "reminderOrAction"
)
data class ReminderOrAction(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var useLocalTiming: Boolean = true,
    var creationDate: Long = System.currentTimeMillis(),

    var chatId: String = "",
    @Embedded(prefix = "backupReminderOrAction_")
    var backupReminderOrAction: BackupReminderOrAction = BackupReminderOrAction(),

    @Embedded(prefix = "title_")
    var title: MAStyledString = MAStyledString(),
    @Embedded(prefix = "additionalNotes_")
    var additionalNotes: MAStyledString = MAStyledString(),

    var isFavorite: Boolean = false,
    var isHidden: Boolean = false,

    @MARoomGsonTypeConverter
    var conditions: List<List<ConditionReminderOrAction>> = listOf(
        listOf(
            ConditionReminderOrAction.Timing.ExactTime(
                12, 30, true
            )
        )
    ),

    @Embedded(prefix = "chainedRepeatPolicy_")
    var chainedRepeatPolicy: ChainedRepeatPolicy = ChainedRepeatPolicy(),

    @MARoomGsonTypeConverter
    var snoozePolicyAsJsonString: SnoozePolicy = SnoozePolicy.AccToGlobalAppSettings,

    var trashTimeInMillis: Long? = null,

    var isGentleAlarm: Boolean? = true,
    var priorNotificationTimeInMillis: Long? = null,

    @MARoomGsonTypeConverter
    var dismissConstraintAsJsonString: DismissOrSnoozeConstraint? = null,
    var snoozeConstraintAsJsonString: DismissOrSnoozeConstraint? = null,
    var turnOffConstraintAsJsonString: DismissOrSnoozeConstraint? = null,
    var turnOnConstraintAsJsonString: DismissOrSnoozeConstraint? = null,

    @MARoomGsonTypeConverter
    var notifyAsReminderOrActionAsJsonString: NotifyAsReminderOrAction = NotifyAsReminderOrAction.Alarm()

)
