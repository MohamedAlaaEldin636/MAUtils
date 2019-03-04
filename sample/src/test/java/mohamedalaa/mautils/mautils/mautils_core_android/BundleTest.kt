package mohamedalaa.mautils.mautils.mautils_core_android

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import androidx.core.os.bundleOf
import mohamedalaa.mautils.core_android.*
import mohamedalaa.mautils.mautils.fake_data.CustomObject
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
    fun simulates_onSaveInstanceState_and_retrievingValues() {
        val bundle = Bundle()

        // On save instance state
        bundle.addValues(primitiveIntArray, longArray, stringList, sparseArray, myBundle, listOfBundles)

        // Retrieving values ( Note must be in same order isa. )
        val getterBundle = bundle.getKGetterBundle()
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

        val valuesGetterBundle = bundle.getKGetterBundle()

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

        val getterBundle = bundle.getKGetterBundle()
        assertEquals(nullableElementsFloatList, getterBundle.get())
        assertEquals(allNullableElementsFloatList, getterBundle.get())
    }

    @Test
    fun customObjects() {
        // val bundle = buildBundle(customObject) Error
        // use buildBundleGson() instead check unit tests in module_mautils_gson.GsonWithBundleTest
    }

}