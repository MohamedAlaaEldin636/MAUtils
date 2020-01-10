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

package mohamedalaa.mautils.sample.gson.model.repeat

import mohamedalaa.mautils.gson.java.GsonConverter
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter

/**
 * @param currentIndex used to keep progress isa.
 *
 * @param listOfRepeatPolicyAsJsonString json [String] of [List]<[RepeatPolicy]> && must never be
 * `empty` isa, **Note** no need for [GsonConverter] just use [toJson] && [fromJson] for to/from conversion isa.
 */
data class ChainedRepeatPolicy(
    var currentIndex: Int = 0,

    @MARoomGsonTypeConverter
    var listOfRepeatPolicyAsJsonString: List<RepeatPolicy> = listOf(
        RepeatPolicy.SameConditions(1)
    )
)
