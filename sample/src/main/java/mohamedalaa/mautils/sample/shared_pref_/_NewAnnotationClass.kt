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

import mohamedalaa.mautils.sample.general_custom_classes.Person
import mohamedalaa.mautils.shared_pref_annotation.MAKClass
import mohamedalaa.mautils.shared_pref_annotation.MAParameterizedKClass
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair

// todo -> update wiki isa.
@MASharedPrefFileConfigs(
    supportJavaConsumerCode = true,
    addClearFun = true,
    addFileNameFun = true,
    addSharedPrefChangeListener = true,
    addSharedPrefInstanceFun = true,
    supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation = true
)
// Check manipulation with nullability isa.
@MASharedPrefPair(
    name = "nullableBoolean",
    defaultValue = "null", // since default for boolean is false.
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Boolean::class, true) // has to be nullable check default value isa.
        ]
    )
)
@MASharedPrefPair(
    name = "nonNullBoolean",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Boolean::class
        ]
    )
)
// check Custom types
@MASharedPrefPair(
    name = "nestedTypeParam",
    // defaultValue must be provided, since default value for custom types is null and type specified requires non-null isa.
    defaultValue = "emptyList()",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            List::class, Pair::class, Pair::class, Int::class, Set::class, Float::class, String::class
        ]
    ),
    supportJavaConsumerCode = MASharedPrefPair.JavaConsumerCode.DO_NOT_SUPPORT
)
@MASharedPrefPair(
    name = "person",
    type = MAParameterizedKClass(
        maKClass = [
            // No need to import Person in MASharedPrefFileConfig, Since it is declared in type isa.
            // Also don't worry about conversion it 'll be done for you auto isa.
            MAKClass(Person::class, true)
        ]
    )
)
@MASharedPrefPair(
    name = "personWithDefaultValue",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Person::class)
        ]
    ),
    defaultValue = "Person()"
)
@MASharedPrefPair(
    name = "pairStringAndBoolean",
    defaultValue = "\"string string \" to false",
    type = MAParameterizedKClass(
        nonNullKClasses = [Pair::class, String::class, Boolean::class]
    )
)
@MASharedPrefPair(
    name = "int",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Int::class
        ]
    )
)
@MASharedPrefPair(
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
@MASharedPrefPair(
    name = "setOfInts",
    // normally you should add Context in imports in MASharedPrefFileConfigs
    // but since generated functions already does that no need in this case isa.
    defaultValue = "setOf(Context.BIND_ABOVE_CLIENT, 88)",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            Set::class, Int::class
        ]
    )
)
@MASharedPrefPair(
    name = "setOfNullableStrings",
    defaultValue = "setOf()",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Set::class),
            MAKClass(String::class, true)
        ]
    )
)
@MASharedPrefPair(
    name = "nullableSetOfNullableStrings",
    defaultValue = "setOf()",
    type = MAParameterizedKClass(
        maKClass = [
            MAKClass(Set::class, true),
            MAKClass(String::class, true)
        ]
    ),
    supportJavaConsumerCode = MASharedPrefPair.JavaConsumerCode.SUPPORT
)
class _NewAnnotationClass
