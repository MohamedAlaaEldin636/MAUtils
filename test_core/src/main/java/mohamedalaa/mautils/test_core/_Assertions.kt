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

package mohamedalaa.mautils.test_core

import mohamedalaa.mautils.core_kotlin.zipSameSize
import mohamedalaa.mautils.reflection.isObjectInstance
import mohamedalaa.mautils.reflection.isPrimitiveInstance
import kotlin.reflect.KClass
import kotlin.reflect.full.IllegalCallableAccessException
import kotlin.reflect.full.memberExtensionProperties
import kotlin.reflect.full.memberProperties
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

private var toggler: Boolean = true
    get() {
        val value = field
        toggler = value.not()
        return value
    }

/**
 * Compares [KClass.memberProperties] && [KClass.memberExtensionProperties]
 * in [expected] && [actual], with exact value only if primitive else if instance not object
 * recursively using this fun again else if instance is object since object
 * in kotlin is singleton so 2 instances are not equal in toString() but actually they are equal
 * that is the case in gson serialization && deserialization See gson module in MAUtils library
 * for more explanation isa,
 *
 * Also prints logs for each equality process via [TestingLog] isa.
 */
fun <T : Any> assertEquality(
    expected: T,
    actual: T,
    ignoredThrowableList: List<Throwable> = emptyList(),
    actionOnReflectionAccessError: (IllegalCallableAccessException) -> Unit = {
        TestingLog.e("Error Access Exception msg -> ${it.message}")
    }
) {
    val expectedMemberProperties = expected::class.memberProperties.toList()
    val actualMemberProperties = actual::class.memberProperties.toList()

    expectedMemberProperties.zipSameSize(actualMemberProperties).forEach { (first, second) ->
        runCatching {
            TestingLog.w("COMPARING MEMBER PROPERTIES CLASS ${expected::class} OF VALUE -> $expected \nWITH CLASS ${actual::class} OF VALUE ->$actual")

            val itemExpected = first.getter.call(expected)
            val itemActual = second.getter.call(actual)

            val msg = "$itemExpected\n$itemActual"
            if (toggler) TestingLog.v(msg) else TestingLog.i(msg)

            if (itemExpected?.isObjectInstance() == true) {
                assertTrue { itemActual?.isObjectInstance() == true }
            }else if (itemExpected?.isPrimitiveInstance() == true){
                assertEquals(itemExpected, itemActual)
            }else if (itemExpected == null || itemActual == null){
                assertNull(itemExpected)
                assertNull(itemActual)
            }else {
                assertEquality(itemExpected, itemActual)
            }
        }.getOrElse {
            when(it) {
                is IllegalCallableAccessException -> actionOnReflectionAccessError(it)
                else -> {
                    if (it !in ignoredThrowableList) {
                        throw it
                    }
                }
            }
        }

        println()
    }

    val expectedMemberExtensionProperties = expected::class.memberExtensionProperties.toList()
    val actualMemberExtensionProperties = actual::class.memberExtensionProperties.toList()

    expectedMemberExtensionProperties.zipSameSize(actualMemberExtensionProperties).forEach { (first, second) ->
        runCatching {
            TestingLog.w("COMPARING MEMBER EXTENSION PROPERTIES CLASS ${expected::class} OF VALUE -> $expected \nWITH CLASS ${actual::class} OF VALUE ->$actual")

            val itemExpected = first.getter.call(expected)
            val itemActual = second.getter.call(actual)

            val msg = "$itemExpected\n$itemActual"
            if (toggler) TestingLog.v(msg) else TestingLog.i(msg)

            if (itemExpected?.isObjectInstance() == true) {
                assertTrue { itemActual?.isObjectInstance() == true }
            }else if (itemExpected?.isPrimitiveInstance() == true){
                assertEquals(itemExpected, itemActual)
            }else if (itemExpected == null || itemActual == null){
                assertNull(itemExpected)
                assertNull(itemActual)
            }else {
                assertEquality(itemExpected, itemActual)
            }
        }.getOrElse {
            when(it) {
                is IllegalCallableAccessException -> actionOnReflectionAccessError(it)
                else -> {
                    if (it !in ignoredThrowableList) {
                        throw it
                    }
                }
            }
        }

        println()
    }
}