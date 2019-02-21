package mohamedalaa.mautils.mautils_open_source_licences.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Returns an existing ViewModel or creates a new one in the scope (usually, a fragment or
 * an activity) isa.
 *
 * @see ViewModelProvider.get
 */
inline fun <reified VM : ViewModel> FragmentActivity.getViewModel()
    = ViewModelProviders.of(this).get(VM::class.java)
