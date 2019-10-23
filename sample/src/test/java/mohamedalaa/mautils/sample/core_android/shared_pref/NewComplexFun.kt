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

import android.app.Application
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import mohamedalaa.mautils.core_android.custom_classes.SharedPrefSupportedTypesParams
import mohamedalaa.mautils.core_android.extensions.*
import mohamedalaa.mautils.core_kotlin.extensions.throwRuntimeException
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class NewComplexFun {

    @Test
    fun regularTypesIsa() {
        val boolean = true
        val string = "String"
        val float = 35f
        val stringSet = setOf("", null, "s")

        val application = ApplicationProvider.getApplicationContext<Application>()

        application.sharedPrefSetComplex(
            "fileName", "key_1", boolean
        )
        application.sharedPrefSetComplex(
            "fileName", "key_2", string
        )
        application.sharedPrefSetComplex(
            "fileName", "key_3", float
        )
        application.sharedPrefSetComplex(
            "fileName", "key_4", stringSet
        )

        application.sharedPrefGetComplex("fileName", "key_1", false).run {
            assertEquals(boolean, this)
        }
        application.sharedPrefGetComplex<String?>("fileName", "key_2", null).run {
            assertEquals(string, this)
        }
        application.sharedPrefGetComplex("fileName", "key_3", 212121212f).run {
            assertEquals(float, this)
        }
        application.sharedPrefGetComplex("fileName", "key_4", setOf(""), acceptNullableItemInSet = true).run {
            stringSet.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
    }

    @Test
    fun additionalTypesIsa_nullableItemsInSetNotJustForStrings() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        val setWithNullableItemFloat = setOf(null, 5f, 66f)
        val setNoNullableItemInt = setOf(55, 4)
        val setWithNullableItemBoolean = setOf(null, true, false)

        application.sharedPrefSetComplex(
            "fileName", "key_1", setWithNullableItemFloat,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.FLOAT
            )
        )
        application.sharedPrefSetComplex(
            "fileName", "key_2", setNoNullableItemInt,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.INT
            )
        )
        application.sharedPrefSetComplex(
            "fileName", "key_3", setWithNullableItemBoolean,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.BOOLEAN
            )
        )

        application.sharedPrefGetComplex(
            "fileName", "key_1", setOf<Float?>(), acceptNullableItemInSet = true,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.FLOAT
            )
        ).run {
            setWithNullableItemFloat.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
        application.sharedPrefGetComplex(
            "fileName", "key_2", setOf<Int>(), acceptNullableItemInSet = false/*default value isa.*/,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.INT
            )
        ).run {
            setNoNullableItemInt.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
        application.sharedPrefGetComplex(
            "fileName", "key_3", setOf<Boolean?>(true), acceptNullableItemInSet = true,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.BOOLEAN
            )
        ).run {
            setWithNullableItemBoolean.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
    }

    /**
     * ### Notes ( Results )
     * 1. if put null string that means you put nothing so when you get it back it seams like the
     * key never created so you will get the default value that only happens if the initial
     * value was null, however if initial value was non null string then you put null then initial value
     * wil be kept as is, no save for null values, so in future isa, either make another key-value
     * pair to ensure this or BETTER allow no null value to String? or to set<String?>? at all isa,
     * but i think you can keep Set<String?> check this out isa.
     */
    @Test
    fun additionalTypesIsa() {
        val pair = 5 to "mido"
        val setOfInts = setOf(2, 33)
        val anotherPair: Pair<Float, String?> = 7776f to ""

        val application = ApplicationProvider.getApplicationContext<Application>()

        application.sharedPrefSetComplex(
            "fileName", "key_1", pair,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.INT,
                SharedPrefSupportedTypesParams.STRING
            )
        )
        application.sharedPrefSetComplex(
            "fileName", "key_2", setOfInts,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.INT
            )
        )
        application.sharedPrefSetComplex(
            "fileName", "key_3", anotherPair,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.FLOAT,
                SharedPrefSupportedTypesParams.STRING
            )
        )

        application.sharedPrefGetComplex(
            "fileName", "key_1", 11 to "ewew",
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.INT,
                SharedPrefSupportedTypesParams.STRING
            )
        ).run {
            assertEquals(pair, this)
        }
        application.sharedPrefGetComplex(
            "fileName", "key_2", setOf<Int>(),
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.INT
            )
        ).run {
            setOfInts.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
        application.sharedPrefGetComplex(
            "fileName", "key_3", 3f to "dddddd",
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.FLOAT,
                SharedPrefSupportedTypesParams.STRING
            )
        ).run {
            assertEquals(anotherPair, this)
        }

        // null is not allowed isa.
        application.sharedPrefSetComplex(
            "fileName", "key_3", anotherPair.first to ""/*null makes error isa.*/,
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.FLOAT,
                SharedPrefSupportedTypesParams.STRING
            )
        )
        application.sharedPrefGetComplex(
            "fileName", "key_3", 3f to "dddddd",
            sharedPrefSupportedTypesParamsArray = *arrayOf(
                SharedPrefSupportedTypesParams.FLOAT,
                SharedPrefSupportedTypesParams.STRING
            )
        ).run {
            // def value is chosen instead (for string since it is null/*cleared*/) isa.
            assertEquals(this, anotherPair.first to "")
        }

        // null check for set like string isa. (set full of nulls + null set isa.)
        val set5: Set<String?>? = setOf(null, null, null)
        val set6 = setOf("ss", null, "", "sss")
        val set7 = setOf("f", "S")
        val stringNull: String? = null

        application.sharedPrefSetComplex(
            "fileName", "key_strange", stringNull/*since null then it will remove that key isa*/,
            removeIfValueParamIsNullOtherwiseThrowException = true
        )
        application.sharedPrefRemoveKey(
            "fileName", "key_4"
        )
        application.sharedPrefSetComplex(
            "fileName", "key_5", set5
        )
        application.sharedPrefSetComplex(
            "fileName", "key_6", set6
        )
        application.sharedPrefSetComplex(
            "fileName", "key_7", set7
        )

        val diffDefSet = setOf("DEFAULT SET ISA")
        application.sharedPrefGetComplex(
            "fileName", "key_4", diffDefSet
        ).run {
            diffDefSet.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
        application.sharedPrefGetComplex(
            "fileName", "key_5", diffDefSet, acceptNullableItemInSet = true
        ).run {
            set5.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
        application.sharedPrefGetComplex(
            "fileName", "key_6", diffDefSet, acceptNullableItemInSet = true
        ).run {
            set6.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
        application.sharedPrefGetComplex(
            "fileName", "key_7", diffDefSet
        ).run {
            set7.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
        }
    }

    /**
     * ### Results
     * 1. Yes when you put null that is remove(key) isa.
     */
    @Test
    fun does_actual_sharedPref_del_str_when_set_null_isa() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        assertEquals(
            false,
            application.sharedPrefHasKey("fileName", "key_strange")
        )

        var stringNull: String? = "Some string isa."
        application.sharedPrefSetComplex(
            "fileName", "key_strange", stringNull
        )

        assertEquals(
            true,
            application.sharedPrefHasKey("fileName", "key_strange")
        )

        stringNull = null
        try {
            application.sharedPrefSetComplex<String?>(
                "fileName", "key_strange", stringNull
            )
        }catch (throwable: Throwable) {
            assert(throwable is Exception)
        }
        application.sharedPrefSetComplex<String?>(
            "fileName", "key_strange", stringNull,
            removeIfValueParamIsNullOtherwiseThrowException = true
        )

        assertEquals(
            false,
            application.sharedPrefHasKey("fileName", "key_strange")
        )
    }

    private fun <T> Set<T>?.assertAllItemsInSetIsInsideAnotherIgnoreOrder(other: Set<T>?) {
        when {
            this == null && other == null -> return
            this == null || other == null || size != other.size -> throwRuntimeException("Expected $this, Actual $other")
            else -> forEach {
                assert(it in other)
            }
        }
    }

}