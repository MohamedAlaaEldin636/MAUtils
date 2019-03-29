package mohamedalaa.mautils.mautils.module_mautils_gson

import mohamedalaa.mautils.gson_annotation.MASealedAbstractOrInterface

@MASealedAbstractOrInterface
sealed class NoArgsSealedClass {

    data class NoDataClass(val bool: Boolean?) : NoArgsSealedClass()

    object NoSingleton : NoArgsSealedClass()

}

@MASealedAbstractOrInterface
sealed class WithArgsSealedClass {

    object WithSingleton : WithArgsSealedClass()

    data class WithDataClass(val bool: Boolean?) : WithArgsSealedClass()

}

data class UsingBoth(
    val noArgsSealedClass: NoArgsSealedClass,
    val withArgsSealedClass: WithArgsSealedClass,
    val float: Float
)

data class ParentUsingBoth(
    val usingBoth: UsingBoth,
    val withArgsSealedClass: WithArgsSealedClass,
    val int: Int,
    val implSomeInterface: ImplSomeInterface,
    val implAbstractClass: ImplAbstractClass
)

@MASealedAbstractOrInterface
interface SomeInterface {
    val someInterfaceInt: Int
}

class ImplSomeInterface(override val someInterfaceInt: Int, s: String) : SomeInterface {

    val string: String = s

}

@MASealedAbstractOrInterface
abstract class AbstractClass(val abstractClassInt: Int)

class ImplAbstractClass(int: Int, val b: Boolean) : AbstractClass(int)