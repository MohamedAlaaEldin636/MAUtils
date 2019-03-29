package mohamedalaa.mautils.mautils.module_mautils_gson

import mohamedalaa.mautils.mautils.fake_data.AnotherPair
import mohamedalaa.mautils.mautils.fake_data.AnotherPairNoOut
import mohamedalaa.mautils.mautils.fake_data.CustomObject
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import mohamedalaa.mautils.core_kotlin.contains
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.fromJsonOrNull
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.gson.toJsonOrNull
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertFalse

@RunWith(RobolectricTestRunner::class)
class GsonTest {

    private val customObject = CustomObject()

    private val listOfCustomObjects = listOf(customObject, customObject.copy(age = 66), customObject.copy(name = "Strange Name"))

    private val mapOfCustomObjects = HashMap<CustomObject, List<CustomObject>>().apply {
        put(customObject, listOfCustomObjects)
        put(customObject.copy(age = 66), listOfCustomObjects.toMutableList().apply { add(customObject.copy(age = 66)) })
        put(customObject.copy(name = "no thing currently in mind"), emptyList())
    }

    private val nullableElementsList = mutableListOf(null, 4, null, 9, null)

    @Test
    fun customObjectConversion_toAndFromJson() {
        val jsonStringCustomObject = customObject.toJson()

        val fromJsonCustomObject = jsonStringCustomObject.fromJson<CustomObject>()

        assertEquals(fromJsonCustomObject, customObject)
    }

    @Test
    fun pairAndTripleOfCustomAndNonCustomObjects_toAndFromJson() {
        val pair = 5 to customObject
        val triple = Triple("word", listOfCustomObjects, 55)

        val jsonPair = pair.toJson()
        val jsonTriple = triple.toJson()

        assertEquals(pair, jsonPair.fromJson())
        assertEquals(triple, jsonTriple.fromJson())
    }

    @Test
    fun mapOfCustomObjects_toAndFromJson() {
        val jsonMap = mapOfCustomObjects.toJsonOrNull()

        val fromJsonMap = jsonMap.fromJson<HashMap<CustomObject, List<CustomObject>>>()

        assertEquals(mapOfCustomObjects, fromJsonMap)
    }

    @Test
    fun listOfNullableElements_toAndFromJson() {
        val jsonNullableElementsList = nullableElementsList.toJson()
        val fromJson = jsonNullableElementsList.fromJson<List<Int?>>()

        assertEquals(nullableElementsList, fromJson)

        // Although it refer to non null elements in list, it actually is and assertTrue proves it isa.
        val nonNullElementsListFromJson = jsonNullableElementsList.fromJsonOrNull<List<Int>>()
        assertTrue { null in nonNullElementsListFromJson }


        val sureNoNullableTypeParam = listOf(6, 6, 6)
        val sureJson = sureNoNullableTypeParam.toJson()
        val retrievedSure = sureJson.fromJson<List<Int>>()
        assertEquals(sureNoNullableTypeParam, retrievedSure)
        assertFalse { null in retrievedSure }
    }

    @Test
    fun customObjectsWithCustomTypeParameters() {
        val pairCustomObjectAndInt = customObject to 6

        val jsonPairCustomObjectAndInt = pairCustomObjectAndInt.toJson()

        val retrievedPairCustomObjectAndInt: Pair<CustomObject, Int> = jsonPairCustomObjectAndInt.fromJson()

        assertEquals(pairCustomObjectAndInt, retrievedPairCustomObjectAndInt)
    }

    @Test
    fun nestedTypeParams_1() {
        val customWithTypeParam = CustomWithTypeParam<CustomWithTypeParam<CustomObject, CustomObject>, CustomObject>().apply {
            element1 = CustomWithTypeParam(CustomObject(), CustomObject())
            element2 = CustomObject()
        }

        val json = customWithTypeParam.toJson()

        val retrievedCustomWithTypeParam = json.fromJson<CustomWithTypeParam<CustomWithTypeParam<CustomObject, CustomObject>, CustomObject>>()

        assertEquals(customWithTypeParam, retrievedCustomWithTypeParam)

        val trial1 = CustomWithTypeParam<CustomWithTypeParam<CustomObject, CustomWithTypeParam<CustomObject, CustomWithTypeParam<CustomObject, CustomWithTypeParam<CustomObject, CustomObject>>>>, CustomObject>().apply {
            //element1 = CustomWithTypeParam(CustomObject(), CustomWithTypeParam(CustomObject(), CustomObject()))
            element2 = CustomObject()
        }
        val oneJson = trial1.toJson()
        val reTrial1 = oneJson.fromJson<CustomWithTypeParam<CustomWithTypeParam<CustomObject, CustomWithTypeParam<CustomObject, CustomWithTypeParam<CustomObject, CustomWithTypeParam<CustomObject, CustomObject>>>>, CustomObject>>()

        assertEquals(trial1, reTrial1)

        val trial2 = Pair(8, CustomObject())
        val twoJson = trial2.toJson()
        val reTrial2 = twoJson.fromJson<Pair<Int, CustomObject>>()

        assertEquals(trial2, reTrial2)

        val trial3 = Pair(8, CustomObject() to "7")
        val threeJson = trial2.toJson()
        val reTrial3 = threeJson.fromJson<Pair<Int, Pair<CustomObject, String>>>()

        // Error here, they should be equal isa.
        assertNotEquals(trial3, reTrial3)

        val trial4 = AnotherPair(8, AnotherPair(CustomObject(), "7"))
        val json4 = trial4.toJson()
        val reTrial4 = json4.fromJson<AnotherPair<Int, AnotherPair<CustomObject, String>>>()

        // Error here, they should be equal isa.
        assertNotEquals(trial4, reTrial4)

        val trial5 = AnotherPair(8, CustomObject())
        val json5 = trial5.toJson()
        val reTrial5 = json5.fromJson<AnotherPair<Int, CustomObject>>()

        assertEquals(trial5, reTrial5)

        val trial6 = AnotherPairNoOut(8, AnotherPair(CustomObject(), "7"))
        val json6 = trial6.toJson()
        val reTrial6 = json6.fromJson<AnotherPairNoOut<Int, AnotherPair<CustomObject, String>>>()

        // Succeeds although object with out type parameters is used but itself is not in a nested type parameters so it's ok isa.
        assertEquals(trial6, reTrial6)

        // So nested param of out type makes error so java must be used isa.
    }

    @Test
    fun nestedTypeParams_2() {
        // Preparation of val any
        val list = ArrayList<CustomObject>()
        list.add(CustomObject())
        list.add(CustomObject("name", 66, "add", ArrayList()))

        val any =
            CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>()
        any.element1 = CustomObject()
        any.element2 = Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>(
            list,
            CustomWithTypeParam(Pair(3.0f, 6), false, "n1", "an1")
        )

        // any json string
        val json = any.toJson()

        // Currently only way to return the exact result correctly isa.
        val retrievedAny = GsonTestGsonHelper.getCustomWithTypeParam(json)

        val anotherRetrieval = GsonTestGsonHelper.GsonCustomWithTypeParam2().fromJson(json)

        val anotherWay = GsonCustomWithTypeParam3().fromJson(json)

        assertEquals(any, retrievedAny) // Must be done by java isa.
        assertEquals(any, anotherRetrieval) // Must be done by java isa.
        assertEquals(any, anotherWay) // Must be done by java isa.
    }

}