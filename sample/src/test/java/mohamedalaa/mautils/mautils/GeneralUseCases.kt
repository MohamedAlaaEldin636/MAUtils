package mohamedalaa.mautils.mautils

import android.content.Intent
import mohamedalaa.mautils.core_android.getExtra
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/22/2019.
 *
 */
@RunWith(RobolectricTestRunner::class)
class GeneralUseCases {

    @Test
    fun intent_copy() {
        val baseIntent = Intent()
        baseIntent.putExtra("1", 1)
        baseIntent.putExtra("2", "2")

        val newIntent = Intent()
        newIntent.replaceExtras(baseIntent)

        assertEquals(1, newIntent.getExtra("1"))
    }

}