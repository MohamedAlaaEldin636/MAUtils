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

package mohamedalaa.mautils.core_android_processor

import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.core_android_annotation.MASharedPref
import mohamedalaa.mautils.core_android_annotation.ProcessorConstants
import mohamedalaa.mautils.core_android_processor.extensions.KotlinpoetUtils
import mohamedalaa.mautils.core_android_processor.extensions.error
import java.io.IOException
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.*

@SupportedAnnotationTypes(value = ["mohamedalaa.mautils.core_android_annotation.MASharedPref"])
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ProcessorOfMASharedPref : AbstractProcessor() {

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val mutableListOfKFiles = mutableListOf<FileSpec>()

        for (element in roundEnv.getElementsAnnotatedWith(MASharedPref::class.java)) {
            if (element !is TypeElement) {
                continue
            }

            // 1. File Name ( Same as KClass name )
            val simpleName = element.simpleName.toString()
            val fileAndKClassName = ProcessorConstants.generationClassNamePrefix + simpleName

            // 2. Create object of that name isa.
            val objectBuilder = TypeSpec.Companion.objectBuilder(fileAndKClassName)

            // 3. Iterate through fields && Create Functions isa
            val functions = mutableListOf<FunSpec>()
            element.enclosedElements.forEach {
                if (it is VariableElement) {
                    val (first, second) = buildFunctions(it, fileAndKClassName) ?: return@forEach
                    functions += first
                    functions += second
                }
            }

            functions.forEach {
                objectBuilder.addFunction(it)
            }

            // 4. Create file of same object name isa.
            mutableListOfKFiles += FileSpec.builder(ProcessorConstants.generationPackage, fileAndKClassName)
                .addImport("mohamedalaa.mautils.core_android", "sharedPrefGet", "sharedPrefSet")
                .addImport("android.content", "Context")
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

    private fun buildFunctions(element: VariableElement, fileName: String): Pair<FunSpec, FunSpec>? {
        // Name
        val simpleName = element.simpleName.toString().capitalize()
        val setterName = "set$simpleName"
        val getterName = "get$simpleName"

        // Return Type && Value
        val setterBuilder = FunSpec.builder(setterName)
        val getterBuilder = FunSpec.builder(getterName)

        when (element.asType().toString()) {
            String::class.java.name.toString() -> {
                val nullableStringTypeName = String::class.asTypeName().copy(nullable = true)
                val defValue = element.constantValue as? String

                modifyFunctions(nullableStringTypeName, defValue, getterBuilder, setterBuilder, fileName, simpleName, "<String?>")
            }
            Int::class.java.name.toString(), Int::class.javaObjectType.name.toString() -> {
                val typeName = Int::class.asTypeName()
                val defValue = element.constantValue as? Int ?: 0

                modifyFunctions(typeName, defValue, getterBuilder, setterBuilder, fileName, simpleName, "<Int>")
            }
            Boolean::class.java.name.toString(), Boolean::class.javaObjectType.name.toString() -> {
                val typeName = Boolean::class.asTypeName()
                val defValue = element.constantValue as? Boolean ?: false

                modifyFunctions(typeName, defValue, getterBuilder, setterBuilder, fileName, simpleName, "<Boolean>")
            }
            Long::class.java.name.toString(), Long::class.javaObjectType.name.toString() -> {
                val typeName = Long::class.asTypeName()
                val defValue = element.constantValue as? Long ?: 0L

                modifyFunctions(typeName, defValue, getterBuilder, setterBuilder, fileName, simpleName, "<Long>")
            }
            Float::class.java.name.toString(), Float::class.javaObjectType.name.toString() -> {
                val typeName = Float::class.asTypeName()
                val defValue = element.constantValue as? Float ?: 0f

                modifyFunctions(typeName, defValue, getterBuilder, setterBuilder, fileName, simpleName, "<Float>")
            }
            Set::class.java.name.toString(), "java.util.Set<java.lang.String>" -> {
                val typeName = KotlinpoetUtils.parameterizedTypeName(
                    Set::class.asTypeName(),
                    String::class.asTypeName().copy(nullable = true)
                ).copy(nullable = true)

                val defValue = element.getAnnotation(MASharedPref.SetOfStringsDefValue::class.java)?.stringSetValue?.toSet()

                modifyFunctions(typeName, defValue, getterBuilder, setterBuilder, fileName, simpleName, "<Set<String?>?>")
            }
            else -> processingEnv.error("Unsupported type ${element.asType()} in shared pref")
        }

        return setterBuilder.build() to getterBuilder.build()
    }

    private fun Any?.nullOrValueAndQuotedIfNeeded(): Any? = when (this) {
        null -> null
        is String -> "\"$this\""
        is Set<*> -> {
            var value = "mutableSetOf("

            this.forEachIndexed { index, item: Any? ->
                value += item.nullOrValueAndQuotedIfNeeded()

                if (index != this.size.dec()) {
                    value += ", "
                }
            }

            value += ")"

            value
        }
        else -> this
    }

    private fun modifyFunctions(
        typeName: TypeName,
        defValue: Any?,
        getterBuilder: FunSpec.Builder,
        setterBuilder: FunSpec.Builder,
        fileName: String,
        simpleName: String,
        parameterizedString: String
    ) {
        // Init
        val contextParameterSpec = ParameterSpec.builder(
            "context",
            ClassName.bestGuess("android.content.Context")
        ).build()

        // Return Type
        getterBuilder.returns(typeName)

        // Params
        getterBuilder.addParameter(contextParameterSpec)
        getterBuilder.addParameter(
            ParameterSpec.builder("defValue", typeName)
                .defaultValue("${defValue.nullOrValueAndQuotedIfNeeded()}")
                .build()
        )
        getterBuilder.addAnnotation(JvmOverloads::class)
        getterBuilder.addAnnotation(Synchronized::class)

        setterBuilder.addParameter(contextParameterSpec)
        setterBuilder.addParameter("value", typeName)
        setterBuilder.addAnnotation(Synchronized::class)

        // Value
        getterBuilder.addStatement(
            "return context.sharedPrefGet$parameterizedString(\"$fileName\", \"$simpleName\", defValue)"
        )

        setterBuilder.addStatement("context.sharedPrefSet$parameterizedString(\"$fileName\", \"$simpleName\", value)")
    }

}
