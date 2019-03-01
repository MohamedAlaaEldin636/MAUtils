package mohamedalaa.mautils.mautils.module_mautils_gson.gson_converters_for_kotlin;

import kotlin.Pair;
import mohamedalaa.mautils.mautils.fake_data.CustomObject;
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam;
import mohamedalaa.mautils.mautils_gson.java.GsonConverter;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/1/2019.
 */
public class CustomWithTypeParamOfNestedPairs extends GsonConverter<CustomWithTypeParam<Pair<Pair<Integer, Pair<CustomWithTypeParam<CustomObject, Integer>, Integer>>, CustomObject>, Integer>> {}
