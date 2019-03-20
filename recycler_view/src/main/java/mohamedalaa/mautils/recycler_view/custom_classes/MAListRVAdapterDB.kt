package mohamedalaa.mautils.recycler_view.custom_classes

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import mohamedalaa.mautils.recycler_view.custom_classes.internal.ListRVAdapterCommonActions

/**
 * Same as [MAListRVAdapter] but provides [VDB] instead of root view of item layout in [onBindViewHolder],
 * for more clarification See [MARVAdapterDB].
 */
abstract class MAListRVAdapterDB<E, VDB : ViewDataBinding>(dataList: List<E>, @LayoutRes layoutRes: Int)
    : MARVAdapterDB<VDB>(layoutRes), ListRVAdapterCommonActions<E> {

    final override val _dataList: MutableList<E> = dataList.toMutableList()

    val dataList: List<E>
        get() = _dataList

    // ---- Overridden fun

    final override fun getItemCount(): Int
        = _dataList.size

}