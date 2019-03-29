package mohamedalaa.mautils.mautils.module_mautils_gson;

import java.util.List;

import kotlin.Pair;
import mohamedalaa.mautils.gson.java.GsonConverter;
import mohamedalaa.mautils.mautils.fake_data.CustomObject;
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam;

/**
 * Used for Gson conversion by kotlin developers in case of non-invariant nested type parameters isa.
 *
 * Note
 *
 * Prefer extending class since like in nested class here, as it will make less code isa,
 * also you can have it in a separate class like GsonCustomWithTypeParam3 instead of nested class isa.
 */
public class GsonTestGsonHelper {

    public static CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>> getCustomWithTypeParam(
            String jsonString) {
        return new GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>>(){}.fromJson(jsonString);
    }

    public static class GsonCustomWithTypeParam2 extends GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>> {}


    public static class Contain111 extends GsonConverter<GsonTest.Contain1> {}

}
