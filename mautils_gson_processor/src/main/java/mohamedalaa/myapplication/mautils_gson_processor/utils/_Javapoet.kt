package mohamedalaa.myapplication.mautils_gson_processor.utils

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.MethodSpec
import javax.lang.model.element.Modifier

/** Generate public static final method that returns given [init] isa. */
fun buildMethodSpec(init: List<String>, methodName: String = "method1"): MethodSpec {
    return MethodSpec.methodBuilder(methodName)
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)

        .returns(ProcessorsUtils.jParameterizedTypeName(List::class.java, String::class.java))

        .init(init)

        .build()
}

fun MethodSpec.Builder.init(list: List<String>) = apply {
    addStatement("List<String> result = new \$T<>()", ClassName.get("java.util", "ArrayList"))

    for (item in list) {
        addStatement("result.add(\"$item\")")
    }

    addStatement("return result")
}