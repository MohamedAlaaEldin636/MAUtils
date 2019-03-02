package mohamedalaa.mautils.mautils_view_model.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Observes the given [LiveData], and whenever a change occur to it, it will run the [block]
 * code isa, then return the result and set value of this mediator, to refresh it isa.
 *
 * Note you can change data of mediator object acc to change of liveDate with the [block] isa.
 */
fun <T, E> MediatorLiveData<T>.addSourceAndSetSafely(source: LiveData<E>, block: T.(E) -> Unit) {
    addSource(source) {
        this.value?.apply {
            this@addSourceAndSetSafely.value = apply { block(this, it) }
        }
    }
}