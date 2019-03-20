package mohamedalaa.mautils.recycler_view.custom_classes.internal

import androidx.recyclerview.widget.RecyclerView

internal interface RVAdapterCommonActions {

    /**
     * Changes whole data by executing [changeAction] then calling [RecyclerView.Adapter.notifyDataSetChanged] afterwards isa.
     * */
    fun changeData(changeAction: () -> Unit) {
        changeAction()

        if (this is RecyclerView.Adapter<*>) {
            notifyDataSetChanged()
        }
    }

    /**
     * Uses [RecyclerView.Adapter.notifyItemRemoved] so animation can be done isa.
     *
     * @param removeAction fun that changes data which affects [RecyclerView.Adapter.getItemCount] isa.
     */
    fun removeItemAt(position: Int, removeAction: (Int) -> Unit) {
        removeAction(position)

        if (this is RecyclerView.Adapter<*>) {
            notifyItemRemoved(position)

            notifyItemRangeChanged(0, this.itemCount, java.lang.Boolean.FALSE)
        }
    }

    /**
     * call [insertAction] with [position] then call [RecyclerView.Adapter.notifyItemInserted] for animation isa.
     *
     * @param insertAction fun that changes data which affects [RecyclerView.Adapter.getItemCount] isa.
     */
    fun insertItemAt(position: Int, insertAction: (Int) -> Unit) {
        insertAction(position)

        if (this is RecyclerView.Adapter<*>) {
            notifyItemInserted(position)

            notifyItemRangeChanged(0, this.itemCount, java.lang.Boolean.FALSE)
        }
    }
}