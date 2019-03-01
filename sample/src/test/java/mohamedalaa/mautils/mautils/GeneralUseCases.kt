package mohamedalaa.mautils.mautils

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import androidx.core.os.bundleOf
import mohamedalaa.mautils.core_android.addValues
import mohamedalaa.mautils.core_android.buildBundle
import mohamedalaa.mautils.core_android.getExtra
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.Serializable
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

        //val c = ApplicationProvider.getApplicationContext<Context>()

        assertEquals(1, newIntent.getExtra("1"))
    }

    @Test
    fun trial611() {
        val sa = SparseArray<Parcelable>().apply {
            put(1, bundleOf("Abc" to "abc"))
        }
        /*println(sa is SparseArray<*>)
        println(sa as? SparseArray<*>)
        println(sa as? SparseArray<Parcelable>)

        println(arrayOf(sa).toList())*/

        //bundleOf("key" to sa)

        buildBundle(99, "h", sa)

        Bundle().addValues(99, "h", sa)

        // todo also for addAll inside apply not working maybe try reified or just generic instead of Any? isa.

        // todo obtain them here and comment providing access to them and test lists as well isa.

    }

}