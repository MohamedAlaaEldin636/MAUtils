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
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair
import mohamedalaa.mautils.shared_pref_processor.*
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_DEF_VALUE
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_PRIVATE_FILE_NAME
import mohamedalaa.mautils.shared_pref_processor.contextClassName
import mohamedalaa.mautils.shared_pref_processor.extensions.kotlinpoet.getDefaultValueAsKotlinCodeExpression
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.AnnotationMirror

/*
@JvmName("getName2")
@JvmOverloads
@Synchronized
internal fun Context.sharedPref_SomeClassName_GetName2(
    defValue: DefValueType/?/ = defaultOrByUserDefValue
): DefValueType {
    return sharedPrefGet<DefValueType/?/>(
        privateFileName,
        "mySetOfStrings",
        defValue,
        Context.MODE_PRIVATE
    )
}
 */
/**
 * @param annotationMirrorOfGivenMASharedPrefPair the [AnnotationMirror] corresponding
 * to given [maSharedPrefPair] isa.
 */
fun ProcessingEnvironment.buildGetterFun(
    annotatedClassName: String,

    maSharedPrefPair: MASharedPrefPair,

    fileConfigsSupportsJavaConsumer: Boolean,

    annotationMirrorOfGivenMASharedPrefPair: AnnotationMirror?,

    valueTypeName: TypeName
): FunSpec {
    return FunSpec.builder(
        Constants.getFunctionName(false, annotatedClassName, maSharedPrefPair.name, true)
    ).apply {
        // Annotations isa.
        val supportsJavaCode = maSharedPrefPair.supportJavaConsumerCode == MASharedPrefPair.JavaConsumerCode.SUPPORT
            || (maSharedPrefPair.supportJavaConsumerCode == MASharedPrefPair.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS
            && fileConfigsSupportsJavaConsumer)

        if (supportsJavaCode) {
            addAnnotation(
                AnnotationSpec.builder(JvmName::class)
                    .addMember(
                        "\"${Constants.getFunctionName(false, annotatedClassName, maSharedPrefPair.name, false)}\""
                    )
                    .build()
            )
            addAnnotation(JvmOverloads::class)
        }
        addAnnotation(Synchronized::class)

        // receiver & return types isa.
        val valueCanBeNullable = maSharedPrefPair.classIsNullable()
        val toBeUsedValueTypeName = if (valueTypeName.isNullable) {
            valueTypeName
        }else {
            valueTypeName.copy(nullable = valueCanBeNullable)
        }

        receiver(contextClassName)
        returns(toBeUsedValueTypeName)

        // Params isa.
        val paramSpecDefValue = ParameterSpec.builder(VAR_NAME_DEF_VALUE, toBeUsedValueTypeName)
            .apply {
                val defValueIsDeclaredByUser = annotationMirrorOfGivenMASharedPrefPair?.run {
                    this.elementValues.any {
                        it.key.simpleName.toStringOrEmpty() == MASharedPrefPair::defaultValue.name
                    }
                } ?: false

                val defValue = if (maSharedPrefPair.defaultValue == "null" && valueCanBeNullable.not()) {
                    if (defValueIsDeclaredByUser) {
                        error(
                            "You can only pass null to ${MASharedPrefPair::name}.${MASharedPrefPair::defaultValue.name} " +
                                "If and ONLY IF you set class type as a nullable type isa."
                        )
                    }else {
                        val defValue = toBeUsedValueTypeName.getDefaultValueAsKotlinCodeExpression()

                        if (defValue == "null") {
                            error(
                                "You didn't specify a ${MASharedPrefPair::name}.${MASharedPrefPair::defaultValue.name} " +
                                    "for a custom type so null will be used, but null can ONLY be passed " +
                                    "If and ONLY IF you set class type as a nullable type isa."
                            )
                        }else {
                            defValue
                        }
                    }
                }else {
                    maSharedPrefPair.defaultValue
                }

                val isString = toBeUsedValueTypeName == String::class.asTypeName()
                    || toBeUsedValueTypeName == String::class.java.asTypeName()
                    || toBeUsedValueTypeName == String::class.java.asTypeName().copy(nullable = true)
                    || toBeUsedValueTypeName == String::class.asTypeName().copy(nullable = true)
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
        addStatement(
            "return sharedPrefGet<%T>(" +
                "$VAR_NAME_PRIVATE_FILE_NAME, " +

                "\"${maSharedPrefPair.name}\", " +
                "$VAR_NAME_DEF_VALUE, " +

                "Context.MODE_PRIVATE" +

                ")",
            toBeUsedValueTypeName
        )
    }.build()
}
