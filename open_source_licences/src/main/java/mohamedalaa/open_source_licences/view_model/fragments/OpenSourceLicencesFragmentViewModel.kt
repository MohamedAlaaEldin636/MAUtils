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

package mohamedalaa.open_source_licences.view_model.fragments

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mohamedalaa.mautils.core_android.extensions.logError
import mohamedalaa.mautils.core_android.extensions.logWarn
import mohamedalaa.mautils.core_kotlin.extensions.substring
import mohamedalaa.mautils.core_kotlin.extensions.withContextMain
import mohamedalaa.mautils.lifecycle_extensions.custom_classes.QuickMutableLiveData
import mohamedalaa.open_source_licences.custom_classes.Constants
import java.io.BufferedReader
import java.io.InputStreamReader

internal class OpenSourceLicencesFragmentViewModel(
    application: Application,
    assetsFolderPath: String,
    searchViewIsIconified: Boolean,
    searchText: String?,
    matchCase: Boolean,
    anyLetter: Boolean,
    includeAuthor: Boolean
) : AndroidViewModel(application) {

    /** [Pair.first] represents license name while [Pair.second] represents content isa. */
    val mutableLiveDataLicencesNameAndContent = MutableLiveData<List<Pair<String, String>>>()

    val mutableLiveDataSearchViewIsIconified = QuickMutableLiveData(searchViewIsIconified)
    val mutableLiveDataSearchText = QuickMutableLiveData(searchText)
    val mutableLiveDataMatchCase = QuickMutableLiveData(matchCase)
    val mutableLiveDataAnyLetter = QuickMutableLiveData(anyLetter)
    val mutableLiveDataIncludeAuthor = QuickMutableLiveData(includeAuthor)

    val mutableLiveDataLicensesAreEmpty = QuickMutableLiveData(false)

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val assetManager: AssetManager? = application.assets
            val subPathList = assetManager?.list(assetsFolderPath)?.toList()?.filterNotNull()
                ?.filter { it.length > 4 && it.endsWith(".txt") }.orEmpty()

            val apacheLicenceNameAndContent = listOf(Constants.APACHE_2_0_LICENCE_NAME to Constants.APACHE_2_0_LICENCE_CONTENT)
            val listOfLicencesNameAndContent = apacheLicenceNameAndContent + subPathList.mapNotNull {
                val fullPath = if (assetsFolderPath.isNotEmpty()) "$assetsFolderPath/$it" else it
                val licenceName = it.substring(0, ".")

                readTextFileToGetLicenceContent(assetManager, fullPath)?.run {
                    licenceName to this
                }
            }

            if (listOfLicencesNameAndContent.size != listOfLicencesNameAndContent.distinct().size) {
                logWarn(
                    "There are 2 or more licences with same content" +
                        "\nIt's better to remove duplications since .txt files affect size of app isa."
                )
            }

            withContextMain { mutableLiveDataLicencesNameAndContent.value = listOfLicencesNameAndContent }
        }
    }

    private fun readTextFileToGetLicenceContent(assetManager: AssetManager?, fullPath: String): String? {
        if (assetManager == null) return null

        var reader: BufferedReader? = null
        var licenceContent = ""
        try {
            reader = BufferedReader(InputStreamReader(assetManager.open(fullPath), "UTF-8"))

            reader.forEachLine {
                licenceContent += it
                licenceContent += "\n"
            }

            licenceContent = licenceContent.trim()
        }catch (throwable: Throwable) {
            logError("Unexpected error code 1092 isa, with msg ${throwable.message}\nAnd stacktrace $throwable")
        }finally {
            kotlin.runCatching { reader?.close() }
        }

        return if (licenceContent.isEmpty()) null else licenceContent
    }

}