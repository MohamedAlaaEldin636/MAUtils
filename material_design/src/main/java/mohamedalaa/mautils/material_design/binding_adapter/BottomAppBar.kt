package mohamedalaa.mautils.material_design.binding_adapter

import androidx.annotation.MenuRes
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomappbar.BottomAppBar

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/30/2019.
 *
 */
object BottomAppBar {

    @JvmStatic
    @BindingAdapter("app:bottomAppBar_menuRes")
    fun inflateMenu(bottomAppBar: BottomAppBar, @MenuRes menuRes: Int?) {
        menuRes?.apply { bottomAppBar.inflateMenu(this) }
    }

}