package mohamedalaa.mautils.mautils

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/30/2019.
 *
 */
object Bindinggg {

    @JvmStatic
    @BindingAdapter("app:setTranslateBgd")
    fun translateBgd(view: View, integer: Int) {
        view.post {
            view.translationY = 600f
        }
    }

}