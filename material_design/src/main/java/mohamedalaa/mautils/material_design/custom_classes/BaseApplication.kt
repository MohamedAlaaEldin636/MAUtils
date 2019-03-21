@file:Suppress("UnusedImport")

package mohamedalaa.mautils.material_design.custom_classes

import android.content.Context
import com.google.android.material.snackbar.Snackbar
import mohamedalaa.mautils.core_android.custom_classes.BaseApplication
import mohamedalaa.mautils.material_design.snackbar
import mohamedalaa.mautils.material_design.dismissSnackbar

/**
 * Used with other functions for easier and better approaches for common tasks isa.
 *
 * Ex. [Context.snackbar] & [Context.dismissSnackbar] & features in the super class [BaseApplication]
 *
 * **Note** for how to use see **How to use** in [BaseApplication] isa.
 *
 * @see [Context.snackbar]
 * @see dismissSnackbar
 */
open class BaseApplication: BaseApplication() {

    var snackbar: Snackbar? = null

}
