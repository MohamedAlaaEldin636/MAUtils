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

package com.maproductions.mohamedalaa.library_common_processors

import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.Diagnostic

fun ProcessingEnvironment.error(msg: String): Nothing {
    messager.printMessage(
        Diagnostic.Kind.ERROR,
        msg
    )

    throw RuntimeException(msg)
}

fun ProcessingEnvironment.note(msg: String) {
    messager.printMessage(
        Diagnostic.Kind.NOTE,
        msg
    )
}

fun ProcessingEnvironment.mandatoryWarning(msg: String) {
    messager.printMessage(
        Diagnostic.Kind.MANDATORY_WARNING,
        msg
    )
}

fun ProcessingEnvironment.warning(msg: Any?) {
    messager.printMessage(
        Diagnostic.Kind.WARNING,
        msg.toStringOrNull() ?: "null"
    )
}

fun ProcessingEnvironment.other(msg: String) {
    messager.printMessage(
        Diagnostic.Kind.OTHER,
        msg
    )
}
