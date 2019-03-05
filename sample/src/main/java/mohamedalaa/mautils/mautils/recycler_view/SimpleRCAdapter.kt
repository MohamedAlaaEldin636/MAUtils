package mohamedalaa.mautils.mautils.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.my_rc_item.view.*
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.recycler_view.ListRecyclerViewAdapter
import mohamedalaa.mautils.recycler_view.RecyclerViewAdapter

// More Concise Approach

class MyRecyclerViewAdapter(private val namesList: List<String>)
    : RecyclerViewAdapter(R.layout.my_rc_item) {

    override fun onBindViewHolder(itemView: View, position: Int) {
        // By Kotlin Android Extensions
        itemView.textView.text = namesList[position]
    }

    override fun getItemCount(): Int = namesList.size

}

// Old Approach

class MyRecyclerViewAdapter2(private val namesList: List<String>)
    : RecyclerView.Adapter<MyRecyclerViewAdapter2.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.my_rc_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // By Kotlin Android Extensions
        holder.itemView.textView.text = namesList[position]
    }

    override fun getItemCount(): Int = namesList.size


    // ----- View Holder

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

class MyRecyclerViewAdapter3(namesList: List<String>)
    : ListRecyclerViewAdapter<String>(R.layout.my_rc_item, namesList.toMutableList()) {

    override fun onBindViewHolder(itemView: View, position: Int) {
        // By Kotlin Android Extensions
        itemView.textView.text = dataList[position]
    }

}

private fun rcConsumer() {
    val myRecyclerViewAdapter = MyRecyclerViewAdapter3(listOf("name1", "name2"))

    val list = myRecyclerViewAdapter.dataList + "" + "saas"
    myRecyclerViewAdapter.dataList += listOf("")
}
