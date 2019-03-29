package mohamedalaa.myapplication.mautils_gson_annotation

/**
 * Used only for mautils_gson_processor library module isa.
 *
 * Name of generated code should be as follows isa,
 *
 * [package name of annotation].[prefixOfAllAnnotations].[Annotation class simple name]
 */
object GsonAnnotationConstants {

    const val prefixOfAllAnnotations = "_Generated"

    private val jClass = MASealedAbstractOrInterface::class.java
    val generatedMASealedAbstractOrInterfaceFullName: String
        = jClass.getPackage().name + "." + GsonAnnotationConstants.prefixOfAllAnnotations + jClass.simpleName

}