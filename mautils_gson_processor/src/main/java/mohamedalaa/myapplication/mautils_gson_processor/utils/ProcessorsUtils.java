package mohamedalaa.myapplication.mautils_gson_processor.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.kotlinpoet.ParameterizedTypeName;
import com.squareup.kotlinpoet.TypeName;

import java.util.List;

import kotlin.reflect.KClass;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/29/2019.
 */
public class ProcessorsUtils {

    public static TypeName parameterizedTypeName(KClass<?> kClass, KClass<?>... typeArguments) {
        return ParameterizedTypeName.get(kClass, typeArguments);
    }

    public static com.squareup.javapoet.TypeName jParameterizedTypeName(Class<?> jClass, Class<?>... typeArguments) {
        return com.squareup.javapoet.ParameterizedTypeName.get(jClass, typeArguments);
    }

    public static com.squareup.javapoet.TypeName jParameterizedTypeNameListOfStrings() {
        return com.squareup.javapoet.ParameterizedTypeName.get(List.class, String.class);
    }

    public static ClassName getClassNameArrayList() {
        return ClassName.get("java.util", "ArrayList");
    }

}
