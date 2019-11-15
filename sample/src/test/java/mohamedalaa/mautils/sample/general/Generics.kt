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

package mohamedalaa.mautils.sample.general

import android.app.Application
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.google.gson.reflect.TypeToken
import mohamedalaa.mautils.shared_pref_core.sharedPrefGet
import mohamedalaa.mautils.shared_pref_core.sharedPrefSet
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class Generics {

    /**
     * - Get another type from shared pref surely throws exception of ast exception isa
     * - override another type in set is ok isa.
     */
    @Test
    fun fff() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        application.sharedPrefSet("f", "k", 3)
        application.sharedPrefGet("f", "k", 0).apply(::println)

        application.sharedPrefSet("f", "k", 33f)
        application.sharedPrefGet("f", "k", 3f).apply(::println)
    }

    @Test
    fun f2() {
        f1(setOf("hii"))
        f1(setOf("hii", null, "ss"))
        f1(setOf(null, "aaa"))
        f1(setOf<String?>())
        f1(setOf<String>())
    }

    @Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
    private /*inline */fun </*reified */T> f1(v1: T) {
        // explicitly accept nullable item in the set isa. todo da el 7al el wa7ed isa.
        try {
            setOf<String?>() as T
            setOf<String>() as T

            println("object : TypeToken<T>(){}.type ${object : TypeToken<T>(){}.type}")
        }catch (throwable: Throwable) {
            println("error ${throwable.message}")
        }
    }

    @Test
    fun checkClassFromKClassIsa() {
        println("${Int::class.javaObjectType}") // == class java.lang.Integer
        println("${Int::class.javaPrimitiveType}") // == int == println("${Int::class.java}")
        println("${java.lang.Integer::class.java}")
    }

    @Test
    fun checkIfGenericAcceptsNullIsa() {
        println(reifiedCheckGenericIfAcceptsNullIsa(4))
        val v1 = 66
        val v2: Int? = null
        val v3: Int? = 704
        println(reifiedCheckGenericIfAcceptsNullIsa(v1))
        println(reifiedCheckGenericIfAcceptsNullIsa(v2))
        println(reifiedCheckGenericIfAcceptsNullIsa(v3))
    }

    private inline fun <reified T> reifiedCheckGenericIfAcceptsNullIsa(value: T): T {
        return checkGenericIfAcceptsNullIsa(value, T::class.java)
    }

    @Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
    private fun <T> checkGenericIfAcceptsNullIsa(value: T, jClass: Class<T>): T {
        return try {
            1000 as T
        }catch (throwable: Throwable) {
            0 as T
        }
    }

}
