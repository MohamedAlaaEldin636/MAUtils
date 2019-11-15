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

package mohamedalaa.mautils.sample.module_mautils_gson;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;
import mohamedalaa.mautils.gson.java.GsonConverter;
import mohamedalaa.mautils.gson.java.GsonUtils;
import mohamedalaa.mautils.sample.fake_data.CustomObject;
import mohamedalaa.mautils.sample.fake_data.CustomWithTypeParam;
import mohamedalaa.mautils.sample.fake_data.JavaCustomObj;
import mohamedalaa.mautils.sample.fake_data.WithVarianceJavaObj;

import static org.junit.Assert.*;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/20/2019.
 */
@Config(manifest = Config.NONE, sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class GsonJavaTest {

    @Test
    public void regularObjects() {
        int num = 4;
        String jsonString = GsonUtils.toJson(num, int.class);
        int retrievedNum = GsonUtils.fromJson(jsonString, int.class);

        Integer wrappedIntNum = 5;
        String wrappedJsonString = GsonUtils.toJson(wrappedIntNum, Integer.class);
        Integer retrievedWrapped = GsonUtils.fromJson(wrappedJsonString, Integer.class);

        assertEquals(num, retrievedNum);
        assertEquals(wrappedIntNum, retrievedWrapped);
    }

    @Test
    public void withParameterType_toAndFrom() {
        CustomWithTypeParam<CustomObject, Integer> customWithTypeParam = new CustomWithTypeParam<>();

        String jsonString = new GsonConverter<CustomWithTypeParam<CustomObject, Integer>>(){}.toJsonOrNull(customWithTypeParam);

        CustomWithTypeParam<CustomObject, Integer> fromJsonObject
                = new GsonConverter<CustomWithTypeParam<CustomObject, Integer>>(){}.fromJsonOrNull(jsonString);

        assertEquals(customWithTypeParam, fromJsonObject);

        Gson customGson = new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .enableComplexMapKeySerialization()
                .create();

        ArrayList<String> f1 = new ArrayList<>();
        f1.add("sub f1 1");

        ArrayList<String> f2 = new ArrayList<>();
        f1.add("sub f2 1");
        f1.add("sub f2 2");

        List<CustomObject> list = new ArrayList<>();
        list.add(new CustomObject());
        list.add(new CustomObject("n1", 1, "a1", f1));
        list.add(new CustomObject("n2", 2, "a2", f2));

        String jsonList = new GsonConverter<List<CustomObject>>(customGson){}.toJson(list);

        List<CustomObject> fromJsonList = new GsonConverter<List<CustomObject>>(customGson){}.fromJson(jsonList);

        assertEquals(list, fromJsonList);
    }

    @Test
    public void nestedTypeParams() {
        List<CustomObject> list = new ArrayList<>();
        list.add(new CustomObject());
        list.add(new CustomObject("name", 66, "add", new ArrayList<>()));

        CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>> any
                = new CustomWithTypeParam<>();
        any.setElement1(new CustomObject());
        any.setElement2(new Pair<>(list, new CustomWithTypeParam<>(new Pair<>(3.0f, 6), false, "n1", "an1")));

        // todo does this work now isa. ?!
        String json = GsonUtils.toJson(any, CustomWithTypeParam.class);

        CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>> retrievedAny
                = new GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>>(){}.fromJson(json);

        assertEquals(any, retrievedAny);
    }

    @Test
    public void nestedTypeParam2() {
        JavaCustomObj prepare1 = new JavaCustomObj("name", 55);
        WithVarianceJavaObj<JavaCustomObj, Integer> withVarianceJavaObj = new WithVarianceJavaObj<>(prepare1, 99);
        WithVarianceJavaObj<JavaCustomObj, WithVarianceJavaObj<JavaCustomObj, Integer>> withVarianceJavaObjParent
                = new WithVarianceJavaObj<>(prepare1, withVarianceJavaObj);

        String json = GsonUtils.toJson(withVarianceJavaObjParent, WithVarianceJavaObj.class);

        WithVarianceJavaObj<JavaCustomObj, WithVarianceJavaObj<JavaCustomObj, Integer>> re
                = new GsonConverter<WithVarianceJavaObj<JavaCustomObj, WithVarianceJavaObj<JavaCustomObj, Integer>>>(){}.fromJson(json);

        assertEquals(withVarianceJavaObjParent.integer.integer, re.integer.integer);
        assertEquals(withVarianceJavaObjParent.javaCustomObj.name, re.javaCustomObj.name);
        assertEquals(withVarianceJavaObjParent.javaCustomObj.age, re.javaCustomObj.age);
        assertEquals(withVarianceJavaObjParent.integer.javaCustomObj.name, re.integer.javaCustomObj.name);
        assertEquals(withVarianceJavaObjParent.integer.javaCustomObj.age, re.integer.javaCustomObj.age);

        assertEquals(withVarianceJavaObjParent.toString(), re.toString());
    }

}
