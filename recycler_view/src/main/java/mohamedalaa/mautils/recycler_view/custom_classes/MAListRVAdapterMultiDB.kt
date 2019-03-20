package mohamedalaa.mautils.recycler_view.custom_classes

import androidx.databinding.ViewDataBinding
import mohamedalaa.mautils.recycler_view.custom_classes.internal.ListRVAdapterCommonActions

/**
 * Same as [MAListRVAdapter] but provides [ViewDataBinding] instead of root view of item layout in [onBindViewHolder],
 * for more clarification See [MARVAdapterMultiDB].
 */
abstract class MAListRVAdapterMultiDB<E>(dataList: List<E>)
    : MARVAdapterMultiDB(), ListRVAdapterCommonActions<E> {

    final override val _dataList: MutableList<E> = dataList.toMutableList()

    val dataList: List<E>
        get() = _dataList

    // ---- Overridden fun

    final override fun getItemCount(): Int
        = _dataList.size

}