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

package mohamedalaa.mautils.sample.core_android.annotation_processing_isa

// below class just used to ensure no problem in case of two same named files but in different packages will have no problem
// in generated file which in old version could make some bugs isa.
/*
@MASharedPrefFileConfigs(
    imports = [
        "mohamedalaa.mautils.sample.general_custom_classes.Person",
        "mohamedalaa.mautils.core_kotlin.custom_classes.mutablePair",
        "android.content.Context",
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
@MASharedPrefKeyValuePair(
    name = "nestedTypeParamWithGsonConverterConversion",
    defaultValue = "emptyList()",
    type = MAParameterizedKClass(
        nonNullKClasses = [
            List::class, Pair::class, Pair::class, Int::class, Float::class, String::class
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
    convertAnyToString = "toJson()",
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
*//*@MASharedPrefKeyValuePair(
    name = "mutablePairStringAndBoolean",
    defaultValue = "\"string string \" mutablePair false",
    type = MAParameterizedKClass(
        nonNullKClasses = [MutablePair::class, String::class, Boolean::class] // todo try custom with nullable nested type param kda isa
        // todo ops we don't add typeConverter ahaaa ma feh el convertTo hna use it when needed isa.
    )
)*//*
*//*
@MASharedPrefKeyValuePair(
    name = "pairFloatAndLong",
    defaultValue = "4f to 57L",
    type = MAParameterizedKClass(
        Pair::class, Float::class, Long::class
    )
)
@MASharedPrefKeyValuePair(
    name = "setInts1",
    defaultValue = "setOf(Context.BIND_ABOVE_CLIENT, 88)",
    type = MAParameterizedKClass(
        Set::class, Int::class
    )
)
@MASharedPrefKeyValuePair(
    name = "setStringsNullable1",
    defaultValue = "setOf()",
    acceptNullableSetItem = true,
    type = MAParameterizedKClass(
        Set::class, String::class
    )
)
@MASharedPrefKeyValuePair(
    name = "setBooleanNullable1",
    defaultValue = "setOf(false, null, true)",
    acceptNullableSetItem = true,
    supportSetterNullValue = true,
    type = MAParameterizedKClass(
        Set::class, Boolean::class
    )
)
@MASharedPrefKeyValuePair(
    name = "string1",
    defaultValue = "\"my value is this str ferferferferfer this is looooooooong string isa, fkerokfpeorkfpoerkfpeokropfekoprfekprfkoeprkp pokfpoerkfpoekrfopekrfopekrpfkeprfkepk poekroekfoperfk ferq wffe fwewfew few efw fefwfew fewwfefereqgreqg isa.\"",
    supportSetterAndGetterNullValues = true,
    type = MAParameterizedKClass(
        String::class
    )
)
@MASharedPrefKeyValuePair(
    name = "string2",
    *//**//*no def val isa.*//**//*
    supportGetterNullValue = true,
    type = MAParameterizedKClass(
        String::class
    )
)
@MASharedPrefKeyValuePair(
    name = "string23333",
    defaultValue = "\"sa\"saas\"",
    supportGetterNullValue = true,
    type = MAParameterizedKClass(
        String::class
    )
)
@MASharedPrefKeyValuePair(
    name = "stringExpressionIsa1",
    defaultValue = "\"\".toJsonOrNull()",
    supportGetterNullValue = true,
    type = MAParameterizedKClass(
        String::class
    )
)
@MASharedPrefKeyValuePair(
    name = "stringWithValueAsSet",
    defaultValue = "\"setOf()\"",
    type = MAParameterizedKClass(
        String::class
    )
)
@MASharedPrefKeyValuePair(
    name = "stringWithValueAsSet22222",
    defaultValue = "setOf<Int>().toString()",
    type = MAParameterizedKClass(
        String::class
    )
)
@MASharedPrefKeyValuePair(
    name = "boolean1",
    defaultValue = "false",
    type = MAParameterizedKClass(
        Boolean::class
    ),
    supportJavaConsumerCode = MASharedPrefKeyValuePair.JavaConsumerCode.SUPPORT
)*//*
class _SomeClassName*/

//private val person = Person()
