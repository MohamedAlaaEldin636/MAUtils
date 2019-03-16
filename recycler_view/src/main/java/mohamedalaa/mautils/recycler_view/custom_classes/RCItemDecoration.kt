package mohamedalaa.mautils.recycler_view.custom_classes

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.recyclerview.widget.*
import mohamedalaa.mautils.core_android.dpToPx
import mohamedalaa.mautils.recycler_view.extensions.internal.*
import mohamedalaa.mautils.recycler_view.extensions.isBorderBottom
import mohamedalaa.mautils.recycler_view.extensions.isBorderLeft
import mohamedalaa.mautils.recycler_view.extensions.isBorderRight
import mohamedalaa.mautils.recycler_view.extensions.isBorderTop
import androidx.recyclerview.widget.RecyclerView



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
// todo make copy fun == constructors isa + swap fun takes rc && own item decor as param isa. rc as weak reference isa.
// todo treat linear as grid with is border things isa, by that no need for isVertical param isa.
// todo ignore usage of divider param of drawable keep it color and dimen isa, and nullable drawable isa. , and no need of float constructor isa.
// todo by that i think notify item on deletion won't be needed so a test or tests are needed to recheck isa.
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

    internal val paint = Paint()

    internal val fullDimen: Int

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

        // Paint setup
        paint.color = dividerColor ?: Color.BLACK
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL

        fullDimen = dimenInPx.plus(additionalOffsetInPx)
    }

    // ---- Overridden fun

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        when(val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> {
                drawGrid(canvas, parent, layoutManager)
            }
            is LinearLayoutManager -> if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                drawVertical(canvas, parent)
            }else {
                drawHorizontal(canvas, parent)
            }
        }
    }

    private val mSizeGridSpacingPx: Int = context.dpToPx(20).toInt()
    private val mGridSize: Int = 5

    private var mNeedLeftSpacing = false

    /*override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val frameWidth =
            ((parent.width - mSizeGridSpacingPx.toFloat() * (mGridSize - 1)) / mGridSize).toInt()
        val padding = parent.width / mGridSize - frameWidth
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
        if (itemPosition < mGridSize) {
            outRect.top = 0
        } else {
            outRect.top = mSizeGridSpacingPx
        }
        if (itemPosition % mGridSize == 0) {
            outRect.left = 0
            outRect.right = padding
            mNeedLeftSpacing = true
        } else if ((itemPosition + 1) % mGridSize == 0) {
            mNeedLeftSpacing = false
            outRect.right = 0
            outRect.left = padding
        } else if (mNeedLeftSpacing) {
            mNeedLeftSpacing = false
            outRect.left = mSizeGridSpacingPx - padding
            if ((itemPosition + 2) % mGridSize == 0) {
                outRect.right = mSizeGridSpacingPx - padding
            } else {
                outRect.right = mSizeGridSpacingPx / 2
            }
        } else if ((itemPosition + 2) % mGridSize == 0) {
            mNeedLeftSpacing = false
            outRect.left = mSizeGridSpacingPx / 2
            outRect.right = mSizeGridSpacingPx - padding
        } else {
            mNeedLeftSpacing = false
            outRect.left = mSizeGridSpacingPx / 2
            outRect.right = mSizeGridSpacingPx / 2
        }
        outRect.bottom = 0
    }*/

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter
        val layoutManager = parent.layoutManager
        val fullDimen = dimenInPx.plus(additionalOffsetInPx)

        when {
            adapter == null || adapter.itemCount < 2 || position == RecyclerView.NO_POSITION -> outRect.setEmpty()
            layoutManager is GridLayoutManager -> {
                val top: Int
                val bottom: Int
                val left: Int
                val right: Int

                when(val isVertical = layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                    gridIgnoreBorder && gridMergeOffsets -> if (isVertical) {
                        //top = if (layoutManager.isBorderTop(position)) 0 else fullDimen
                        //right = if (layoutManager.isBorderRight(position)) 0 else fullDimen
                        //left = 0
                        //bottom = 0

                        val rect = subItemOffsetIgnoreBorderMergeOffsetsVertical2(layoutManager, position)
                        top = rect.top
                        right = rect.right
                        left = rect.left
                        bottom = rect.bottom
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
            layoutManager is LinearLayoutManager -> when {
                adapter.itemCount.dec() == position -> outRect.setEmpty()
                layoutManager.orientation == LinearLayoutManager.VERTICAL -> outRect.set(0, 0, 0, fullDimen)
                else -> outRect.set(0, 0, fullDimen, 0)
            }
        }
    }

    // ---- Private fun

    private fun drawGrid(canvas: Canvas, parent: RecyclerView, layoutManager: GridLayoutManager) {
        canvas.save()

        // Loop boundaries isa.
        val itemCount = parent.adapter?.itemCount ?: 0
        if (itemCount < 2) {
            return
        }
        val firstVisible = layoutManager.findFirstVisibleItemPosition().apply { if (isRCNoPosition()) return }
        val lastVisible = layoutManager.findLastVisibleItemPosition().apply { if (isRCNoPosition()) return }

        // Some Quick Calculations instead of putting them inside the loop isa.
        val isVertical = layoutManager.orientation == LinearLayoutManager.VERTICAL
        if (isVertical) {
            when {
                gridIgnoreBorder && gridMergeOffsets -> {
                    for (position in firstVisible..lastVisible) {
                        val child = parent.getChildAt(position.minus(firstVisible)) ?: continue
                        val bounds = Rect()
                        layoutManager.getDecoratedBoundsWithMargins(child, bounds)
                        Log.e("Check2", "position -> $position, width -> ${child.width}, height -> ${child.height}" +
                            "bounds -> $bounds --- ${Rect().apply { layoutManager.getDecoratedBoundsWithMargins(child, this) }}")
                    }
                    canvas.drawPaint(paint)
                    //subDrawGridIgnoreBorderAndMergeOffsetsVertical(canvas, parent, layoutManager, firstVisible, lastVisible)
                }
                gridIgnoreBorder -> subDrawGridIgnoreBorderAndNoMergeOffsets(canvas, parent, layoutManager, firstVisible, lastVisible)
                gridMergeOffsets -> subDrawGridNoIgnoreBorderAndMergeOffsetsVertical(canvas, parent, layoutManager, firstVisible, lastVisible)
                else -> subDrawGridNoIgnoreBorderAndNoMergeOffsets(canvas, parent, layoutManager, firstVisible, lastVisible)
            }
        }else {
            when {
                gridIgnoreBorder && gridMergeOffsets -> subDrawGridIgnoreBorderAndMergeOffsetsHorizontal(canvas, parent, layoutManager, firstVisible, lastVisible)
                gridIgnoreBorder -> subDrawGridIgnoreBorderAndNoMergeOffsets(canvas, parent, layoutManager, firstVisible, lastVisible)
                gridMergeOffsets -> subDrawGridNoIgnoreBorderAndMergeOffsetsHorizontal(canvas, parent, layoutManager, firstVisible, lastVisible)
                else -> subDrawGridNoIgnoreBorderAndNoMergeOffsets(canvas, parent, layoutManager, firstVisible, lastVisible)
            }
        }

        canvas.restore()
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

    private fun Int.isRCNoPosition(): Boolean = this == RecyclerView.NO_POSITION

    // ---- Public fun

    fun notifyItemRemoved() {
        ignoreDrawIndices = true
    }

    fun onRemoveFinished() {
        if (ignoreDrawIndices) {
            ignoreDrawIndices = false
        }
    }

    fun swapItemDecoration(recyclerView: RecyclerView,
                           context: Context,
                           dividerDrawable: Drawable? = this.dividerDrawable,
                           @ColorInt dividerColor: Int? = this.dividerColor,
                           @Px dividerDimenInPx: Int? = this.dividerDimenInPx,
                           @Px additionalOffsetInPx: Int = this.additionalOffsetInPx,
                           gravity: Int = this.gravity,
                           orientationIsVertical: Boolean = this.orientationIsVertical,
                           gridIgnoreBorder: Boolean = this.gridIgnoreBorder,
                           gridMergeOffsets: Boolean = this.gridMergeOffsets
    ): RCItemDecoration {
        recyclerView.removeItemDecoration(this)

        val newRCItemDecoration = RCItemDecoration(
            context,
            dividerDrawable,
            dividerColor,
            dividerDimenInPx,
            additionalOffsetInPx,
            gravity,
            orientationIsVertical,
            gridIgnoreBorder,
            gridMergeOffsets
        )
        recyclerView.addItemDecoration(newRCItemDecoration)

        return newRCItemDecoration
    }

}