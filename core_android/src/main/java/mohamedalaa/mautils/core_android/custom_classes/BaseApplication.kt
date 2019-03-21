@file:Suppress("KDocUnresolvedReference", "UnusedImport")

package mohamedalaa.mautils.core_android.custom_classes

import android.app.Application
import android.content.Context
import android.widget.Toast
import mohamedalaa.mautils.core_android.toast
import mohamedalaa.mautils.core_android.dismissToast

/**
 * ## Deprecation Under Condition
 *
 * If you `implement` material_design module then use [mohamedalaa.mautils.material_design.custom_classes.BaseApplication]
 *
 * instead of this [BaseApplication], since it extends from [BaseApplication] and add more functionality
 *
 * Ex. same approach of [Toast] but for [com.google.android.material.snackbar.Snackbar] isa.
 *
 * ## Usage
 *
 * Used with [Context.toast] & [Context.dismissToast] to provide more effective approach to [Toast]
 *
 * with as much less code as possible, along with additional functionality for more clarification See [Context.toast].
 *
 * ## How to Use
 *
 * 1- either extend `this` class then put the derived class in manifest -> android:name=".YourApplication"
 *
 * 2- or directly use `this` class in manifest -> android:name=".BaseApplication"
 *
 * @see dismissToast
 * @see Context.toast
 */
open class BaseApplication: Application() {

    var toast: Toast? = null

}