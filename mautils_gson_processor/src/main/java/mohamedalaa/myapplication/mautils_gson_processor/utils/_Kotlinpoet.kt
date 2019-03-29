package mohamedalaa.myapplication.mautils_gson_processor.utils

import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.buildCodeBlock

fun PropertySpec.Builder.init(list: List<String>) = apply {
    val elementsSeparatedWithComma = StringBuilder()
    for ((index, item) in list.withIndex()) {
        elementsSeparatedWithComma.append("\"$item\"")

        if (index != list.lastIndex) {
            elementsSeparatedWithComma.append(", ")
        }
    }

    initializer(buildCodeBlock {
        addStatement(if (list.isEmpty()) "emptyList()" else "listOf($elementsSeparatedWithComma)")
    })
}