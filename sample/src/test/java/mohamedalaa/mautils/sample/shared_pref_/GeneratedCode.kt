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

package mohamedalaa.mautils.sample.shared_pref_

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import mohamedalaa.mautils.sample.general_custom_classes.Person
import mohamedalaa.mautils.sample.shared_pref_.mautils_sharedPref.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class GeneratedCode {

    private val context by lazy {
        ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun personClass_in_SomeClassName() = context.performIfNotNull {
        val person = Person()

        // personWithDefaultValue
        sharedPref_SomeClassName_SetPersonWithDefaultValue(person)
        assertEquals(
            person,
            sharedPref_SomeClassName_GetPersonWithDefaultValue()
        )

        // personManualConversion
        sharedPref_SomeClassName_SetPersonManualConversion(person)
        assertEquals(
            person,
            sharedPref_SomeClassName_GetPersonManualConversion()
        )

        // personAutoConversion
        sharedPref_SomeClassName_SetPersonAutoConversion(person)
        assertEquals(
            person,
            sharedPref_SomeClassName_GetPersonAutoConversion()
        )
    }

    @Test
    fun others_in_SomeClassName() = context.performIfNotNull {
        // nestedTypeParamWithGsonConverterConversion
        val nestedTypeParamWithGsonConverterConversion = listOf(
            (4 to (setOf(3.4f))) to "str"
        )
        sharedPref_SomeClassName_SetNestedTypeParamWithGsonConverterConversion(nestedTypeParamWithGsonConverterConversion)
        assertEquals(
            nestedTypeParamWithGsonConverterConversion,
            sharedPref_SomeClassName_GetNestedTypeParamWithGsonConverterConversion()
        )

        //mutablePairStringAndBoolean
        //needAutDefValueForNullableBoolean
        //setOfInts1
        //needAutDefValueForNonNullBoolean
        //setOfNullableStrings1
        //pairFloatNullableAndLong
    }

}