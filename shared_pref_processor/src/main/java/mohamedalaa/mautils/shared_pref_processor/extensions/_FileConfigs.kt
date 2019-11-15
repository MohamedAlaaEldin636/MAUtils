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

package mohamedalaa.mautils.shared_pref_processor.extensions

import com.squareup.kotlinpoet.*
import mohamedalaa.mautils.shared_pref_annotation.MASharedPrefFileConfigs
import mohamedalaa.mautils.shared_pref_processor.Constants
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_PRIVATE_FILE_NAME
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_COMMIT
import mohamedalaa.mautils.shared_pref_processor.VAR_NAME_LISTENER
import javax.annotation.processing.RoundEnvironment

@Suppress("unused")
fun RoundEnvironment.setupMASharedPrefFileConfigs(
    annMASharedPrefFileConfigs: MASharedPrefFileConfigs,
    kFileBuilder: FileSpec.Builder,
    annotatedClassName: String,
    fullImportStringsList: MutableList<String>,
    contextClassName: ClassName
) {
    // -- ext fun is actually static fun, I just refer to ext fun for kotlin consumers isa. -- //

    // Clear fun isa. - ext fun
    if (annMASharedPrefFileConfigs.addClearFun) {
        kFileBuilder.addFunction(
            FunSpec.builder("sharedPref${annotatedClassName}_clearAll").apply {
                /*
                @JvmName("clearAll")
                @JvmOverloads
                @Synchronized
                fun Context.sharedPref_SomeClassName_clearAll(commit: Boolean = false): Boolean? =
                        sharedPrefClearAll(privateFileName, Context.MODE_PRIVATE, commit)
                 */

                // Imports
                fullImportStringsList += "mohamedalaa.mautils.shared_pref_core.sharedPrefClearAll"

                // Annotations
                if (annMASharedPrefFileConfigs.supportJavaConsumerCode || annMASharedPrefFileConfigs.supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation) {
                    addAnnotation(
                        AnnotationSpec.builder(JvmName::class)
                            .addMember(
                                "\"clearAll\""
                            )
                            .build()
                    )
                    addAnnotation(JvmOverloads::class)
                }
                addAnnotation(Synchronized::class)

                // receiver && return values isa.
                receiver(contextClassName)
                returns(Boolean::class.asTypeName().copy(nullable = true))

                // params isa.
                addParameter(
                    ParameterSpec.builder(VAR_NAME_COMMIT, Boolean::class.asTypeName())
                        .defaultValue("false")
                        .build()
                )

                // function code isa.
                addStatement(
                    "return sharedPrefClearAll(" +
                        "$VAR_NAME_PRIVATE_FILE_NAME, " +
                        "Context.MODE_PRIVATE, " +
                        VAR_NAME_COMMIT +
                        ")"
                )
            }.build()
        )
    }

    // File name fun isa. - static fun
    if (annMASharedPrefFileConfigs.addFileNameFun) {
        /*
        object SharedPref${annotatedClassName}_NoContext {
            @JvmStatic
            fun fileName(): String = privateFileName
        }
         */
        kFileBuilder.addType(
            TypeSpec.objectBuilder(Constants.getJavaStaticClassName(annotatedClassName) + "_NoContext").apply {
                addFunction(
                    FunSpec.builder("fileName").apply {
                        if (annMASharedPrefFileConfigs.supportJavaConsumerCode || annMASharedPrefFileConfigs.supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation) {
                            addAnnotation(JvmStatic::class)
                        }

                        returns(String::class)

                        addStatement("return $VAR_NAME_PRIVATE_FILE_NAME")
                    }.build()
                )
            }.build()
        )
    }

    // SharedPreferences instance isa. - ext fun
    val makeSharedPrefInstancePrivate = annMASharedPrefFileConfigs.addSharedPrefChangeListener && annMASharedPrefFileConfigs.addSharedPrefInstanceFun.not()
    val nameOfSharedPrefInstanceFun = "sharedPref${annotatedClassName}_asSharedPreferences"
    if (annMASharedPrefFileConfigs.addSharedPrefInstanceFun || makeSharedPrefInstancePrivate) {
        kFileBuilder.addFunction(
            FunSpec.builder(nameOfSharedPrefInstanceFun).apply {
                /*
                @JvmName("asSharedPreferences")
                fun Context.sharedPref_TempValues_asSharedPreferences(): SharedPreferences =
                        getSharedPreferences(privateFileName, Context.MODE_PRIVATE)
                 */

                // check if needs private modifier isa.
                if (makeSharedPrefInstancePrivate) {
                    addModifiers(KModifier.PRIVATE)
                }

                // Imports
                fullImportStringsList += "android.content.SharedPreferences"

                // Kotlinpoet ClassNames
                val sharedPreferencesClassName = ClassName.bestGuess("android.content.SharedPreferences")

                // Annotations
                if (annMASharedPrefFileConfigs.supportJavaConsumerCode || annMASharedPrefFileConfigs.supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation) {
                    addAnnotation(
                        AnnotationSpec.builder(JvmName::class)
                            .addMember("\"asSharedPreferences\"")
                            .build()
                    )
                }

                // receiver && return values isa.
                receiver(contextClassName)
                returns(sharedPreferencesClassName)

                // function code isa.
                addStatement(
                    "return getSharedPreferences(" +
                        "$VAR_NAME_PRIVATE_FILE_NAME, " +
                        "Context.MODE_PRIVATE" +
                        ")"
                )
            }.build()
        )
    }

    // Shared Pref Listener isa. - ext fun
    if (annMASharedPrefFileConfigs.addSharedPrefChangeListener) {
        // Imports
        fullImportStringsList += "android.content.SharedPreferences"

        // Kotlinpoet ClassNames
        val listenerClassName = ClassName.bestGuess("android.content.SharedPreferences.OnSharedPreferenceChangeListener")

        kFileBuilder.addFunction(
            FunSpec.builder("sharedPref${annotatedClassName}_registerSharedPrefChangeListener").apply {
                /*
                @JvmName("registerSharedPrefChangeListener")
                fun Context.sharedPref_TempValues_registerSharedPrefChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
                        sharedPref_TempValues_asSharedPreferences().registerOnSharedPreferenceChangeListener(listener)
                 */

                // Annotations
                if (annMASharedPrefFileConfigs.supportJavaConsumerCode || annMASharedPrefFileConfigs.supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation) {
                    addAnnotation(
                        AnnotationSpec.builder(JvmName::class)
                            .addMember("\"registerSharedPrefChangeListener\"")
                            .build()
                    )
                }

                // receiver && return values isa.
                receiver(contextClassName)
                returns(Unit::class)

                // params isa.
                addParameter(
                    ParameterSpec.builder(VAR_NAME_LISTENER, listenerClassName)
                        .build()
                )

                // function code isa.
                addStatement(
                    "return $nameOfSharedPrefInstanceFun()" +
                        ".registerOnSharedPreferenceChangeListener($VAR_NAME_LISTENER)"
                )
            }.build()
        )

        kFileBuilder.addFunction(
            FunSpec.builder("sharedPref${annotatedClassName}_unregisterSharedPrefChangeListener").apply {
                /*
                @JvmName("unregisterSharedPrefChangeListener")
                fun Context.sharedPref_TempValues_unregisterSharedPrefChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) =
                        sharedPref_TempValues_asSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener)
                 */

                // Annotations
                if (annMASharedPrefFileConfigs.supportJavaConsumerCode || annMASharedPrefFileConfigs.supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation) {
                    addAnnotation(
                        AnnotationSpec.builder(JvmName::class)
                            .addMember("\"unregisterSharedPrefChangeListener\"")
                            .build()
                    )
                }

                // receiver && return values isa.
                receiver(contextClassName)
                returns(Unit::class)

                // params isa.
                addParameter(
                    ParameterSpec.builder(VAR_NAME_LISTENER, listenerClassName)
                        .build()
                )

                // function code isa.
                addStatement(
                    "return $nameOfSharedPrefInstanceFun()" +
                        ".unregisterOnSharedPreferenceChangeListener($VAR_NAME_LISTENER)"
                )
            }.build()
        )
    }

}

