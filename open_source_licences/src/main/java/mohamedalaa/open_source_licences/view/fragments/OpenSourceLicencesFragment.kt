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

package mohamedalaa.open_source_licences.view.fragments

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.text.buildSpannedString
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import mohamedalaa.mautils.core_android.custom_classes.character_style.DrawableSpan
import mohamedalaa.mautils.core_android.extensions.*
import mohamedalaa.mautils.core_kotlin.extensions.performIfNotNull
import mohamedalaa.mautils.gson.buildBundleGsonForced
import mohamedalaa.mautils.gson.getterBundleGson
import mohamedalaa.mautils.lifecycle_extensions.extensions.getAndroidViewModelWithFactory
import mohamedalaa.open_source_licences.R
import mohamedalaa.open_source_licences.custom_classes.Constants
import mohamedalaa.open_source_licences.databinding.FragmentOpenSourceLicencesBinding
import mohamedalaa.open_source_licences.model.LicenceModel
import mohamedalaa.open_source_licences.presenter.OpenSourceLicencesFragmentPresenter
import mohamedalaa.open_source_licences.model.OpenSourceLicencesModel
import mohamedalaa.open_source_licences.view.rv_adapter.LicenseItemRVAdapter
import mohamedalaa.open_source_licences.view_model.fragments.OpenSourceLicencesFragmentViewModel

