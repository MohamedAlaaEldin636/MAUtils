package mohamedalaa.mautils.mautils.del_later_for_readme

import android.animation.AnimatorSet
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import mohamedalaa.mautils.core_android.addListenerMA
import mohamedalaa.mautils.core_android.hideKeyboardFrom
import mohamedalaa.mautils.core_android.inflateLayout
import mohamedalaa.mautils.core_android.showKeyboardFor
import mohamedalaa.mautils.mautils.R

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 3/20/2019.
 *
 */
class KTrialActivity : AppCompatActivity() {

    private val context = this
    private lateinit var editText: EditText

    fun f1() {
        // Hide keyboard
        context.hideKeyboardFrom(editText/*, false optional clearFocus param*/)

        // Force Show keyboard
        context.showKeyboardFor(editText/*, false optional requestFocus param*/)
    }

    private lateinit var animatorSet: AnimatorSet

    private fun f2() {
        animatorSet.addListenerMA {
            onAnimationStart {
                // Code for on anim start
            } onAnimationEnd {
                // Code for on anim end
            } onAnimationCancel {
                // Code for on anim cancel
            }
            // Don't need -> onAnimationRepeat { /* Do nothing */ }
        }

        // Quick inflation of a layout.xml
        //val rootViewOfLayout = inflateLayout(R.layout.layout/*, parent optional viewGroup, false optional attachToParent*/)
    }

}