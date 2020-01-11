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

package mohamedalaa.mautils.sample.gson.model.styled_string

import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter
import mohamedalaa.mautils.sample.gson.model.styled_string.MAIndexedSpan

data class MAStyledString(

    var string: String = "",

    @MARoomGsonTypeConverter
    var maIndexedSpans: List<MAIndexedSpan> = emptyList()

)