// todo same maintain search text if wan empty or null so check it out isa.
class OpenSourceLicencesFragment(
    private var licenseModelList: List<LicenceModel> = emptyList(),
    openSourceLicencesModel: OpenSourceLicencesModel? = null
) : Fragment() {

    var openSourceLicencesModel: OpenSourceLicencesModel? = openSourceLicencesModel
        private set

    private lateinit var binding: FragmentOpenSourceLicencesBinding

    internal lateinit var viewModel: OpenSourceLicencesFragmentViewModel
        private set

    internal val presenter = OpenSourceLicencesFragmentPresenter()

    /** null means searchView not shown isa. */
    private var searchText: String? = null
    private var matchCase = false
    private var anyLetter = false
    private var includeAuthor = false
    private var searchViewIsIconified = true

    private var adapter: LicenseItemRVAdapter? = null

    init {
        arguments = buildBundleGsonForced(
            licenseModelList,
            openSourceLicencesModel
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        restoreArgsAndSavedInstanceState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Init binding isa.
        if (!::binding.isInitialized) {
            val contextThemeWrapperContext = (activity ?: parentFragment?.context) ?: context
            val specificInflater = openSourceLicencesModel.run {
                if (this == null || styleRes == 0 || contextThemeWrapperContext == null) null else {
                    ContextThemeWrapper(contextThemeWrapperContext, styleRes)
                }
            }?.run { inflater.cloneInContext(this) }

            binding = DataBindingUtil.inflate(
                specificInflater ?: ((activity?.layoutInflater ?: parentFragment?.layoutInflater) ?: inflater),
                R.layout.fragment_open_source_licences,
                container,
                false
            )
        }

        if (!::viewModel.isInitialized) {
            viewModel = getAndroidViewModelWithFactory(
                openSourceLicencesModel?.assetsFolderPath,
                searchViewIsIconified,
                searchText,
                matchCase,
                anyLetter,
                includeAuthor
            )
        }

        val itemBackground = context?.getDrawableFromResOrNull(openSourceLicencesModel?.rvItemBackgroundDrawableRes ?: 0)
            ?: openSourceLicencesModel!!.rvItemBackgroundColor.toDrawable()

        // Assign binding isa.
        binding.itemBackground = itemBackground
        binding.model = openSourceLicencesModel
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Setup xml isa.
        setupXml()

        // Observe live data isa.
        observeLiveData()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.addValues(
            viewModel.mutableLiveDataSearchText.value,
            viewModel.mutableLiveDataMatchCase.value,
            viewModel.mutableLiveDataAnyLetter.value,
            viewModel.mutableLiveDataIncludeAuthor.value,
            (binding.toolbar.menu.findItem(R.id.search).actionView as SearchView).isIconified
        )

        super.onSaveInstanceState(outState)
    }

    // ---- Private fun

    private fun restoreArgsAndSavedInstanceState(savedInstanceState: Bundle?) {
        // Args
        val getterBundleGson = arguments!!.getterBundleGson()

        val thisLibLicenceModel = LicenceModel(
            Constants.MAUtils,
            getString(R.string.mautils_author),
            Constants.MAUtils_LINK,
            Constants.APACHE_2_0_LICENCE_NAME,
            Constants.MAUtils_PRE_LICENSE_CONTENT
        )
        licenseModelList = listOf(thisLibLicenceModel) + getterBundleGson.get<List<LicenceModel>>()
        openSourceLicencesModel = getterBundleGson.getOrNull() ?: OpenSourceLicencesModel(context!!)

        // Saved instance state isa.
        savedInstanceState?.apply {
            val innerGetterBundle = getterBundle()

            searchText = innerGetterBundle.getOrNull()
            matchCase = innerGetterBundle.get()
            anyLetter = innerGetterBundle.get()
            includeAuthor = innerGetterBundle.get()
            searchViewIsIconified = innerGetterBundle.get()
        }
    }

    private fun setupXml() = context.performIfNotNull {
        // toolbar nav click isa.
        binding.toolbar.setNavigationOnClickListener {
            getListenerOrNull()?.onNavIconClick()
        }

        // toolbar menu isa.
        binding.toolbar.postWithReceiver {
            val searchView = menu.findItem(R.id.search).actionView as SearchView
            searchView.setIconsTint(openSourceLicencesModel!!.toolbarIconsTint)
            searchView.textColor = openSourceLicencesModel!!.toolbarIconsTint
            searchView.firstNestedViewIsInstanceOrNull<EditText> {
                val hintColor = getColorFromRes(R.color.toolbar_subtitle_white)
                hint = buildSpannedString {
                    append("? ${getString(R.string.type_here_3_dots)}")

                    this[0] = DrawableSpan(getDrawableFromRes(R.drawable.ic_search_white_24dp, hintColor))
                }
                setHintTextColor(hintColor)

                setText(viewModel.mutableLiveDataSearchText.value)
                setSelectionToLastChar()

                searchView.isIconified = viewModel.mutableLiveDataSearchViewIsIconified.value ?: true

                imeOptions = EditorInfo.IME_ACTION_SEARCH or EditorInfo.IME_FLAG_NO_FULLSCREEN
            }
            searchView.forEachNested { view ->
                view.setBackgroundTintCompat(openSourceLicencesModel!!.toolbarIconsTint, PorterDuff.Mode.DST_ATOP)
            }

            searchView.setOnQueryTextListenerMA {
                onQueryTextChange {
                    viewModel.mutableLiveDataSearchText.value = it

                    true
                }
            }
            searchView.setOnSearchClickListener {
                viewModel.mutableLiveDataSearchViewIsIconified.value = false
            }
            searchView.setOnCloseListener {
                viewModel.mutableLiveDataSearchViewIsIconified.value = true

                false
            }
        }

        // recycler view isa.
        adapter = LicenseItemRVAdapter(
            this,
            this@OpenSourceLicencesFragment,
            licenseModelList,
            viewModel.mutableLiveDataSearchText.value,
            viewModel.mutableLiveDataMatchCase.value!!,
            viewModel.mutableLiveDataAnyLetter.value!!,
            viewModel.mutableLiveDataIncludeAuthor.value!!,
            openSourceLicencesModel!!,
            viewModel.mutableLiveDataLicensesAreEmpty
        )
        binding.recyclerView.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.mutableLiveDataSearchText.observe(this, Observer {
            adapter?.change(searchText = it, mutableLiveDataLicensesAreEmpty = viewModel.mutableLiveDataLicensesAreEmpty)
        })
        viewModel.mutableLiveDataMatchCase.observe(this, Observer {
            adapter?.change(matchCase = it, mutableLiveDataLicensesAreEmpty = viewModel.mutableLiveDataLicensesAreEmpty)
        })
        viewModel.mutableLiveDataAnyLetter.observe(this, Observer {
            adapter?.change(anyLetter = it, mutableLiveDataLicensesAreEmpty = viewModel.mutableLiveDataLicensesAreEmpty)
        })
        viewModel.mutableLiveDataIncludeAuthor.observe(this, Observer {
            adapter?.change(includeAuthor = it, mutableLiveDataLicensesAreEmpty = viewModel.mutableLiveDataLicensesAreEmpty)
        })
    }

    private fun getListenerOrNull(): Listener? {
        // activity
        (activity as? Listener)?.apply { return this }

        // parent fragment
        (parentFragment as? Listener)?.apply { return this }

        // context
        (context as? Listener)?.apply { return this }

        // activity of parent fragment
        (parentFragment?.activity as? Listener)?.apply { return this }

        // context of parent fragment
        (parentFragment?.context as? Listener)?.apply { return this }

        return null
    }

    // ----- Classes

    interface Listener {
        fun onNavIconClick()
    }

}