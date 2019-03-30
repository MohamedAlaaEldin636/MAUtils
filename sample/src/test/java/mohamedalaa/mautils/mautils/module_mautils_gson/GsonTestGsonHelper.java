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

}
