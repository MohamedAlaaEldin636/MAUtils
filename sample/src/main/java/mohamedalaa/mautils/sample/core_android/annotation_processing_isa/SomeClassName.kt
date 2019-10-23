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

package mohamedalaa.mautils.sample.core_android.annotation_processing_isa

import mohamedalaa.mautils.core_android_annotation.*
import mohamedalaa.mautils.core_kotlin.custom_classes.MutablePair

@MASharedPrefField_Configs(
    imports = [
        "mohamedalaa.mautils.core_kotlin.custom_classes.mutablePair",
        "android.content.Context",
        "mohamedalaa.mautils.gson.toJsonOrNull"
    ],
    supportJavaConsumerCode = false,
    addClearFun = true,
    addFileNameFun = true
)
@MASharedPrefField(
    name = "mutablePairStringAndBoolean",
    defaultValue = "\"string string \" mutablePair false",
    type = MANonNestedParameterizedClass(
        MutablePair::class, String::class, Boolean::class
    )
)
@MASharedPrefField(
    name = "pairFloatAndLong",
    defaultValue = "4f to 57L",
    type = MANonNestedParameterizedClass(
        Pair::class, Float::class, Long::class
    )
)
@MASharedPrefField(
    name = "setInts1",
    defaultValue = "setOf(Context.BIND_ABOVE_CLIENT, 88)",
    type = MANonNestedParameterizedClass(
        Set::class, Int::class
    )
)
@MASharedPrefField(
    name = "setStringsNullable1",
    defaultValue = "setOf()",
    acceptNullableSetItem = true,
    type = MANonNestedParameterizedClass(
        Set::class, String::class
    )
)
@MASharedPrefField(
    name = "setBooleanNullable1",
    defaultValue = "setOf(false, null, true)",
    acceptNullableSetItem = true,
    supportSetterNullValue = true,
    type = MANonNestedParameterizedClass(
        Set::class, Boolean::class
    )
)
@MASharedPrefField(
    name = "string1",
    defaultValue = "\"my value is this str ferferferferfer this is looooooooong string isa, fkerokfpeorkfpoerkfpeokropfekoprfekprfkoeprkp pokfpoerkfpoekrfopekrfopekrpfkeprfkepk poekroekfoperfk ferq wffe fwewfew few efw fefwfew fewwfefereqgreqg isa.\"",
    supportSetterAndGetterNullValues = true,
    type = MANonNestedParameterizedClass(
        String::class
    )
)
@MASharedPrefField(
    name = "string2",
    /*no def val isa.*/
    supportGetterNullValue = true,
    type = MANonNestedParameterizedClass(
        String::class
    )
)
@MASharedPrefField(
    name = "string23333",
    defaultValue = "\"sa\"saas\"",
    supportGetterNullValue = true,
    type = MANonNestedParameterizedClass(
        String::class
    )
)
@MASharedPrefField(
    name = "stringExpressionIsa1",
    defaultValue = "\"\".toJsonOrNull()",
    supportGetterNullValue = true,
    type = MANonNestedParameterizedClass(
        String::class
    )
)
@MASharedPrefField(
    name = "stringWithValueAsSet",
    defaultValue = "\"setOf()\"",
    type = MANonNestedParameterizedClass(
        String::class
    )
)
@MASharedPrefField(
    name = "stringWithValueAsSet22222",
    defaultValue = "setOf<Int>().toString()",
    type = MANonNestedParameterizedClass(
        String::class
    )
)
@MASharedPrefField(
    name = "boolean1",
    defaultValue = "false",
    type = MANonNestedParameterizedClass(
        Boolean::class
    ),
    supportJavaConsumerCode = MASharedPrefField.JavaConsumerCode.SUPPORT
)
class SomeClassName
