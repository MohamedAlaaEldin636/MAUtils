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

import mohamedalaa.mautils.gson.buildBundleGson
import mohamedalaa.mautils.gson.getterBundleGson
import mohamedalaa.mautils.sample.fake_data.CustomObject
import mohamedalaa.mautils.sample.fake_data.CustomWithTypeParam
import mohamedalaa.mautils.sample.module_mautils_gson.gson_converters_for_kotlin.CustomWithTypeParamOfNestedPairs
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class GsonWithBundleTest {

    private val customObject = CustomObject()
    private val listOfCustomObjects = listOf(customObject, customObject.copy(age = 66), customObject.copy(name = "Strange Name"))

    private val customWithTypeParam = CustomWithTypeParam(
        customObject,
        55,
        anotherName = "AnotherName"
    )

    private val nestedPair1 = (5 to (customWithTypeParam to 9)) to customObject
    private val nestedPair2 = CustomWithTypeParam(
        (5 to (customWithTypeParam to 9)) to customObject,
        6
    )

    @Test
    fun customObjects() {
        val bundle = buildBundleGson(customObject, listOfCustomObjects)

        val getterBundleGson = bundle.getterBundleGson()
        assertEquals(customObject, getterBundleGson.get())
        assertEquals(listOfCustomObjects, getterBundleGson.get())
    }

    @Test
    fun custom_withTypeParam() {
        val bundle = buildBundleGson(customWithTypeParam)

        val getterBundleGson = bundle.getterBundleGson()
        assertEquals(customWithTypeParam, getterBundleGson.get())
    }

    @Test
    fun custom_withNestedTypeParam_nonInvariance_1() {
        val bundle = buildBundleGson(nestedPair1)

        // Note here we didn't use GsonConverter since nestedPair1 is Serializable so it is
        // added to bundle as serializable not as json string see next test for use of GsonConverter isa.
        val getterBundleGson = bundle.getterBundleGson()
        assertEquals(nestedPair1, getterBundleGson.get())
    }

    @Test
    fun custom_withNestedTypeParam_nonInvariance_2() {
        val bundle = buildBundleGson(nestedPair2)

        val getterBundleGson = bundle.getterBundleGson()
        assertEquals(nestedPair2, getterBundleGson.get(CustomWithTypeParamOfNestedPairs()))
    }

}