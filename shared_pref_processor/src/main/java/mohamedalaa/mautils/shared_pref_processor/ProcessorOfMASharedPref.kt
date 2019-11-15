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

@file:Suppress("UNREACHABLE_CODE")

package mohamedalaa.mautils.shared_pref_processor

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.*

@SupportedAnnotationTypes(
    value = [
        "mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair",
        "mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs",
        "mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair.Container",
        "mohamedalaa.mautils.shared_pref_annotation.MASharedPrefGsonConverter"
    ]
)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ProcessorOfMASharedPref : AbstractProcessor() {

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.process(processingEnv)

        return false
    }

}
