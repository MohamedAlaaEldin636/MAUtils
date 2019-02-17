package mohamedalaa.mautils.mautils.module_mautils_gson

import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder
import mohamedalaa.mautils.mautils.fake_data.CustomObject
import mohamedalaa.mautils.mautils_gson.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@RunWith(RobolectricTestRunner::class)
class GsonTest {

    /*@Test
    fun customObjectAndListConversion_toAndFrom() {
        val customObject = CustomObject()
        val orgList = customObject.friendsNames

        val toJsonString = customObject.toJsonString()
        val jsonOrgList = orgList.toJsonString()

        val restoredCustomObject: CustomObject = toJsonString.fromJsonString()
        // Note safely method, the other approach is in the 3rd assertion isa.
        val restoredList: List<String?> = jsonOrgList.fromJsonStringAsListSafely()

        assertEquals(restoredCustomObject, customObject)
        assertEquals(orgList, restoredList)

        // List of custom objects
        val list = listOf(customObject, customObject.copy(age = 66), customObject.copy(name = "M"))
        println(list)
        val jsonStringForList = list.toJsonString()
        println("\n$jsonStringForList")
        val recoveredList = jsonStringForList.fromJsonStringAsList<CustomObject>()
        assertEquals(list, recoveredList)
    }

    @Test
    fun listWithNullableItems_toAndFrom() {
        val list: List<String?> = listOf("one", null, "three")
        val string = list.toJsonString()
        // Should use safely if not sure that all elements are not null, or explicitly use ?
        val restoredList: List<String> = string.fromJsonStringAsListOrNull() ?: emptyList()
        // Using safely
        val safeRestoredList: List<String?> = string.fromJsonStringAsListSafely()
        // Using ?
        val withNullableRestoredList: List<String?> = string.fromJsonStringAsList()

        assertNotEquals(restoredList, list)
        assertEquals(safeRestoredList, list)
        assertEquals(withNullableRestoredList, list)

        val list2 = listOf(null, 4, 5, 99, null, "any thing")
        val jsonString2 = list2.toJsonString()

        assertEquals(list2, jsonString2.fromJsonStringAsList())
    }

    // Type parameter
    @Test
    fun otherObjects() {
        val pair = 7 to 9
        println(pair)
        val string = pair.toJsonString()
        println(string)
        println(string.fromJsonString<Pair<Int, Int>>() as Pair<Int, Int>)

        // assertEquals(pair, string.fromJsonString<Pair<Int, Int>>()) // not equal it returns them as float

        // -----
        val customObject = CustomObject()
        val list = listOf(customObject, customObject.copy(age = 66), customObject.copy(name = "M"))

        // Type collectionType = new TypeToken<Collection<Integer>>(){}.getType();
        val collectionType = object : TypeToken<A1<CustomObject>>() {}.type

        val orgA1 = A1(5, list*//*listOf(5, 8, 9)*//**//*todo 1*//*, 9 to 99)
        println(orgA1)
        val jsonA1 = orgA1.trialOneToJsonStringOrNull()
        println(jsonA1)
        println("BEFORE")
        println(orgA1.trialOneToJsonStringOrNull())
        println("AFTER adjustments isa.")
        var restoredA1: A1<CustomObject*//*Int*//**//*todo 2*//*>? = jsonA1.fromJsonString()
        restoredA1 = jsonA1.trialOneFromJson()
        println(restoredA1)

        assertEquals(orgA1, restoredA1)
        println("---------")
        val j1 = AnotherGsonUtils.convertObjectToJsonString(orgA1)

        val gson = GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
        //gson.fromJson<E>(jsonString, objectClass)

        val r1: A1<CustomObject> = gson.fromJson(j1, collectionType)*//*AnotherGsonUtils.convertJsonStringToObject(j1, collectionType)*//*
        println(j1)
        println(".....")
        println(r1)
        println(".....")
    }

    data class A1<E>(val int: Int, val list: List<E>, val pairInts: Pair<Int, Int>)*/

    @Test
    fun listImplicitlyWithTypeTokens() {
        val customObject = CustomObject()
        val list = listOf(customObject, customObject.copy(age = 66), customObject.copy(name = "M"))
        println(list)
        println()

        val string = list.trialOneToJsonStringOrNull()
        println(string)
        println()

        val reList = string.trialOneFromJson<List<CustomObject>>()
        println(reList)
        println()

        assertEquals(list, reList)

        // Pair case
        val intsPair = 6 to 99
        val jsonString = intsPair.trialOneToJsonStringOrNull()
        val reIntsPair = jsonString.trialOneFromJson<Pair<Int, Int>>()
        println("$intsPair\n$jsonString\n$reIntsPair")
        assertEquals(intsPair, reIntsPair)

        // Map case
        val map = HashMap<CustomObject, List<Int>>().apply {
            put(customObject, listOf(3, 4, 5))
            put(customObject.copy(age = 66), listOf(2, 3, 4, 5))
            put(customObject.copy(name = "MMM"), emptyList())
        }
        val jsonMap = map.trialOneToJsonStringOrNull()
        val reMap = jsonMap.trialOneFromJson<HashMap<CustomObject, List<Int>>>()
        println("$map\n$jsonMap\n$reMap")
        assertEquals(map, reMap)

        // list of nullable objects kda isa
        println()
        println()
        val nullableElementsList: MutableList<CustomObject?> = list.map { it }.toMutableList()
        nullableElementsList.add(0, null)
        nullableElementsList.add(null)
        val jsonNullableElementsList = nullableElementsList.trialOneToJsonStringOrNull()
        val reNullableElementsList = jsonNullableElementsList.trialOneFromJson<List<CustomObject?>>()
        println("$nullableElementsList\n$jsonNullableElementsList\n$reNullableElementsList")
        assertEquals(nullableElementsList, reNullableElementsList)
    }

}