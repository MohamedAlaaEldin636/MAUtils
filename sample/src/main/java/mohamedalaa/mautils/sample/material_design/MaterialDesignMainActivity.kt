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

package mohamedalaa.mautils.sample.material_design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_material_design_main.*
import mohamedalaa.mautils.core_android.toast
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.mautils.databinding.ActivityMaterialDesignMainBinding

class MaterialDesignMainActivity : AppCompatActivity() {

    //private lateinit var person: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMaterialDesignMainBinding>(this, R.layout.activity_material_design_main)

        // -- custom obj
        val person = Person("Mohamed Alaa", 21, true)

        binding.lifecycleOwner = this
        binding.person = person

        button.setOnClickListener {
            toast("isAdult -> ${person.isAdult}") // works el7

            person.name += "-"

            binding.person = person
            //binding.notifyPropertyChanged(BR.person)
        }
    }
}
