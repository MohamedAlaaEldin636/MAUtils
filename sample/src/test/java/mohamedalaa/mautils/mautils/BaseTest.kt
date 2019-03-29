package mohamedalaa.mautils.mautils

import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BaseTest {

    sealed class BidAttachment {

        object With : BidAttachment()
        data class Risk(val value: Int): BidAttachment()

    }

    data class Contain22(
        val risk: BidAttachment.Risk,
        val with: BidAttachment.With?,
        val int: Int
    )

    @Test
    fun normalClass() {
        val o1 = Contain22(BidAttachment.Risk(33), BidAttachment.With, 6)
        val j1 = o1.toJson()
        val r1 = j1.fromJson<Contain22>()

        println("########")
        println(o1)
        println(j1)
        println(r1)
        println()
    }

}