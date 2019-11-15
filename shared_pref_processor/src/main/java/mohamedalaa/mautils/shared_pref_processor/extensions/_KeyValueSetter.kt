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

package mohamedalaa.mautils.shared_pref_processor.extensions

import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair
import mohamedalaa.mautils.shared_pref_processor.*
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_COMMIT
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_PRIVATE_FILE_NAME
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_VALUE
import javax.annotation.processing.ProcessingEnvironment

/**
 * Should return something like below code isa.
 * ```
 * @JvmName("setName")
 * @JvmOverloads
 * @Synchronized
 * fun Context.sharedPref_SomeClassName_SetName(
 *      value: ValueType,
 *      commit: Boolean = false
 * ): Boolean? = sharedPrefSet<ValueType>(
 *      privateFileName,
 *
 *      "name",
 *      value,
 *      valueCanBeNullable /*removeKeyIfValueIsNull*/,
 *
 *      Context.MODE_PRIVATE,
 *      commit,
 *
 *      gsonConverter/*can be null isa.*/
 * )
 * ```
 */
/*
@JvmName("setName")
@JvmOverloads
@Synchronized
fun Context.sharedPref_SomeClassName_SetName(
    value: CustomValueType,
    commit: Boolean = false
): Boolean? {
    val convertedValueAnyToString = value.run { toString()/*dev code*/ }

    return sharedPrefSet<String/*? might be nullable isa.*/>(
        privateFileName,

        "name",
        convertedValueAnyToString,
        valueCanBeNullable /*removeKeyIfValueIsNull*/,

        Context.MODE_PRIVATE,
        commit,

        null/* or gsonConverter*/
    )
}
 */
@Suppress("unused")
fun ProcessingEnvironment.buildSetComplexFun(
    annotatedClassName: String,

    maSharedPrefKeyValuePair: MASharedPrefKeyValuePair,

    fileConfigsSupportsJavaConsumer: Boolean,

    gsonConverterSimpleName: String?,

    valueTypeName: TypeName,
    isManualConversion: Boolean
): FunSpec {
    return FunSpec.builder(
        Constants.getFunctionName(true, annotatedClassName, maSharedPrefKeyValuePair.name, true)
    ).apply {
        // Annotations isa.
        val supportsJavaCode = maSharedPrefKeyValuePair.supportJavaConsumerCode == MASharedPrefKeyValuePair.JavaConsumerCode.SUPPORT
            || (maSharedPrefKeyValuePair.supportJavaConsumerCode == MASharedPrefKeyValuePair.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS
            && fileConfigsSupportsJavaConsumer)

        if (supportsJavaCode) {
            addAnnotation(
                AnnotationSpec.builder(JvmName::class)
                    .addMember(
                        "\"${Constants.getFunctionName(true, annotatedClassName, maSharedPrefKeyValuePair.name, false)}\""
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
        val valueCanBeNullable = maSharedPrefKeyValuePair.classIsNullable()
            || maSharedPrefKeyValuePair.supportSetterAndGetterNullValues
            || maSharedPrefKeyValuePair.supportSetterNullValue

        addParameter(VAR_NAME_VALUE, valueTypeName)
        addParameter(
            ParameterSpec.builder(VAR_NAME_COMMIT, Boolean::class.asTypeName())
                .defaultValue("false")
                .build()
        )

        // fun code isa.
        if (isManualConversion) {
            val stringAsTypeName = String::class.asTypeName().copy(nullable = valueCanBeNullable)
            addStatement(
                "val $VAR_NAME_CONVERTED_VALUE_ANY_TO_STRING = ${VAR_NAME_VALUE}.run { ${maSharedPrefKeyValuePair.convertAnyToString} }"
            )
            addStatement(
                "return sharedPrefSet<%T>(" +
                    "$VAR_NAME_PRIVATE_FILE_NAME, " +

                    "\"${maSharedPrefKeyValuePair.name}\", " +
                    "$VAR_NAME_CONVERTED_VALUE_ANY_TO_STRING, " +
                    "$valueCanBeNullable, " +

                    "Context.MODE_PRIVATE, " +
                    "$VAR_NAME_COMMIT, " +

                    "null" +
                    ")",
                stringAsTypeName
            )
        }else {
            val gsonConverter = gsonConverterSimpleName?.run { "$this()" }
            addStatement(
                "return sharedPrefSet<%T>(" +
                    "$VAR_NAME_PRIVATE_FILE_NAME, " +

                    "\"${maSharedPrefKeyValuePair.name}\", " +
                    "$VAR_NAME_VALUE, " +
                    "$valueCanBeNullable, " +

                    "Context.MODE_PRIVATE, " +
                    "$VAR_NAME_COMMIT, " +

                    "$gsonConverter" +
                    ")",
                valueTypeName
            )
        }
    }.build()
}
