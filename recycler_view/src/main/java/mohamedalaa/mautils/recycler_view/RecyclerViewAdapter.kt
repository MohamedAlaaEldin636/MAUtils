package mohamedalaa.mautils.recycler_view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Quick build for Simple [RecyclerView.Adapter], see differences below isa
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
 * @see ListRecyclerViewAdapter
 * @see MapRecyclerViewAdapter
 */
abstract class RecyclerViewAdapter(@LayoutRes private val layoutRes: Int,
                                   private val rcItemDecoration: RCItemDecoration? = null)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

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

    /**
     * Takes care of [notifyItemRemoved] in case of using [rcItemDecoration],
     * else then [notifyDataSetChanged] is used instead isa.
     *
     * @param removeAction fun that changes data which affects [getItemCount] isa.
     */
    fun removeItemAt(position: Int, removeAction: (Int) -> Unit) {
        if (rcItemDecoration == null) {
            removeAction(position)

            notifyDataSetChanged()
        }else {
            rcItemDecoration.notifyItemRemoved(position)

            removeAction(position)
            notifyItemRemoved(position)

            notifyItemRangeChanged(0, itemCount, java.lang.Boolean.FALSE)
        }
    }

    // ----- View Holder

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}

/**
 * Same as [RecyclerView] but with additional handling to [RecyclerView.Adapter.getItemCount]
 * using [dataList] isa.
 *
 * **Example**
 * ```
 * class MyRecyclerViewAdapter3(namesList: List<String>)
 *      : ListRecyclerViewAdapter<String>(R.layout.my_rc_item, namesList) {
 *
 *      override fun onBindViewHolder(itemView: View, position: Int) {
 *          // By Kotlin Android Extensions
 *          itemView.textView.text = dataList[position]
 *      }
 *
 * }
 * ```
 *
 * @see MapRecyclerViewAdapter
 */
// todo check other methods i.e notify item Inserted / moved etc... isa.
abstract class ListRecyclerViewAdapter<E>(@LayoutRes private val layoutRes: Int,
                                          val dataList: MutableList<E>,
                                          private val rcItemDecoration: RCItemDecoration? = null,
                                          private val layoutManager: RecyclerView.LayoutManager? = null)
    : RecyclerViewAdapter(layoutRes) {

    override fun getItemCount(): Int
        = dataList.size

    fun removeItemAt(position: Int) {
        if (rcItemDecoration == null) {
            dataList.removeAt(position)

            notifyDataSetChanged()
        }else {
            val notifyRCItemDecoration = (layoutManager as? LinearLayoutManager)?.run {
                val firstVisible = findFirstVisibleItemPosition()
                val lastVisible = findLastVisibleItemPosition()

                Log.e("Adapter", "LINEAR LAYOUT MANAGER, $position, $firstVisible, $lastVisible")

                position in (firstVisible..lastVisible)
            } ?: false

            if (notifyRCItemDecoration) {
                rcItemDecoration.notifyItemRemoved(position)
            }

            dataList.removeAt(position)
            notifyItemRemoved(position)

            notifyItemRangeChanged(0, itemCount, java.lang.Boolean.FALSE)
        }
    }

    fun insertItemAt(position: Int, element: E) {
        if (rcItemDecoration == null) {
            dataList.add(position, element)

            notifyDataSetChanged()
        }else {
            //rcItemDecoration.notifyItemInserted() todo is this necessary ?!

            dataList.add(position, element)
            notifyItemInserted(position)

            notifyItemRangeChanged(0, itemCount, java.lang.Boolean.FALSE)
        }
    }

    // todo if swap how to do
    /*
    notifyItemMoved(fromPosition, toPosition)
    notifyItemMoved(toPosition, fromPosition)
     */

    // todo strange anim ?!
    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (rcItemDecoration == null) {
            dataList.move(fromPosition, toPosition)

            notifyDataSetChanged()
        }else {
            //rcItemDecoration.notifyItemInserted() todo is this necessary ?!

            dataList.move(fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)

            notifyItemRangeChanged(0, itemCount, java.lang.Boolean.FALSE)
        }
    }

}

/**
 * Moves element from index [fromIndex] to index [toIndex] isa.
 */
fun <E> MutableList<E>.move(fromIndex: Int, toIndex: Int) {
    val element = elementAt(fromIndex)

    add(toIndex, element)
    val indexToBeRemoved = if (fromIndex < toIndex) fromIndex else fromIndex.inc()
    removeAt(indexToBeRemoved)
}

/**
 * Swap element at [firstIndex] with element at [secondIndex] isa.
 */
fun <E> MutableList<E>.swap(firstIndex: Int, secondIndex: Int) {
    val firstElement = elementAt(firstIndex)
    val secondElement = elementAt(secondIndex)

    add(firstIndex, secondElement)
    removeAt(firstIndex.inc())

    add(secondIndex, firstElement)
    removeAt(secondIndex.inc())
}

/**
 * Same as [ListRecyclerViewAdapter] but instead of [ListRecyclerViewAdapter.dataList],
 * [dataMap] will be used to handle [RecyclerView.Adapter.getItemCount] isa.
 *
 * @see RecyclerViewAdapter
 */
abstract class MapRecyclerViewAdapter<K, V>(@LayoutRes private val layoutRes: Int,
                                            val dataMap: MutableMap<K, V>)
    : RecyclerViewAdapter(layoutRes) {

    override fun getItemCount(): Int
        = dataMap.size

}