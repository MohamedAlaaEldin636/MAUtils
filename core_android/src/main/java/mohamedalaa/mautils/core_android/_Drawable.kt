@file:JvmName("DrawableUtils")

package mohamedalaa.mautils.core_android

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

/**
 * Set tint for `receiver` drawable isa, and if `receiver` is null nothing will happen isa.
 *
 * @param mutate if you want to make drawable mutable before applying [Drawable.setColorFilter], default is false isa.
 */
@JvmOverloads
fun Drawable?.tint(@ColorInt color: Int, mutate: Boolean = false) {
    this?.apply {
        if (mutate) {
            this.mutate().colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        }else {
            this.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }
}