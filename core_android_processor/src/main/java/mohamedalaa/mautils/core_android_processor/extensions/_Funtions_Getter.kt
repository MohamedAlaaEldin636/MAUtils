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

package mohamedalaa.mautils.core_android_processor.extensions

import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.core_android_annotation.MASharedPrefField
import mohamedalaa.mautils.core_android_processor.*
import mohamedalaa.mautils.core_android_processor.GETTER_PARAM_NAME_DEF_VALUE
import javax.annotation.processing.ProcessingEnvironment

fun ProcessingEnvironment.buildGetComplexFun(
    annotatedClassName: String,
    maSharedPrefField: MASharedPrefField,
    contextClassName: ClassName,
    configSupportsJavaConsumer: Boolean
): FunSpec {
    return FunSpec.builder(
        Constants.getFunctionName(false, annotatedClassName, maSharedPrefField.name, true)
    ).apply {
        // Annotations isa.
        val supportsJavaCode = maSharedPrefField.supportJavaConsumerCode == MASharedPrefField.JavaConsumerCode.SUPPORT
            || (maSharedPrefField.supportJavaConsumerCode == MASharedPrefField.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS
                    && configSupportsJavaConsumer)
        if (supportsJavaCode) {
            addAnnotation(
                AnnotationSpec.builder(JvmName::class)
                    .addMember(
                        "\"${Constants.getFunctionName(false, annotatedClassName, maSharedPrefField.name, false)}\""
                    )
                    .build()
            )
            addAnnotation(JvmOverloads::class)
        }
        addAnnotation(Synchronized::class)

        // receiver & return types isa.
        val valueCanBeNullable = maSharedPrefField.supportSetterAndGetterNullValues || maSharedPrefField.supportGetterNullValue
        val (valueTypeName, supportedTypeParamsAsCSVString) = maSharedPrefField.type.convertToTypeName(
            this@buildGetComplexFun,
            maSharedPrefField.acceptNullableSetItem
        ).run { first.copy(nullable = valueCanBeNullable) to second }

        receiver(contextClassName)
        returns(valueTypeName)

        // Params isa.
        val paramSpecDefValue = ParameterSpec.builder(GETTER_PARAM_NAME_DEF_VALUE, valueTypeName)
            .apply {
                if (maSharedPrefField.defaultValue == "null" && valueCanBeNullable.not()) {
                    error(
                        "You can only pass null to ${MASharedPrefField::name}.${MASharedPrefField::defaultValue.name} " +
                            "If and ONLY IF you set ${MASharedPrefField::name}.${MASharedPrefField::supportGetterNullValue.name} OR " +
                            "${MASharedPrefField::name}.${MASharedPrefField::supportSetterAndGetterNullValues.name} to true isa."
                    )
                }

                val isString = valueTypeName == String::class.asTypeName()
                    || valueTypeName == String::class.asTypeName().copy(nullable = true)
                val isStringAndNotAnExpression = isString && maSharedPrefField.defaultValue.run {
                    startsWith("\"") && endsWith("\"")
                }

                when {
                    isStringAndNotAnExpression -> {
                        val dropLength = "\"".length
                        val newString = maSharedPrefField.defaultValue.drop(dropLength).dropLast(dropLength)

                        defaultValue("%P", newString)
                    }
                    else -> {
                        defaultValue(maSharedPrefField.defaultValue)
                    }
                }
            }
            .build()
        addParameter(paramSpecDefValue)

        // fun code isa
        addStatement(
            "return sharedPrefGetComplex<%T>(" +
                "$PRIVATE_FILE_NAME_PROPERTY_NAME, " +
                "\"${maSharedPrefField.name}\", " +
                "$GETTER_PARAM_NAME_DEF_VALUE, " +
                "Context.MODE_PRIVATE, " +
                "$valueCanBeNullable, " +
                "*arrayOf($supportedTypeParamsAsCSVString)" +
            ")",
            valueTypeName
        )
    }.build()
}
