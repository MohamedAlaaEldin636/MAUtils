package mohamedalaa.mautils.recycler_view_data_binding

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRCAdapter
import mohamedalaa.mautils.recycler_view.custom_classes.MARCAdapter

/**
 * Same as [MARCAdapter] but adds [MARCAdapterDB.onBindViewHolder] instead of same fun in [MARCAdapter]
 * suitable for multiple view types but if you use single item view type See [MARCAdapterDB] isa.
 */
abstract class MARCAdapterMultipleDB : MARCAdapter() {

    // ---- Abstract fun

    /**
     * Same as [MARCAdapter.onBindViewHolder] but provides [binding]
     * as param instead of layout root view for quick access to it isa.
     */
    abstract fun onBindViewHolder(binding: ViewDataBinding, position: Int)

    // ---- Overridden fun

    final override fun onBindViewHolder(itemView: View, position: Int)
        = onBindViewHolder(DataBindingUtil.findBinding<ViewDataBinding>(itemView)
        ?: throw RuntimeException("layoutRes not like VDB generic view data binding"), position)

}

abstract class MAListRCAdapterMultipleDB<E>(dataList: List<E>)
    : MAListRCAdapter<E>(dataList) {

    // ---- Abstract fun

    /**
     * Same as [MARCAdapter.onBindViewHolder] but provides [binding]
     * as param instead of layout root view for quick access to it isa.
     */
    abstract fun onBindViewHolder(binding: ViewDataBinding, position: Int)

    // ---- Overridden fun

    final override fun onBindViewHolder(itemView: View, position: Int)
        = onBindViewHolder(DataBindingUtil.findBinding<ViewDataBinding>(itemView)
        ?: throw RuntimeException("layoutRes not like VDB generic view data binding"), position)

}