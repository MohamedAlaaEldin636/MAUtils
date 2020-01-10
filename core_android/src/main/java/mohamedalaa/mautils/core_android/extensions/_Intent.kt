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

@file:JvmName("IntentUtils")

package mohamedalaa.mautils.core_android.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * - Exactly same as [Intent.getExtras].get([key]), but easier for nullability checks & conciser code isa.
 */
inline fun <reified T> Intent?.getExtraOrNull(key: String): T? {
    this?.apply {
        extras?.apply {
            return this.get(key) as? T
        }
    }

    return null
}

/**
 * - Same as [getExtraOrNull], but throws [RuntimeException] instead of returning `null` isa.
 */
inline fun <reified T> Intent?.getExtra(key: String): T {
    this?.apply {
        extras?.apply {
            return this.get(key) as T
        }
    }

    throw RuntimeException("Extras or this intent is null")
}

/**
 * @return true if `receiver` is `null` or it's [Intent.getExtras] is `null` or empty isa.
 *
 * @see [Bundle.isNullOrEmpty]
 */
fun Intent?.isNullOrEmpty(): Boolean = this == null || extras.isNullOrEmpty()

/**
 * Used to retrieve [Bundle] values created by [Context.startActivityBundle] isa.
 */
fun Intent?.getterBundle(): GetterBundle = GetterBundle(this?.extras.orEmpty())
