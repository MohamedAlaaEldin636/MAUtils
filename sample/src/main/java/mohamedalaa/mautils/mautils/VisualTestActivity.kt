package mohamedalaa.mautils.mautils

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_visual_test.*
import kotlinx.android.synthetic.main.my_rc_item.view.*
import mohamedalaa.mautils.core_android.dpToPx
import mohamedalaa.mautils.core_android.toast
import mohamedalaa.mautils.recycler_view.custom_classes.ListRecyclerViewAdapter
import mohamedalaa.mautils.recycler_view.custom_classes.RCDefaultItemAnimator
import mohamedalaa.mautils.recycler_view.custom_classes.RCItemDecoration

class VisualTestActivity : AppCompatActivity() {

    private lateinit var rcAdapterFakeNames: RCAdapterFakeNames

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visual_test)

        setupXml()
    }

    private fun setupXml() {
        setupToolbar()

        setupRecyclerView()
    }

    private fun setupToolbar() {
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
                            val toIndex = fromIndex.inc().run {
                                if (rcAdapterFakeNames.itemCount > 2) inc() else this
                            }

                            rcAdapterFakeNames.moveItem(fromIndex, toIndex)
                        }
                    }
                    getString(R.string.change_all_data) -> {
                        val list = rcAdapterFakeNames.dataList.toMutableList().apply {
                            clear()
                            add(0, "0")
                            add(1, "1")
                            add(2, "2")
                            add("pre last")
                            add("last")
                        }

                        rcAdapterFakeNames.changeData(list)
                    }
                    getString(R.string.linear_layout_manager) -> {
                        recyclerView.layoutManager = LinearLayoutManager(this)
                    }
                    getString(R.string.grid_layout_manager) -> {
                        recyclerView.layoutManager = GridLayoutManager(this, 2)
                    }
                }
            }

            true
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemDecoration = RCItemDecoration(
            this,
            dividerColor = Color.RED,
            dividerDimenInPx = dpToPx(0),
            additionalOffsetInPx = dpToPx(16)
        )
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.itemAnimator =
            RCDefaultItemAnimator(itemDecoration)

        val namesList = List(20) { it.toString() }/*listOf("Mido", "Mohamed", "Mayar", "Alyaa", "Baba", "Mama", "Amr", "Selena")*/
        rcAdapterFakeNames = RCAdapterFakeNames(namesList, recyclerView)
        recyclerView.adapter = rcAdapterFakeNames
    }

}

class RCAdapterFakeNames(dataList: List<String>, recyclerView: RecyclerView)
    : ListRecyclerViewAdapter<String>(R.layout.my_rc_item, dataList, recyclerView) {

    override fun onBindViewHolder(itemView: View, position: Int) {
        itemView.textView.text = dataList[position]

        itemView.textView.setOnClickListener {
            removeItemAt(position)
        }
    }

}