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
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * @see [TestObject]
 */
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class TestSuperClasses {

    // ==> Second nest of sealed class

    val windowTime1 = ConditionReminderOrAction.Timing.WindowTime(
        ConditionReminderOrAction.Timing.ExactTime(3, 42, true).apply {
            mido = "428093820"; int = 428093820
        },
        ConditionReminderOrAction.Timing.ExactTime(11, 19, false)
    ).apply {
        mido = " kefwoiejf"
        int = 5648
    }
    val windowTime2 = ConditionReminderOrAction.Timing.WindowTime(
        ConditionReminderOrAction.Timing.ExactTime(3, 42, true).apply {
            mido = "428093820"; int = 428093820
        },
        ConditionReminderOrAction.Timing.ExactTime(11, 19, false)
    ).apply {
        mido = " kefwoiejf"
        int = 5648
    }

    val j1 = windowTime1.toJson<ConditionReminderOrAction>()
    val j2 = windowTime2.toJson<ConditionReminderOrAction>()

    @Suppress("unused")
    val r1 = j1.fromJson<ConditionReminderOrAction>()
    @Suppress("unused")
    val r2 = j2.fromJson<ConditionReminderOrAction>()

    // ==> third nest of sealed class

    val daysOfMonth1: ConditionReminderOrAction =
        ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth(
            listOf(32, 43, 53, 55),
            StrangeEnum.STRANGE_3
        )
    val daysOfMonth2: ConditionReminderOrAction =
        ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth(
            listOf(32, 43, 53, 55),
            StrangeEnum.STRANGE_3
        )

    val jj1 = daysOfMonth1.toJson()
    val jj2 = daysOfMonth2.toJson()

    @Suppress("unused")
    val rr1 = jj1.fromJson<ConditionReminderOrAction>()
    @Suppress("unused")
    val rr2 = jj2.fromJson<ConditionReminderOrAction>()

    // ==> additional check second sealed class but has inside it var of third sealed and first sealed isa.

    val abstractWindowDate1: ConditionReminderOrAction = ConditionReminderOrAction.Timing.AbstractWindowDate(
        (daysOfMonth1 as ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth).apply {
            aa = "mido"; int = 3232
        },
        (daysOfMonth2 as ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth).apply {
            aa = "mido"; int = 3232
        },
        ConditionReminderOrAction.SomeObjectAsWellIsa
    ).apply {
        mido = "dewdwedwedewd"
        int = 300000
    }
    val abstractWindowDate2: ConditionReminderOrAction = ConditionReminderOrAction.Timing.AbstractWindowDate(
        (daysOfMonth1 as ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth).apply { aa = "mido"; int = 3232 },
        (daysOfMonth2 as ConditionReminderOrAction.Timing.AbstractExactDate.DaysOfMonth).apply { aa = "mido"; int = 3232 },
        ConditionReminderOrAction.SomeObjectAsWellIsa
    ).apply {
        mido = "dewdwedwedewd"
        int = 300000
    }

    val aj1 = abstractWindowDate1.toJson()
    val aj2 = abstractWindowDate2.toJson()

    @Suppress("unused")
    val ar1 = aj1.fromJson<ConditionReminderOrAction>()
    @Suppress("unused")
    val ar2 = aj2.fromJson<ConditionReminderOrAction>()

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

    val j11 = reminderOrAction1.toJson()
    val j22 = reminderOrAction2.toJson()

    @Suppress("unused")
    val r11 = j11.fromJson<ReminderOrAction>()
    @Suppress("unused")
    val r22 = j22.fromJson<ReminderOrAction>()

    /**
     * - gson serialization support superclass properties isa.
     */
    @Test
    fun f1() {
        val impl1 = ImplOfOpenClassA("", null).apply { def = "fioweijoifewjfowj" }
        val impl2 = ImplOfOpenClassA("", null).apply { def = "fioweijoifewjfowj" }
        assertEquals(impl1, impl2)

        val jImpl1 = impl1.toJson()
        val jImpl2 = impl2.toJson()
        assertEquals(jImpl1, jImpl2)

        val rImpl1 = jImpl1.fromJson<ImplOfOpenClassA>()
        val rImpl2 = jImpl2.fromJson<ImplOfOpenClassA>()
        assertEquals(rImpl1, impl1)
        assertEquals(rImpl2, impl2)
    }

    @Test
    fun notDataClasses() {
        val notDataClass1 = NotDataClass(1, 2, 4)
        val notDataClass2 = NotDataClass(1, 2, 3)
        assertNotEquals(notDataClass1.totalAsString, notDataClass2.totalAsString)

        var j1 = notDataClass1.toJson()
        var j2 = notDataClass2.toJson()
        assertNotEquals(j1, j2)

        var r1 = j1.fromJson<NotDataClass>()
        var r2 = j2.fromJson<NotDataClass>()
        assertNotEquals(r1.totalAsString, r2.totalAsString)

        notDataClass2.totalAsString = notDataClass1.totalAsString
        assertEquals(notDataClass1.totalAsString, notDataClass2.totalAsString)

        j1 = notDataClass1.toJson()
        j2 = notDataClass2.toJson()
        assertEquals(j1, j2)

        r1 = j1.fromJson()
        r2 = j2.fromJson()
        assertEquals(notDataClass1.totalAsString, r1.totalAsString)
        assertEquals(notDataClass2.totalAsString, r2.totalAsString)
    }

    @Test
    fun compareGsonApproaches() {
        // using gson -> same
        val int = 3
        val gson = Gson()
        val j1 = gson.toJson(int)
        val r1 = gson.fromJson(j1, Int::class.java)
        assertEquals(int, r1)

        val j2 = int.toJson()
        val r2 = j2.fromJson<Int>()
        assertEquals(int, r2)

        // using gson isa ( not equals )
        val objectClassA = ObjectClassA
        val j3 = gson.toJson(objectClassA)
        val r3 = gson.fromJson(j3, ObjectClassA::class.java)
        assertNotEquals(objectClassA, r3) // NOT equals

        val j4 = objectClassA.toJson()
        val r4 = j4.fromJson<ObjectClassA>()
        assertEquals(objectClassA, r4)

        // using gson isa. -> same
        val strangeEnum = StrangeEnum.STRANGE_5
        val j5 = gson.toJson(strangeEnum)
        val r5 = gson.fromJson(j5, StrangeEnum::class.java)
        assertEquals(strangeEnum, r5)

        val j6 = strangeEnum.toJson()
        val r6 = j6.fromJson<StrangeEnum>()
        assertEquals(strangeEnum, r6)

        // using gson isa. -> needs type token
        val map1 = mapOf(4 to "")
        val j7 = gson.toJson(map1)
        val token = object : TypeToken<Map<Int, String>>(){}
        val r7: Map<Int, String> = gson.fromJson(j7, token.type)
        assertEquals(map1, r7)

        // no need for GsonConverter since this is a kotlin consumer code isa.
        val j8 = map1.toJson()
        val r8 = j8.fromJson<Map<Int, String>>()
        assertEquals(map1, r8)

        // using gson isa. -> can't since `reminderOrAction1` has a property of a sealed class type
        val map2: Map<List<Int>, ReminderOrAction> = mapOf(listOf(2) to reminderOrAction1)
        // Can't do it just because reminderOrAction1 can't be converted isa.
        try {
            val j9 = gson.toJson(map2)
            val token1 = object : TypeToken<Map<List<Int>, ReminderOrAction>>(){}
            val r9: Map<List<Int>, ReminderOrAction> = gson.fromJson(j9, token1.type)
            // Not reached code
            throw RuntimeException("r9 -> $r9")
        }catch (e: Exception) {
            assertTrue { e is JsonSyntaxException }
        }

        val j10 = map2.toJson()
        val r10 = j10.fromJson<Map<List<Int>, ReminderOrAction>>()
        assertEquals(map2, r10)

        // gson -> again need type token
        val map3: Map<String, List<Int>> = mapOf("" to listOf(4, 5, 33))
        val j11 = gson.toJson(map3)
        val token2 = object : TypeToken<Map<String, List<Int>>>(){}
        val r11: Map<String, List<Int>> = gson.fromJson(j11, token2.type)
        assertEquals(map3, r11)

        val j12 = map3.toJson()
        val r12 = j12.fromJson<Map<String, List<Int>>>()
        assertEquals(map3, r12)

        // gson ( NOTE you must declare TypeToken in a separate java class isa. )
        // can't be used anonymously nor declared in a kotlin file isa,
        // can only be used anonymously if from java consumer code isa.
        val map4: Map<String, List<Pair<Int, Int>>> = mapOf("" to listOf(4 to 3, 5 to 3, 33 to 3))
        val j13 = gson.toJson(map4)
        val token3 = TestTypeToken1()
        val r13: Map<String, List<Pair<Int, Int>>> = gson.fromJson(j13, token3.type)
        assertEquals(map4, r13)
        // Proving that can't be used anonymously in case of gson isa.
        val token3Anonymous = object : TypeToken<Map<String, List<Pair<Int, Int>>>>(){}
        val j13Anonymous = gson.toJson(map4, token3Anonymous.type)
        val r13Anonymous: Map<String, List<Pair<Int, Int>>> = gson.fromJson(j13Anonymous, token3Anonymous.type)
        assertNotEquals(map4, r13Anonymous) // NOTE NotEquals isa. ALTHOUGH if java code calls it, it can becomes equals isa.

        // No need for GsonConverter since we use code from kotlin not java isa,
        // HOWEVER even if java consumer code then GsonConverter can be anonymous check this out
        // in the java test class isa.
        val j14 = map4.toJson()
        val r14 = j14.fromJson<Map<String, List<Pair<Int, Int>>>>()
        assertEquals(map4, r14)

        // No matter how much nested type params still no need for GsonConverter ( again cuz kotlin consumer code ) isa.
        val severalTypeParams1 = SeveralTypeParams(
            SeveralTypeParams(
                SeveralTypeParams(
                    reminderOrAction1, 5f, ""
                ),
                3.3,
                abstractWindowDate1
            ),
            4,
            true
        )
        val severalTypeParams2: SeveralTypeParams<
            SeveralTypeParams<
                SeveralTypeParams<
                    ReminderOrAction,
                    Float,
                    String
                >,
                Double,
                ConditionReminderOrAction
            >,
            Int,
            Boolean
        > = SeveralTypeParams(
            SeveralTypeParams(
                SeveralTypeParams(
                    reminderOrAction1, 5f, ""
                ),
                3.3,
                abstractWindowDate1
            ),
            4,
            true
        )
        assertEquals(severalTypeParams1, severalTypeParams2)

        val jSeveralTypeParams1 = severalTypeParams1.toJson()
        val jSeveralTypeParams2 = severalTypeParams2.toJson()
        assertEquals(jSeveralTypeParams1, jSeveralTypeParams2)

        val rSeveralTypeParams1 = jSeveralTypeParams1.fromJson<SeveralTypeParams<
            SeveralTypeParams<
                SeveralTypeParams<
                    ReminderOrAction,
                    Float,
                    String
                >,
                Double,
                ConditionReminderOrAction
            >,
            Int,
            Boolean
        >>()
        val rSeveralTypeParams2 = jSeveralTypeParams2.fromJson<SeveralTypeParams<
            SeveralTypeParams<
                SeveralTypeParams<
                    ReminderOrAction,
                    Float,
                    String
                >,
                Double,
                ConditionReminderOrAction
            >,
            Int,
            Boolean
        >>()
        assertEquals(rSeveralTypeParams1, severalTypeParams1)
        assertEquals(rSeveralTypeParams2, severalTypeParams2)

        // using gson -> even with token can't be solved isa.
        val testTypeToken2 = TestTypeToken2()
        val jSeveralTypeParams1Gson = gson.toJson(severalTypeParams1, testTypeToken2.type)
        try {
            @Suppress("UNUSED_VARIABLE")
            val rSeveralTypeParams1Gson: SeveralTypeParams<
                SeveralTypeParams<
                    SeveralTypeParams<
                        ReminderOrAction,
                        Float,
                        String
                        >,
                    Double,
                    ConditionReminderOrAction
                    >,
                Int,
                Boolean
            > = gson.fromJson(jSeveralTypeParams1Gson, testTypeToken2.type)
            // Not reached code ( proving gson can't convert this ) isa.
            assertTrue { false }
        }catch (throwable: Throwable) {
            assertTrue { throwable is RuntimeException }
        }
    }

    @Test
    fun nullableTypeParams() {
        val list = listOf(6, null, 53) // Nullable elements list (Nullable type parameter)
        val jsonString = list.toJson()
        // below code makes no error will occur however listOfInts is currently actually List<Int?>?
        val rList: List<Int> = jsonString.fromJson()
        // can't even loop through the list isa.
        assertTrue {
            runCatching {
                @Suppress("SENSELESS_COMPARISON")
                rList.any { it == null }
                // Not reached code isa.
                false
            }.getOrElse {
                it is NullPointerException
            }
        }
    }

}
