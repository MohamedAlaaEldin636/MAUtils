package mohamedalaa.mautils.recycler_view

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Same as [ListRecyclerViewAdapter] but instead of [ListRecyclerViewAdapter.dataList],
 * [dataMap] will be used to handle [RecyclerView.Adapter.getItemCount] isa.
 *
 *  todo continue like list isa.
 *
 * @see RecyclerViewAdapter
 */
abstract class MapRecyclerViewAdapter<K, V>(@LayoutRes private val layoutRes: Int,
                                            val dataMap: MutableMap<K, V>)
    : RecyclerViewAdapter(layoutRes) {

    override fun getItemCount(): Int
        = dataMap.size

}