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

package mohamedalaa.mautils.mautils.delLaterIsa

import android.view.View
import mohamedalaa.mautils.material_design.custom_classes.RVCommonAdapter
import mohamedalaa.mautils.material_design.custom_classes.RVListCommonAdapter

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/24/2019.
 *
 */
class UdacityKotlinClass {

    // maybe use View.snackbar instead of context.snackBar and get application for baseApplication
    // from view.context.baseApplication isa...
    /*val a = bundleOf()

    private fun a1 (v: View) {
        v.snackbar("")
    }*/

    private fun aaaa() {
        val list = listOf(5, 6, 7)

        //list.
    }


    lateinit var s: String

    private fun v1() {
        if (!::s.isInitialized) {

        }
    }

}

class SomeThing : RVCommonAdapter() {
    override fun onBindViewHolder(itemView: View, position: Int) {

    }

    override fun getItemCount(): Int {
        return 8
    }
}

class Another : RVListCommonAdapter<Int>(null) {
    override fun onBindViewHolder(itemView: View, position: Int) {

    }
}

private fun consumer(s: SomeThing, a: Another, map: Map<String, Int>) {
    map.size
    s.insertItemAt(2) {

    }

    a.insertItemAt(5, 44)
}

/*open fun View.aa() {

}*/
