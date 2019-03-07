package mohamedalaa.mautils.recycler_view.custom_classes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.*
import mohamedalaa.mautils.recycler_view.extensions.internal.dpToPx
import mohamedalaa.mautils.recycler_view.extensions.isBorderRight
import mohamedalaa.mautils.recycler_view.extensions.isBorderTop

/**
 * Same as [DividerItemDecoration], but without divider after last index isa,
 *
 * Also if used with [RecyclerViewAdapter] AND [RCDefaultItemAnimator]
 *
 * then it maintains proper [RecyclerView.ItemDecoration] drawing and offsets
 *
 * when using [RecyclerView.Adapter.notifyItemRemoved] isa.
 *
 * **Notes**
 *
 * 1- If [dividerDrawable] is null, then [dividerColor] and [dividerDimenInPx] must be not null isa,
 *
 * however if that happens a fallback to 1 dp with [Color.BLACK] will be used isa.
 *
 * @param dividerDimenInPx if null then [Drawable.getIntrinsicHeight] or [Drawable.getIntrinsicWidth]
 * is used according to [orientationIsVertical], along with [dividerDrawable] isa.
 *
 * @param gravity Effective only when [additionalOffsetInPx] is > 0
 * so divider needs to have [Gravity] values isa.
 *
 * @param gridIgnoreBorder true means do not put offsets between items and borders isa.
 *
 * @param gridMergeOffsets true means keep equal offset between items, else right border
 * of first item will be added to left border of second item isa.
 */
