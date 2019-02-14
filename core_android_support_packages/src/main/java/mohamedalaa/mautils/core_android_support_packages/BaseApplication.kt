package mohamedalaa.mautils.core_android_support_packages

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Used with other functions for easier and better approaches for common tasks isa, for examples of these tasks
 * see below See Also tags.
 *
 * **How to Use**
 * 1- either extend `this` class then put the derived class in manifest -> android:name=".YourApplication"
 * 2- or directly use `this` class in manifest -> android:name=".BaseApplication"
 *
 * @see [Context.snackbar]
 */
open class BaseApplication: Application() {

    var snackbar: Snackbar? = null

    var toast: Toast? = null

}