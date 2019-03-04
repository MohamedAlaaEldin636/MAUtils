package mohamedalaa.mautils.mautils_view_model.custom_classes

import androidx.lifecycle.MutableLiveData

/**
 * more concise way to initialize [MutableLiveData] with an initial value
 *
 * **Example**
 * ```
 * // Instead of below code
 * val StringMutableLiveData = MutableLiveData<String>().apply { this.value = "Initial Value" }
 * // Use below one for initial values isa.
 * val StringQuickMutableLiveData = QuickMutableLiveData("Initial Value")
 * ```
 */
class QuickMutableLiveData<T>(value: T? = null): MutableLiveData<T>() {
    init {
        this.value = value
    }
}