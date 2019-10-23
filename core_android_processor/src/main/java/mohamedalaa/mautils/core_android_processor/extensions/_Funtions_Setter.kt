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
import mohamedalaa.mautils.core_android_processor.SETTER_PARAM_NAME_COMMIT
import mohamedalaa.mautils.core_android_processor.SETTER_PARAM_NAME_VALUE
import javax.annotation.processing.ProcessingEnvironment

/**
 * Should return something like below code isa.
 * ```
 * @JvmName("setBoolean1")
 * @JvmOverloads
 * @Synchronized
 * fun Context.sharedPref_SomeClassName_SetBoolean1(
 *      value: Boolean, commit: Boolean = false
 * ): Boolean? = sharedPrefSetComplex<kotlin.Boolean>(
 *      privateFileName,
 *      "boolean1",
 *      value,
 *      Context.MODE_PRIVATE,
 *      commit,
 *      acc to if nullable setter is enabled isa., // removeIfValueParamIsNullOtherwiseThrowException
 *      *arrayOf()
 *)
 * ```
 */
fun ProcessingEnvironment.buildSetComplexFun(
    annotatedClassName: String,
    maSharedPrefField: MASharedPrefField,
    contextClassName: ClassName,
    configSupportsJavaConsumer: Boolean
): FunSpec {
    return FunSpec.builder(
        Constants.getFunctionName(true, annotatedClassName, maSharedPrefField.name, true)
    ).apply {
        // Annotations isa.
        val supportsJavaCode = maSharedPrefField.supportJavaConsumerCode == MASharedPrefField.JavaConsumerCode.SUPPORT
            || (maSharedPrefField.supportJavaConsumerCode == MASharedPrefField.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS
                    && configSupportsJavaConsumer)
        if (supportsJavaCode) {
            addAnnotation(
                AnnotationSpec.builder(JvmName::class)
                    .addMember(
                        "\"${Constants.getFunctionName(true, annotatedClassName, maSharedPrefField.name, false)}\""
                    )
                    .build()
            )
            addAnnotation(JvmOverloads::class)
        }
        addAnnotation(Synchronized::class)

        // receiver & return isa.
        receiver(contextClassName)
        returns(Boolean::class.asTypeName().copy(nullable = true))

        // params isa.
        val valueCanBeNullable = maSharedPrefField.supportSetterAndGetterNullValues
            || maSharedPrefField.supportSetterNullValue
        val (valueTypeName, supportedTypeParamsAsCSVString) = maSharedPrefField.type.convertToTypeName(
            this@buildSetComplexFun,
            maSharedPrefField.acceptNullableSetItem
        ).run { first.copy(nullable = valueCanBeNullable) to second }
        addParameter(SETTER_PARAM_NAME_VALUE, valueTypeName)
        addParameter(
            ParameterSpec.builder(SETTER_PARAM_NAME_COMMIT, Boolean::class.asTypeName())
                .defaultValue("false")
                .build()
        )

        // fun code isa.
        addStatement(
            "return sharedPrefSetComplex<%T>(" +
                "$PRIVATE_FILE_NAME_PROPERTY_NAME, " +
                "\"${maSharedPrefField.name}\", " +
                "$SETTER_PARAM_NAME_VALUE, " +
                "Context.MODE_PRIVATE, " +
                "$SETTER_PARAM_NAME_COMMIT, " +
                "$valueCanBeNullable, " +
                "*arrayOf($supportedTypeParamsAsCSVString)" +
            ")",
            valueTypeName
        )
    }.build()
}
