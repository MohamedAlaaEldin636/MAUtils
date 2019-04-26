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

package mohamedalaa.mautils.room_gson_processor

import androidx.room.TypeConverter
import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter
import mohamedalaa.mautils.room_gson_annotation.ProcessorConstants
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/26/2019.
 *
 */
@SupportedAnnotationTypes(value = ["mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter"])
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ProcessorOfMARoomGsonTypeConverter : AbstractProcessor() {

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val mutableListOfKFiles = mutableListOf<FileSpec>()

        for (element in roundEnv.getElementsAnnotatedWith(MARoomGsonTypeConverter::class.java)) {
            if (element !is TypeElement) {
                continue
            }

            // 1. File Name ( Same as KClass name )
            val simpleName = element.simpleName.toString()
            val fileAndKClassName = ProcessorConstants.generationClassNamePrefix + simpleName

            // 2. Create object of that name isa.
            val objectBuilder = TypeSpec.objectBuilder(fileAndKClassName)

            // 3. Build functions
            val functions = buildFunctions(simpleName, element.qualifiedName.toString())
            functions.forEach {
                objectBuilder.addFunction(it)
            }

            // 4. Create file of same object name isa.
            mutableListOfKFiles += FileSpec.builder(ProcessorConstants.generationPackage, fileAndKClassName)
                .addImport("mohamedalaa.mautils.gson", "fromJsonOrNull", "toJsonOrNull")
                .addType(objectBuilder.build())
                .build()
        }

        // Generate Code by Building each file isa.
        for (kFile in mutableListOfKFiles) {
            try {
                kFile.writeTo(processingEnv.filer)
            }catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return false
    }

    private fun buildFunctions(classSimpleName: String, classFullName: String): List<FunSpec> {
        val mutableList = mutableListOf<FunSpec>()

        // Name of fun
        val fromStringFunName = "${classSimpleName.decapitalize()}fromString"
        val toStringFunName = "${classSimpleName.decapitalize()}toString"

        // Builders
        val fromStringFun = FunSpec.builder(fromStringFunName)
        val toStringFun = FunSpec.builder(toStringFunName)

        // Return Type
        val typeNameString = String::class.asTypeName().copy(nullable = true)
        val typeNameClass = ClassName.bestGuess(classFullName).copy(nullable = true)
        fromStringFun.returns(typeNameClass)
        toStringFun.returns(typeNameString)

        // Annotations
        fromStringFun.addAnnotation(TypeConverter::class)
        toStringFun.addAnnotation(TypeConverter::class)

        // Parameters
        fromStringFun.addParameter("string", typeNameString)
        toStringFun.addParameter("value", typeNameClass)

        // Body
        fromStringFun.addStatement(
            "return string.fromJsonOrNull<$classSimpleName>()"
        )
        toStringFun.addStatement(
            "return value.toJsonOrNull()"
        )

        mutableList += toStringFun.build()
        mutableList += fromStringFun.build()
        return mutableList
    }

    private fun FileSpec.Builder.addFullImport(fullImport: String) = apply {
        val lastIndexOfDot = fullImport.lastIndexOf(".")

        addImport(fullImport.substring(0, lastIndexOfDot), fullImport.substring(lastIndexOfDot.inc()))
    }

}