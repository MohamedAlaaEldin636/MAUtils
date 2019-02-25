@file:JvmName("SearchLogic")

package mohamedalaa.mautils.mautils_open_source_licences.logic

import mohamedalaa.mautils.mautils_open_source_licences.extensions.contains
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence

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