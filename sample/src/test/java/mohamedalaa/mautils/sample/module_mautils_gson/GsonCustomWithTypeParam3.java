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

import java.util.List;

import kotlin.Pair;
import mohamedalaa.mautils.gson.java.GsonConverter;
import mohamedalaa.mautils.sample.fake_data.CustomObject;
import mohamedalaa.mautils.sample.fake_data.CustomWithTypeParam;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/27/2019.
 */
public class GsonCustomWithTypeParam3 extends GsonConverter<CustomWithTypeParam<CustomObject, Pair<List<CustomObject>, CustomWithTypeParam<Pair<Float, Integer>, Boolean>>>> {}
