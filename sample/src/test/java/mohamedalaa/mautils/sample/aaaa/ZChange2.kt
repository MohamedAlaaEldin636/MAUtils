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

package mohamedalaa.mautils.sample.aaaa

import android.content.Context
import android.content.SharedPreferences

internal abstract class ZChange2(
    private val fileName: String
): SharedPreferences.OnSharedPreferenceChangeListener {

    final override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == null || sharedPreferences == null) return

        onSharedPrefChangeListener(
            fileName,
            key,
            sharedPreferences
        )
    }

    abstract fun onSharedPrefChangeListener(
        fileName: String,
        key: String,
        sharedPreferences: SharedPreferences
    )

}

private fun registerLis(zChange2: ZChange2) {
    context.getSharedPreferences("", Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(
        zChange2
    )
}
private fun registerLis(action: (fileName: String, key: String, sharedPreferences: SharedPreferences) -> Unit) {
    context.getSharedPreferences("", Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(
        object : ZChange2("") {
            override fun onSharedPrefChangeListener(
                fileName: String,
                key: String,
                sharedPreferences: SharedPreferences
            ) {
                action(fileName, key, sharedPreferences)
            }
        }
    )
}
private fun registerLisTwo(sharedPreferencesOnSharedPreferenceChangeListener: SharedPreferences.OnSharedPreferenceChangeListener) {
    context.getSharedPreferences("", Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(
        sharedPreferencesOnSharedPreferenceChangeListener
    )
}

private lateinit var context: Context

private fun user1() {
    //registerLisTwo()
    ZChange1.s1 { sharedPreferences, key ->

    }
    //ZChange1.z1()
    // todo no need for class just interface isa.
}
