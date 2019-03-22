@file:JvmName("DataBindingUtils")

package mohamedalaa.mautils.core_android

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <VDB : ViewDataBinding> View.findBindingOrNull(): VDB?
    = DataBindingUtil.findBinding(this)

fun <VDB : ViewDataBinding> View.findBinding(): VDB
    = findBindingOrNull() ?: throw RuntimeException("Cannot find corresponding view data binding.")