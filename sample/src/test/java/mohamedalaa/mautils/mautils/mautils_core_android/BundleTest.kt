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

package mohamedalaa.mautils.mautils.mautils_core_android

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import androidx.core.os.bundleOf
import mohamedalaa.mautils.core_android.*
import mohamedalaa.mautils.mautils.fake_data.CustomObject
import mohamedalaa.mautils.mautils.material_design.BoatActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class BundleTest {

    private val primitiveIntArray = intArrayOf(4, 99)
    private val longArray = arrayOf(5L, 900L)
    private val stringList = listOf("S1", "s2")
    private val sparseArray = SparseArray<Parcelable>().apply {
        put(1, bundleOf("Abc" to "abc"))
    }
    private val myBundle = Bundle()
    private val listOfBundles = listOf(myBundle, myBundle.apply { addValues("Hello") })
    private val nullableElementsFloatList = listOf(null, 3.5f, 4f)
    private val allNullableElementsFloatList: List<Float?> = listOf(null, null)

    private val customObject = CustomObject()
    private val listOfCustomObjects = listOf(customObject, customObject.copy(age = 66), customObject.copy(name = "Strange Name"))

    @Test
    fun check_for_bundleOf_support() {
        val sparseArrayOfBundle = SparseArray<Bundle>().apply {
            put(1, Bundle())
            put(2, buildBundle(2))
            put(3, buildBundle("44"))
        }

        //bundleOf("key_1" to sparseArrayOfBundle)

        val bundleWithKeys = buildBundleWithKeys("key_1" to sparseArrayOfBundle)
    }

    @Test
    fun changedCore() {
        val bundle = buildBundle(4, "s", true)

        val getter = bundle.getterBundle()
        //bundle.javaGetGetterBundle()

        println("${getter.get<Int>()}, ${getter.get<String>()}, ${getter.get<Boolean>()}")
    }

    private fun dweidjwo(context: Context) {
        context.startActivity<BoatActivity>()
    }

    @Test
    fun simulates_onSaveInstanceState_and_retrievingValues() {
        val bundle = Bundle()

        // On save instance state
        bundle.addValues(primitiveIntArray, longArray, stringList, sparseArray, myBundle, listOfBundles)

        // Retrieving values ( Note must be in same order isa. )
        val getterBundle = bundle.getterBundle()
        assertEquals(primitiveIntArray, getterBundle.get())
        assertEquals(longArray, getterBundle.get())
        val reStringList = getterBundle.getOrNull<List<String>>()
        assertEquals(stringList, reStringList)
        assertEquals(sparseArray, getterBundle.get())
        assertEquals(myBundle, getterBundle.get())
        assertEquals(listOfBundles, getterBundle.get())
    }

    @Test
    fun putAndGet_1() {
        // Values in the bundle, Note androidx do not support sparse array -> bundleOf("key" to sparseArray) Error
        val int  = 99
        val string = "h"
        val sparseArray = SparseArray<Parcelable>().apply {
            put(1, buildBundle("abc"))
        }
        val primitiveFloatArray = floatArrayOf(5f, 2.3f)
        val bundle = buildBundle(int, string, sparseArray, primitiveFloatArray)

        val valuesGetterBundle = bundle.getterBundle()

        // Checks ( Note must be in same order isa. )
        val retrievedInt = valuesGetterBundle.get<Int>()
        assertEquals(int, retrievedInt)
        assertEquals(string, valuesGetterBundle.get())
        assertEquals(sparseArray, valuesGetterBundle.get())
        assertEquals(primitiveFloatArray, valuesGetterBundle.get())
    }

    @Test
    fun nullableElementsList() {
        val bundle = buildBundle(nullableElementsFloatList, allNullableElementsFloatList)

        val getterBundle = bundle.getterBundle()
        assertEquals(nullableElementsFloatList, getterBundle.get())
        assertEquals(allNullableElementsFloatList, getterBundle.get())
    }

    @Test
    fun customObjects() {
        // val bundle = buildBundle(customObject) Error
        // use buildBundleGson() instead check unit tests in module_mautils_gson.GsonWithBundleTest
    }

}