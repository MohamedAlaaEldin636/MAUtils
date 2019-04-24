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

package mohamedalaa.mautils.sample.module_mautils_gson

import mohamedalaa.mautils.core_kotlin.zipSameSize
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.test_core.TestingLog
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberExtensionProperties
import kotlin.reflect.full.memberProperties
import kotlin.test.*

@RunWith(RobolectricTestRunner::class)
class GsonTestV2 {

    internal class C1 {
        companion object
    }

    internal object O1

    @Test
    fun cccclas() {
        val c1 = C1()
        val o1 = O1

        println(c1::class.objectInstance)
        println(o1::class.objectInstance)
        println()
        println(c1::class.companionObject)
        println(o1::class.companionObject)

        println()
        println()

        println(4.isPrimitiveInstance())
        println("s".isPrimitiveInstance())

        println()
        println()

        println(toggler)
        println(toggler)
        println(toggler)
        println(toggler)
    }

    @Test
    fun toAndFrom_withSealedClass() {
        val o1 = ParentUsingBoth(
            UsingBoth(
                NoArgsSealedClass.NoSingleton,
                WithArgsSealedClass.WithDataClass(
                    false
                ),
                35f
            ),
            WithArgsSealedClass.WithDataClass(false),
            984,
            ImplSomeInterface(12, "s as string"),
            ImplAbstractClass(602, true)
        )
        val j1 = o1.toJson()
        val r1 = j1.fromJson<ParentUsingBoth>()

        println("########")
        println(o1)
        println()
        println(j1)
        println()
        println(r1)
        println()

        assertEquality(o1, r1)
    }

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

    private fun <T : Any> assertEquality(expected: T, actual: T) {
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

    /*private fun getSuperclassTypeAndClassParameter(
        paramType: Type
    ): Pair<Type, Class<*>> {
        return try {
            val parameterizedType = paramType as ParameterizedType
            val type = `$Gson$Types`.canonicalize(parameterizedType.actualTypeArguments[0])

            type to (type as Class<*>)
        }catch (e: Exception) {
            Any::class.java to Any::class.java
        }
    }

    private inline fun <reified T : Type> T.geSTC(): Pair<Type, Class<*>> {
        return try {
            val parameterizedType = this as ParameterizedType
            val type = `$Gson$Types`.canonicalize(parameterizedType.actualTypeArguments[0])

            type to (type as Class<*>)
        }catch (e: Exception) {
            println("error is -> ${e.message}")
            Any::class.java to Any::class.java
        }
    }

    @Test
    fun f2() {
        val nestedListOfInts = listOf(listOf(4, 3), listOf(8))

        nestedListOfInts::class.java.geSTC()
        println()
        println()
        getSuperclassTypeAndClassParameter(nestedListOfInts::class.java.apply(::println)).apply(::println)
    }*/

}