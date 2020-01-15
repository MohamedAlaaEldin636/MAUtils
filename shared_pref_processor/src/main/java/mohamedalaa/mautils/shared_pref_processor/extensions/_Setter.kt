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
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair
import mohamedalaa.mautils.shared_pref_processor.*
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_COMMIT
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_PRIVATE_FILE_NAME
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_VALUE
import javax.annotation.processing.ProcessingEnvironment

/*
@JvmName("setName")
@JvmOverloads
@Synchronized
fun Context.sharedPref_SomeClassName_SetName(
    value: ValueType/?/,
    commit: Boolean = false
): Boolean? {
    return sharedPrefSet<ValueType/?/>(
        privateFileName,
        "mySetOfStrings",
        value,
        Context.MODE_PRIVATE,
        commit
    )
}
*/
@Suppress("unused")
fun ProcessingEnvironment.buildSetterFun(
    annotatedClassName: String,

    maSharedPrefPair: MASharedPrefPair,

    fileConfigsSupportsJavaConsumer: Boolean,

    valueTypeName: TypeName
): FunSpec {
    return FunSpec.builder(
        Constants.getFunctionName(true, annotatedClassName, maSharedPrefPair.name, true)
    ).apply {
        // Annotations isa.
        val supportsJavaCode = maSharedPrefPair.supportJavaConsumerCode == MASharedPrefPair.JavaConsumerCode.SUPPORT
            || (maSharedPrefPair.supportJavaConsumerCode == MASharedPrefPair.JavaConsumerCode.BEHAVE_AS_IN_CONFIGS
            && fileConfigsSupportsJavaConsumer)

        if (supportsJavaCode) {
            addAnnotation(
                AnnotationSpec.builder(JvmName::class)
                    .addMember(
                        "\"${Constants.getFunctionName(true, annotatedClassName, maSharedPrefPair.name, false)}\""
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
        val valueCanBeNullable = maSharedPrefPair.classIsNullable()
        val toBeUsedValueTypeName = if (valueTypeName.isNullable) {
            valueTypeName
        }else {
            valueTypeName.copy(nullable = valueCanBeNullable)
        }
        addParameter(VAR_NAME_VALUE, toBeUsedValueTypeName)
        addParameter(
            ParameterSpec.builder(VAR_NAME_COMMIT, Boolean::class.asTypeName())
                .defaultValue("false")
                .build()
        )

        // fun code isa.
        addStatement(
            "return sharedPrefSet<%T>(" +
                "$VAR_NAME_PRIVATE_FILE_NAME, " +

                "\"${maSharedPrefPair.name}\", " +
                "$VAR_NAME_VALUE, " +

                "Context.MODE_PRIVATE, " +
                VAR_NAME_COMMIT +

                ")",
            toBeUsedValueTypeName
        )
    }.build()
}
