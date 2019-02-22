package mohamedalaa.mautils.mautils_open_source_licences.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohamedalaa.mautils.mautils_open_source_licences.custom_classes.QuickMutableLiveData
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.model.isAuthorExists

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/22/2019.
 *
 * todo if [getApplication] not used, then use [ViewModel] instead of [AndroidViewModel] isa.
 */
class RCAdapterLicenceViewModel(application: Application, licence: Licence): AndroidViewModel(application) {

    val licenceNameLiveData = QuickMutableLiveData(licence.licenceName)

    val licenceAuthorLiveData = QuickMutableLiveData(licence.licenceAuthor)

    val linkLiveData = QuickMutableLiveData(licence.link)

}