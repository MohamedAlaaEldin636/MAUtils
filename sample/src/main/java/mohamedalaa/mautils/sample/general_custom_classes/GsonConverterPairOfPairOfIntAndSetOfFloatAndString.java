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

package mohamedalaa.mautils.sample.general_custom_classes;

import java.util.List;
import java.util.Set;

import kotlin.Pair;
import mohamedalaa.mautils.gson.java.GsonConverter;
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefGsonConverter;

@MASharedPrefGsonConverter
public class GsonConverterPairOfPairOfIntAndSetOfFloatAndString
    extends GsonConverter<List<Pair<Pair<Integer, Set<Float>>, String>>> {}
