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

package mohamedalaa.mautils.gson_processor

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import mohamedalaa.mautils.gson_annotation.GsonAnnotationConstants

import java.io.IOException

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion

import javax.lang.model.element.TypeElement

import javax.lang.model.element.Modifier
import mohamedalaa.mautils.gson_processor.utils.buildMethodSpec

@SupportedAnnotationTypes("mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ProcessorOfMASealedAbstractOrInterface : AbstractProcessor() {

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val mutableList = mutableListOf<String>()
        for (element in roundEnv.getElementsAnnotatedWith(GsonAnnotationConstants.maSealedAbstractOrInterfaceJClass)) {
            mutableList += (element as TypeElement).qualifiedName.toString()
        }

        // method
        val methodSpecListOfStrings = buildMethodSpec(mutableList)

        // class
        val typeSpecJavaClass = TypeSpec.classBuilder(GsonAnnotationConstants.generatedMASealedAbstractOrInterfaceSimpleName)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)

            .addMethod(methodSpecListOfStrings)

            .build()

        // file
        val javaFile = JavaFile.builder(GsonAnnotationConstants.generatedMASealedAbstractOrInterfacePackageName, typeSpecJavaClass)
            .build()

        try {
            javaFile.writeTo(processingEnv.filer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return false
    }

}
