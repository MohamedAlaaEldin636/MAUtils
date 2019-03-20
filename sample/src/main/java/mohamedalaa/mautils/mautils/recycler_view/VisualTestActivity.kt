package mohamedalaa.mautils.mautils.recycler_view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_visual_test.*
import mohamedalaa.mautils.core_android.dpToPx
import mohamedalaa.mautils.core_android.toast
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.recycler_view.custom_classes.MAItemDecoration

class VisualTestActivity : AppCompatActivity() {

    private lateinit var rcAdapterFakeNames: RVAdapterFakeNames

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
                        when (val layoutManager = recyclerView.layoutManager) {
                            is LinearLayoutManager -> {
                                val isVertical = layoutManager.orientation == RecyclerView.VERTICAL

                                toast("gridOrientationIsVertical -> ${isVertical.not()}", duration = Toast.LENGTH_LONG)

                                Log.e("Before", "before orient changes isa -> ${isVertical.not()}")
                                layoutManager.orientation = if (isVertical) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL
                                recyclerView.invalidateItemDecorations()
                            }
                        }
                    }
                    getString(R.string.track_change) -> {
                        /*if (recyclerView.layoutManager as? GridLayoutManager == null) {
                            toast("Not a grid layout manager")

                            return@post
                        }else {
                            toast("gridIgnoreBorder -> $gridIgnoreBorder\ngridMergeOffsets -> $gridMergeOffsets", duration = Toast.LENGTH_LONG)
                        }*/
                        toast("gridIgnoreBorder -> $gridIgnoreBorder\ngridMergeOffsets -> $gridMergeOffsets", duration = Toast.LENGTH_LONG)

                        maItemDecoration.ignoreBorder = gridIgnoreBorder
                        maItemDecoration.mergeOffsets = gridMergeOffsets
                        recyclerView.invalidateItemDecorations()
                        tempCounter++
                    }
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

                        maItemDecoration.dividerColor = Color.BLUE
                        recyclerView.invalidateItemDecorations()
                    }
                    getString(R.string.linear_layout_manager) -> {
                        val layoutManager = LinearLayoutManager(this)
                        recyclerView.layoutManager = layoutManager
                        rcAdapterFakeNames.layoutManager = layoutManager
                    }
                    getString(R.string.grid_layout_manager) -> {
                        val layoutManager = GridLayoutManager(this, 5)
                        recyclerView.layoutManager = layoutManager
                        rcAdapterFakeNames.layoutManager = layoutManager
                    }
                    getString(R.string.toggle_single_item) -> {
                        maItemDecoration.singleItemDivider = maItemDecoration.singleItemDivider.not().apply {
                            toast("Single item divider -> $this")
                        }
                        recyclerView.invalidateItemDecorations()
                    }
                }
            }

            true
        }
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        maItemDecoration = MAItemDecoration(
            dividerColor = Color.RED,
            dividerDimenInPx = dpToPx(16).toInt()
        )
        recyclerView.addItemDecoration(maItemDecoration)

        /*recyclerView.itemAnimator =
            RCDefaultItemAnimator(maItemDecoration)*/

        val namesList = List(60) { it.toString() }
        rcAdapterFakeNames = RVAdapterFakeNames(namesList, linearLayoutManager)
        recyclerView.adapter = rcAdapterFakeNames
    }

}
