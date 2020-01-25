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

package mohamedalaa.mautils.room_gson_processor.extensions

import androidx.room.TypeConverter
import com.maproductions.mohamedalaa.kotlin_poet.analyzeDeeplyToListOfTypeNames
import com.maproductions.mohamedalaa.kotlin_poet.convertJavaToKotlinTypeAsTypeName
import com.maproductions.mohamedalaa.library_common_processors.asTypeName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.util.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element

private const val PARAM_NAME_STRING = "string"
private const val PARAM_NAME_VALUE = "value"

/**
 * - Returns below functions isa.
 * ```
 * // to conversion isa.
 * @TypeConverter
 * fun nameToString(value: ValueType?): String? = value.toJsonOrNull()
 *
 * // from conversion isa.
 * @TypeConverter
 * fun nameFromString(string: String?): ValueType? = string.fromJsonOrNull()
 * ```
 */
fun ProcessingEnvironment.buildToAndFromConversionFunctions(element: Element): List<FunSpec> =
    buildToAndFromConversionFunctions(element.asTypeName().copy(nullable = true))

/** Same as [buildToAndFromConversionFunctions] isa. */
@Suppress("unused")
fun ProcessingEnvironment.buildToAndFromConversionFunctions(typeName: TypeName): List<FunSpec> {
    val mutableList = mutableListOf<FunSpec>()

    val valueTypeName = typeName.convertJavaToKotlinTypeAsTypeName().copy(nullable = true)
    val stringTypeName = String::class.asTypeName().copy(nullable = true)

    // Name of fun
    val fromStringFunName = valueTypeName.conversionFunctionName(true)
    val toStringFunName = valueTypeName.conversionFunctionName(false)

    // Builders
    val fromStringFun = FunSpec.builder(fromStringFunName)
    val toStringFun = FunSpec.builder(toStringFunName)

    // Annotations
    fromStringFun.addAnnotation(TypeConverter::class)
    toStringFun.addAnnotation(TypeConverter::class)

    // Parameters
    fromStringFun.addParameter(PARAM_NAME_STRING, stringTypeName)
    toStringFun.addParameter(PARAM_NAME_VALUE, valueTypeName)

    // Return Type
    fromStringFun.returns(valueTypeName)
    toStringFun.returns(stringTypeName)

    // Body
    fromStringFun.addStatement(
        "return string.fromJsonOrNull()"
    )
    toStringFun.addStatement(
        "return value.toJsonOrNull()"
    )

    mutableList += toStringFun.build()
    mutableList += fromStringFun.build()
    return mutableList
}

// ---- Private fun

private fun TypeName.conversionFunctionName(from: Boolean): String {
    val listOfTypes = analyzeDeeplyToListOfTypeNames()

    val name = listOfTypes.joinToString("") {
        it.toString().typeSimpleNameFromFullName()
    }

    return if (from) {
        "${name.decapitalizeString()}FromString"
    }else {
        "${name.decapitalizeString()}ToString"
    }
}

/** @return Simple name from a class full name Ex. java.lang.String returns String isa. */
private fun String.typeSimpleNameFromFullName(): String {
    var name = if ("." !in this) this else substring(lastIndexOf(".").inc())

    name = name.replace("?", "")
    name = name.replace("<", "")
    name = name.replace(">", "")

    return name
}

private fun String.decapitalizeString(): String {
    return if (isNotEmpty() && this[0].isUpperCase()) substring(0, 1).toLowerCase(Locale.getDefault()) + substring(1) else this
}
