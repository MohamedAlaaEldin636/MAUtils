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

package mohamedalaa.mautils.sample.gson;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Pair;
import kotlin.Triple;
import mohamedalaa.mautils.gson.java.GsonConverter;
import mohamedalaa.mautils.gson.java.GsonUtils;
import mohamedalaa.mautils.sample.fake_data.CustomObject;
import mohamedalaa.mautils.sample.fake_data.CustomWithTypeParam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@Config(manifest = Config.NONE, sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class JavaTestClass {

    private List<String> stringList = new ArrayList<>();

    private TestSuperClasses testSuperClasses = new TestSuperClasses();

    @Before
    public void init() {
        for (int index = 0; index < 10; index++) {
            stringList.add(String.valueOf(index));
        }
    }

    @Test
    public void nestedTypeParams_whereNoNestingForVarianceOnesIsa() {
        assertTrue(stringList.size() > 0);

        // Non-nested type params, yet still use gsonConverter cuz not all the time it works without it isa.
        Triple<Float, String, Integer> triple1 = new Triple<>(
            5.356f, "sasasas", 4232
        );
        GsonConverter<Triple<Float, String, Integer>> triple1GsonConverter = new GsonConverter<Triple<Float, String, Integer>>() {};
        String jTriple1 = triple1GsonConverter.toJson(triple1);
        Triple<Float, String, Integer> rTriple1 = triple1GsonConverter.fromJson(jTriple1);
        assertEquals(triple1, rTriple1);

        // same isa.
        CustomWithTypeParam<CustomWithTypeParam<Pair<Float, Integer>, Boolean>, Pair<Integer, CustomObject>> v1 = new CustomWithTypeParam<>(
            new CustomWithTypeParam<>(
                new Pair<>(3.5659f, 43443),
                true,
                "yyyyyyyyyyyyyyyyyyyyyy",
                "xzxzxzxzxz"
            ),
            new Pair<>(33, new CustomObject("nameee", 43, "adasadas", stringList)),
            "sasaas",
            "saaaaaaaaaaaaaaaaaaaaaa"
        );
        GsonConverter<CustomWithTypeParam<CustomWithTypeParam<Pair<Float, Integer>, Boolean>, Pair<Integer, CustomObject>>> v1GsonConverter
            = new GsonConverter<CustomWithTypeParam<CustomWithTypeParam<Pair<Float, Integer>, Boolean>, Pair<Integer, CustomObject>>>() {};
        String j1 = v1GsonConverter.toJson(v1);
        CustomWithTypeParam<CustomWithTypeParam<Pair<Float, Integer>, Boolean>, Pair<Integer, CustomObject>> r1 =
            v1GsonConverter.fromJson(j1);
        assertEquals(v1, r1);
    }

    @Test
    public void a1() {
        // In case you don't want to call it anonymously ( case called in several other places ) isa.
        CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>> value1 =
            new CustomWithTypeParam<>();
        String jsonString = new HelperJavaClass().toJson(value1);
        CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>
            rValue1 = new HelperJavaClass().fromJson(jsonString);
        assertEquals(value1, rValue1);

        List<String> stringList = new ArrayList<>(); stringList.add("Dewd"); stringList.add("Dewdwde");
        CustomObject customObject1 = new CustomObject("namesasa", 442242, "Sasas", stringList);
        value1.setElement1(customObject1);
        List<CustomObject> first = new ArrayList<>();
        for (int index = 0; index < 3; index++) {
            first.add(customObject1);
        }
        CustomWithTypeParam<Pair<Float, Integer>, Boolean> second = new CustomWithTypeParam<>(
            new Pair<>(3f, 33),
            true,
            "sasa",
            "sasasas"
        );
        Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>> pair = new Pair<>(
            first,
            second
        );
        value1.setElement2(pair);

        // Has to use GsonConverter since there is/are type param/s isa.
        String jsonString3 = GsonUtils.toJson(value1);
        @SuppressWarnings("unchecked")
        CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>
            rValue3 = GsonUtils.fromJson(jsonString3, CustomWithTypeParam.class);
        assertNotEquals(value1, rValue3);

        CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>
            rValue3GsonConverter = new HelperJavaClass().fromJson(jsonString3);
        assertEquals(value1, rValue3GsonConverter);
    }

    @Test
    public void compareGsonApproaches() {
        Gson gson = new Gson();

        // using gson isa. -> gson
        int int1 = 3;
        String j1 = gson.toJson(int1);
        int r1 = gson.fromJson(j1, int.class);
        assertEquals(int1, r1);

        // using this library isa. ( no benefit so far isa. ) -> json
        String j2 = GsonUtils.toJson(int1);
        int r2 = GsonUtils.fromJson(j2, int.class);
        assertEquals(int1, r2);

        // gson ( not same instance in case of kotlin object keyword isa. )
        ObjectClassA objectClassA = ObjectClassA.INSTANCE;
        String j3 = gson.toJson(objectClassA);
        ObjectClassA r3 = gson.fromJson(j3, ObjectClassA.class);
        assertNotEquals(objectClassA, r3); // Not Equals

        // json -> same instance isa. -> benefits +1
        String j4 = GsonUtils.toJson(objectClassA);
        ObjectClassA r4 = GsonUtils.fromJson(j4, ObjectClassA.class);
        assertEquals(objectClassA, r4);

        // gson
        StrangeEnum strangeEnum = StrangeEnum.STRANGE_4;
        String j5 = gson.toJson(strangeEnum);
        StrangeEnum r5 = gson.fromJson(j5, StrangeEnum.class);
        assertEquals(strangeEnum, r5);

        // json -> same isa.
        String j6 = GsonUtils.toJson(strangeEnum);
        StrangeEnum r6 = GsonUtils.fromJson(j6, StrangeEnum.class);
        assertEquals(strangeEnum, r6);

        // gson
        Map<Integer, String> map1 = new HashMap<>(); map1.put(4, "");
        TypeToken<Map<Integer, String>> token = new TypeToken<Map<Integer, String>>() {};
        String j7 = gson.toJson(map1, token.getType());
        Map<Integer, String> r7 = gson.fromJson(j7, token.getType());
        assertEquals(map1, r7);

        // json -> same since GsonConverter always needed in case of type params existence isa.
        GsonConverter<Map<Integer, String>> gsonConverter1 = new GsonConverter<Map<Integer, String>>() {};
        String j8 = gsonConverter1.toJson(map1);
        Map<Integer, String> r8 = gsonConverter1.fromJson(j8);
        assertEquals(map1, r8);

        // gson -> can't convert sealed/abstract/interface types isa.
        List<Integer> map2IntegerList = new ArrayList<>(); map2IntegerList.add(2);
        Map<List<Integer>, ReminderOrAction> map2 = new HashMap<>(); map2.put(map2IntegerList, testSuperClasses.getReminderOrAction1());
        /*val j9 = gson.toJson(map2)
        val token1 = object : TypeToken<Map<List<Int>, ReminderOrAction>>(){}
        val r9: Map<List<Int>, ReminderOrAction> = gson.fromJson(j9, token1.type)
        assertEquals(map2, r9)*/

        // json needs gson converter isa. -> but can do sealed class property isa. benefit +2
        GsonConverter<Map<List<Integer>, ReminderOrAction>> gsonConverter2 = new GsonConverter<Map<List<Integer>, ReminderOrAction>>() {};
        String j10 = gsonConverter2.toJson(map2);
        Map<List<Integer>, ReminderOrAction> r10 = gsonConverter2.fromJson(j10);
        assertEquals(map2, r10);

        // gson
        List<Integer> map3IntegerList = new ArrayList<>(); map3IntegerList.add(4); map3IntegerList.add(5); map3IntegerList.add(33);
        Map<String, List<Integer>> map3 = new HashMap<>(); map3.put("", map3IntegerList);
        TypeToken<Map<String, List<Integer>>> token3 = new TypeToken<Map<String, List<Integer>>>() {};
        String j11 = gson.toJson(map3, token3.getType());
        Map<String, List<Integer>> r11 = gson.fromJson(j11, token3.getType());
        assertEquals(map3, r11);

        // json -> same isa.
        GsonConverter<Map<String, List<Integer>>> gsonConverter3 = new GsonConverter<Map<String, List<Integer>>>() {};
        String j12 = gsonConverter3.toJson(map3);
        Map<String, List<Integer>> r12 = gsonConverter3.fromJson(j12);
        assertEquals(map3, r12);

        // gson
        List<Pair<Integer, Integer>> map4ValueList = new ArrayList<>();
        map4ValueList.add(new Pair<>(4, 3));
        map4ValueList.add(new Pair<>(5, 3));
        map4ValueList.add(new Pair<>(33, 3));
        Map<String, List<Pair<Integer, Integer>>> map4 = new HashMap<>(); map4.put("", map4ValueList);

        TypeToken<Map<String, List<Pair<Integer, Integer>>>> token4 = new TypeToken<Map<String, List<Pair<Integer, Integer>>>>() {};
        String j13 = gson.toJson(map4, token4.getType());
        Map<String, List<Pair<Integer, Integer>>> r13 = gson.fromJson(j13, token4.getType());
        assertEquals(map4, r13);
        // trying non-anonymous isa. ( doesn't work in case of a kotlin consumer code isa. )
        TestTypeToken1 token4NonA = new TestTypeToken1();
        String j13NonA = gson.toJson(map4, token4NonA.getType());
        Map<String, List<Pair<Integer, Integer>>> r13NonA = gson.fromJson(j13NonA, token4NonA.getType());
        assertEquals(map4, r13NonA);

        // json -> trying anonymous isa.
        GsonConverter<Map<String, List<Pair<Integer, Integer>>>> gsonConverter4 = new GsonConverter<Map<String, List<Pair<Integer, Integer>>>>() {};
        String j14 = gsonConverter4.toJson(map4);
        Map<String, List<Pair<Integer, Integer>>> r14 = gsonConverter4.fromJson(j14);
        assertEquals(map4, r14);

        // gson
        SeveralTypeParams<
            SeveralTypeParams<
                SeveralTypeParams<
                    ReminderOrAction,
                    Float,
                    String
                >,
                Double,
                ConditionReminderOrAction
            >,
            Integer,
            Boolean
        > severalTypeParams1 = new SeveralTypeParams<>(
            new SeveralTypeParams<>(
                new SeveralTypeParams<>(
                    testSuperClasses.getReminderOrAction1(),
                    5f,
                    ""
                ),
                3.3,
                testSuperClasses.getAbstractWindowDate1()
            ),
            4,
            true
        );
        SeveralTypeParams<
            SeveralTypeParams<
                SeveralTypeParams<
                    ReminderOrAction,
                    Float,
                    String
                >,
                Double,
                ConditionReminderOrAction
            >,
            Integer,
            Boolean
        > severalTypeParams2 = new SeveralTypeParams<>(
            new SeveralTypeParams<>(
                new SeveralTypeParams<>(
                    testSuperClasses.getReminderOrAction1(),
                    5f,
                    ""
                ),
                3.3,
                testSuperClasses.getAbstractWindowDate1()
            ),
            4,
            true
        );
        assertEquals(severalTypeParams1, severalTypeParams2);

        TestTypeToken2 testTypeToken2 = new TestTypeToken2();
        String js1 = gson.toJson(severalTypeParams1, testTypeToken2.getType());
        SeveralTypeParams<SeveralTypeParams<SeveralTypeParams<ReminderOrAction, Float, String>, Double, ConditionReminderOrAction>, Integer, Boolean>
            rs1;
        try {
            rs1 = gson.fromJson(js1, testTypeToken2.getType());
        }catch (Throwable throwable) {
            rs1 = null;
        }
        assertNotEquals(severalTypeParams1, rs1);

        // json -> YEAH look at above line this can't be done in type token isa.
        GsonConverter<SeveralTypeParams<SeveralTypeParams<SeveralTypeParams<ReminderOrAction, Float, String>, Double, ConditionReminderOrAction>, Integer, Boolean>>
            gsonConverter5 = new GsonConverter<SeveralTypeParams<SeveralTypeParams<SeveralTypeParams<ReminderOrAction, Float, String>, Double, ConditionReminderOrAction>, Integer, Boolean>>() {};
        String jSeveralTypeParams1 = gsonConverter5.toJson(severalTypeParams1);
        String jSeveralTypeParams2 = gsonConverter5.toJson(severalTypeParams2);
        assertEquals(jSeveralTypeParams1, jSeveralTypeParams2);

        SeveralTypeParams<SeveralTypeParams<SeveralTypeParams<ReminderOrAction, Float, String>, Double, ConditionReminderOrAction>, Integer, Boolean>
            rSeveralTypeParams1 = gsonConverter5.fromJson(jSeveralTypeParams1);
        SeveralTypeParams<SeveralTypeParams<SeveralTypeParams<ReminderOrAction, Float, String>, Double, ConditionReminderOrAction>, Integer, Boolean>
            rSeveralTypeParams2 = gsonConverter5.fromJson(jSeveralTypeParams2);
        assertEquals(severalTypeParams1, rSeveralTypeParams1);
        assertEquals(severalTypeParams1, rSeveralTypeParams2);
    }

}
