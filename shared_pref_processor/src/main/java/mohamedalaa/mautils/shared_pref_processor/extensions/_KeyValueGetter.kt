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
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair
import mohamedalaa.mautils.shared_pref_processor.*
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_CONVERTED_VALUE_ANY_TO_STRING
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_DEF_VALUE
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_PRIVATE_FILE_NAME
import mohamedalaa.mautils.shared_pref_processor.contextClassName
import mohamedalaa.mautils.shared_pref_processor.extensions.kotlinpoet.getDefaultValueAsKotlinCodeExpression
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.AnnotationMirror

/**
 * Should return something like below code isa.
 * ```
 * @JvmName("getName")
 * @JvmOverloads
 * @Synchronized
 * fun Context.sharedPref_SomeClassName_GetName(
 *      defValue: DefValueType = defaultOrByUserDefValue
 * ): DefValueType = sharedPrefGetComplex<DefValueType>(
 *      privateFileName,
 *
 *      "name",
 *      defValue,
 *
 *      Context.MODE_PRIVATE,
 *
 *      gsonConverter/*or null isa.*/
 * )
 * ```
 *
 * @param annotationMirrorOfGivenMASharedPrefKeyValuePair the [AnnotationMirror] corresponding
 * to given [maSharedPrefKeyValuePair] isa.
 */
/*
@JvmName("getName2")
@JvmOverloads
@Synchronized
internal fun Context.sharedPref_SomeClassName_GetName2(
    defValue: DefValueType = defaultOrByUserDefValue
): DefValueType {
    val convertedValueAnyToString = defValue.run { toJson()/*dev code*/ }

    return sharedPrefGetComplex<String>(
        privateFileName,
        "name2",
        convertedValueAnyToString,

        Context.MODE_PRIVATE,

        null/* or gsonConverter*/
    ).run { fromJson() }
}
 */
fun ProcessingEnvironment.buildGetComplexFun(
    annotatedClassName: String,

    maSharedPrefKeyValuePair: MASharedPrefKeyValuePair,

    fileConfigsSupportsJavaConsumer: Boolean,

    gsonConverterSimpleName: String?,

    annotationMirrorOfGivenMASharedPrefKeyValuePair: AnnotationMirror?,

    valueTypeName: TypeName,
    isManualConversion: Boolean
): FunSpec {
    return FunSpec.builder(
        Constants.getFunctionName(false, annotatedClassName, maSharedPrefKeyValuePair.name, true)
    ).apply {
        // Annotations isa.
        val supportsJavaCode = maSharedPrefKeyValuePair.supportJavaConsumerCode == MASharedPrefKeyValuePair.JavaConsumerCode.SUPPORT
            || (maSharedPrefKeyValuePair.supportJavaConsumerCode == MASharedPrefKeyValuePair.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS
            && fileConfigsSupportsJavaConsumer)

        if (supportsJavaCode) {
            addAnnotation(
                AnnotationSpec.builder(JvmName::class)
                    .addMember(
                        "\"${Constants.getFunctionName(false, annotatedClassName, maSharedPrefKeyValuePair.name, false)}\""
                    )
                    .build()
            )
            addAnnotation(JvmOverloads::class)
        }
        addAnnotation(Synchronized::class)

        // receiver & return types isa.
        val valueCanBeNullable = maSharedPrefKeyValuePair.classIsNullable()
            || maSharedPrefKeyValuePair.supportSetterAndGetterNullValues
            || maSharedPrefKeyValuePair.supportGetterNullValue

        receiver(contextClassName)
        returns(valueTypeName)

        // Params isa.
        val paramSpecDefValue = ParameterSpec.builder(VAR_NAME_DEF_VALUE, valueTypeName)
            .apply {
                val defValueIsDeclaredByUser = annotationMirrorOfGivenMASharedPrefKeyValuePair?.run {
                    this.elementValues.any {
                        it.key.simpleName.toStringOrEmpty() == MASharedPrefKeyValuePair::defaultValue.name
                    }
                } ?: false

                val defValue = if (maSharedPrefKeyValuePair.defaultValue == "null" && valueCanBeNullable.not()) {
                    if (defValueIsDeclaredByUser) {
                        error(
                            "You can only pass null to ${MASharedPrefKeyValuePair::name}.${MASharedPrefKeyValuePair::defaultValue.name} " +
                                "If and ONLY IF you set ${MASharedPrefKeyValuePair::name}.${MASharedPrefKeyValuePair::supportGetterNullValue.name} to true OR " +
                                "${MASharedPrefKeyValuePair::name}.${MASharedPrefKeyValuePair::supportSetterAndGetterNullValues.name} to true isa."
                        )
                    }else {
                        val defValue = valueTypeName.getDefaultValueAsKotlinCodeExpression()

                        if (defValue == "null") {
                            error(
                                "You didn't specify a ${MASharedPrefKeyValuePair::name}.${MASharedPrefKeyValuePair::defaultValue.name} " +
                                    "for a custom type so null will be used, but null can ONLY be passed " +
                                    "If and ONLY IF you set ${MASharedPrefKeyValuePair::name}.${MASharedPrefKeyValuePair::supportGetterNullValue.name} to true OR " +
                                    "${MASharedPrefKeyValuePair::name}.${MASharedPrefKeyValuePair::supportSetterAndGetterNullValues.name} to true isa."
                            )
                        }else {
                            defValue
                        }
                    }
                }else {
                    maSharedPrefKeyValuePair.defaultValue
                }

                val isString = valueTypeName == String::class.asTypeName()
                    || valueTypeName == String::class.java.asTypeName()
                    || valueTypeName == String::class.java.asTypeName().copy(nullable = true)
                    || valueTypeName == String::class.asTypeName().copy(nullable = true)
                val isStringAndNotAnExpression = isString && defValue.run {
                    startsWith("\"") && endsWith("\"")
                }

                when {
                    isStringAndNotAnExpression -> {
                        val dropLength = "\"".length
                        val newString = defValue.drop(dropLength).dropLast(dropLength)

                        defaultValue("%P", newString)
                    }
                    else -> {
                        defaultValue(defValue)
                    }
                }
            }.build()
        addParameter(paramSpecDefValue)

        // fun code isa
        if (isManualConversion) {
            val stringAsTypeName = String::class.asTypeName().copy(nullable = valueCanBeNullable)
            addStatement(
                "val $VAR_NAME_CONVERTED_VALUE_ANY_TO_STRING = $VAR_NAME_DEF_VALUE.run { ${maSharedPrefKeyValuePair.convertAnyToString} }"
            )
            addStatement(
                "return sharedPrefGetComplex<%T>(" +
                    "$VAR_NAME_PRIVATE_FILE_NAME, " +

                    "\"${maSharedPrefKeyValuePair.name}\", " +
                    "$VAR_NAME_CONVERTED_VALUE_ANY_TO_STRING, " +

                    "Context.MODE_PRIVATE, " +

                    "null" +
                    ").run { ${maSharedPrefKeyValuePair.convertStringToAny} }",
                stringAsTypeName
            )
        }else {
            val gsonConverter = gsonConverterSimpleName?.run { "$this()" }
            addStatement(
                "return sharedPrefGetComplex<%T>(" +
                    "$VAR_NAME_PRIVATE_FILE_NAME, " +

                    "\"${maSharedPrefKeyValuePair.name}\", " +
                    "$VAR_NAME_DEF_VALUE, " +

                    "Context.MODE_PRIVATE, " +

                    "$gsonConverter" +
                    ")",
                valueTypeName
            )
        }
    }.build()
}
