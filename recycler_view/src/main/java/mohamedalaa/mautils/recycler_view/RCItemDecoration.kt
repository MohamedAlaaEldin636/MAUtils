package mohamedalaa.mautils.recycler_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * Same as [DividerItemDecoration], but without divider after last index isa.
 *
 * **Note**
 *
 * If [dividerDrawable] is null, then [dividerColor] and [dividerDimenInPx] must be not null isa,
 * however if that happens a fallback to 1 dp with [Color.BLACK] will be used isa.
 *
 * @param dividerDimenInPx if null then [Drawable.getIntrinsicHeight] or [Drawable.getIntrinsicWidth]
 * is used according to [orientationIsVertical], along with [dividerDrawable] isa.
 *
 * @param gravity Effective only when [additionalOffsetInPx] is > 0
 * so divider needs to have [Gravity] values isa.
 */
// todo zawed type of layout manager, linear walla grid isa, and only those ones are supported isa
// el staggered msh 3aref ezay for now isa.
// todo MUST HAVES
// 1- IN LAYOUT rc should not be wrap_content isa (last item will blink isa dunno why) ....
class RCItemDecoration(context: Context,
                       private val dividerDrawable: Drawable? = null,
                       @ColorInt private var dividerColor: Int? = null,
                       @Px private var dividerDimenInPx: Int? = null,
                       @Px private val additionalOffsetInPx: Int = 0,
                       private val gravity: Int = Gravity.CENTER,
                       private val orientationIsVertical: Boolean = true)
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

    private val ignoreDrawIndices = mutableListOf<Int>()

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
        when {
            parent.layoutManager == null -> Unit
            orientationIsVertical -> drawVertical(canvas, parent)
            else -> drawHorizontal(canvas, parent)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        // hide the divider for the last child
        parent.adapter.apply {
            when {
                (this != null && position == this.itemCount.dec()) -> outRect.setEmpty()
                orientationIsVertical -> outRect.set(0, 0, 0, dimenInPx.plus(additionalOffsetInPx))
                else -> outRect.set(0, 0, dimenInPx.plus(additionalOffsetInPx), 0)
            }
        }
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
        // todo
        val preLastIndex = parent.childCount.dec().dec()
        for (index in 0 until parent.childCount.dec()) {
            if (ignoreDrawIndices.isNotEmpty() && index == preLastIndex) {
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
        for (index in 0 until parent.childCount.dec()) {
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

    fun notifyItemRemoved(position: Int) {
        ignoreDrawIndices.add(position)
        // todo do we need invalidate isa ?!
    }

    fun onRemoveFinished() {
        ignoreDrawIndices.clear()
    }

}

private fun Context.dpToPx(dp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics)