@file:JvmName("MenuItemUtils")

package mohamedalaa.mautils.core_android

import android.graphics.PorterDuff
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import androidx.annotation.ColorInt

/** Changes menu item's icon tint color according to given [color] isa. */
fun MenuItem.setIconTint(@ColorInt color: Int) {
    val tempIcon = icon
    tempIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN)

    icon = tempIcon
}

/** Changes menu item's title color according to given [color] isa. */
fun MenuItem.setTitleColor(@ColorInt color: Int) {
    val spannableString = SpannableString(title)
    spannableString += ForegroundColorSpan(color)

    title = spannableString
}