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

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import mohamedalaa.mautils.core_android_annotation.MANonNestedParameterizedClass
import mohamedalaa.mautils.core_android_annotation.MASharedPrefField
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.type.MirroredTypesException
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

/**
 * @return [Pair.first] ==> [TypeName] corresponding to [MANonNestedParameterizedClass] even
 * if has type params isa,
 *
 * && [Pair.second] ==> SharedPrefSupportedTypesParams array as string isa.
 */
fun MANonNestedParameterizedClass.convertToTypeName(
    processingEnv: ProcessingEnvironment,
    acceptNullableSetItem: Boolean
): Pair<TypeName, String> = processingEnv.run processingEnvRun@ {
    val typeMirrors: List<TypeMirror> = try {
        @Suppress("UNCHECKED_CAST")
        value as List<TypeMirror> // Has to throw an exception isa.
    }catch (e: MirroredTypesException) {
        e.typeMirrors
    }
    return typeMirrors.run {
        when (size) {
            0 -> error(
                "at least 1 ${KClass::class.simpleName} has to be provided in ${MANonNestedParameterizedClass::class.simpleName}" +
                    "which is inside ${MASharedPrefField::class.simpleName} annotation isa."
            )
            1 -> this[0].asTypeName().convertJavaClassTypeNameToKClassTypeNameIsa() to ""
            else -> {
                val firstTypeName = this[0].asTypeName().convertJavaClassTypeNameToKClassTypeNameIsa()
                val isSetClass = firstTypeName == Set::class.asTypeName() || firstTypeName == Set::class.java.asTypeName()

                val containsNullableItemInsideSet = isSetClass && acceptNullableSetItem

                val (otherTypeNames, stringValue) = drop(1).map {
                    it.asTypeName().convertJavaClassTypeNameToKClassTypeNameIsa().copy(nullable = containsNullableItemInsideSet)
                }.run {
                    toTypedArray() to convertToCommaSeparatedSharedPrefSupportedTypesParamsFromKClassTypeName(this@processingEnvRun)
                }
                KotlinpoetUtils.parameterizedTypeName(
                    firstTypeName,
                    *otherTypeNames
                ) to stringValue
            }
        }
    }
}

private fun List<TypeName>.convertToCommaSeparatedSharedPrefSupportedTypesParamsFromKClassTypeName(processingEnv: ProcessingEnvironment): String {
    return joinToString(", ") {
        when (it) {
            String::class.asTypeName(), String::class.asTypeNameNullable() -> "SharedPrefSupportedTypesParams.STRING"
            Boolean::class.asTypeName(), Boolean::class.asTypeNameNullable() -> "SharedPrefSupportedTypesParams.BOOLEAN"
            Int::class.asTypeName(), Int::class.asTypeNameNullable() -> "SharedPrefSupportedTypesParams.INT"
            Float::class.asTypeName(), Float::class.asTypeNameNullable() -> "SharedPrefSupportedTypesParams.FLOAT"
            Long::class.asTypeName(), Long::class.asTypeNameNullable() -> "SharedPrefSupportedTypesParams.LONG"
            else -> processingEnv.error("Unexpected error code typeName $it 9201373cwkehw839283 isa.")
        }
    }
}

private fun KClass<*>.asTypeNameNullable(): ClassName = asTypeName().copy(nullable = true)