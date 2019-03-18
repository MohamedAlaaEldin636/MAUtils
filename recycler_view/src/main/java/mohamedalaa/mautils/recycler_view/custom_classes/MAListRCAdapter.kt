package mohamedalaa.mautils.recycler_view.custom_classes

/**
 * Same as [MARCAdapter] but facilitates methods in it and even add more,
 * 1. [MARCAdapter.removeItemAt] is replaced with [removeItemAt] using [dataList],
 * as well as replacing [MARCAdapter.insertItemAt], [MARCAdapter.changeData].
 * 2. auto handle for [getItemCount] using [dataList]
 * 3. Adds [moveItem] fun which internally remove then insert using corresponding fun isa.
 *
 * **Example of extending this class**
 * ```
 * class MyRecyclerViewAdapter(namesList: List<String>)
 *      : MAListRCAdapter<String>(R.layout.my_rc_item, namesList) {
 *
 *      override fun getLayoutRes(): Int
 *          = if (layoutManager.orientation == LinearLayoutManager.VERTICAL) R.layout.my_rc_item else R.layout.my_rc_item_hz
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          itemView.rootView.setOnClickListener {
 *              removeItemAt(position)
 *          }
 *      }
 *
 * }
 *
 * // Then to call insert, remove, move, or change whole data it is easy
 * // without coding any line of code.
 * myRecyclerViewAdapter.removeItemAt(index)
 * ```
 */
abstract class MAListRCAdapter<E>(dataList: List<E>)
    : MARCAdapter() {

    private val _dataList: MutableList<E> = dataList.toMutableList()

    val dataList: List<E>
        get() = _dataList

    // ---- Overridden fun

    final override fun getItemCount(): Int
        = _dataList.size

    // ---- Public fun

    /** Same as [MARCAdapter.changeData] isa. */
    fun changeData(dataList: List<E>) = super.changeData {
        this._dataList.clear()
        this._dataList.addAll(dataList)
    }

    /** Same as [MARCAdapter.removeItemAt] */
    fun removeItemAt(position: Int) = super.removeItemAt(position) {
        _dataList.removeAt(position)
    }

    /** Same as [MARCAdapter.insertItemAt] */
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