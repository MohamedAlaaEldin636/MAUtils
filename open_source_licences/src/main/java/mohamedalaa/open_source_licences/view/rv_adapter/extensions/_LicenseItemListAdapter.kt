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

package mohamedalaa.open_source_licences.view.rv_adapter.extensions

import mohamedalaa.mautils.core_kotlin.extensions.map
import mohamedalaa.mautils.core_kotlin.extensions.triple
import mohamedalaa.open_source_licences.model.LicenceModel
import mohamedalaa.open_source_licences.view.rv_adapter.LicenseItemRVAdapter
import java.util.*

internal fun LicenseItemRVAdapter.filterLicenseModelList(licenseModelList: List<LicenceModel>): List<LicenceModel> = searchText.run{
    if (this == null || isEmpty()) {
        return licenseModelList
    }

    licenseModelList.mapNotNull {
        val newTriple = it.openSourceName to it.listOfAuthorsNames.joinToString(", ") triple this
        val (newOpenSourceName, newAuthorName, newSearchText) = if (matchCase) {
            newTriple
        }else {
            newTriple.map { innerIt -> innerIt.toLowerCase(Locale.getDefault()) }
        }

        if (anyLetter) {
            val nameConditionPassed = newSearchText.toCharArray() in newOpenSourceName
            val authorConditionPassed = newSearchText.toCharArray() in newAuthorName

            if (nameConditionPassed || (includeAuthor && authorConditionPassed)) {
                it
            }else {
                null
            }
        }else {
            if (newSearchText in newOpenSourceName || (includeAuthor && newSearchText in newAuthorName)) {
                it
            }else {
                null
            }
        }
    }
}

/** @param text is the text to be spanned Ex. [LicenceModel.openSourceName] isa. */
internal fun LicenseItemRVAdapter.createToBeHighlightedIndices(text: String): List<Int> {
    if (text.isEmpty() || searchText.isNullOrEmpty()) {
        return emptyList()
    }

    val newText = if (matchCase) text else text.toLowerCase(Locale.getDefault())
    val newSearchText = (if (matchCase) searchText else searchText?.toLowerCase(Locale.getDefault())).orEmpty()

    val mutableList = mutableListOf<Int>()
    if (anyLetter) {
        newText.forEachIndexed { index, char ->
            if (char in newSearchText) {
                mutableList += index
            }
        }
    }else if (newSearchText in newText) {
        var startIndex = newText.indexOf(newSearchText)
        while (startIndex >= 0) {
            val endIndex = startIndex + newSearchText.length

            mutableList += startIndex until endIndex

            if (endIndex == newText.length) {
                return mutableList
            }else {
                startIndex = newText.indexOf(newSearchText, startIndex = endIndex)
            }
        }
    }

    return mutableList
}

// ---- Private fun

private operator fun String?.contains(charArray: CharArray): Boolean {
    if (this == null) {
        return false
    }

    charArray.forEach {
        if (it !in this) {
            return false
        }
    }

    return true
}
