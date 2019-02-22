package mohamedalaa.mautils.mautils_open_source_licences.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import mohamedalaa.mautils.mautils_open_source_licences.custom_classes.QuickMutableLiveData

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/21/2019.
 *
 */
class OpenSourceLicencesViewModel(application: Application): AndroidViewModel(application) {

    val loading = QuickMutableLiveData(true)

}