package mohamedalaa.mautils.mautils.module_mautils_gson_java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import mohamedalaa.mautils.mautils.fake_data.CustomObject;
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam;
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

}
