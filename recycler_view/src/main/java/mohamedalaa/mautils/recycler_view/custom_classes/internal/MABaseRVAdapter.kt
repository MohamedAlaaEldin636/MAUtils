package mohamedalaa.mautils.recycler_view.custom_classes.internal

import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.custom_classes.MARVAdapter

/**
 * This is internal for the library only.
 *
 * @see MARVAdapter todo others
 */
abstract class MABaseRVAdapter internal constructor (@LayoutRes private val layoutRes: Int? = null)
    : RecyclerView.Adapter<MABaseRVAdapter.ViewHolder>(), RVAdapterCommonActions {

    // ---- Abstract fun

    /**
     * @return layout resource for the item layout, Note this is tied to [getItemViewType]
     * so returning several layout resources will auto-change [getItemViewType] isa.
     */
    @LayoutRes open fun getLayoutRes(): Int = layoutRes ?: 0

    internal abstract fun internalOnBindViewHolder(holder: ViewHolder, position: Int)

    // ---- Overridden fun

    final override fun getItemViewType(position: Int): Int {
        return getLayoutRes()
    }

    final override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = internalOnBindViewHolder(holder, position)

    // ----- View Holder

    class ViewHolder(internal val any: Any): RecyclerView.ViewHolder(
        when (any) {
            is View -> any
            is ViewDataBinding -> any.root
            else -> throw RuntimeException("MAUtils Error Contact developer code view holder initialization")
        }
    )

}