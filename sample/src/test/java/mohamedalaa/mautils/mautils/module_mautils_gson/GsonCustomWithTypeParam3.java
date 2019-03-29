package mohamedalaa.mautils.mautils.module_mautils_gson;

import java.util.List;

import kotlin.Pair;
import mohamedalaa.mautils.gson.java.GsonConverter;
import mohamedalaa.mautils.mautils.fake_data.CustomObject;
import mohamedalaa.mautils.mautils.fake_data.CustomWithTypeParam;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/27/2019.
 */
public class GsonCustomWithTypeParam3 extends GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>> {}
