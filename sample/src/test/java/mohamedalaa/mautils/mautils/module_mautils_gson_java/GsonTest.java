package mohamedalaa.mautils.mautils.module_mautils_gson_java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;
import mohamedalaa.mautils.mautils.fake_data.CustomObject;
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam;
import mohamedalaa.mautils.mautils.fake_data.JavaCustomObj;
import mohamedalaa.mautils.mautils.fake_data.WithVarienceJavaObj;
import mohamedalaa.mautils.mautils_gson_java.GsonConverter;
import mohamedalaa.mautils.mautils_gson_java.GsonUtils;

import static org.junit.Assert.*;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/20/2019.
 */

public class GsonTest {

    @Test
    public void regularObjects() {
        int num = 4;
        String jsonString = GsonUtils.toJson(num);
        int retrievedNum = GsonUtils.fromJson(jsonString, int.class);

        Integer wrappedIntNum = 5;
        String wrappedJsonString = GsonUtils.toJson(wrappedIntNum);
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

        String json = GsonUtils.toJson(any);

        CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>> retrievedAny
                = new GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>>(){}.fromJson(json);

        assertEquals(any, retrievedAny);
    }

    @Test
    public void nestedTypeParam2() {
        JavaCustomObj prepare1 = new JavaCustomObj("name", 55);
        WithVarienceJavaObj<JavaCustomObj, Integer> withVarienceJavaObj = new WithVarienceJavaObj<>(prepare1, 99);
        WithVarienceJavaObj<JavaCustomObj, WithVarienceJavaObj<JavaCustomObj, Integer>> withVarienceJavaObjParent
                = new WithVarienceJavaObj<>(prepare1, withVarienceJavaObj);

        String json = GsonUtils.toJson(withVarienceJavaObjParent);

        WithVarienceJavaObj<JavaCustomObj, WithVarienceJavaObj<JavaCustomObj, Integer>> re
                = new GsonConverter<WithVarienceJavaObj<JavaCustomObj, WithVarienceJavaObj<JavaCustomObj, Integer>>>(){}.fromJson(json);

        assertEquals(withVarienceJavaObjParent.integer.integer, re.integer.integer);
        assertEquals(withVarienceJavaObjParent.javaCustomObj.name, re.javaCustomObj.name);
        assertEquals(withVarienceJavaObjParent.javaCustomObj.age, re.javaCustomObj.age);
        assertEquals(withVarienceJavaObjParent.integer.javaCustomObj.name, re.integer.javaCustomObj.name);
        assertEquals(withVarienceJavaObjParent.integer.javaCustomObj.age, re.integer.javaCustomObj.age);

        assertEquals(withVarienceJavaObjParent.toString(), re.toString());
    }

}
