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

package mohamedalaa.mautils.core_android_processor

import mohamedalaa.mautils.core_android_annotation.MASharedPrefField
import mohamedalaa.mautils.core_android_processor.extensions.toUpperCaseFirstLetter

object Constants {

    val generatedFilePackage: String = MASharedPrefField::class.java.getPackage().name.orEmpty()

    fun getKotlinFileName(annotatedClassName: String): String {
        return "SharedPrefUtils_$annotatedClassName"
    }

    /** @return "SharedPref$annotatedClassName" isa. */
    fun getJavaStaticClassName(annotatedClassName: String): String {
        return "SharedPref$annotatedClassName"
    }

    /** @return sharedPref_ClassName_SetSomeField || sharedPref_ClassName_GetSomeField isa. */
    fun getFunctionName(isSet: Boolean, annotatedClassName: String, fieldName: String, forKotlin: Boolean): String {
        return if (forKotlin) {
            val thirdPart = "_${if (isSet) "Set" else "Get"}${fieldName.toUpperCaseFirstLetter()}"

            "sharedPref_$annotatedClassName$thirdPart"
        }else {
            "${if (isSet) "set" else "get"}${fieldName.toUpperCaseFirstLetter()}"
        }
    }

}