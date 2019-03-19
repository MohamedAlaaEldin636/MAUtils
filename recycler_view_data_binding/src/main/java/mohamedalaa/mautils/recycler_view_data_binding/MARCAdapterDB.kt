package mohamedalaa.mautils.recycler_view_data_binding

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import mohamedalaa.mautils.recycler_view.custom_classes.MAListRCAdapter
import mohamedalaa.mautils.recycler_view.custom_classes.MARCAdapter

/**
 * Same as [MARCAdapter] but adds [MARCAdapterDB.onBindViewHolder] instead of same fun in [MARCAdapter]
 * but takes in 1 view type, for multiple view types See [MARCAdapterMultipleDB] isa.
 */
abstract class MARCAdapterDB<VDB : ViewDataBinding>(@LayoutRes layoutRes: Int): MARCAdapter(layoutRes) {

    // ---- Abstract fun

    /**
     * Same as [MARCAdapter.onBindViewHolder] but provides [binding]
     * as param instead of layout root view for quick access to it isa.
     */
    abstract fun onBindViewHolder(binding: VDB, position: Int)

    // ---- Overridden fun

    final override fun getLayoutRes(): Int = super.getLayoutRes()

    final override fun onBindViewHolder(itemView: View, position: Int)
        = onBindViewHolder(itemView.hello<VDB>(), position)
    /**
     * DataBindingUtil.findBinding<VDB>(itemView)
    ?: throw RuntimeException("layoutRes not like VDB generic view data binding")
     */
}

fun <VDB : ViewDataBinding> View.hello(): VDB {
    return DataBindingUtil.findBinding(this) ?: throw RuntimeException("dewd")
}

abstract class MAListRCAdapterDB<E, VDB : ViewDataBinding>(dataList: List<E>)
    : MAListRCAdapter<E>(dataList) {

    // ---- Abstract fun

    /**
     * Same as [MARCAdapter.onBindViewHolder] but provides [binding]
     * as param instead of layout root view for quick access to it isa.
     */
    abstract fun onBindViewHolder(binding: VDB, position: Int)

    // ---- Overridden fun

    final override fun getLayoutRes(): Int = super.getLayoutRes()

    final override fun onBindViewHolder(itemView: View, position: Int)
        = onBindViewHolder(DataBindingUtil.findBinding<VDB>(itemView)
        ?: throw RuntimeException("layoutRes not like VDB generic view data binding"), position)

}
