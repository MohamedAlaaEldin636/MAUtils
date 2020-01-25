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

package mohamedalaa.mautils.sample

import android.graphics.Color
import android.graphics.drawable.Drawable
import mohamedalaa.mautils.core_android.extensions.getCircle
import mohamedalaa.mautils.core_android.extensions.tintColorFilter
import mohamedalaa.mautils.gson.toJsonOrNull
import mohamedalaa.mautils.sample.custom_classes.helper_classes.GameTarneebTypeTrumpSuit
import mohamedalaa.mautils.sample.shared_pref_.SharedPref_SomeClassName_NoContext

private fun sas(d: Drawable) {
    val a: GameTarneebTypeTrumpSuit

    d.toJsonOrNull()

    d.tintColorFilter(Color.WHITE)
    getCircle(2)

    SharedPref_SomeClassName_NoContext.fileName()
}
