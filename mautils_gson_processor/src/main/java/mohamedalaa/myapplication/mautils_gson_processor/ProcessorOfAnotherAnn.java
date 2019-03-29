package mohamedalaa.myapplication.mautils_gson_processor;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import mohamedalaa.myapplication.mautils_gson_annotation.AnotherAnn;

import javax.annotation.processing.Processor;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/29/2019.
 */
@SupportedAnnotationTypes("mohamedalaa.myapplication.mautils_gson_annotation.AnotherAnn")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ProcessorOfAnotherAnn extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //String packageName = "";
        StringBuilder value = new StringBuilder();
        for (Element element : roundEnv.getElementsAnnotatedWith(AnotherAnn.class)) {
            //packageName = ((PackageElement) element.getEnclosingElement()).getQualifiedName().toString();
            value.append(((TypeElement) element).getQualifiedName());
            value.append(",");
        }

        FieldSpec fieldSpec = FieldSpec.builder(String.class, "string")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
            .initializer("$S", value.toString())
            .build();

        TypeSpec helloWorld = TypeSpec.classBuilder(/*"AboAlaa" todo */AnotherAnn.class.getSimpleName())
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            //.addMethod(main)
            .addField(fieldSpec)
            .build();

        JavaFile javaFile = JavaFile.builder(/*"my.package"*/getClass().getPackage().getName()/*packageName*/, helloWorld)
            .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
