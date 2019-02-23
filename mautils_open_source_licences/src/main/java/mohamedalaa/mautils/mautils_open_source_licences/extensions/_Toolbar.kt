package mohamedalaa.mautils.mautils_open_source_licences.extensions

import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import androidx.core.text.toSpannable
import androidx.core.view.forEach
import mohamedalaa.mautils.core_android.plusAssign
import mohamedalaa.mautils.core_android.tint

fun Toolbar.setIconsTint(@ColorInt color: Int, changeTitleTextColor: Boolean = false) {
    menu.forEach {
        it.icon.tint(color)

        if (changeTitleTextColor) {
            it.title = it.title.toSpannable().apply { this += ForegroundColorSpan(color) }
        }
    }
    overflowIcon.tint(color)
    navigationIcon.tint(color)
}