package mohamedalaa.mautils.mautils.module_mautils_gson

import android.text.Annotation
import com.google.gson.annotations.SerializedName
import mohamedalaa.mautils.mautils.fake_data.AnotherPair
import mohamedalaa.mautils.mautils.fake_data.AnotherPairNoOut
import mohamedalaa.mautils.mautils.fake_data.CustomObject
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam
import mohamedalaa.mautils.mautils_gson.toJson
import mohamedalaa.mautils.mautils_gson.toJsonOrNull
import mohamedalaa.mautils.mautils_gson.fromJson
import mohamedalaa.mautils.mautils_gson.fromJsonOrNull
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import mohamedalaa.mautils.core_kotlin.contains
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
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

    sealed class BidAttachment {

        object With : BidAttachment()
        data class Risk(val value: Int): BidAttachment()

    }

    data class Contain1(
        // Field.getAnnotation if equals SerializedName
        @SerializedName("bidAttachment5000908907")
        val bidAttachment1: BidAttachment = BidAttachment.With,
        val bidAttachment2: BidAttachment = BidAttachment.With,
        val int: Int = 5522
    )

    data class Contain22(
        val risk: BidAttachment.Risk,
        val with: BidAttachment.With?,
        val int: Int
    )

    sealed class TrumpSuit(val value: Int) {
        object NoTrump : TrumpSuit(1)
        data class NotReal(val bool: Boolean) : TrumpSuit(4)
    }

    data class Contain33333(
        val trumpSuit: TrumpSuit,
        val int: Int
    )

    // ---- open

    open class TrumpS(val value: Int) {
        object NT : TrumpS(1)
        data class RRRR(val bool: Boolean) : TrumpS(4)
    }

    data class V3(
        val trumpS: TrumpS,
        val int: Int
    )

    @Test
    fun normalClass() {
        val o1 = V3(TrumpS.NT, 66)
        val j1 = o1.toJson()
        val r1 = j1.fromJson<V3>()

        println("########")
        println(o1)
        println(j1)
        println(r1)
        println()

        //assertEquals()
    }

    // todo needs mocking isa. see Contain22 with nullability and without isa.
    @Test
    fun whatIfSealedClassWithValImplementedByObjectOrClass() {
        val o1 = TrumpSuit.NoTrump
        val j1 = o1.toJson()
        val r1 = j1.fromJson<TrumpSuit.NoTrump>()

        println("########")
        println(o1)
        println(j1)
        println(r1)
        println()

        val o2 = TrumpSuit.NotReal(true)
        val j2 = o2.toJson()
        val r2 = j2.fromJson<TrumpSuit.NotReal>()

        println("########")
        println(o2)
        println(j2)
        println(r2)
        println()

        val o3 = Contain33333(TrumpSuit.NoTrump, 44)
        val j3 = o3.toJson()
        val r3 = j3.fromJson<Contain33333>()

        println("########")
        println(o3)
        println(j3)
        println(r3)
        println()

        /*o3::class.java.fields.forEach {
            //it.se
            it.set(o3, "")
            it.annotations
            it.name
            //
        }
        o3::class.declaredMemberProperties.forEach {
            it.call()
        }

        assertEquals(o1.value, r1.value)*/
    }

    @Test
    fun sasaoijaso() {

        val o1 = BidAttachment.With
        val j1 = o1.toJson()
        val reO1 = j1.fromJson<BidAttachment.With>()

        println()
        println("$o1 == $reO1 -> ${o1 == reO1}")
        println("${Class.forName("mohamedalaa.mautils.mautils.module_mautils_gson.GsonTest\$BidAttachment\$With").kotlin.objectInstance}")
        //assertEquals(o1, reO1)

        println("BIG CHECK ISA")

        val con1 = Contain22(BidAttachment.Risk(5), null, 54654)
        val jav1 = con1.toJson()
        val reCon1 = jav1.fromJson<Contain22>()

        println()
        println(con1)
        println(jav1)
        println(reCon1)
        println()

        println("BIG CHECK ISA")
        println("${reCon1.with is BidAttachment.With}")

        assertEquals(con1, reCon1)

        val contain1 = Contain1(BidAttachment.Risk(3)/*With*/, BidAttachment.Risk(3), 99)

        val j0 = contain1.toJson()
        val r0 = j0.fromJson<Contain1>()

        println("TESTING")
        println("$contain1 - $j0 - $contain1")
        println("TESTING")

        assertEquals(contain1, r0)

        //assertEquals(o1, reO1)

        /*// todo try GsonConverter
        val json = contain1.toJsonJava()
        //val reContain1 = json.fromJsonJava<Contain1>()

        println(contain1)
        println(json)
        //println(reContain1)

        println()
        val json1 = GsonTestGsonHelper.Contain111().toJsonJava(contain1)
        println(json1)
        val reC1 = GsonTestGsonHelper.Contain111().fromJsonJava(json1)
        println(reC1)*/

        //assertEquals(contain1, reO1)

        //assertEquals(contain1, reContain1)

        // todo ---------

        /*val aSub1 = ASub(Sub1.S2, null, 99)
        val json = aSub1.toJsonJava()
        val reASub1 = json.fromJsonJava<ASub>()


        val e1 = Sub1.S1
        val j1 = e1.toJsonJava()
        val reE1 = j1.fromJsonJava<Sub1>()

        println(aSub1)              // ASub(sub1=S2, sub2=null, int=99)
        println(json)               // {"sub1":"S2","sub2":null,"int":99}
        println(reASub1)            // ASub(sub1=S2, sub2=null, int=99)
        println("????")

        println(e1)                 // S1
        println(j1)                 // "S1"
        println(reE1)               // S1
        println("????")

        assertEquals(aSub1, reASub1)
        assertEquals(e1, reE1)*/
    }
    enum class Sub1 {
        S1, S2, S3
    }

    data class ASub(val sub1: Sub1, val sub2: Sub1?, val int: Int)
    class BSub(val sub1: Sub1, val sub2: Sub1?, val int: Int)



    /*fun Any?.isObject2() : Boolean
        = this?.run { this::class.java.constructors.isEmpty() } ?: false*/

    /*class Po1<T> : JsonSerializer<T> {


        override fun serialize(
            src: T,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {

            return if (src.isObject()) kospa else JsonPrimitive(src.toString())
        }
    }*/

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

        // Failure 1
        //val retrievedAny = json.fromJsonJava<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>()

        // Failure 2
        //val retrievedAny = object : GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Int>, Boolean>>>>(){}.fromJsonJava(json)

        // Currently only way to return the exact result correctly isa.
        val retrievedAny = GsonTestGsonHelper.getCustomWithTypeParam(json)

        val anotherRetrieval = GsonTestGsonHelper.GsonCustomWithTypeParam2().fromJson(json)

        val anotherWay = GsonCustomWithTypeParam3().fromJson(json)

        assertEquals(any, retrievedAny) // Must be done by java isa.
        assertEquals(any, anotherRetrieval) // Must be done by java isa.
        assertEquals(any, anotherWay) // Must be done by java isa.
    }

}