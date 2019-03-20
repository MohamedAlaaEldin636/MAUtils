package mohamedalaa.mautils.recycler_view.custom_classes.internal

import mohamedalaa.mautils.recycler_view.custom_classes.MARVAdapter

@Suppress("PropertyName")
internal interface ListRVAdapterCommonActions<E> : RVAdapterCommonActions {

    val _dataList: MutableList<E>

    // ---- Public fun

    /** Same as [MARVAdapter.changeData] isa. */
    fun changeData(dataList: List<E>) = super.changeData {
        this._dataList.clear()
        this._dataList.addAll(dataList)
    }

    /** Same as [MARVAdapter.removeItemAt] */
    fun removeItemAt(position: Int) = super.removeItemAt(position) {
        _dataList.removeAt(position)
    }

    /** Same as [MARVAdapter.insertItemAt] */
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