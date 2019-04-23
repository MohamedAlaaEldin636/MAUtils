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

@file:JvmName("SearchLogic")

package mohamedalaa.mautils.open_source_licences.logic

import mohamedalaa.mautils.open_source_licences.extensions.contains
import mohamedalaa.mautils.open_source_licences.model.Licence

internal fun computeSearchedLicences(licences: List<Licence>?,
                                     searchText: String?,
                                     matchCase: Boolean,
                                     anyLetter: Boolean,
                                     includeAuthor: Boolean): MutableList<Licence> = searchText.run {
    if (this == null || this.isEmpty()) {
        return licences?.toMutableList() ?: mutableListOf()
    }

    licences?.mapNotNull {
        val (parentName, childName) = if (matchCase) {
            it.licenceName to this
        }else {
            it.licenceName.toLowerCase() to this.toLowerCase()
        }

        val (parentAuthor, childAuthor) = if (matchCase) {
            it.licenceAuthor to this
        }else {
            it.licenceAuthor?.toLowerCase() to this.toLowerCase()
        }

        if (anyLetter) {
            val nameConditionPassed = childName.toCharArray() in parentName
            val authorConditionPassed = childAuthor.toCharArray() in parentAuthor

            if (nameConditionPassed
                || (includeAuthor && authorConditionPassed)) {
                it
            }else {
                null
            }
        }else {
            if (childName in parentName
                || (includeAuthor && parentAuthor?.contains(childAuthor) == true)) {
                it
            }else {
                null
            }
        }
    }?.toMutableList() ?: mutableListOf()
}