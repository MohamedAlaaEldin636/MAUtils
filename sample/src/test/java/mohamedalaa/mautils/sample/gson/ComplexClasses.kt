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

import android.app.RemoteAction
import android.os.Build
import mohamedalaa.mautils.core_kotlin.extensions.*
import mohamedalaa.mautils.gson.*
import mohamedalaa.mautils.sample.assertEquality
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
import java.lang.RuntimeException
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals

// todo same like reminderOrAction1 but some fields become null kda garab w 2ole isa.
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
        //assertStringEquality(reminderOrAction1.toJson(), r1.toJson()) // todo always error ezay isa ?!
        /*
        TODO OOOOOOOOH NOOOOOOOOOOO THE PROBLEM IS NOT SOLVED UNFORTUNATELY ISA.
         */
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
        val j1 = gc.toJson(reminderOrAction1)//.apply(::println)
        val r1 = gc.fromJson(j1)

        /*infoPrintLn("Expected")
        warnPrintLn(j1)
        infoPrintLn("Actual")
        infoPrintLn(r1.toJson())
        println()*/

        assertStringEquality(j1, r1.toJson())
        assertEquals(reminderOrAction1, r1)

        // listOfRepeatPolicyAsJsonString -> chainedRepeatPolicy
        // listOfTasks -> KEY_NORMAL_SERIALIZATION_JSON_STRING -> notifyAsReminderOrActionAsJsonString
    }

    /*
    todo below works so maybe no need for the new approach isa,
    but even if won't be needed make below checks isa
    1. does it work for java
    2. remove the when forced to gson to fallback to regular no fallback to null isa more logical isa.
     */
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

        assertStringEquality(reminderOrAction1.toJson(), r1.toJson())

        assertEquals(reminderOrAction1, r1)
        assertEquals(reminderOrAction2, r2)
        assertEquals(rInt, 33)
    }

    @Test
    fun bundle_take2() {
        assertEquals(reminderOrAction1, reminderOrAction2)
        val bundle = buildBundleGson2 {
            put(reminderOrAction1)

            put(reminderOrAction2)
        }

        val getterBundle = bundle.getterBundleGson2()
        val r1 = getterBundle.get<ReminderOrAction>()
        val r2 = getterBundle.get<ReminderOrAction>()

        assertStringEquality(reminderOrAction1.toJson(), r1.toJson())

        assertEquals(reminderOrAction1, r1)
        assertEquals(reminderOrAction2, r2)
    }

    /*
    // dunno how for java devs yet isa.
    buildBundleGson {
        put(object) // reified ext fun put isa. , optionalKey isa. -> MUST BE SAME ORDER && OPTIONAL KEY NOT NEEDED ISA.
    }
     */

    @Test
    fun hello1() {
        val list = takeVars(reminderOrAction1, 3)
        val r1 = list[0].fromJson<ReminderOrAction>()
        val r2 = list[1].fromJson<Int>()  // todo explicit put with name isa.

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

    private fun assertStringEquality2(string1: String, string2: String) {
        if (string1 == string2) return

        if (string1.length != string2.length) {
            infoPrintLn(
                "Different Length : string1.length ${string1.length}, string2.length ${string2.length}"
            )
            println()
        }

        infoPrintLn("Expected")
        var index1 = 0
        var index2 = 0
        while (max(index1, index2) < min(string1.length, string2.length)) {
            val char1 = string1[index1]; index1++
            val char2 = string2[index2]; index2++

            if (char1 == char2) {
                print(char1)
            }else {
                if (index1 == string1.length) break // No warn cuz no more chars isa.

                val newIndex1 = kotlin.runCatching {
                    string1.anyIndexOfOrNull(index1, string2.substring(index2))
                }.getOrNull() ?: string1.length

                warnPrint(string1.substring(index1.dec(), newIndex1))

                index1 = newIndex1
            }
        }
        println()

        infoPrintLn("Found")
        index1 = 0
        index2 = 0
        while (max(index1, index2) < min(string1.length, string2.length)) {
            val char1 = string1[index1]; index1++
            val char2 = string2[index2]; index2++

            if (char1 == char2) {
                print(char2)
            }else {
                if (index2 == string2.length) break

                val newIndex2 = kotlin.runCatching {
                    string2.anyIndexOfOrNull(index2, string1.substring(index1))
                }.getOrNull() ?: string2.length

                warnPrint(string2.substring(index2.dec(), newIndex2))

                index2 = newIndex2
            }
        }
        println()

        throw RuntimeException("Not Equals isa.")
    }

    private fun assertStringEquality(string1: String, string2: String) {
        if (string1 == string2) return

        if (string1.length != string2.length) {
            infoPrintLn(
                "Different Length : string1.length ${string1.length}, string2.length ${string2.length}"
            )
            println()
        }

        infoPrintLn("Expected")
        for (index in 0 until min(string1.length, string2.length)) {
            val char1 = string1[index]
            val char2 = string2[index]

            if (char1 == char2) {
                print(char1)
            }else {
                warnPrint(char1)
            }
        }
        println()

        infoPrintLn("Found")
        for (index in 0 until min(string1.length, string2.length)) {
            val char1 = string1[index]
            val char2 = string2[index]

            if (char1 == char2) {
                print(char2)
            }else {
                warnPrint(char2)
            }
        }
        println()

        throw RuntimeException("Not Equals isa.")
    }

}
