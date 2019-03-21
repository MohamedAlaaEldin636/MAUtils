package mohamedalaa.mautils.material_design

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

// when fragment is used without UI ?!? todo
fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return

    val fragmentView = view ?: return

    val rootView = fragmentView.rootView
    imm.hideSoftInputFromWindow(rootView.windowToken, 0)
}

open class AA {
    open fun Int.hi() {

    }
}



fun abc1() {
    //todo rename to toastApp isa
}