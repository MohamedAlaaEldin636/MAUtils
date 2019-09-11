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

package mohamedalaa.open_source_licences.model

/**
 * - **ONLY** [preLicenseContentSpecificToOpenSource] && [listOfAuthorsNames] can be empty which then
 * for 2nd one will be replaced with "Unknown" isa.
 * - **VIP NOTE** DO NOT add Apache 2.0 license sine it's already added isa.
 *
 * @param listOfAuthorsNames if more than 1 then all names will be added separated by comma and space
 * like this ", " without the quotes isa.
 *
 * @param preLicenseContentSpecificToOpenSource
 * - used in case the [openSourceName] has specific
 * line(s) needed to be added before the licence content in the licence detail activity isa.
 * - Ex. we have 7 diff [openSourceName] with same licence so we have in assets only 1 licence
 * but each [openSourceName] have let's say 1 line needed to be distinguish pre license content
 * like Copyright (c) 2019 Mohamed Alaa for 1st one, and Copyright © [year] [name] for 2nd and so on
 * so we still will add 1 .txt and use this field for these variations to make most possible
 * low size for .txt files isa.
 * - Also note each item in the list represents a line so you can add empty item to be an empty line
 * and after every thing is added an empty line will be added before content in .txt file isa.
 */
@Suppress("KDocUnresolvedReference")
data class LicenceModel(
    var openSourceName: String,
    var listOfAuthorsNames: List<String>,
    var openSourceLink: String,
    var licenceName: String,
    var preLicenseContentSpecificToOpenSource: List<String> = emptyList()
) {
    constructor(
        openSourceName: String,
        authorName: String,
        openSourceLink: String,
        licenceName: String,
        preLicenseContentSpecificToOpenSource: List<String> = emptyList()
    ): this(
        openSourceName,
        listOf(authorName),
        openSourceLink,
        licenceName,
        preLicenseContentSpecificToOpenSource
    )
}
