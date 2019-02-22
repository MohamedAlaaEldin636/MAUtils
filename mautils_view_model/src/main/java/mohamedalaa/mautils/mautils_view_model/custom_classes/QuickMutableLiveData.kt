package mohamedalaa.mautils.mautils_view_model.custom_classes

import androidx.lifecycle.MutableLiveData

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/21/2019.
 *
 */
class QuickMutableLiveData<T>(value: T? = null): MutableLiveData<T>() {
    init {
        this.value = value
    }
}