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

package mohamedalaa.mautils.sample.model_for_testing

import com.maproductions.mohamedalaa.library_common_annotations.MAKClass
import com.maproductions.mohamedalaa.library_common_annotations.MAParameterizedKClass
import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface
import mohamedalaa.mautils.room_gson_annotation.MAAutoTypeConverters
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverterType

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/4/2019.
 *
 */
@MASealedAbstractOrInterface
@MARoomGsonTypeConverter
sealed class CanYouSeeMe(

    @MARoomGsonTypeConverter
    val intStringMap: IntMap<String>
)

typealias IntMap<T> = Map<Int, T>

enum class SomeEnumClass {
    A, B, C, D, E, F
}

@MARoomGsonTypeConverterType(
    MAParameterizedKClass(
        nonNullKClasses = [SomeEnumClass::class]
    )
)
class _AllTypesOne

@MARoomGsonTypeConverterType(
    MAParameterizedKClass(
        nonNullKClasses = [List::class, SomeEnumClass::class]
    )
)
@MARoomGsonTypeConverterType(
    MAParameterizedKClass(
        nonNullKClasses = [List::class, List::class, List::class, SomeEnumClass::class]
    )
)
@MARoomGsonTypeConverterType(
    MAParameterizedKClass(
        maKClass = [
            MAKClass(Pair::class),
            MAKClass(Int::class, true),
            MAKClass(Float::class)
        ]
    )
)
class _AllTypesTwo // todo use them isa.

private fun ab(a: MAAutoTypeConverters_AllTypesOne, b: MAAutoTypeConverters_AllTypesTwo, c: MAAutoTypeConverters) {
}

