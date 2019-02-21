package mohamedalaa.mautils.mautils.module_mautils_gson

import mohamedalaa.mautils.mautils.fake_data.CustomObject
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam
import mohamedalaa.mautils.mautils.fake_data.Token1
import mohamedalaa.mautils.mautils.module_mautils_gson_java.GsonTest
import mohamedalaa.mautils.mautils_gson.*
import mohamedalaa.mautils.mautils_gson_java.GsonConverter
import mohamedalaa.mautils.mautils_gson_java.toJsonOrNull
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class GsonTest {

    private val customObject = CustomObject()

    private val listOfCustomObjects = listOf(customObject, customObject.copy(age = 66), customObject.copy(name = "Strange Name"))

    private val mapOfCustomObjects = HashMap<CustomObject, List<CustomObject>>().apply {
        put(customObject, listOfCustomObjects)
        put(customObject.copy(age = 66), listOfCustomObjects.toMutableList().apply { add(customObject.copy(age = 66)) })
        put(customObject.copy(name = "no thing currently in mind"), emptyList())
    }

    private val nullableElementsList = mutableListOf(null, 4, null, 9, null)

    private val customWithTypeParamBothNull: CustomWithTypeParam<CustomObject, Int?> = CustomWithTypeParam()
    private val customWithTypeParamOneNull: CustomWithTypeParam<CustomObject, Int?>
        = CustomWithTypeParam(customObject.copy(), null, "carla", "buffy")

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
        // Note specifying nullability, since deserialize might returns nullable type parameters
        val fromJson = jsonNullableElementsList.fromJson<List<Int?>>()

        assertEquals(nullableElementsList, fromJson)

        // In case a non-type parameters needed then custom check is needed
        val nonNullElementsListFromJson = jsonNullableElementsList.fromJsonOrNullCheck<List<Int>, List<Int?>> {
            filterNotNull().size == size
        }

        assertEquals(null, nonNullElementsListFromJson)

        // Since nullable list elements is commonly needed check, a shortcut exists note below
        val anotherNonNullElementsListFromJson = jsonNullableElementsList.fromJsonOrNullCheck<List<Int>>(JsonCheck.NON_NULL_ELEMENTS_LIST)

        assertEquals(null, anotherNonNullElementsListFromJson)

        // However if list was originally with non-null elements
        val ints = listOf(4, 99)
        val jsonString = ints.toJson()
        val nonNullList = jsonString.fromJsonCheck<List<Int>>(JsonCheck.NON_NULL_ELEMENTS_LIST)

        assertEquals(ints, nonNullList)
    }

    @Test
    fun customWithCustomTypeParameters_toAndFromJson() {
        val json1 = customWithTypeParamBothNull.toJson()
        val json2 = customWithTypeParamOneNull.toJson()

        val acceptingNullFrom1 = json1.fromJsonOrNull<CustomWithTypeParam<CustomObject?, Int?>>()

        val likeOriginalOne = json2.fromJsonQuick<CustomWithTypeParam<CustomObject, Int?>> {
            element1 != null
        }

        val bothNotNullFrom2 = json2.fromJsonOrNullCheck<CustomWithTypeParam<CustomObject, Int>, CustomWithTypeParam<CustomObject, Int>> {
            element1 != null && element2 != null
        }

        val anotherLike = json2.fromJsonOrNullCheck<CustomWithTypeParam<CustomObject, Int?>, CustomWithTypeParam<CustomObject?, Int?>> {
            element1 != null
        }

        assertNotEquals(null, acceptingNullFrom1)

        assertEquals(customWithTypeParamOneNull, likeOriginalOne)
        assertEquals(customWithTypeParamOneNull, anotherLike)

        assertEquals(null, bothNotNullFrom2)
    }

    // todo any nested type param is ok but if nested was primitive then an error will happen, so make own wrapper isa.
    @Test
    fun nestedTypeParams() {
        val any = CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>()
        any.element1 = CustomObject()
        any.element2 = listOfCustomObjects to CustomWithTypeParam(element1 = 3.0f to 6, element2 = false)

        val json = any.toJson()

        val retrievedAny = json.fromJson<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>()

        println(any)
        println()
        println(retrievedAny)

        //assertNotEquals(any, retrievedAny) // error as Int is converted as Float

        //assertEquals(any, object : GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>(){}.fromJson(json))

        val retrievedAny2 = object :
            GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>() {
        }.fromJson(json)

        println(retrievedAny2)

        val int: Int? = retrievedAny2.element2?.second?.element1?.second
        println(int)

        // todo

    }

    @Test
    fun nestedTypeParams2() {
        val any = CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>()
        any.element1 = CustomObject()
        any.element2 = listOfCustomObjects to CustomWithTypeParam(element1 = 3.0f to 6, element2 = false)

        val json = any.toJson()

        val retrievedAny = json.fromJson<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>()

        println(any)
        println()
        println(retrievedAny)

        //assertNotEquals(any, retrievedAny) // error as Int is converted as Float
        //assertEquals(any, object : GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>(){}.fromJson(json))

        val retrievedAny2 = object :
            GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>() {
        }.fromJson(json)

        println(retrievedAny2)

        class HH: GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<CustomObject, CustomObject>, Boolean>>>>()
        val a = HH()
        val retrievedAny4 = a.fromJson(json)

        println(retrievedAny4)

        /*val int: Double? = retrievedAny2.element2?.second?.element1?.second
        println(int)*/

        // todo assert
        //assertEquals(any, retrievedAny)
        //assertEquals(any, retrievedAny2)

        val goal = object :
            Token1.Goal<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>() {}
        val j = goal.toJsonOrNull(any)
        val a2 = goal.fromJsonOrNull(j)

        val a33 = GsonTest.ghjk(any)

        // todo ezan lazm from java isa.

        println()
        println()
        println(a33.element2?.second?.element1?.second)

        assertEquals(any, a33)
    }


    data class IntWrapper(val int: Float)



}