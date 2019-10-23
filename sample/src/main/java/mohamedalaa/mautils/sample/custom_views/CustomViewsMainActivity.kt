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

package mohamedalaa.mautils.sample.custom_views

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mohamedalaa.mautils.core_android.custom_classes.SharedPrefSupportedTypesParams
import mohamedalaa.mautils.core_android.extensions.*
import mohamedalaa.mautils.core_kotlin.extensions.throwRuntimeException
import mohamedalaa.mautils.gson.addValuesGsonForced
import mohamedalaa.mautils.gson.getterBundleGson
import mohamedalaa.mautils.lifecycle_extensions.extensions.getAndroidViewModelWithFactory
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.mautils.databinding.ActivityCustomViewsMainBinding
import mohamedalaa.mautils.sample.custom_views.presenter.CustomViewsMainActivityPresenter
import mohamedalaa.mautils.sample.custom_views.view_model.CustomViewsMainActivityViewModel

class CustomViewsMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomViewsMainBinding
    private lateinit var viewModel: CustomViewsMainActivityViewModel

    private var checkedNames = emptyList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!::binding.isInitialized) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_views_main)
        }

        checkedNames = listOf(getString(R.string.chip_5))
        savedInstanceState?.getterBundleGson()?.apply {
            checkedNames = getOrNull() ?: emptyList()
        }

        if (!::viewModel.isInitialized) {
            viewModel = getAndroidViewModelWithFactory(
                checkedNames
            )
        }

        binding.viewModel = viewModel
        binding.presenter = CustomViewsMainActivityPresenter()
        binding.lifecycleOwner = this

        // Observe live data in view model isa.
        viewModel.mutableLiveDataCheckedChipsNames.observe(this, Observer {
            checkedNames = it.orEmpty()
            logError("VIEWMODEL old checked names -> ${binding.maChipsContainer.getLastCheckedChipsNames()}," +
                "\nnew checked names isa -> $it")
        })

        /*viewModel.viewModelScope.launch {
            val set1 = setOf("hi", "sss", "", "bye")
            sharedPrefSet("fileName", "key", set1)

            logError("set1 -> ${set1.toList()}")
            delay(5_000)

            val abc: Set<String> = sharedPrefGet("fileName", "key", setOf())

            logError("set1 -> ${set1.toList()}" +
                "\nabc -> ${abc.toList()}")
        }*/
        sharedPrefChecks()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.addValuesGsonForced(
            checkedNames
        )

        super.onSaveInstanceState(outState)
    }

    private fun sharedPrefChecks() {
        runCatching {
            val pair = 5 to "mido"
            val setOfInts = setOf(2, 33)
            val anotherPair: Pair<Float, String?> = 7776f to ""

            application.sharedPrefSetComplex(
                "fileName", "key_1", pair,
                sharedPrefSupportedTypesParamsArray = *arrayOf(
                    SharedPrefSupportedTypesParams.INT,
                    SharedPrefSupportedTypesParams.STRING
                )
            )
            application.sharedPrefSetComplex(
                "fileName", "key_2", setOfInts,
                sharedPrefSupportedTypesParamsArray = *arrayOf(
                    SharedPrefSupportedTypesParams.INT
                )
            )
            application.sharedPrefSetComplex(
                "fileName", "key_3", anotherPair,
                sharedPrefSupportedTypesParamsArray = *arrayOf(
                    SharedPrefSupportedTypesParams.FLOAT,
                    SharedPrefSupportedTypesParams.STRING
                )
            )

            application.sharedPrefGetComplex(
                "fileName", "key_1", 11 to "ewew",
                sharedPrefSupportedTypesParamsArray = *arrayOf(
                    SharedPrefSupportedTypesParams.INT,
                    SharedPrefSupportedTypesParams.STRING
                )
            ).run {
                assertEquals(pair, this)
            }
            application.sharedPrefGetComplex(
                "fileName", "key_2", setOf<Int>(),
                sharedPrefSupportedTypesParamsArray = *arrayOf(
                    SharedPrefSupportedTypesParams.INT
                )
            ).run {
                setOfInts.assertAllItemsInSetIsInsideAnotherIgnoreOrder(this)
            }
            application.sharedPrefGetComplex(
                "fileName", "key_3", 3f to "dddddd",
                sharedPrefSupportedTypesParamsArray = *arrayOf(
                    SharedPrefSupportedTypesParams.FLOAT,
                    SharedPrefSupportedTypesParams.STRING
                )
            ).run {
                assertEquals(anotherPair, this)
            }

            // null DO NOT change anything isa.
            application.sharedPrefSetComplex(
                "fileName", "key_3", anotherPair.first to null,
                sharedPrefSupportedTypesParamsArray = *arrayOf(
                    SharedPrefSupportedTypesParams.FLOAT,
                    SharedPrefSupportedTypesParams.STRING
                )
            )
            application.sharedPrefGetComplex(
                "fileName", "key_3", 3f to "dddddd",
                sharedPrefSupportedTypesParamsArray = *arrayOf(
                    SharedPrefSupportedTypesParams.FLOAT,
                    SharedPrefSupportedTypesParams.STRING
                )
            ).run {
                assertEquals(anotherPair, 7776.0f to "" /* null do NOT change anything isa. */)
            }
        }.getOrElse {
            logError("Error isa is ${it.message}")
        }
        logError("Finished el7")
    }

    private fun <T> Set<T>.assertAllItemsInSetIsInsideAnotherIgnoreOrder(other: Set<T>) {
        forEach {
            assertSpecial(it in other)
        }
    }

    private fun <T> assertEquals(expected: T, actual: T) {
        if (expected != actual) {
            logError("Expected $expected\nActual $actual")
        }
        assertSpecial(expected == actual)
    }

    private fun <T> T.assertSpecial(boolean: Boolean) {
        if (boolean.not()) {
            throwRuntimeException("assert error isa.")
        }
    }
}
