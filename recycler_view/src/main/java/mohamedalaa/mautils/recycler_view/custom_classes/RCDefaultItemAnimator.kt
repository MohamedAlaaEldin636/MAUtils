package mohamedalaa.mautils.recycler_view.custom_classes

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

/**
 * Same as [DefaultItemAnimator] but if used with [RecyclerViewAdapter] AND [RCItemDecoration]
 *
 * then it maintains proper [RecyclerView.ItemDecoration] drawing and offsets
 *
 * when using [RecyclerView.Adapter.notifyItemRemoved] isa.
 */
class RCDefaultItemAnimator(private val itemDecoration: RCItemDecoration)
    : DefaultItemAnimator() {

    override fun onRemoveFinished(item: RecyclerView.ViewHolder?) {
        super.onRemoveFinished(item)

        itemDecoration.onRemoveFinished()
    }

}