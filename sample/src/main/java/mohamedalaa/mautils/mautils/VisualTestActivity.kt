package mohamedalaa.mautils.mautils

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_visual_test.*
import kotlinx.android.synthetic.main.my_rc_item.view.*
import mohamedalaa.mautils.core_android.dpToPx
import mohamedalaa.mautils.recycler_view.ListRecyclerViewAdapter
import mohamedalaa.mautils.recycler_view.MapRecyclerViewAdapter
import mohamedalaa.mautils.recycler_view.RCItemDecoration

class VisualTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visual_test)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemDecoration = RCItemDecoration(this,
            dividerColor = Color.RED,
            dividerDimenInPx = dpToPx(30),
            additionalOffsetInPx = dpToPx(90)
        )
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.itemAnimator = Hiiiii(itemDecoration)

        recyclerView.adapter = RCAdapterFakeNames(itemDecoration)
    }
}

class Hiiiii(val itemDecoration: RCItemDecoration) : DefaultItemAnimator() {
    override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
        super.onAnimationFinished(viewHolder)

    }

    override fun onRemoveFinished(item: RecyclerView.ViewHolder?) {
        super.onRemoveFinished(item)

        Log.e("HHHHHHH", "finished isa")

        itemDecoration.onRemoveFinished()
    }
}

val namesList = listOf("Mido", "Mohamed", "Mayar", "Alyaa", "Baba", "Mama", "Amr", "Selena")

class RCAdapterFakeNames(private val itemDecoration: RCItemDecoration)
    : ListRecyclerViewAdapter<String>(R.layout.my_rc_item, namesList.toMutableList()) {

    override fun onBindViewHolder(itemView: View, position: Int) {
        itemView.textView.text = dataList[position]

        itemView.textView.setOnClickListener {
            Log.e("ZZZ", "position -> $position")
            removeItem(position)
        }
    }

    private fun removeItem(position: Int) {
        itemDecoration.notifyItemRemoved(position)

        dataList.removeAt(position)
        notifyItemRemoved(position)

        notifyItemRangeChanged(0, itemCount, java.lang.Boolean.FALSE)
    }

}

@Suppress("unused")
private fun m3(adapter: MapRecyclerViewAdapter<String, Int>) {
    adapter.dataMap.clear()
}