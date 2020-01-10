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

package mohamedalaa.mautils.sample.sample

import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import mohamedalaa.mautils.core_android.extensions.firstNestedActionViewIsInstanceOrNull
import mohamedalaa.mautils.core_android.extensions.logError
import mohamedalaa.mautils.core_android.extensions.setOnOneClickListener
import mohamedalaa.mautils.core_android.extensions.text
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import mohamedalaa.mautils.lifecycle_extensions.extensions.getAndroidViewModelWithFactory
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.mautils.databinding.ActivityEntryBinding
import mohamedalaa.mautils.sample.sample.interactor.EntryActivityInteractor
import mohamedalaa.mautils.sample.sample.view_model.EntryActivityViewModel

class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityEntryBinding = DataBindingUtil.setContentView(this, R.layout.activity_entry)

        val viewModel: EntryActivityViewModel = getAndroidViewModelWithFactory()

        // Assign binding isa.
        binding.viewModel = viewModel
        binding.interactor = EntryActivityInteractor()
        binding.lifecycleOwner = this

        binding.materialButton1.setOnOneClickListener {
            viewModel.mutableLiveDataSearchQuery.value = "hello"
        }

        // todo work around is use observeNewOnly isa ext fun by checking prev ex. but what
        // if in case you make changes differently 1 acc to user changes and 1 acc to programmatic changes isa.....
        // only way is to check ui below to know if it is programmatic or xml one isa.


        // viewModel.mutableLiveDataSearchQuery.observeNewOnlyIsa()
        // msh 7al ashan efred el user 7at nfs input el button isa ...
        // kda mmkn ne3mel threshold check same value isa. ex. law same value f 50 ms or 100 ezan
        // sa3b ykon el user isa.

        viewModel.mutableLiveDataSearchQuery.observe(this, Observer {

            // if so tell user in kdoc todo isa
            // todo PLUS try if binding adapter made by andorid itself example
            // edit text text=@{} and see if same prob happen isa.
            binding.toolbar.menu.firstNestedActionViewIsInstanceOrNull<SearchView>().performIfNotNull {
                logError("-> current -> text -> $text")
            }

            // TODO PROB if changed by button "hello" is logged twice isa.
            logError("changed text isa is -> $it")
        })


        // seeing android itself 2 way isa.
        /*binding.editText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                logError("WOW beforeTextChanged -> $s, ${binding.editText1.text}, ${viewModel.mutableLiveDataEditText1.value}")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // todo here if vm value differ 3n el s ezan user change not programmatic isa. -> ezan here do not call inversLis.onChange isa.

                logError("WOW onTextChanged -> $s, ${binding.editText1.text}, ${viewModel.mutableLiveDataEditText1.value}")
            }

            override fun afterTextChanged(s: Editable?) {
                logError("WOW afterTextChanged -> $s, ${binding.editText1.text}, ${viewModel.mutableLiveDataEditText1.value}") // TEXTtWOwAY
            }
        })*/
        binding.editText1MaterialButton.setOnOneClickListener {
            viewModel.mutableLiveDataEditText1.value = "hello"
        }
        viewModel.mutableLiveDataEditText1.observe(this, Observer {
            /*val currentElapsedRealtime = SystemClock.elapsedRealtime()
            if (lastElapsedRealtime == 0L) {
                logError("first invocation isa.")
            }else {
                logError("lastElapsedRealtime - currentOne = ${currentElapsedRealtime - lastElapsedRealtime} ms")
            }
            lastElapsedRealtime = currentElapsedRealtime*/

            logError("liveData.observe() called el7 with value -> $it") // 50 ms

            // maybe use bindable notify explicilty plus onchange and after change maybe detect it isa...
        })
    }

    private var lastElapsedRealtime = 0L // SystemClock.elapsedRealtime()



}
