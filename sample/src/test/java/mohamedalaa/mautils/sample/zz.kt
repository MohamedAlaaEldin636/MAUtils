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

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import mohamedalaa.mautils.core_android.custom_classes.dialog_fragments.MADialogFragment
import mohamedalaa.mautils.core_android.extensions.GetterBundle
import mohamedalaa.mautils.core_android.extensions.getterBundle
import mohamedalaa.mautils.core_android.extensions.isNullOrEmpty
import mohamedalaa.mautils.core_android.extensions.showDialogFragment
import mohamedalaa.mautils.core_kotlin.extensions.applyIf
import mohamedalaa.mautils.core_kotlin.extensions.firstIsInstance
import mohamedalaa.mautils.core_kotlin.extensions.firstIsInstanceOrNull
import mohamedalaa.mautils.core_kotlin.extensions.format
//import mohamedalaa.mautils.room_gson_annotation.MAAutoTypeConverters

private fun f1(int: Int?, list: List<Long>, bbbbb: Bundle) {

    val bundle: Bundle? = null

    val b = bundle.isNullOrEmpty()

    val getterBundle: GetterBundle = bbbbb.getterBundle()
    val retrievedInt = getterBundle.get<Int>()
    val retrievedStringList = getterBundle.getOrNull<List<String>>()
    val retrievedAnotherInt: Int = getterBundle.get()

    list.firstIsInstance<TextView>()
    list.firstIsInstanceOrNull<TextView>()?.apply {

    }

    int.applyIf(int != null) {

    }

    System.currentTimeMillis().format(
        "d MMM" to true
    )

    //
    //val a = MAAutoTypeConverters::class.java
}


