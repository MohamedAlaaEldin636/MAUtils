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

package mohamedalaa.mautils.sample.gson

import android.os.Build
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.sample.gson.model.*
import mohamedalaa.mautils.sample.gson.model.ConditionReminderOrAction
import mohamedalaa.mautils.sample.gson.model.entity.ReminderOrAction
import mohamedalaa.mautils.sample.gson.model.repeat.ChainedRepeatPolicy
import mohamedalaa.mautils.sample.gson.model.repeat.RepeatPolicy
import mohamedalaa.mautils.sample.gson.model.repeat.RepeatUntilPolicy
import mohamedalaa.mautils.sample.gson.model.styled_string.MAIndexedSpan
import mohamedalaa.mautils.sample.gson.model.styled_string.MASpan
import mohamedalaa.mautils.sample.gson.model.styled_string.MAStyledString
import mohamedalaa.mautils.sample.gson.open_classes.BaseComplexClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*
import kotlin.test.assertEquals

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class ComplexClasses : BaseComplexClass() {

    @Test
    fun fullDataInClassIsa() {
        assertEquals(
            reminderOrAction1.id,
            reminderOrAction2.id
        )

        assertEquals(
            reminderOrAction1.useLocalTiming,
            reminderOrAction2.useLocalTiming
        )
        assertEquals(
            reminderOrAction1.creationDate,
            reminderOrAction2.creationDate
        )

        assertEquals(
            reminderOrAction1.chatId,
            reminderOrAction2.chatId
        )
        assertEquals(
            reminderOrAction1.backupReminderOrAction,
            reminderOrAction2.backupReminderOrAction
        )

        assertEquals(
            reminderOrAction1.title,
            reminderOrAction2.title
        )
        assertEquals(
            reminderOrAction1.additionalNotes,
            reminderOrAction2.additionalNotes
        )

        assertEquals(
            reminderOrAction1.isFavorite,
            reminderOrAction2.isFavorite
        )
        assertEquals(
            reminderOrAction1.isHidden,
            reminderOrAction2.isHidden
        )

        assertEquals(
            reminderOrAction1.conditions,
            reminderOrAction2.conditions
        )

        assertEquals(
            reminderOrAction1.chainedRepeatPolicy,
            reminderOrAction2.chainedRepeatPolicy
        )

        assertEquals(
            reminderOrAction1.snoozePolicyAsJsonString,
            reminderOrAction2.snoozePolicyAsJsonString
        )
        assertEquals(
            reminderOrAction1.trashTimeInMillis,
            reminderOrAction2.trashTimeInMillis
        )
        assertEquals(
            reminderOrAction1.isGentleAlarm,
            reminderOrAction2.isGentleAlarm
        )
        assertEquals(
            reminderOrAction1.priorNotificationTimeInMillis,
            reminderOrAction2.priorNotificationTimeInMillis
        )

        assertEquals(
            reminderOrAction1.dismissConstraintAsJsonString,
            reminderOrAction2.dismissConstraintAsJsonString
        )
        assertEquals(
            reminderOrAction1.snoozeConstraintAsJsonString,
            reminderOrAction2.snoozeConstraintAsJsonString
        )
        assertEquals(
            reminderOrAction1.turnOnConstraintAsJsonString,
            reminderOrAction2.turnOnConstraintAsJsonString
        )
        assertEquals(
            reminderOrAction1.turnOffConstraintAsJsonString,
            reminderOrAction2.turnOffConstraintAsJsonString
        )

        assertEquals(
            reminderOrAction1.notifyAsReminderOrActionAsJsonString,
            reminderOrAction2.notifyAsReminderOrActionAsJsonString
        )

        // 1 & 2
        assertEquals(reminderOrAction1, reminderOrAction2)

        val j1 = reminderOrAction1.toJson()
        val j2 = reminderOrAction2.toJson()
        assertEquals(j1, j2)

        val r1 = j1.fromJson<ReminderOrAction>()
        val r2 = j2.fromJson<ReminderOrAction>()
        assertEquals(
            r1,
            r2
        )

        // 3 & 4
        assertEquals(reminderOrAction3, reminderOrAction4)

        val j3 = reminderOrAction3.toJson()
        val j4 = reminderOrAction4.toJson()
        assertEquals(j3, j4)
    }

}
