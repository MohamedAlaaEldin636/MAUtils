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

package mohamedalaa.mautils.sample.mautils_core_android.shared_pref

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import mohamedalaa.mautils.core_android.extensions.sharedPrefGet
import mohamedalaa.mautils.core_android.extensions.sharedPrefSet
import mohamedalaa.mautils.test_core.TestingLog
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.reflect.jvm.jvmName

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/24/2019.
 *
 */
@RunWith(RobolectricTestRunner::class)
class SharedPrefTest {

    private fun abc(context: Context): String
        = context.sharedPrefGet("fileName_1", "key_1", "defValue_1")

    @Test
    fun base_test() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Getting default value
        var string: String = context.sharedPrefGet("fileName_1", "key_1", "defValue_1")
        var integer: Int = context.sharedPrefGet("fileName_1", "key_2", 5)
        var bool: Boolean = context.sharedPrefGet("fileName_1", "key_3", true)
        var longg: Long = context.sharedPrefGet("fileName_1", "key_4", 5L)
        var floatt: Float = context.sharedPrefGet("fileName_1", "key_5", 5f)
        var stringSet: MutableSet<String> = context.sharedPrefGet("fileName_1", "key_6", mutableSetOf("1", "2", "4"))
        println("$string - $integer - $bool - $longg - $floatt - $stringSet")

        // Setting a value
        context.sharedPrefSet("fileName_1", "key_1", "newValue_1")
        context.sharedPrefSet("fileName_1", "key_2", 6)
        context.sharedPrefSet("fileName_1", "key_3", false)
        context.sharedPrefSet("fileName_1", "key_4", 6L)
        context.sharedPrefSet("fileName_1", "key_5", 6f)
        context.sharedPrefSet("fileName_1", "key_6", mutableSetOf("some new def", "val"))
        TestingLog.i("Set successfully isa")

        // Getting previously set value isa
        string = context.sharedPrefGet("fileName_1", "key_1", "defValue_1")
        integer = context.sharedPrefGet("fileName_1", "key_2", 5)
        bool = context.sharedPrefGet("fileName_1", "key_3", true)
        longg = context.sharedPrefGet("fileName_1", "key_4", 5L)
        floatt = context.sharedPrefGet("fileName_1", "key_5", 5f)
        stringSet = context.sharedPrefGet("fileName_1", "key_6", mutableSetOf("1", "2", "4"))
        println("$string - $integer - $bool - $longg - $floatt - $stringSet")
    }

    @Test
    fun z_1() {
        println("${String::class} ==== ${String::class.jvmName} ==== ${String::class.qualifiedName}")
    }

}