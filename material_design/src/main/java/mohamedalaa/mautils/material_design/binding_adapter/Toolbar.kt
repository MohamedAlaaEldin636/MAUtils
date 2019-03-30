package mohamedalaa.mautils.material_design.binding_adapter

import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

object Toolbar {

    @JvmStatic
    @BindingAdapter("app:toolbar_menuRes",
        "app:toolbar_onMenuItemClickListener",
        "app:toolbar_onNavigationIconClickListener",
        requireAll = false)
    fun setupToolbar(
        toolbar: Toolbar,
        @MenuRes menuRes: Int?,
        onMenuItemClickListener: Toolbar.OnMenuItemClickListener?,
        onNavigationIconClickListener: View.OnClickListener?
    ) {
        menuRes?.apply { toolbar.inflateMenu(this) }

        onMenuItemClickListener?.apply { toolbar.setOnMenuItemClickListener(this) }

        onNavigationIconClickListener?.apply { toolbar.setNavigationOnClickListener(this) }
    }

}