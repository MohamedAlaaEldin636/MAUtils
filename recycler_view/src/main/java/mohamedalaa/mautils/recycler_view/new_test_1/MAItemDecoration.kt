package mohamedalaa.mautils.recycler_view.new_test_1

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import mohamedalaa.mautils.core_android.dpToPx
import mohamedalaa.mautils.recycler_view.extensions.isBorderBottom
import mohamedalaa.mautils.recycler_view.extensions.isBorderLeft
import mohamedalaa.mautils.recycler_view.extensions.isBorderRight
import mohamedalaa.mautils.recycler_view.extensions.isBorderTop
import mohamedalaa.mautils.recycler_view.new_test_1.extensions.subItemOffsetIgnoreBorderMergeOffsetsVertical

/**
 * TODO (S)
 * 1- secondary constructor takes context and InDp instead of px isa.
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
                       private val singleItemDivider: Boolean = false)
    : RecyclerView.ItemDecoration() {

    // ---- Private / Internal properties

    internal val fullDimen = dividerDimenInPx.plus(additionalOffsetInPx)

    private val paint = Paint().apply {
        color = dividerColor
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    // ---- Overridden fun

    /*override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter
        val layoutManager = parent.layoutManager

        when {
            adapter == null || position == RecyclerView.NO_POSITION || (singleItemDivider.not() && adapter.itemCount < 2) -> outRect.setEmpty()
            layoutManager is GridLayoutManager -> {
                val top: Int
                val bottom: Int
                val left: Int
                val right: Int

                if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                    when { // todo use ext fun isa.
                        ignoreBorder && mergeOffsets -> {
                            val rectt = subItemOffsetIgnoreBorderMergeOffsetsVertical()

                            top = if (layoutManager.isBorderTop(position)) 0 else fullDimen
                            right = if (layoutManager.isBorderRight(position)) 0 else fullDimen
                            left = 0
                            bottom = 0
                        }
                    }
                }else {

                }

                when(val isVertical = layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                    gridIgnoreBorder && gridMergeOffsets -> if (isVertical) {
                        top = if (layoutManager.isBorderTop(position)) 0 else fullDimen
                        right = if (layoutManager.isBorderRight(position)) 0 else fullDimen
                        left = 0
                        bottom = 0
                    }else {
                        top = if (layoutManager.isBorderTop(position)) 0 else fullDimen
                        right = 0
                        left = if (layoutManager.isBorderLeft(position)) 0 else fullDimen
                        bottom = 0
                    }
                    gridIgnoreBorder -> {
                        top = if (layoutManager.isBorderTop(position)) 0 else fullDimen
                        right = if (layoutManager.isBorderRight(position)) 0 else fullDimen
                        left = if (layoutManager.isBorderLeft(position)) 0 else fullDimen
                        bottom = if (layoutManager.isBorderBottom(position)) 0 else fullDimen
                    }
                    gridMergeOffsets -> if (isVertical) {
                        top = fullDimen
                        right = fullDimen
                        left = if (layoutManager.isBorderLeft(position)) fullDimen else 0
                        bottom = if (layoutManager.isBorderBottom(position)) fullDimen else 0
                    }else {
                        top = fullDimen
                        right = if (layoutManager.isBorderRight(position)) fullDimen else 0
                        left = fullDimen
                        bottom = if (layoutManager.isBorderBottom(position)) fullDimen else 0
                    }
                    // Else both booleans are false isa.
                    else -> {
                        top = fullDimen
                        right = fullDimen
                        left = fullDimen
                        bottom = fullDimen
                    }
                }

                outRect.set(left, top, right, bottom)
            }
        }
    }*/

}