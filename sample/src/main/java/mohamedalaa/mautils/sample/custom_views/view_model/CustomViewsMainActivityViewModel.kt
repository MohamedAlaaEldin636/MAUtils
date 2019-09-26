/*
 * Copyright (c) 2019 Mohamed Alaa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package mohamedalaa.mautils.sample.custom_views.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import mohamedalaa.mautils.core_android.extensions.getString
import mohamedalaa.mautils.lifecycle_extensions.custom_classes.QuickMutableLiveData
import mohamedalaa.mautils.mautils.R

class CustomViewsMainActivityViewModel(
    application: Application,
    checkedNames: List<String>
) : AndroidViewModel(application) {

    val mutableLiveDataCheckedChipsNames = QuickMutableLiveData(checkedNames)

}