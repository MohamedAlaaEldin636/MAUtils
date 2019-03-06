package mohamedalaa.mautils.recycler_view

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Same as [RecyclerViewAdapter] but facilitates methods in it and even add more,
 *
 * 1. [RecyclerViewAdapter.removeItemAt] is replaced with [removeItemAt] using [dataList],
 * as well as replacing [RecyclerViewAdapter.insertItemAt], [RecyclerViewAdapter.changeData].
 * 2. auto handle for [getItemCount] using [dataList]
 * 3. Adds [moveItem] fun which internally remove then insert using corresponding fun isa.
 *
 * **Example of extending this class**
 * ```
 * class MyRecyclerViewAdapter(namesList: List<String>)
 *      : ListRecyclerViewAdapter<String>(R.layout.my_rc_item, namesList) {
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          // By Kotlin Android Extensions
 *          itemView.textView.text = dataList[position]
 *      }
 *
 * }
 *
 * // Then to call insert, remove, move, or change whole data it is easy
 * // without coding any line of code.
 * myRecyclerViewAdapter.removeItemAt(index)
 * ```
 *
 * @see MapRecyclerViewAdapter
 */
abstract class ListRecyclerViewAdapter<E>(@LayoutRes private val layoutRes: Int,
                                          dataList: List<E>,
                                          rcItemDecoration: RCItemDecoration? = null,
                                          layoutManager: RecyclerView.LayoutManager? = null)
    : RecyclerViewAdapter(layoutRes, rcItemDecoration, layoutManager) {

    private val _dataList: MutableList<E> = dataList.toMutableList()

    val dataList: List<E>
        get() = _dataList

    // ---- Overridden Methods ( Abstract )

    final override fun getItemCount(): Int
        = _dataList.size

    // ---- Public fun

    /** Same as [RecyclerViewAdapter.changeData] */
    fun changeData(dataList: List<E>) = super.changeData {
        this._dataList.clear()
        this._dataList.addAll(dataList)
    }

    /** Same as [RecyclerViewAdapter.removeItemAt] */
    fun removeItemAt(position: Int) = super.removeItemAt(position) {
        _dataList.removeAt(position)
    }

    /** Same as [RecyclerViewAdapter.insertItemAt] */
    fun insertItemAt(position: Int, element: E) = super.insertItemAt(position) {
        _dataList.add(position, element)
    }

    /**
     * Moves an item from [fromPosition] to [toPosition] using [removeItemAt] then [insertItemAt]
     *
     * @see [changeData]
     */
    fun moveItem(fromPosition: Int, toPosition: Int) {
        val element = _dataList.elementAt(fromPosition)

        removeItemAt(fromPosition)
        insertItemAt(toPosition, element)
    }

}