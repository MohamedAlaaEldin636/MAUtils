package mohamedalaa.mautils.recycler_view.custom_classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * **Usage**
 *
 * - Quick build for Simple [RecyclerView.Adapter], see differences below isa
 * ```
 * // ==> More Concise Approach
 * class MyRecyclerViewAdapter(private val namesList: List<String>)
 *      : MARCAdapter() {
 *
 *      override fun getLayoutRes(): Int
 *          = if (layoutManager.orientation == LinearLayoutManager.VERTICAL) R.layout.my_rc_item else R.layout.my_rc_item_hz
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          itemView.setOnClickListener {
 *              removeItemAt(position)
 *          }
 *      }
 *
 *      override fun getItemCount(): Int = namesList.size
 *
 * }
 *
 * // ==> Note if you have 1 item type layout resource then add it
 * // in constructor instead (Alternative More Concise Approach)
 * class MyRecyclerViewAdapter(private val namesList: List<String>)
 *      : MARCAdapter(R.layout.my_rc_item) {
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          itemView.setOnClickListener {
 *              removeItemAt(position)
 *          }
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
 * @param layoutRes better use this instead of [getLayoutRes] if you have single item type isa.
 *
 * @see MAListRCAdapter
 */
abstract class MARCAdapter(@LayoutRes private val layoutRes: Int? = null) : RecyclerView.Adapter<MARCAdapter.ViewHolder>() {

    // ---- Abstract fun

    /**
     * @return layout resource for the item layout, Note this is tied to [getItemViewType]
     * so returning several layout resources will auto-change [getItemViewType] isa.
     */
    @LayoutRes
    open fun getLayoutRes(): Int = layoutRes ?: 0

    /**
     * Same as [onBindViewHolder] but provides [itemView] (Root view of item layout)
     * as param instead of [ViewHolder] for quick access to [ViewHolder.itemView] isa.
     *
     * **Note**
     *
     * Use [itemView] instead of [itemView].[View.getRootView] as second approach is
     * not logic plus makes errors sometimes isa.
     */
    abstract fun onBindViewHolder(itemView: View, position: Int)

    // ---- Overridden fun

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(getLayoutRes(), parent, false)

        return ViewHolder(view)
    }

    final override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = onBindViewHolder(holder.itemView, position)

    final override fun getItemViewType(position: Int): Int {
        return getLayoutRes()
    }

    // ---- Public fun

    /**
     * Changes whole data by executing [changeAction] then calling [notifyDataSetChanged] afterwards isa.
     * */
    fun changeData(changeAction: () -> Unit) {
        changeAction()

        notifyDataSetChanged()
    }

    /**
     * Uses [notifyItemRemoved] so animation can be done isa.
     *
     * @param removeAction fun that changes data which affects [getItemCount] isa.
     */
    fun removeItemAt(position: Int, removeAction: (Int) -> Unit) {
        removeAction(position)
        notifyItemRemoved(position)

        notifyItemRangeChanged(0, itemCount, java.lang.Boolean.FALSE)
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