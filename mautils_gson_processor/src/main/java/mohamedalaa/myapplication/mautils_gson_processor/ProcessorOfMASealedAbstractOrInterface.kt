package mohamedalaa.myapplication.mautils_gson_processor

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import mohamedalaa.myapplication.mautils_gson_annotation.GsonAnnotationConstants

import java.io.IOException

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion

import javax.lang.model.element.TypeElement

import mohamedalaa.myapplication.mautils_gson_annotation.MASealedAbstractOrInterface
import javax.lang.model.element.Modifier
import mohamedalaa.myapplication.mautils_gson_processor.utils.buildMethodSpec

@SupportedAnnotationTypes("mohamedalaa.myapplication.mautils_gson_annotation.MASealedAbstractOrInterface")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ProcessorOfMASealedAbstractOrInterface : AbstractProcessor() {

    private val jClass = MASealedAbstractOrInterface::class.java
    private val fileName = GsonAnnotationConstants.prefixOfAllAnnotations + jClass.simpleName

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val mutableList = mutableListOf<String>()
        for (element in roundEnv.getElementsAnnotatedWith(jClass)) {
            mutableList += (element as TypeElement).qualifiedName.toString()
        }

        // method
        val methodSpecListOfStrings = buildMethodSpec(mutableList)

        // class
        val typeSpecJavaClass = TypeSpec.classBuilder(fileName)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)

            .addMethod(methodSpecListOfStrings)

            .build()

        // file
        val javaFile = JavaFile.builder(jClass.getPackage().name, typeSpecJavaClass)
            .build()

        try {
            //kotlinFile.writeTo(processingEnv.filer)

            javaFile.writeTo(processingEnv.filer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return false
    }

}
