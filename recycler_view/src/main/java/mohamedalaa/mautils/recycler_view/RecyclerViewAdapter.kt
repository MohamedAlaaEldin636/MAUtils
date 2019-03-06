package mohamedalaa.mautils.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.extensions.itemDecorations

/**
 * **Usage**
 *
 * - Quick build for Simple [RecyclerView.Adapter], see differences below isa
 * ```
 * // ==> More Concise Approach
 * class MyRecyclerViewAdapter(private val namesList: List<String>)
 *      : RecyclerViewAdapter(R.layout.my_rc_item) {
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          // By Kotlin Android Extensions
 *          itemView.textView.text = namesList[position]
 *      }
 *
 *      override fun getItemCount(): Int = namesList.size
 *
 * }
 *
 * // ==> Old Approach
 * class MyRecyclerViewAdapter2(private val namesList: List<String>)
 *      : RecyclerView.Adapter<MyRecyclerViewAdapter2.MyViewHolder>() {
 *
 *      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
 *          val inflater = LayoutInflater.from(parent.context)
 *
 *          val view = inflater.inflate(R.layout.my_rc_item, parent, false)
 *
 *          return MyViewHolder(view)
 *      }
 *
 *      override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
 *          // By Kotlin Android Extensions
 *          holder.itemView.textView.text = namesList[position]
 *      }
 *
 *      override fun getItemCount(): Int = namesList.size
 *
 *      // ----- View Holder
 *
 *      class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
 *
 * }
 * ```
 *
 * - Working with other classes for better common item animation approach
 *
 * if used with [RCItemDecoration] AND [RCDefaultItemAnimator]
 *
 * then it maintains proper [RecyclerView.ItemDecoration] drawing and offsets
 *
 * when using [RecyclerView.Adapter.notifyItemRemoved] isa,
 *
 * **But** Ensure instantiating this class after the given param recyclerView
 *
 * has been attached with [RCItemDecoration] AND [RCDefaultItemAnimator]
 *
 * @param layoutRes item layout to be inflate isa.
 * @param recyclerView recycler view instance to fetch [RCItemDecoration] AND [RCDefaultItemAnimator]
 * from it if exists isa.
 *
 * @see ListRecyclerViewAdapter
 */
abstract class RecyclerViewAdapter(@LayoutRes private val layoutRes: Int,
                                   recyclerView: RecyclerView? = null)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val rcItemDecoration: RCItemDecoration?
        = recyclerView?.itemDecorations?.firstOrNull { it is RCItemDecoration } as? RCItemDecoration

    private val layoutManager: RecyclerView.LayoutManager?
        = recyclerView?.layoutManager

    // ---- Overridden Methods ( Abstract )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(layoutRes, parent, false)

        return ViewHolder(view)
    }

    final override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = onBindViewHolder(holder.itemView, position)

    /**
     * Same as [onBindViewHolder] but provides [itemView] as param instead of [ViewHolder]
     * for quick access to [ViewHolder.itemView] isa.
     */
    abstract fun onBindViewHolder(itemView: View, position: Int)

    // ---- Public fun

    /** Changes whole data by executing [changeAction] then calling [notifyDataSetChanged] afterwards isa. */
    fun changeData(changeAction: () -> Unit) {
        changeAction()

        notifyDataSetChanged()
    }

    /**
     * Takes care of [notifyItemRemoved] in case of using [rcItemDecoration],
     * else then [notifyDataSetChanged] is used instead isa.
     *
     * @param removeAction fun that changes data which affects [getItemCount] isa.
     */
    fun removeItemAt(position: Int, removeAction: (Int) -> Unit) {
        if (rcItemDecoration == null || layoutManager !is LinearLayoutManager) {
            removeAction(position)

            notifyDataSetChanged()
        }else {
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            val lastVisible = layoutManager.findLastVisibleItemPosition()

            if (position in (firstVisible..lastVisible)) {
                rcItemDecoration.notifyItemRemoved()
            }

            removeAction(position)
            notifyItemRemoved(position)

            notifyItemRangeChanged(0, itemCount, java.lang.Boolean.FALSE)
        }
    }

    /**
     * call [insertAction] with [position] then call [notifyItemInserted] for animation isa.
     *
     * @param insertAction fun that changes data which affects [getItemCount] isa.
     */
    fun insertItemAt(position: Int, insertAction: (Int) -> Unit) {
        insertAction(position)
        notifyItemInserted(position)

        notifyItemRangeChanged(0, itemCount, java.lang.Boolean.FALSE)
    }

    // ----- View Holder

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}