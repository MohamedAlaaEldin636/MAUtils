package mohamedalaa.mautils.gson_annotation

/**
 * Used only for mautils_gson_processor library module isa.
 *
 * Name of generated code should be as follows isa,
 *
 * [package name of annotation].[prefixOfAllAnnotations].[Annotation class simple name]
 */
object GsonAnnotationConstants {

    private const val prefixOfAllAnnotations = "_Generated"

    val maSealedAbstractOrInterfaceJClass = MASealedAbstractOrInterface::class.java

    val generatedMASealedAbstractOrInterfacePackageName: String
        = maSealedAbstractOrInterfaceJClass.getPackage().name

    val generatedMASealedAbstractOrInterfaceSimpleName: String
        = prefixOfAllAnnotations + maSealedAbstractOrInterfaceJClass.simpleName

    val generatedMASealedAbstractOrInterfaceFullName: String
        = "$generatedMASealedAbstractOrInterfacePackageName.$generatedMASealedAbstractOrInterfaceSimpleName"

}