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

package mohamedalaa.mautils.mautils.module_gson_processor

import android.os.Bundle
import android.os.Parcelable
import mohamedalaa.mautils.core_android.buildBundle
import mohamedalaa.mautils.gson.*
import mohamedalaa.mautils.mautils.for_unit_testing.TestingLog
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.Serializable

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/14/2019.
 *
 */
@RunWith(RobolectricTestRunner::class)
class IsCoreAndroidNeeded {

    private fun <T> safeAssertEquals(first: T?, second: T?) {
        if (first != second) {
            TestingLog.e("FAILED ==> $first === $second")
        }else {
            TestingLog.v("Succeeded -> $first")
        }
    }

    @Test
    fun clazzes() {
        println(Int::class)
        println(Int::class.java)
        println(java.lang.Integer::class)
        println(java.lang.Integer::class.java)
    }

    @Test
    fun test_all() {
        val v0 = A1234()
        val v1: String? = null
        val v2 = false
        val v3: Byte = 3
        val v4 = '2'
        val v5 = 3.0
        val v6 = 4.5f
        val v7 = 5
        val v8 = 5L
        val v9: Short = 3

        val v10: CharSequence = ""
        val v11: Bundle = buildBundle(4, "")
        val v12: Parcelable = buildBundle(4, "")

        val v13 = booleanArrayOf(false, true, false)
        val v14 = byteArrayOf(2, 5)
        val v15 = charArrayOf('a')
        val v16 = doubleArrayOf(9.3, 55.99)
        val v17 = floatArrayOf(4.0f, 5f, 3.2f)
        val v18 = intArrayOf(2, 51212)
        val v19 = longArrayOf(2, 52)
        val v20 = shortArrayOf(2, 51)

        val v21 = Array(4) {1}

        val v22: Serializable = false
        val v23 = listOf(4.66, "")

        val bundle = buildBundleGson(
            v0.forceUsingJsonInBundle(), v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17, v18, v19, v20, v21,
            v22, v23
        )

        val getter = bundle.getKGetterBundleGson()

        safeAssertEquals(v0, getter.getOrNull())
        safeAssertEquals(v1, getter.getOrNull())
        safeAssertEquals(v2, getter.getOrNull())
        safeAssertEquals(v3, getter.getOrNull())
        safeAssertEquals(v4, getter.getOrNull())
        safeAssertEquals(v5, getter.getOrNull())
        safeAssertEquals(v6, getter.getOrNull())
        safeAssertEquals(v7, getter.getOrNull())
        safeAssertEquals(v8, getter.getOrNull())
        safeAssertEquals(v9, getter.getOrNull())
        safeAssertEquals(v10, getter.getOrNull())
        safeAssertEquals(v11, getter.getOrNull())
        safeAssertEquals(v12, getter.getOrNull())
        safeAssertEquals(v13, getter.getOrNull())
        safeAssertEquals(v14, getter.getOrNull())
        safeAssertEquals(v15, getter.getOrNull())
        safeAssertEquals(v16, getter.getOrNull())
        safeAssertEquals(v17, getter.getOrNull())
        safeAssertEquals(v18, getter.getOrNull())
        safeAssertEquals(v19, getter.getOrNull())
        safeAssertEquals(v20, getter.getOrNull())
        safeAssertEquals(v21, getter.getOrNull())
        safeAssertEquals(v22, getter.getOrNull())
        safeAssertEquals(v23, getter.getOrNull())
    }

    private data class A1234(
        var int: Int = 9,
        var s: String = ""
    )

}