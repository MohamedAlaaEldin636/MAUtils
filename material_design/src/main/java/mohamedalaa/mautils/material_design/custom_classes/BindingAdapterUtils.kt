package mohamedalaa.mautils.material_design.custom_classes

import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

/**
 * Use **xmlns:mautils="http://schemas.android.com/tools"** in your layout.xml
 * for mautils namespace to work isa.
 */
object BindingAdapterUtils {

    // todo test it in sample isa.
    @JvmStatic
    @BindingAdapter("setToolbarMenu_resId",
        "setToolbarMenu_menuItemClickListener",
        "setToolbarMenu_navIconClickListener",
        requireAll = false)
    fun setToolbarMenuAndActions(toolbar: Toolbar,
                                 @MenuRes menuResId: Int?,
                                 menuItemClickListener: Toolbar.OnMenuItemClickListener?,
                                 navIconClickListener: View.OnClickListener?) {
        menuResId?.apply {
            toolbar.inflateMenu(this)
        }

        toolbar.setOnMenuItemClickListener(menuItemClickListener)

        toolbar.setNavigationOnClickListener(navIconClickListener)
    }

    @JvmStatic
    @BindingAdapter("setA1")
    fun setA1(view: View, integer: Int) {

    }

}