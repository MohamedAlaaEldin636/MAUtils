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

@file:Suppress("ClassName")

package mohamedalaa.mautils.sample.shared_pref_

import mohamedalaa.mautils.core_kotlin.custom_classes.MutablePair
import mohamedalaa.mautils.sample.general_custom_classes.Person
import mohamedalaa.mautils.shared_pref_annotation.MAKClass
import mohamedalaa.mautils.shared_pref_annotation.MAParameterizedKClass
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair

@MASharedPrefFileConfigs(
    imports = [
        "mohamedalaa.mautils.sample.general_custom_classes.Person",

        "mohamedalaa.mautils.core_kotlin.custom_classes.mutablePair",
        "mohamedalaa.mautils.core_kotlin.custom_classes.MutablePair",

        "mohamedalaa.mautils.gson.toJsonOrNull",
        "mohamedalaa.mautils.gson.toJson",
        "mohamedalaa.mautils.gson.fromJson",
        "mohamedalaa.mautils.gson.fromJsonOrNull"
    ],
    supportJavaConsumerCode = true,
    addClearFun = true,
    addFileNameFun = true,
    addSharedPrefChangeListener = true,
    addSharedPrefInstanceFun = true,
    supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation = true
)

// Check manipulation with nullability isa.
@MASharedPrefKeyValuePair(
    name = "booleanWithNullableGetterOnly",
    defaultValue = "null", // since default for boolean is false
    // & I wanna check that it is not null anymore.
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    ),
    supportGetterNullValue = true
    // no need to make type has nullable it will be done auto for you.
)
@MASharedPrefKeyValuePair(
    name = "booleanWithNullableSetterOnly",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    ),
    supportSetterNullValue = true
)
@MASharedPrefKeyValuePair(
    name = "booleanWithNullableSetterAndGetter",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    ),
    supportSetterAndGetterNullValues = true
)


@MASharedPrefKeyValuePair(
    name = "nestedTypeParamWithGsonConverterConversion",
    defaultValue = "emptyList()",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            List::class, Pair::class, Pair::class, Int::class, Set::class, Float::class, String::class
        ]
    ),
    supportJavaConsumerCode = MASharedPrefKeyValuePair.JavaConsumerCode.DO_NOT_SUPPORT
)
@MASharedPrefKeyValuePair(
    name = "personAutoConversion",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Person::class, true)
        ]
    )
)
@MASharedPrefKeyValuePair(
    name = "personManualConversion",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Person::class, true)
        ]
    ),
    convertAnyToString = "toJsonOrNull()",
    convertStringToAny = "fromJsonOrNull()"
)
@MASharedPrefKeyValuePair(
    name = "personWithDefaultValue",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Person::class)
        ]
    ),
    defaultValue = "Person()"
)
@MASharedPrefKeyValuePair(
    name = "mutablePairStringAndBoolean",
    defaultValue = "\"string string \" mutablePair false",
    type = MAParameterizedKClass(
        nonNullKClasses = [MutablePair::class, String::class, Boolean::class]
    )
)
/*@MASharedPrefKeyValuePair(
    name = "none1",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Int::class
        ]
    ),
    convertAnyToString = "conversion Expression without reverse one should make build error isa."
)*/
@MASharedPrefKeyValuePair(
    name = "pairFloatNullableAndLong",
    defaultValue = "4f to 57L",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Pair::class),
            MAKClass(Float::class, true),
            MAKClass(Long::class)
        ]
    )
)
@MASharedPrefKeyValuePair(
    name = "needAutDefValueForNonNullBoolean",
    type = MAParameterizedKClass(
        nonNullKClasses = [Boolean::class]
    )
)
@MASharedPrefKeyValuePair(
    name = "needAutDefValueForNullableBoolean",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Boolean::class, true)
        ]
    )
)
@MASharedPrefKeyValuePair(
    name = "setOfInts1",
    defaultValue = "setOf(Context.BIND_ABOVE_CLIENT, 88)",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Set::class, Int::class
        ]
    )
)
@MASharedPrefKeyValuePair(
    name = "setOfNullableStrings1",
    defaultValue = "setOf()",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Set::class),
            MAKClass(String::class, true)
        ]
    )
)
@MASharedPrefKeyValuePair(
    name = "keepScreenOn",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    )
)
@MASharedPrefKeyValuePair(
    name = "mySetOfStrings",
    defaultValue = "setOf()",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Set::class),
            MAKClass(String::class, true)
        ]
    ),
    supportJavaConsumerCode = MASharedPrefKeyValuePair.JavaConsumerCode.SUPPORT
)
class _SomeClassName
