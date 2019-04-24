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

package mohamedalaa.mautils.sample.core_android

import android.content.Context
import android.content.SharedPreferences
import mohamedalaa.mautils.core_android_annotation.MASharedPref

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/24/2019.
 *
 * annotation test isa.
 */
@MASharedPref
class SomeClass {

    val bye: String? = "Bye"

    val string: String? = null

    val int0: Int = 7

    val s2: String = ""

    val anotherInt: Int = 3

    val firstInt: Int = 9

    val secondString: String = "def string val"

    @MASharedPref.SetOfStringsDefValue(stringSetValue = ["", "sa"])
    val thirdStringSet: MutableSet<String> = mutableSetOf("1", "320")

}

//@MASharedPref.Alaa
private fun dwoiejdow(someClass: SomeClass, anotherSomeClass: AnotherSomeClass, sharedPreferences: SharedPreferences, context: Context) {
    //someClass.int
    anotherSomeClass.another = 99

    //sharedPreferences.edit().putString("key", null).apply()
}