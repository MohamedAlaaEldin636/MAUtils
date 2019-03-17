package mohamedalaa.mautils.recycler_view.new_test_1

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mohamedalaa.mautils.core_kotlin.applyIf
import mohamedalaa.mautils.recycler_view.new_test_1.extensions.*

/**
 * TODO (S)
 * 1- secondary constructor takes context and InDp instead of px isa.
 *
 * 2- onDraw isn't done yet, but planned to isa.
 *
 * Z- [LinearLayoutManager] not supported yet, but in future isa.
 *
 * @param singleItemDivider true means if [RecyclerView] has only 1 item, it will still draw
 * divider, and surely after considering [ignoreBorder] & [mergeOffsets] values isa.
 */
class MAItemDecoration(@ColorInt private var dividerColor: Int = Color.BLACK,
                       @Px private val dividerDimenInPx: Int = 0,
                       @Px private val additionalOffsetInPx: Int = 0,
                       private val dividerGravity: Int = Gravity.CENTER,
                       private val ignoreBorder: Boolean = true,
                       private val mergeOffsets: Boolean = true,
                       private val singleItemDivider: Boolean = false
) : RecyclerView.ItemDecoration() {

    // ---- Private / Internal properties

    internal val fullDimen = dividerDimenInPx.plus(additionalOffsetInPx)

    private val paint = Paint().apply {
        color = dividerColor
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    // ---- Overridden fun

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view).run { if (this == RecyclerView.NO_POSITION) 0 else this }
        val adapter = parent.adapter
        val layoutManager = parent.layoutManager

        val rect: Rect = when {
            adapter == null|| (singleItemDivider.not() && adapter.itemCount < 2) -> Rect()
            layoutManager is GridLayoutManager -> {
                val isHorizontal = layoutManager.orientation != LinearLayoutManager.VERTICAL
                when {
                    ignoreBorder && mergeOffsets -> if(isHorizontal) {
                        subItemOffsetIgnoreBorderMergeOffsetsHorizontal(layoutManager, position)
                    }else {
                        subItemOffsetIgnoreBorderMergeOffsetsVertical(layoutManager, position)
                    }
                    ignoreBorder -> if (isHorizontal) {
                        subItemOffsetIgnoreBorderNoMergeOffsetsHorizontal(layoutManager, position)
                    }else {
                        subItemOffsetIgnoreBorderNoMergeOffsetsVertical(layoutManager, position)
                    }
                    mergeOffsets -> if (isHorizontal) {
                        subItemOffsetNoIgnoreBorderMergeOffsetsHorizontal(layoutManager, position)
                    }else {
                        subItemOffsetNoIgnoreBorderMergeOffsetsVertical(layoutManager, position)
                    }
                    // Else both booleans are false isa.
                    else -> if (isHorizontal) {
                        subItemOffsetNoIgnoreBorderNoMergeOffsetsHorizontal(layoutManager, position)
                    }else {
                        subItemOffsetNoIgnoreBorderNoMergeOffsetsVertical(layoutManager, position)
                    }
                }
            }
            layoutManager is LinearLayoutManager -> {
                Rect() // todo linear layout manager isa.
            }
            else -> Rect()
        }

        outRect.set(rect)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager as? GridLayoutManager ?: return

        val firstVisible = layoutManager.findFirstVisibleItemPosition().apply { if (this == RecyclerView.NO_POSITION) return }
        val lastVisible = layoutManager.findLastVisibleItemPosition().apply { if (this == RecyclerView.NO_POSITION) return }

        for (position in firstVisible..lastVisible) {
            val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
            val bounds = Rect()
            layoutManager.getDecoratedBoundsWithMargins(child, bounds)
            Log.e("OnDrawCheck", "position -> $position, width -> ${child.width}, height -> ${child.height}" +
                "bounds -> $bounds --- ${Rect().apply { layoutManager.getDecoratedBoundsWithMargins(child, this) }}")
        }

        super.onDraw(c, parent, state)
    }

    // ---- Public fun

    fun swapItemDecoration(recyclerView: RecyclerView,
                           @ColorInt dividerColor: Int = this.dividerColor,
                           @Px dividerDimenInPx: Int = this.dividerDimenInPx,
                           @Px additionalOffsetInPx: Int = this.additionalOffsetInPx,
                           dividerGravity: Int = this.dividerGravity,
                           ignoreBorder: Boolean = this.ignoreBorder,
                           mergeOffsets: Boolean = this.mergeOffsets,
                           singleItemDivider: Boolean = this.singleItemDivider
    ): MAItemDecoration {
        recyclerView.removeItemDecoration(this)

        val newMAItemDecoration = MAItemDecoration(
            dividerColor,
            dividerDimenInPx,
            additionalOffsetInPx,
            dividerGravity,
            ignoreBorder,
            mergeOffsets,
            singleItemDivider
        )
        recyclerView.addItemDecoration(newMAItemDecoration)

        return newMAItemDecoration
    }

}