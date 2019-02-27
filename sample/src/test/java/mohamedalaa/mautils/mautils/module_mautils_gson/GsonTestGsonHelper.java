package mohamedalaa.mautils.mautils.module_mautils_gson;

import java.util.List;

import kotlin.Pair;
import mohamedalaa.mautils.mautils.fake_data.CustomObject;
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam;
import mohamedalaa.mautils.mautils_gson_java.GsonConverter;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/26/2019.
 */
public class GsonTestGsonHelper {

    public static CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>> getCustomWithTypeParam(
            String jsonString) {
        return new GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>>(){}.fromJson(jsonString);
    }

}
