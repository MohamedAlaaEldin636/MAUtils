package mohamedalaa.mautils.mautils_open_source_licences.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import mohamedalaa.mautils.mautils_open_source_licences.custom_classes.QuickMutableLiveData

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/21/2019.
 *
 */
class OpenSourceLicencesViewModel(application: Application): AndroidViewModel(application) {

    // todo but if on save instance has state then this should not happen isa...
    // todo ext, class MutableLiveData(initial val isa we el <T> inferred isa.), bs check orient changes in apply println isa.
    val loading = QuickMutableLiveData(true)

}