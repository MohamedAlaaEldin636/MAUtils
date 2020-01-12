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

@file:Suppress("MemberVisibilityCanBePrivate")

package mohamedalaa.mautils.sample.gson

import android.os.Build
import mohamedalaa.mautils.core_kotlin.extensions.*
import mohamedalaa.mautils.gson.*
import mohamedalaa.mautils.sample.gson.model.*
import mohamedalaa.mautils.sample.gson.model.entity.ReminderOrAction
import mohamedalaa.mautils.sample.gson.open_classes.BaseComplexClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class ComplexClasses : BaseComplexClass() {

    @Test
    fun abccc() {
        val j1 = reminderOrAction1.toJson()
        val r1 = j1.fromJson<ReminderOrAction>()

        assertEquals(reminderOrAction1, r1)
    }

    @Test
    fun withNulls() {
        val gc = GCROA()
        val j1 = gc.toJson(reminderOrAction1WithSomeNulls)
        val r1 = gc.fromJson(j1)

        assertEquals(j1, r1.toJson())
        assertEquals(reminderOrAction1WithSomeNulls, r1)

        assertEquals(
            (reminderOrAction1WithSomeNulls.dismissConstraintAsJsonString as DismissOrSnoozeConstraint.Pattern).patternAsJsonString,
            "dwdqlwldkql"
        )
        assertEquals(
            (reminderOrAction1WithSomeNulls.dismissConstraintAsJsonString as DismissOrSnoozeConstraint.Pattern).canUseBackupAfterTimeInSeconds,
            45
        )

        val b = buildBundleGson(5, "dsds", reminderOrAction1WithSomeNulls)
        b.getterBundleGson().apply {
            assertEquals(5, get())

            val s = get<String>()
            assertEquals(s, "dsds")

            val z = get<ReminderOrAction>()
            assertEquals(z, reminderOrAction1WithSomeNulls)

            assertEquals(
                (reminderOrAction1WithSomeNulls.dismissConstraintAsJsonString as DismissOrSnoozeConstraint.Pattern).patternAsJsonString,
                (z.dismissConstraintAsJsonString as DismissOrSnoozeConstraint.Pattern).patternAsJsonString
            )
            assertEquals(
                (reminderOrAction1WithSomeNulls.dismissConstraintAsJsonString as DismissOrSnoozeConstraint.Pattern).canUseBackupAfterTimeInSeconds,
                (z.dismissConstraintAsJsonString as DismissOrSnoozeConstraint.Pattern).canUseBackupAfterTimeInSeconds
            )
        }
    }

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
        assertEquals(reminderOrAction1.toJson(), r1.toJson())
        assertEquals(
            r1,
            r2
        )
        assertEquals(
            r1,
            reminderOrAction1
        )

        // 3 & 4
        assertEquals(reminderOrAction3, reminderOrAction4)

        val j3 = reminderOrAction3.toJson()
        val j4 = reminderOrAction4.toJson()
        assertEquals(j3, j4)
    }

    @Test
    fun aaaa() {
        val gc = GCROA()
        val j1 = gc.toJson(reminderOrAction1)
        val r1 = gc.fromJson(j1)

        assertEquals(j1, r1.toJson())
        assertEquals(reminderOrAction1, r1)
    }

    @Test
    fun bundle() {
        assertEquals(reminderOrAction1, reminderOrAction2)
        val bundle = buildBundleGson(
            reminderOrAction1,
            33,
            reminderOrAction2
        )

        val getterBundle = bundle.getterBundleGson()
        val r1 = getterBundle.get<ReminderOrAction>()
        val rInt = getterBundle.get<Int>()
        val r2 = getterBundle.get<ReminderOrAction>()

        assertEquals(reminderOrAction1.toJson(), r1.toJson())

        assertEquals(reminderOrAction1, r1)
        assertEquals(reminderOrAction2, r2)
        assertEquals(rInt, 33)
    }

    @Test
    fun hello1() {
        val list = takeVars(reminderOrAction1, 3)
        val r1 = list[0].fromJson<ReminderOrAction>()
        val r2 = list[1].fromJson<Int>()

        assertEquals(reminderOrAction1, r1)
        assertEquals(3, r2)
    }

    inline fun <reified V> takeVars(vararg values: V?): List<String> = values.map { it.toJson() }

    fun String.anyIndexOfOrNull(startIndexForReceiver: Int, other: String): Int? {
        if (isEmpty() || other.isEmpty()) return null

        var startIndexForOther = 0
        while (startIndexForOther < other.length) {
            val newOther = other.substring(startIndexForOther)

            indexOfOrNull(newOther, startIndexForReceiver)?.apply {
                return this
            } ?: startIndexForOther++
        }

        return null
    }

}
