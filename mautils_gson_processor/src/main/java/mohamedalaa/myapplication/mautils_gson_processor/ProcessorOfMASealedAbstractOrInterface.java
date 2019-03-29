package mohamedalaa.myapplication.mautils_gson_processor;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.kotlinpoet.FileSpec;
import com.squareup.kotlinpoet.FileSpecKt;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import javax.annotation.processing.Processor;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import mohamedalaa.myapplication.mautils_gson_annotation.MASealedAbstractOrInterface;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/29/2019.
 */
@SupportedAnnotationTypes("mohamedalaa.myapplication.mautils_gson_annotation.MASealedAbstractOrInterface")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ProcessorOfMASealedAbstractOrInterface extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //String packageName = "";
        StringBuilder value = new StringBuilder();
        for (Element element : roundEnv.getElementsAnnotatedWith(MASealedAbstractOrInterface.class)) {
            //packageName = ((PackageElement) element.getEnclosingElement()).getQualifiedName().toString();
            value.append(((TypeElement) element).getQualifiedName());
            value.append(",");
        }

        FieldSpec fieldSpec = FieldSpec.builder(String.class, "string")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer("$S", value.toString())
            .build();

        TypeSpec helloWorld = TypeSpec.classBuilder(/*"AboAlaa" todo */"Prefix" + MASealedAbstractOrInterface.class.getSimpleName())
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            //.addMethod(main)
            .addField(fieldSpec)
            .build();

        JavaFile javaFile = JavaFile.builder(/*"my.package"*/getClass().getPackage().getName()/*packageName*/, helloWorld)
            .build();

        String fileName = "Prefix" + MASealedAbstractOrInterface.class.getSimpleName();
        FileSpec kotlinFile = FileSpec.builder(getClass().getPackage().getName(), fileName)
            .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());

            kotlinFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
