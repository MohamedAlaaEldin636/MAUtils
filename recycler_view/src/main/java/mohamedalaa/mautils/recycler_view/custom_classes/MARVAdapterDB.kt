package mohamedalaa.mautils.recycler_view.custom_classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import mohamedalaa.mautils.recycler_view.custom_classes.internal.MABaseRVAdapter

/**
 * Same as [MARVAdapter] but with change in [MARVAdapterDB.onBindViewHolder] to have [VDB]
 * instead of the root view of the item layout.
 *
 * Also you cannot override [getLayoutRes] since [layoutRes] param will do the job,
 * So this is used when you have only 1 item view type if you need more item view types
 * then use [MARVAdapterMultiDB] instead isa.
 *
 * @param layoutRes item layout resource.
 * @param VDB any [ViewDataBinding] corresponds to [layoutRes] param.
 */
abstract class MARVAdapterDB<VDB : ViewDataBinding>(@LayoutRes layoutRes: Int)
    : MABaseRVAdapter(layoutRes) {

    // ---- Abstract fun

    /**
     * Same as [MARVAdapter.onBindViewHolder] but provides [binding]
     * as param instead of layout root view for quick access to it isa.
     */
    abstract fun onBindViewHolder(binding: VDB, position: Int)

    // ---- Overridden fun

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<VDB>(inflater, getLayoutRes(), parent, false)

        return ViewHolder(binding)
    }

    final override fun getLayoutRes(): Int = super.getLayoutRes()

    @Suppress("UNCHECKED_CAST")
    final override fun internalOnBindViewHolder(holder: ViewHolder, position: Int)
        = onBindViewHolder(holder.any as VDB, position)
}
