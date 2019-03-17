package mohamedalaa.mautils.mautils

import android.graphics.Color
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import mohamedalaa.mautils.recycler_view.new_test_1.MAItemDecoration

class VisualTestActivity : AppCompatActivity() {

    private lateinit var rcAdapterFakeNames: RCAdapterFakeNames

    private lateinit var maItemDecoration: MAItemDecoration

    private var tempCounter = 1
        set(value) {
            field = if (value == 4) 0 else value
        }

    private val gridIgnoreBorder: Boolean
        get() = when(tempCounter) {
            0, 1 -> true
            else -> false
        }

    private val gridMergeOffsets: Boolean
        get() = when(tempCounter) {
            0, 2 -> true
            else -> false
        }

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
                    getString(R.string.screen_rotation) -> {
                        when (val layoutManager = recyclerView.layoutManager as? GridLayoutManager) {
                            is GridLayoutManager -> {
                                val isVertical = layoutManager.orientation == RecyclerView.VERTICAL

                                toast("gridOrientationIsVertical -> ${isVertical.not()}", duration = Toast.LENGTH_LONG)

                                Log.e("Before", "before orient changes isa -> ${isVertical.not()}")
                                layoutManager.orientation = if (isVertical) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
                                recyclerView.invalidateItemDecorations()
                            }
                            else -> {
                                // todo linear layout like grid one isa.
                                toast("(orientation) Not a grid layout manager")

                                return@post
                            }
                        }
                    }
                    getString(R.string.track_change) -> {
                        if (recyclerView.layoutManager as? GridLayoutManager == null) {
                            toast("Not a grid layout manager")

                            return@post
                        }else {
                            toast("gridIgnoreBorder -> $gridIgnoreBorder\ngridMergeOffsets -> $gridMergeOffsets", duration = Toast.LENGTH_LONG)
                        }

                        maItemDecoration = maItemDecoration.swapItemDecoration(
                            recyclerView,
                            ignoreBorder = gridIgnoreBorder,
                            mergeOffsets = gridMergeOffsets)
                        tempCounter++
                    }
                    getString(R.string.insert) -> {
                        rcAdapterFakeNames.insertItemAtForceAnim(index, "hello, there")
                    }
                    getString(R.string.remove) -> {
                        rcAdapterFakeNames.removeItemAtForceAnim(index)
                    }
                    getString(R.string.move) -> {
                        if (rcAdapterFakeNames.itemCount > 1) {
                            val fromIndex = 0
                            val toIndex = fromIndex.inc().run {
                                if (rcAdapterFakeNames.itemCount > 2) inc() else this
                            }

                            rcAdapterFakeNames.moveItemForceAnim(fromIndex, toIndex)
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
                        recyclerView.layoutManager = GridLayoutManager(this, 5/*, RecyclerView.HORIZONTAL, false*/)
                    }
                }
            }

            true
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        maItemDecoration = MAItemDecoration(
            dividerColor = Color.RED,
            dividerDimenInPx = dpToPx(0).toInt(),
            additionalOffsetInPx = dpToPx(16).toInt()
        )
        recyclerView.addItemDecoration(maItemDecoration)

        /*recyclerView.itemAnimator =
            RCDefaultItemAnimator(maItemDecoration)*/

        val namesList = List(60) { it.toString() }/*listOf("Mido", "Mohamed", "Mayar", "Alyaa", "Baba", "Mama", "Amr", "Selena")*/
        rcAdapterFakeNames = RCAdapterFakeNames(namesList, recyclerView)
        recyclerView.adapter = rcAdapterFakeNames
    }

}

class RCAdapterFakeNames(dataList: List<String>, recyclerView: RecyclerView)
    : ListRecyclerViewAdapter<String>(R.layout.my_rc_item_hz/*_hz todo */, dataList, recyclerView) {

    // called after orientation el7
    /*override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position).apply {
            Log.e("ViewType", "hi")
        }
    }*/

    // gela open for getLayout res even law property not fun isa.

    override fun onBindViewHolder(itemView: View, position: Int) {
        itemView.textView.text = dataList[position]

        //Rect() left top right bottom
        itemView.textView.setOnClickListener {
            removeItemAtForceAnim(position)
        }
    }

}