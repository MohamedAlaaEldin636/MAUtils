package mohamedalaa.mautils.mautils.module_mautils_gson

import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.mautils.assertEquality
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GsonTestV3 {

    @Test
    fun trial_with_annotation_isa() {
        println("hello".toJson())
        println()
        println("PRE bye".toJson())
        println()
        println("bye".toJson())
    }

    @Test
    fun toAndFrom_withSealedClass() {
        val o1 = ParentUsingBoth(
            UsingBoth(NoArgsSealedClass.NoSingleton, WithArgsSealedClass.WithDataClass(false), 35f),
            WithArgsSealedClass.WithDataClass(false),
            984,
            ImplSomeInterface(12, "s as string"),
            ImplAbstractClass(602, true)
        )
        val j1 = o1.toJson()
        val r1 = j1.fromJson<ParentUsingBoth>()

        println("########")
        println(o1)
        println()
        println(j1)
        println()
        println(r1)
        println()

        assertEquality(o1, r1)
    }

}