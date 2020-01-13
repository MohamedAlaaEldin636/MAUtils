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
import com.google.gson.Gson
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.fromJsonOrNull
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.gson.toJsonOrNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import mohamedalaa.mautils.sample.gson.ConditionReminderOrAction.*
import mohamedalaa.mautils.sample.gson.ConditionReminderOrAction.Timing.*
import mohamedalaa.mautils.sample.gson.ConditionReminderOrAction.Timing.AbstractExactDate.*
import kotlin.test.assertNotEquals

/**
 * @see [TestSuperClasses]
 */
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class TestObject {

    @Test
    fun sealedInsideData() {
        val dataClass = DataClass(
            "some string",
            SealedClass.ObjectClass
        )
        val json = dataClass.toJson()
        val value = json.fromJson<DataClass>()
        assertEquals(dataClass, value)
    }

    @Test
    fun sealedF111332() {
        val sealedClass = SealedClass.DataClass(77)
        val json = sealedClass.toJson<SealedClass>() // Must be explicitly defined since the inferred instance is .DataClass now isa.
        val value = json.fromJson<SealedClass>()
        assertEquals(sealedClass, value)

        val sealedClass2 = SealedClass.DataClass(77)
        val json2 = sealedClass2.toJson() // had to explicitly define toJson.<SealedClass> isa.
        val value2 = runCatching {
            json2.fromJson<SealedClass>()
        }.getOrNull() // error can'tbe converted, since it has been wrongly serialized.
        assertNotEquals(sealedClass2, value2)
    }

    @Test
    fun sealedF1() {
        val sealedClass: SealedClass = SealedClass.DataClass(77)
        val json = sealedClass.toJson()
        val value = json.fromJson<SealedClass>()
        assertEquals(sealedClass, value)

        val abstractClassKt: AbstractClassKt = ImplKt("sasasasdasdasdasdadsaas")
        val json2 = abstractClassKt.toJson()
        val value2 = json2.fromJson<AbstractClassKt>()
        assertEquals(abstractClassKt, value2)
    }

    /**
     * Below test succeeded el7, in last version but we wanna change that for objects isa.
     */
    @Test
    fun objectJson() {
        val o1 = ObjectClass // creation code -> object ObjectClass
        val j1 = o1.toJson()
        val r1 = j1.fromJson<ObjectClass>()
        assertEquals(o1, r1)

        val gson = Gson()
        val j2 = gson.toJson(o1)
        val r2 = gson.fromJson(j2, ObjectClass::class.java)
        assertNotEquals(o1, r2)
    }

    @Test
    fun sealedClassInstanceInsideDataClassConversionIsa() {
        val testSealedClassA1: TestSealedClassA = TestSealedClassA.DataClassA("", 4)
        val testSealedClassA2: TestSealedClassA = TestSealedClassA.DataClassA("", 4)

        assertEquals(testSealedClassA1, testSealedClassA2)

        val j1 = testSealedClassA1.toJson()
        val j2 = testSealedClassA2.toJsonOrNull() // remember this is toJson<TestSealedClassA>() isa.

        assertEquals(j1, j2)

        val r1 = j1.fromJson<TestSealedClassA>()
        val r2 = j2.fromJsonOrNull<TestSealedClassA>()

        assertEquals(r1, testSealedClassA1)
        assertEquals(r2, testSealedClassA2)

        val testSealedClassA3: TestSealedClassA = TestSealedClassA.ObjectA
        val testSealedClassA4: TestSealedClassA = TestSealedClassA.ObjectA

        assertEquals(testSealedClassA3, testSealedClassA4)

        val j3 = testSealedClassA3.toJson()
        val j4 = testSealedClassA4.toJsonOrNull()!!

        assertEquals(j3, j4)

        val r3 = j3.fromJson<TestSealedClassA>()
        val r4 = j4.fromJsonOrNull<TestSealedClassA>()!!

        assertEquals(r3, testSealedClassA3)
        assertEquals(r4, testSealedClassA4)

        // ---- Below starts the problems isa.

        val testDataClassA1 = TestDataClassA("", 44, testSealedClassA1, testSealedClassA3)
        val testDataClassA2 = TestDataClassA("", 44, testSealedClassA2, testSealedClassA4)

        assertEquals(testDataClassA1, testDataClassA2)

        val jj1 = testDataClassA1.toJson()
        val jj2 = testDataClassA1.toJson()

        assertEquals(jj1, jj2)

        val rr1 = jj1.fromJson<TestDataClassA>()
        val rr2 = jj2.fromJson<TestDataClassA>()

        assertEquals(rr1, testDataClassA1)
        assertEquals(rr2, testDataClassA2)
    }

    @Test
    fun reminderChecks() {

        // ==> Second nest of sealed class

        val windowTime1 = WindowTime(
            ExactTime(3, 42, true).apply { mido = "428093820"; int = 428093820 },
            ExactTime(11, 19, false)
        ).apply {
            mido = " kefwoiejf"
            int = 5648
        }
        val windowTime2 = WindowTime(
            ExactTime(3, 42, true).apply { mido = "428093820"; int = 428093820 },
            ExactTime(11, 19, false)
        ).apply {
            mido = " kefwoiejf"
            int = 5648
        }

        assertEquals(windowTime1, windowTime2)

        val j1 = windowTime1.toJson<ConditionReminderOrAction>()
        val j2 = windowTime2.toJson<ConditionReminderOrAction>()

        assertEquals(j1, j2)

        val r1 = j1.fromJson<ConditionReminderOrAction>()
        val r2 = j2.fromJson<ConditionReminderOrAction>()

        assertEquals(r1, windowTime1)
        assertEquals(r2, windowTime2)
        assertEquals(windowTime1.fromInclusive.int, 428093820)
        assertEquals((r1 as WindowTime).fromInclusive.int, 428093820)
        assertEquals(windowTime1.fromInclusive.mido, r1.fromInclusive.mido)
        assertNotEquals(windowTime1.fromInclusive.mido, r1.toInclusive.mido) // Note NOT equals isa.
        assertEquals(windowTime1.int, r1.int)
        assertEquals(windowTime1.mido, r1.mido)
        assertEquals(5648, r1.int)

        // ==> third nest of sealed class

        val daysOfMonth1: ConditionReminderOrAction = DaysOfMonth(listOf(32, 43, 53, 55), StrangeEnum.STRANGE_3)
        val daysOfMonth2: ConditionReminderOrAction = DaysOfMonth(listOf(32, 43, 53, 55), StrangeEnum.STRANGE_3)

        assertEquals(daysOfMonth1, daysOfMonth2)

        val jj1 = daysOfMonth1.toJson()
        val jj2 = daysOfMonth2.toJson()

        assertEquals(jj1, jj2)

        val rr1 = jj1.fromJson<ConditionReminderOrAction>()
        val rr2 = jj2.fromJson<ConditionReminderOrAction>()

        assertEquals(rr1, daysOfMonth1)
        assertEquals(rr2, daysOfMonth2)

        // ==> additional check second sealed class but has inside it var of third sealed and first sealed isa.

        val abstractWindowDate1: ConditionReminderOrAction = AbstractWindowDate(
            (daysOfMonth1 as DaysOfMonth).apply { aa = "mido"; int = 3232 },
            (daysOfMonth2 as DaysOfMonth).apply { aa = "mido"; int = 3232 },
            SomeObjectAsWellIsa
        ).apply {
            mido = "dewdwedwedewd"
            int = 300000
        }
        val abstractWindowDate2: ConditionReminderOrAction = AbstractWindowDate(
            daysOfMonth1.apply { aa = "mido"; int = 3232 },
            daysOfMonth2.apply { aa = "mido"; int = 3232 },
            SomeObjectAsWellIsa
        ).apply {
            mido = "dewdwedwedewd"
            int = 300000
        }

        assertEquals(abstractWindowDate1, abstractWindowDate2)

        val aj1 = abstractWindowDate1.toJson()
        val aj2 = abstractWindowDate2.toJson()

        assertEquals(aj1, aj2)

        val ar1 = aj1.fromJson<ConditionReminderOrAction>()
        val ar2 = aj2.fromJson<ConditionReminderOrAction>()

        assertEquals(ar1, abstractWindowDate1)
        assertEquals(ar2, abstractWindowDate2)
        assertEquals(abstractWindowDate1.mido, ar1.mido)
        assertEquals((abstractWindowDate1 as AbstractWindowDate).int, (ar1 as AbstractWindowDate).int)
        assertEquals(abstractWindowDate1.int, 300000)
        assertEquals(abstractWindowDate1.fromInclusive.aa, ar1.fromInclusive.aa)
        assertEquals(abstractWindowDate1.fromInclusive.mido, ar1.fromInclusive.mido)
        assertEquals(abstractWindowDate1.fromInclusive.int, ar1.fromInclusive.int)

        // ==> Data class having third nest of instance of sealed class isa.

        val reminderOrAction1 = ReminderOrAction(
            "",
            33,
            abstractWindowDate1,
            daysOfMonth1,
            true,
            32323.4232,
            32.3f
        )
        val reminderOrAction2 = ReminderOrAction(
            "",
            33,
            abstractWindowDate2,
            daysOfMonth2,
            true,
            32323.4232,
            32.3f
        )
        assertEquals(reminderOrAction1, reminderOrAction2)

        val j11 = reminderOrAction1.toJson()
        val j22 = reminderOrAction2.toJson()
        assertEquals(j11, j22)

        val r11 = j11.fromJson<ReminderOrAction>()
        val r22 = j22.fromJson<ReminderOrAction>()
        assertEquals(r11, reminderOrAction1)
        assertEquals(r22, reminderOrAction2)

        assertEquals(
            (r11.condition1 as AbstractWindowDate).fromInclusive.int,
            (reminderOrAction1.condition1 as AbstractWindowDate).fromInclusive.int
        )
        assertEquals(
            (r11.condition1 as AbstractWindowDate).fromInclusive.int,
            abstractWindowDate1.fromInclusive.int
        )
        assertEquals(
            (((r11.condition1 as AbstractWindowDate).fromInclusive) as DaysOfMonth).strangeEnum,
            (abstractWindowDate1.fromInclusive as DaysOfMonth).strangeEnum
        )
        assertEquals(
            (((r11.condition1 as AbstractWindowDate).fromInclusive) as DaysOfMonth).strangeEnum,
            StrangeEnum.STRANGE_3
        )
    }

}
