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

package mohamedalaa.mautils.sample.core_android.shared_pref

import android.content.Context
import android.os.Build
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("ClassName")
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class General_1 {

    @Test
    fun test_fun() {
        var nonNullableBool1 = true
        var nullableBool1: Boolean? = false

        nonNullableBool1 = test_sharedPref_SomeClassName_GetBoolean1(true)
        println(nonNullableBool1)
        nonNullableBool1 = test_sharedPref_SomeClassName_GetBoolean1()
        println(nonNullableBool1)
        nullableBool1 = test_sharedPref_SomeClassName_GetBoolean1(null)
        println(nullableBool1)
        println()

        println(
            test_sharedPref_SomeClassName_GetBoolean1<Boolean>().apply {

            }
        )
        println(
            test_sharedPref_SomeClassName_GetBoolean1<Boolean?>().apply {

            }
        )
    }

    @Suppress("UNCHECKED_CAST")
    @JvmName("getBoolean1")
    @JvmOverloads
    @Synchronized
    // TODO Test 7war el false as T if T is Boolean or Boolean? isa.
    fun <T> test_sharedPref_SomeClassName_GetBoolean1(
        defValue: T = false as T,
        acceptNullableItemInSet: Boolean = false
    ): T {
        /*= sharedPrefGetComplex<kotlin.Boolean>(
        "SomeClassName",
        "boolean1",
        defValue as? Boolean,
        Context.MODE_PRIVATE,
        false*//*acceptNullableItemInSet todo from annotation params isa.*//*,
        *arrayOf()*/
        val newValue = defValue as? Boolean

        return newValue as T
    }

}