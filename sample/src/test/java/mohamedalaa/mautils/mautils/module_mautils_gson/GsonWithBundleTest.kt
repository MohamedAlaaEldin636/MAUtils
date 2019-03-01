package mohamedalaa.mautils.mautils.module_mautils_gson

import mohamedalaa.mautils.mautils.fake_data.CustomObject
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam
import mohamedalaa.mautils.mautils.module_mautils_gson.gson_converters_for_kotlin.CustomWithTypeParamOfNestedPairs
import mohamedalaa.mautils.mautils_gson.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class GsonWithBundleTest {

    private val customObject = CustomObject()
    private val listOfCustomObjects = listOf(customObject, customObject.copy(age = 66), customObject.copy(name = "Strange Name"))

    private val customWithTypeParam = CustomWithTypeParam(customObject, 55, anotherName = "AnotherName")

    private val nestedPair1 = (5 to (customWithTypeParam to 9)) to customObject
    private val nestedPair2 = CustomWithTypeParam((5 to (customWithTypeParam to 9)) to customObject, 6)

    @Test
    fun customObjects() {
        val bundle = buildBundleGson(customObject, listOfCustomObjects)

        val getterBundleGson = bundle.getKGetterBundleGson()
        assertEquals(customObject, getterBundleGson.get())
        assertEquals(listOfCustomObjects, getterBundleGson.get())
    }

    @Test
    fun custom_withTypeParam() {
        val bundle = buildBundleGson(customWithTypeParam)

        val getterBundleGson = bundle.getKGetterBundleGson()
        assertEquals(customWithTypeParam, getterBundleGson.get())
    }

    @Test
    fun custom_withNestedTypeParam_nonInvariance_1() {
        val bundle = buildBundleGson(nestedPair1)

        // Note here we didn't use GsonConverter since nestedPair1 is Serializable so it is
        // added to bundle as serializable not as json string see next test for use of GsonConverter isa.
        val getterBundleGson = bundle.getKGetterBundleGson()
        assertEquals(nestedPair1, getterBundleGson.get())
    }

    @Test
    fun custom_withNestedTypeParam_nonInvariance_2() {
        val bundle = buildBundleGson(nestedPair2)

        val getterBundleGson = bundle.getKGetterBundleGson()
        assertEquals(nestedPair2, getterBundleGson.get(CustomWithTypeParamOfNestedPairs()))
    }

}