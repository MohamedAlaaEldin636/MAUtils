package mohamedalaa.mautils.recycler_view.extensions

import android.content.Context
import android.util.TypedValue

internal fun Context.dpToPx(dp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics)