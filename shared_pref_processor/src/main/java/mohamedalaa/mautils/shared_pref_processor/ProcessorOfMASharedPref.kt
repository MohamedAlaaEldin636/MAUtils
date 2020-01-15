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

import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair
import mohamedalaa.mautils.shared_pref_processor.extensions.error
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

        "mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair",
        "mohamedalaa.mautils.shared_pref_annotation.MASharedPrefPair.Container",

        "mohamedalaa.mautils.shared_pref_annotation.MASharedPrefKeyValuePair.Container"
    ]
)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ProcessorOfMASharedPref : AbstractProcessor() {

    /**
     * Note an error will occur If one class is annotated by both SharedPair annotation isa.
     */
    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        // Check that no class have both annotations isa.
        val maSharedPrefAllKeyValuePairElements = (
            (roundEnv.getElementsAnnotatedWith(MASharedPrefKeyValuePair.Container::class.java) +
                roundEnv.getElementsAnnotatedWith(MASharedPrefKeyValuePair::class.java)).mapNotNull { it as? TypeElement }
            ).distinct()
        val maSharedPrefPairAllElements = (
            (roundEnv.getElementsAnnotatedWith(MASharedPrefPair.Container::class.java) +
                roundEnv.getElementsAnnotatedWith(MASharedPrefPair::class.java)).mapNotNull { it as? TypeElement }
            ).distinct()
        val maSharedPrefPairAllElementsQualifiedNames = maSharedPrefPairAllElements.mapNotNull {
            it.qualifiedName
        }
        val fullClassesNamesWithCollision = maSharedPrefAllKeyValuePairElements.mapNotNull {
            if (it.qualifiedName in maSharedPrefPairAllElementsQualifiedNames) it else null
        }
        if (fullClassesNamesWithCollision.isNotEmpty()) {
            processingEnv.error(
                "Only one of ${MASharedPrefKeyValuePair::class} & ${MASharedPrefPair::class} can be " +
                    "annotated per class, Not both of them isa."
            )
        }

        // Backward compatibility processing to the deprecated annotation isa.
        roundEnv.process(processingEnv)

        // New annotation processing isa.
        roundEnv.processMASharedPrefPairAndFileConfigs(processingEnv)

        return false
    }

}
