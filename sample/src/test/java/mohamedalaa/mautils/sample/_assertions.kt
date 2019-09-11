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

package mohamedalaa.mautils.sample

import mohamedalaa.mautils.core_kotlin.extensions.zipSameSize
import mohamedalaa.mautils.test_core.TestingLog
import kotlin.reflect.KClass
import kotlin.reflect.full.memberExtensionProperties
import kotlin.reflect.full.memberProperties
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

private val KClass<*>.isObject: Boolean
    get() = this.objectInstance != null

private val KClass<*>.isInterface: Boolean
    get() = java.isInterface

private val KClass<*>.isPrimitive: Boolean
    get() = javaPrimitiveType != null

private inline fun <reified T : Any> T.isObjectInstance(): Boolean
    = this::class.isObject

private inline fun <reified T : Any> T.isInterfaceInstance(): Boolean
    = this::class.isInterface

private inline fun <reified T : Any> T.isAbstractInstance(): Boolean
    = this::class.isAbstract

private inline fun <reified T : Any> T.isSealedInstance(): Boolean
    = this::class.isSealed

private inline fun <reified T : Any> T.isPrimitiveInstance(): Boolean
    = this::class.isPrimitive

private var toggler: Boolean = true
    get() {
        val value = field
        toggler = value.not()
        return value
    }

fun <T : Any> assertEquality(expected: T, actual: T) {
    val expectedMemberProperties = expected::class.memberProperties.toList()
    val actualMemberProperties = actual::class.memberProperties.toList()

    expectedMemberProperties.zipSameSize(actualMemberProperties).forEach { (first, second) ->
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
    }

    println()

    val expectedMemberExtensionProperties = expected::class.memberExtensionProperties.toList()
    val actualMemberExtensionProperties = actual::class.memberExtensionProperties.toList()

    expectedMemberExtensionProperties.zipSameSize(actualMemberExtensionProperties).forEach { (first, second) ->
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
    }

    println()
}