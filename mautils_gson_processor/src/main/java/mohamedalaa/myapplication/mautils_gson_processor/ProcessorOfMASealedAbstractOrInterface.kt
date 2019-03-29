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

import javax.lang.model.element.Modifier
import mohamedalaa.myapplication.mautils_gson_processor.utils.buildMethodSpec

@SupportedAnnotationTypes("mohamedalaa.myapplication.mautils_gson_annotation.MASealedAbstractOrInterface")
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