// todo zawed type of layout manager, linear walla grid isa, and only those ones are supported isa
// el staggered msh 3aref ezay for now isa.
// todo if done maybe make quick classes class kaza for quicker grid isa.
// todo secondary qquick constructors ae better isa.
// todo MUST HAVES
// 1- IN LAYOUT rc should not be wrap_content isa (last item will blink isa dunno why) ....
class RCItemDecoration(context: Context,
                       private val dividerDrawable: Drawable? = null,
                       @ColorInt private var dividerColor: Int? = null,
                       @Px private var dividerDimenInPx: Int? = null,
                       @Px private val additionalOffsetInPx: Int = 0,
                       private val gravity: Int = Gravity.CENTER,
                       private val orientationIsVertical: Boolean = true,
                       private val gridIgnoreBorder: Boolean = true,
                       private val gridMergeOffsets: Boolean = true)
    : RecyclerView.ItemDecoration() {

    constructor(context: Context,
                dividerDrawable: Drawable? = null,
                @ColorInt dividerColor: Int? = null,
                @Px dividerDimenInPx: Float? = null,
                @Px additionalOffsetInPx: Float = 0f,
                gravity: Int = Gravity.CENTER,
                orientationIsVertical: Boolean = true)
        : this(context, dividerDrawable, dividerColor, dividerDimenInPx?.toInt(), additionalOffsetInPx.toInt(), gravity, orientationIsVertical)

    private val dimenInPx: Int

    private val colorDrawable = dividerColor?.run { ColorDrawable(this) }

    private var ignoreDrawIndices = false

    init {
        if (dividerDrawable == null && dividerColor == null) {
            dividerColor = Color.BLACK

            if (dividerDimenInPx == null) {
                dividerDimenInPx = context.dpToPx(1).toInt()
            }
        }

        dimenInPx = if (dividerDrawable != null) {
            if (orientationIsVertical) dividerDrawable.intrinsicHeight else dividerDrawable.intrinsicWidth
        }else {
            dividerDimenInPx ?: context.dpToPx(1).toInt()
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        when(val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> {
                drawGrid(canvas, parent)
            }
            is LinearLayoutManager -> if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                drawVertical(canvas, parent)
            }else {
                drawHorizontal(canvas, parent)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter
        val layoutManager = parent.layoutManager
        val fullDimen = dimenInPx.plus(additionalOffsetInPx)
        when {
            adapter == null || position == RecyclerView.NO_POSITION -> outRect.setEmpty()
            layoutManager is GridLayoutManager -> if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                // Vertical so top && right isa.
                // todo here we apply ignore border true && merge true , so make the other cases later isa.

                val top = if (layoutManager.isBorderTop(position)) 0 else fullDimen
                val right = if (layoutManager.isBorderRight(position)) 0 else fullDimen

                outRect.set(0, top, right, 0)
            }else {
                // todo note reverse layout not taken in consideration here yet isa.
                // Horizontal so top && right isa.
                // todo
            }
            layoutManager is LinearLayoutManager -> when {
                adapter.itemCount.dec() == position -> outRect.setEmpty()
                layoutManager.orientation == LinearLayoutManager.VERTICAL -> outRect.set(0, 0, 0, fullDimen)
                else -> outRect.set(0, 0, fullDimen, 0)
            }
        }
    }

    private fun drawGrid(canvas: Canvas, parent: RecyclerView) {
        // todo drawGrid
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        val (left, right) = if (parent.clipToPadding) {
            val tempLeft = parent.paddingLeft
            val tempRight = parent.width - parent.paddingRight
            canvas.clipRect(tempLeft, parent.paddingTop, tempRight, parent.height - parent.paddingBottom)

            tempLeft to tempRight
        }else {
            0 to parent.width
        }

        val bounds = Rect()
        val itemCount = parent.adapter?.itemCount ?: return
        val lastVisible = (parent.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: return

        val untilIndex = if (lastVisible == itemCount.dec()) {
            parent.childCount.dec()
        }else {
            parent.childCount
        }

        val preLastIndex = untilIndex.dec()
        for (index in 0 until untilIndex) {
            if (ignoreDrawIndices && index == preLastIndex) {
                continue
            }

            val child = parent.getChildAt(index)

            parent.layoutManager?.getDecoratedBoundsWithMargins(child, bounds)

            var bottom = bounds.bottom + Math.round(child.translationY) // All the way bottom
            bottom -= when(gravity) {
                Gravity.TOP -> additionalOffsetInPx
                Gravity.CENTER, Gravity.CENTER_VERTICAL -> additionalOffsetInPx.div(2)
                else -> 0
            }
            val top = bottom - dimenInPx

            dividerDrawable?.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            } ?: colorDrawable?.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }

        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()

        val (top, bottom) = if (parent.clipToPadding) {
            val tempTop = parent.paddingTop
            val tempBottom = parent.height - parent.paddingBottom
            canvas.clipRect(parent.paddingLeft, tempTop, parent.width - parent.paddingRight, tempBottom)

            tempTop to tempBottom
        }else {
            0 to parent.height
        }

        val bounds = Rect()
        val itemCount = parent.adapter?.itemCount ?: return
        val lastVisible = (parent.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: return

        val untilIndex = if (lastVisible == itemCount.dec()) {
            parent.childCount.dec()
        }else {
            parent.childCount
        }

        val preLastIndex = untilIndex.dec()
        for (index in 0 until untilIndex) {
            if (ignoreDrawIndices && index == preLastIndex) {
                continue
            }

            val child = parent.getChildAt(index)

            parent.layoutManager?.getDecoratedBoundsWithMargins(child, bounds)

            var right = bounds.right + Math.round(child.translationX) // All the way right
            right -= when(gravity) {
                Gravity.START -> additionalOffsetInPx
                Gravity.CENTER, Gravity.CENTER_HORIZONTAL -> additionalOffsetInPx.div(2)
                else -> 0
            }
            val left = right - dimenInPx

            dividerDrawable?.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            } ?: colorDrawable?.apply {
                setBounds(left, top, right, bottom)
                draw(canvas)
            }
        }

        canvas.restore()
    }

    fun notifyItemRemoved() {
        ignoreDrawIndices = true
    }

    fun onRemoveFinished() {
        if (ignoreDrawIndices) {
            ignoreDrawIndices = false
        }
    }

}