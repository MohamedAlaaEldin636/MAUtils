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

package mohamedalaa.mautils.shared_pref_core

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import mohamedalaa.mautils.shared_pref_core.helper_classes.CustomClass
import mohamedalaa.mautils.shared_pref_core.helper_classes.GsonConverterPairBooleanAndListPairIntAndPairFloatAndDouble
import mohamedalaa.mautils.shared_pref_core.helper_classes.MyEnum
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class SettersAndGettersOfSharedPrefs {

    private val fileName = "fileName"

    @Test
    fun simple_supported_by_sharedPrefs_types() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // non-null String
        var string: String? = "string"
        context.sharedPrefSetComplex(fileName, "k1", string)
        assertEquals(
            string,
            context.sharedPrefGetComplex<String?>(fileName, "k1", null)
        )

        // null string -> 2 cases if permitted and if not isa.
        string = null
        try {
            context.sharedPrefSetComplex<String?>(fileName, "k1", string, removeKeyIfValueIsNull = false)
            throw Throwable("should never reach here isa.")
        }catch (runtimeException: RuntimeException) {
            context.sharedPrefSetComplex<String?>(fileName, "k1", string, removeKeyIfValueIsNull = true)
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
        context.sharedPrefSetComplex(fileName, "k1", customClass)
        assertEquals(
            customClass,
            context.sharedPrefGetComplex<CustomClass?>(fileName, "k1", null)
        )

        val myEnum = MyEnum.THREE
        context.sharedPrefSetComplex(fileName, "k2", myEnum)
        assertEquals(
            myEnum,
            context.sharedPrefGetComplex(fileName, "k2", MyEnum.UNKNOWN)
        )

        // with GsonConverter<Pair<Boolean, List<Pair<Integer, Pair<Float, Double>>>>>
        val value: Pair<Boolean, List<Pair<Int, Pair<Float, Double>>>> = true to listOf(
            5 to (6f to 5.6)
        )
        val diffValue: Pair<Boolean, List<Pair<Int, Pair<Float, Double>>>> = false to listOf(
            51 to (6.32f to 545.6),
            951 to (656.32f to 545.0)
        )
        context.sharedPrefSetComplex(
            fileName,
            "k3",
            value,
            gsonConverter = GsonConverterPairBooleanAndListPairIntAndPairFloatAndDouble()
        )
        assertEquals(
            value,
            context.sharedPrefGetComplex(
                fileName,
                "k3",
                diffValue,
                gsonConverter = GsonConverterPairBooleanAndListPairIntAndPairFloatAndDouble()
            )
        )
    }

}