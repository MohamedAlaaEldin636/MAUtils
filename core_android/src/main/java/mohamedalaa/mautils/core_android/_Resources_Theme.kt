@file:JvmName("Resources_ThemeUtils")

package mohamedalaa.mautils.core_android

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

@ColorInt
fun Resources.Theme.getColorFromAttrRes(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    resolveAttribute(attrRes, typedValue, true)
    return typedValue.data
}
