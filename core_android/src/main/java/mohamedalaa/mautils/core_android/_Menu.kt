@file:JvmName("MenuUtils")

package mohamedalaa.mautils.core_android

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.ColorInt
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed

/** @return index of [menuItem] in this `receiver` OR null if not found isa. */
fun Menu.indexOf(menuItem: MenuItem): Int? {
    forEachIndexed { index, item ->
        if (menuItem == item) {
            return index
        }
    }

    return null
}

/**
 * Changes icon tint by given [color] for all menu items in this `receiver` isa.
 *
 * @see MenuItem.setIconTint
 * @see setItemsTitleColor
 */
fun Menu.setItemsIconTint(@ColorInt color: Int) = forEach {
    it.setIconTint(color)
}

/**
 * Changes title color by given [color] for all menu items in this `receiver` isa.
 *
 * @see MenuItem.setTitleColor
 * @see setItemsIconTint
 */
fun Menu.setItemsTitleColor(@ColorInt color: Int) = forEach {
    it.setTitleColor(color)
}
