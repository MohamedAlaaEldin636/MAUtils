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

package mohamedalaa.mautils.sample.shared_pref_core

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import mohamedalaa.mautils.sample.gson.model.entity.ReminderOrAction
import mohamedalaa.mautils.sample.gson.open_classes.BaseComplexClass
import mohamedalaa.mautils.sample.shared_pref_core.helper_classes.CustomClass
import mohamedalaa.mautils.sample.shared_pref_core.helper_classes.MyEnum
import mohamedalaa.mautils.shared_pref_core.sharedPrefGet
import mohamedalaa.mautils.shared_pref_core.sharedPrefHasKey
import mohamedalaa.mautils.shared_pref_core.sharedPrefSet
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class SettersAndGettersOfSharedPrefs : BaseComplexClass() {

    private val fileName = "fileName"

    @Test
    fun complexTypes() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val set = setOf("abc", null, "cde")
        context.sharedPrefSet(fileName, "key1", set)
        assertEquals(
            set,
            context.sharedPrefGet<Set<String?>?>(
                fileName, "key1", null
            )
        )
        val set2: Set<String?>? = null
        context.sharedPrefSet(
            fileName = fileName,
            key = "set2",
            value = set2
        )
        assertEquals(
            set2,
            context.sharedPrefGet<Set<String?>?>(
                fileName, "set2", null
            )
        )

        val set3 = setOf(2, null, 45)
        context.sharedPrefSet(fileName, "key2", set3)
        assertEquals(
            set3,
            context.sharedPrefGet<Set<Int?>?>(
                fileName, "key2", null
            )
        )
        val set4: Set<Int?>? = null
        context.sharedPrefSet(fileName, "set4", set4, true)
        assertEquals(
            set4,
            context.sharedPrefGet<Set<Int?>?>(
                fileName, "set4", null
            )
        )
    }

    @Test
    fun complexGson() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        context.sharedPrefSet(fileName, "key1", reminderOrAction1)
        assertEquals(
            reminderOrAction1,
            context.sharedPrefGet<ReminderOrAction?>(
                fileName, "key1", null
            )
        )
    }

    @Test
    fun simple_supported_by_sharedPrefs_types() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // non-null String
        var string: String? = "string"
        context.sharedPrefSet(fileName, "k1", string)
        assertEquals(
            string,
            context.sharedPrefGet<String?>(fileName, "k1", null)
        )

        // null string -> 2 cases if permitted and if not isa.
        string = null
        try {
            context.sharedPrefSet<String?>(fileName, "k1", string, removeKeyIfValueIsNull = false)
            throw Throwable("should never reach here isa.")
        }catch (runtimeException: RuntimeException) {
            context.sharedPrefSet<String?>(fileName, "k1", string, removeKeyIfValueIsNull = true)
        }
        assertEquals(
            context.sharedPrefHasKey(fileName, "k1"),
            false
        )
    }

    @Test
    fun complex_types_not_supported_by_sharedPrefs() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // without GsonConverter
        val customClass = CustomClass()
        context.sharedPrefSet(fileName, "k1", customClass)
        assertEquals(
            customClass,
            context.sharedPrefGet<CustomClass?>(fileName, "k1", null)
        )

        val myEnum = MyEnum.THREE
        context.sharedPrefSet(fileName, "k2", myEnum)
        assertEquals(
            myEnum,
            context.sharedPrefGet(fileName, "k2", MyEnum.UNKNOWN)
        )

        val value: Pair<Boolean, List<Pair<Int, Pair<Float, Double>>>> = true to listOf(
            5 to (6f to 5.6)
        )
        val diffValue: Pair<Boolean, List<Pair<Int, Pair<Float, Double>>>> = false to listOf(
            51 to (6.32f to 545.6),
            951 to (656.32f to 545.0)
        )
        context.sharedPrefSet(
            fileName,
            "k3",
            value
        )
        assertEquals(
            value,
            context.sharedPrefGet(
                fileName,
                "k3",
                diffValue
            )
        )
    }

}