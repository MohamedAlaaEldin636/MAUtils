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
import mohamedalaa.mautils.core_android_annotation.SharedPrefSomeClassNameNoContext
import mohamedalaa.mautils.core_android_annotation.sharedPref_SomeClassName_clearAll
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class Quick {

    @Test
    fun consumer() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        application.sharedPref_SomeClassName_clearAll()

        SharedPrefSomeClassNameNoContext.fileName()
    }

    /*@Test
    fun consumer() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        //application.shsom
        application.sharedPref_SomeClassName_SetString1("")
        val a = application.sharedPref_SomeClassName_GetString1("")
        //application.sharedPref_SomeClassName_GetSetInts1()

        application.sharedPrefSetComplex("fileName", "key", "value")
        application.sharedPrefSetComplex("fileName", "key", "value")

        SharedPrefSomeClassName.fileName()

        //MASharedPrefField.Container
        //@Nullable
        val vall = 4.33f

        MutablePair("", 88)

        5 to 88

        val s = 99

        MutablePair("", "")

        //SomeClass
        val sasa = SharedPrefSomeClass.getAnotherInt(application)

        val aa: Set<Int> = a().filterNotNull().toSet()

        application.sharedPrefClearAll("filename")
    }

    private fun a(): Set<Int?> = setOf(null)

    private fun SharedPreferences.a1() {
        edit {
            putString("", "")
        }

        getString("key", "def")
        getInt("key", 0)
        // int, long, float, boolean, string, stringSet

        //getStringSet()
    }*/

}