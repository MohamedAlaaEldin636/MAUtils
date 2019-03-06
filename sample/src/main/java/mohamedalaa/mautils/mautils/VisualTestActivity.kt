package mohamedalaa.mautils.mautils

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_visual_test.*
import kotlinx.android.synthetic.main.my_rc_item.view.*
import mohamedalaa.mautils.core_android.dpToPx
import mohamedalaa.mautils.recycler_view.ListRecyclerViewAdapter
import mohamedalaa.mautils.recycler_view.MapRecyclerViewAdapter
import mohamedalaa.mautils.recycler_view.RCItemDecoration

class VisualTestActivity : AppCompatActivity() {

    private lateinit var rcAdapterFakeNames: RCAdapterFakeNames

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visual_test)

        setupXml()

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val itemDecoration = RCItemDecoration(this,
            dividerColor = Color.RED,
            dividerDimenInPx = dpToPx(30),
            additionalOffsetInPx = dpToPx(90)
        )
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.itemAnimator = RCDefaultItemAnimator(recyclerView, itemDecoration)

        rcAdapterFakeNames = RCAdapterFakeNames(itemDecoration, linearLayoutManager)
        recyclerView.adapter = rcAdapterFakeNames
    }

    private fun setupXml() {
        toolbar.inflateMenu(R.menu.visual_test_activity)
        toolbar.setOnMenuItemClickListener {
            val index = if (rcAdapterFakeNames.itemCount > 2) 1 else 0

            Handler().post {
                when(it.title.toString()) {
                    getString(R.string.insert) -> {
                        rcAdapterFakeNames.insertItemAt(index, "hello, there")
                    }
                    getString(R.string.remove) -> {
                        rcAdapterFakeNames.removeItemAt(index)
                    }
                    getString(R.string.move) -> {
                        if (rcAdapterFakeNames.itemCount > 1) {
                            val fromIndex = 0
                            val toIndex = fromIndex.inc()

                            rcAdapterFakeNames.moveItem(fromIndex, toIndex)
                        }
                    }
                }
            }

            true
        }
    }

}

class RCDefaultItemAnimator(private val recyclerView: RecyclerView,
                            private val itemDecoration: RCItemDecoration)
    : DefaultItemAnimator() {

    //private var triggeredOnce = false

    override fun onRemoveFinished(item: RecyclerView.ViewHolder?) {
        // todo logs isa.
        Log.e("RCDef", "onRemoveFinished")

        super.onRemoveFinished(item)

        itemDecoration.onRemoveFinished()

        //triggeredOnce = false
    }

    // todo if not visible then do not do it isa.
    /*override fun onAnimationFinished(viewHolder: RecyclerView.ViewHolder) {
        Log.e("RCDef", "on anim finish")

        Handler().post { Log.e("RCDef", "HANDLER on anim finish") }

        if (triggeredOnce) {
            itemDecoration.onRemoveFinished()
        }
        triggeredOnce = triggeredOnce.not()

        super.onAnimationFinished(viewHolder)
    }*/

}

val namesList = listOf("Mido", "Mohamed", "Mayar", "Alyaa", "Baba", "Mama", "Amr", "Selena")

class RCAdapterFakeNames(rcItemDecoration: RCItemDecoration,
                         layoutManager: RecyclerView.LayoutManager)
    : ListRecyclerViewAdapter<String>(R.layout.my_rc_item, namesList.toMutableList(), rcItemDecoration, layoutManager) {

    override fun onBindViewHolder(itemView: View, position: Int) {
        itemView.textView.text = dataList[position]

        itemView.textView.setOnClickListener {
            removeItemAt(position)
        }
    }

}

@Suppress("unused")
private fun m3(adapter: MapRecyclerViewAdapter<String, Int>) {
    adapter.dataMap.clear()
}