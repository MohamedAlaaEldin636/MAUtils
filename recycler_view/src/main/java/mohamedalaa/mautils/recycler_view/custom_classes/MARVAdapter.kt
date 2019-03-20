package mohamedalaa.mautils.recycler_view.custom_classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.recycler_view.custom_classes.internal.MABaseRVAdapter

/**
 * **Usage**
 *
 * - Quick build for Simple [RecyclerView.Adapter], see differences below isa
 * ```
 * // ==> More Concise Approach
 * class MyRecyclerViewAdapter(private val namesList: List<String>)
 *      : MARVAdapter() {
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
 *      : MARVAdapter(R.layout.my_rc_item) {
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
 * - [getLayoutRes] is tied to [RecyclerView.Adapter.getItemViewType] so you don't need to worry
 * about item types, just make your condition checking in [getLayoutRes] and it will auto
 * generate item view types isa.
 *
 * @param layoutRes better use this instead of [getLayoutRes] if you have single item type isa.
 *
 * @see MAListRVAdapter
 */
abstract class MARVAdapter(@LayoutRes layoutRes: Int? = null)
    : MABaseRVAdapter(layoutRes) {

    // ---- Abstract fun

    /**
     * Same as [onBindViewHolder] but provides [itemView] (Root view of item layout)
     * as param instead of [MABaseRVAdapter.ViewHolder] for quick access to [MABaseRVAdapter.ViewHolder.itemView] isa.
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

    final override fun internalOnBindViewHolder(holder: ViewHolder, position: Int)
        = onBindViewHolder(holder.itemView, position)

}