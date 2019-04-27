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

import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter
import mohamedalaa.mautils.room_gson_annotation.ProcessorConstants
import mohamedalaa.mautils.room_gson_processor.extensions.functionsInNoTypeParamBuild
import mohamedalaa.mautils.room_gson_processor.extensions.functionsInWithTypeParamBuild
import mohamedalaa.mautils.room_gson_processor.extensions.noTypeParamBuild
import mohamedalaa.mautils.room_gson_processor.extensions.withTypeParamBuild
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

@SupportedAnnotationTypes(value = ["mohamedalaa.mautils.room_gson_annotation.MARoomGsonTypeConverter"])
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ProcessorOfMARoomGsonTypeConverter : AbstractProcessor() {

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        //val mutableListOfKFiles = mutableListOf<FileSpec>()

        val allFunctions = mutableListOf<FunSpec>()
        for (element in roundEnv.getElementsAnnotatedWith(MARoomGsonTypeConverter::class.java)) {
            if (element !is TypeElement && element !is VariableElement) {
                processingEnv.error("full type -> ${element.asType()}")
            }

            allFunctions += buildFunctions(element)

            // Build object class and functions isa.
            /*val pair = fileAndObjectNameToObjectBuilder(element)

            // 4. Create file of same object name isa.
            mutableListOfKFiles += FileSpec.builder(ProcessorConstants.generationPackage, pair.first)
                .addImport("mohamedalaa.mautils.gson", "fromJsonOrNull", "toJsonOrNull")
                .addType(pair.second.build())
                .addAnnotation(AnnotationSpec.builder(Suppress::class)
                    .addMember("\"PLATFORM_CLASS_MAPPED_TO_KOTLIN\"")
                    .build()
                )
                .build()*/
        }

        val kClass = TypeSpec.classBuilder(ProcessorConstants.generationClassSimpleName)
            .addFunctions(allFunctions)
            .build()

        val kFile = FileSpec.builder(ProcessorConstants.generationPackage, ProcessorConstants.generationClassSimpleName)
            .addImport("mohamedalaa.mautils.gson", "fromJsonOrNull", "toJsonOrNull")
            .addType(kClass)
            .addAnnotation(AnnotationSpec.builder(Suppress::class)
                .addMember("\"PLATFORM_CLASS_MAPPED_TO_KOTLIN\"")
                .build()
            )
            .build()

        try {
            kFile.writeTo(processingEnv.filer)
        }catch (e: IOException) {
            e.printStackTrace()
        }

        // Generate Code by Building each file isa.
        /*for (kFile in mutableListOfKFiles) {
            try {
                kFile.writeTo(processingEnv.filer)
            }catch (e: IOException) {
                e.printStackTrace()
            }
        }*/

        return false
    }

    /**
     * @return pair.first is kFileName, .second fully done object builder isa.
     */
    private fun buildFunctions(element: Element): List<FunSpec> {
        val asTypeString = element.asType().toString()

        return if (asTypeString.contains("<")) {
            functionsInWithTypeParamBuild(element)
        }else {
            functionsInNoTypeParamBuild(element)
        }
    }

    /**
     * @return pair.first is kFileName, .second fully done object builder isa.
     */
    private fun fileAndObjectNameToObjectBuilder(element: Element): Pair<String, TypeSpec.Builder> {
        val asTypeString = element.asType().toString()

        return if (asTypeString.contains("<")) {
            withTypeParamBuild(element)
        }else {
            noTypeParamBuild(element)
        }
    }

}