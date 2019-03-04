@file:JvmName("FragmentUtils")

package mohamedalaa.mautils.mautils_view_model.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Same as [ViewModelProviders.of] `receiver` then [ViewModelProvider.get]<[VM]>
 * but is more simplified version of it isa.
 *
 * @see [FragmentActivity.getViewModel]
 */
inline fun <reified VM : ViewModel> Fragment.getViewModel()
    = ViewModelProviders.of(this).get(VM::class.java)