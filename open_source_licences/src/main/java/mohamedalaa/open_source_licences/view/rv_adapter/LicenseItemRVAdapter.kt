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

package mohamedalaa.open_source_licences.view.rv_adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.open_source_licences.R
import mohamedalaa.open_source_licences.model.LicenceModel
import mohamedalaa.open_source_licences.model.OpenSourceLicencesModel
import mohamedalaa.open_source_licences.view.fragments.OpenSourceLicencesFragment
import mohamedalaa.open_source_licences.view.rv_adapter.extensions.createToBeHighlightedIndices
import mohamedalaa.open_source_licences.view.rv_adapter.extensions.filterLicenseModelList
import java.lang.ref.WeakReference
import androidx.core.text.set
import androidx.lifecycle.MutableLiveData
import mohamedalaa.mautils.core_android.extensions.*

internal class LicenseItemRVAdapter(
    context: Context,
    fragment: OpenSourceLicencesFragment,

    private val licenseModelList: List<LicenceModel>,

    searchText: String?,
    matchCase: Boolean,
    anyLetter: Boolean,
    includeAuthor: Boolean,

    private val openSourceLicencesModel: OpenSourceLicencesModel,

    mutableLiveDataLicensesAreEmpty: MutableLiveData<Boolean>
) : RecyclerView.Adapter<LicenseItemRVAdapter.ViewHolder>() {

    var searchText: String? = searchText
        private set
    var matchCase: Boolean = matchCase
        private set
    var anyLetter: Boolean = anyLetter
        private set
    var includeAuthor: Boolean = includeAuthor
        private set

    private val itemBackground = context.getDrawableFromResOrNull(openSourceLicencesModel.rvItemBackgroundDrawableRes)
        ?: openSourceLicencesModel.rvItemBackgroundColor.toDrawable()
    private val itemButtonTintList = ColorStateList.valueOf(openSourceLicencesModel.rvItemLinkButtonBackgroundColor)

    private val byString = context.getString(R.string.by)
    private val licenceString = context.getString(R.string.licence)

    private val weakRefFragment = WeakReference(fragment)

    private var filteredLicenseModelList = filterLicenseModelList(licenseModelList).apply {
        mutableLiveDataLicensesAreEmpty.value = isEmpty()
    }

    override fun getItemCount(): Int = filteredLicenseModelList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.context.inflateLayout(R.layout.item_license, parent, false)

        return ViewHolder(view).apply {
            // Dev configurations
            rootLinearLayout.backgroundCompat = itemBackground

            openSourceNameAppCompatTextView.setTextColor(openSourceLicencesModel.rvItemTextsColor)
            licenceAuthorAppCompatTextView.setTextColor(openSourceLicencesModel.rvItemTextsColor)
            licenceNameAppCompatTextView.setTextColor(openSourceLicencesModel.rvItemTextsColor)

            linkMaterialButton.setTextColor(openSourceLicencesModel.rvItemLinkButtonTextColor)
            linkMaterialButton.backgroundTintList = itemButtonTintList
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val licenceModel = filteredLicenseModelList[position]

        val highlightText = searchText.isNullOrEmpty().not()

        // [name]
        holder.openSourceNameAppCompatTextView.text = buildSpannedString {
            append(licenceModel.openSourceName)
            if (highlightText.not()) return@buildSpannedString

            if (setSpansAndCheckIfIsSpanned()) {
                holder.openSourceNameAppCompatTextView.checkEllipsize()
            }
        }

        // By : [author]
        holder.licenceAuthorAppCompatTextView.text = buildSpannedString {
            append("$byString : ${licenceModel.listOfAuthorsNames.joinToString(", ")}")
            if (highlightText.not() || includeAuthor.not()) return@buildSpannedString

            if (setSpansAndCheckIfIsSpanned()) {
                holder.openSourceNameAppCompatTextView.checkEllipsize()
            }
        }

        // Licence : [name]
        holder.licenceNameAppCompatTextView.text = "$licenceString : ${licenceModel.licenceName}"

        // Item Click isa.
        holder.rootLinearLayout.setOnOneClickListener {
            weakRefFragment.get()?.apply {
                presenter.launchDetailedOpenSourceActivity(
                    this, licenceModel
                )
            }
        }

        // Button Click isa.
        holder.linkMaterialButton.setOnOneClickListener {
            weakRefFragment.get()?.apply {
                context?.launchWebLink(licenceModel.openSourceLink, createIntentChooser = true)
            }
        }
    }

    /** @return true if at least 1 index should be highlighted isa. */
    private fun SpannableStringBuilder.setSpansAndCheckIfIsSpanned(): Boolean {
        val toBeHighlightedIndices = createToBeHighlightedIndices(toStringOrEmpty())

        for (index in toBeHighlightedIndices) {
            this[index] = ForegroundColorSpan(openSourceLicencesModel.searchHighlightColor)
        }

        return toBeHighlightedIndices.isNotEmpty()
    }

    private fun AppCompatTextView.checkEllipsize() = postWithReceiver {
        val count = layout.getEllipsisCount(0)
        if (count > 0) {
            val start = layout.getEllipsisStart(0)
            val end = start + count

            text = buildSpannedString {
                append(text)

                this[start..end] = ForegroundColorSpan(openSourceLicencesModel.searchHighlightColor)
            }
        }
    }

    // ---- Public fun

    fun change(
        searchText: String? = this.searchText,
        matchCase: Boolean = this.matchCase,
        anyLetter: Boolean = this.anyLetter,
        includeAuthor: Boolean = this.includeAuthor,
        mutableLiveDataLicensesAreEmpty: MutableLiveData<Boolean>
    ) {
        if (this.searchText == searchText
            && this.matchCase == matchCase
            && this.anyLetter == anyLetter
            && this.includeAuthor == includeAuthor) {
            return
        }

        this.searchText = searchText
        this.matchCase = matchCase
        this.anyLetter = anyLetter
        this.includeAuthor = includeAuthor

        filteredLicenseModelList = filterLicenseModelList(licenseModelList).apply {
            mutableLiveDataLicensesAreEmpty.value = isEmpty()
        }

        notifyDataSetChanged()
    }

    // ----- Classes

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootLinearLayout: LinearLayout = itemView.findViewById(R.id.rootLinearLayout)
        val openSourceNameAppCompatTextView: AppCompatTextView = itemView.findViewById(R.id.openSourceNameAppCompatTextView)
        val licenceAuthorAppCompatTextView: AppCompatTextView = itemView.findViewById(R.id.licenceAuthorAppCompatTextView)
        val licenceNameAppCompatTextView: AppCompatTextView = itemView.findViewById(R.id.licenceNameAppCompatTextView)
        val linkMaterialButton: MaterialButton = itemView.findViewById(R.id.linkMaterialButton)
    }

}