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

package mohamedalaa.mautils.open_source_licences.view

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_licence_details.*
import mohamedalaa.mautils.core_android.*
import mohamedalaa.mautils.open_source_licences.R
import mohamedalaa.mautils.open_source_licences.model.Licence
import mohamedalaa.mautils.open_source_licences.model.isAuthorExists
import mohamedalaa.mautils.open_source_licences.model.isLinkExists

internal class LicenceDetailsActivity : AppCompatActivity() {

    companion object {
        internal const val INTENT_KEY_LICENCE_NAME = "INTENT_KEY_LICENCE_NAME"
        internal const val INTENT_KEY_LICENCE_AUTHOR = "INTENT_KEY_LICENCE_AUTHOR"
        internal const val INTENT_KEY_LINK = "INTENT_KEY_LINK"
        internal const val INTENT_KEY_LICENCE_CONTENT = "INTENT_KEY_LICENCE_CONTENT"
    }

    private lateinit var licence: Licence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_THEME_STYLE_RES)?.run { setTheme(this) }
        setContentView(R.layout.activity_licence_details)

        // fetch data from intent isa.
        resolveIntent()

        setupDevConfigurations()

        setupXml()
    }

    private fun resolveIntent() {
        val licenceName = intent.getExtra<String>(INTENT_KEY_LICENCE_NAME)
        val licenceAuthor = intent.getExtraOrNull<String>(INTENT_KEY_LICENCE_AUTHOR)
        val link = intent.getExtraOrNull<String>(INTENT_KEY_LINK)
        val licenceContent = intent.getExtra<String>(INTENT_KEY_LICENCE_CONTENT)

        licence = Licence(licenceName, licenceAuthor, link, licenceContent)
    }

    private fun setupDevConfigurations() {
        // Toolbar
        val nameColor = intent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_NAME)
        val authorColor = intent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_AUTHOR)
        when {
            nameColor != null -> {
                toolbar.setTitleTextColor(nameColor)

                toolbar.setSubtitleTextColor(authorColor ?: nameColor.addColorAlpha(0.75f))
            }
            authorColor != null -> toolbar.setSubtitleTextColor(authorColor)
        }

        intent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_TOOLBAR_ICONS_TINT)?.apply {
            toolbar.navigationIcon.tint(this)
        }

        // Change link button isa
        intent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TEXT_COLOR)?.apply {
            linkButton.setTextColor(this)
        }
        intent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TINT)?.apply {
            linkButton.setBackgroundTint(this)
        }

        // Licence content configs isa.
        intent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_TEXT_COLOR)?.apply {
            licenceContentTextView.setTextColor(this)
        }
        val enableCustomFont = intent.getExtraOrNull<Boolean>(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_ENABLE_CUSTOM_FONT)
            ?: true
        if (enableCustomFont) {
            val typeface = ResourcesCompat.getFont(this@LicenceDetailsActivity, R.font.source_sans_pro_light)
            licenceContentTextView.typeface = typeface
        }
        intent.getExtraOrNull<Int>(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_BACKGROUND_COLOR)?.apply {
            nestedScrollView.backgroundCompat = ColorDrawable(this)
        }
    }

    private fun setupXml() {
        ViewCompat.setElevation(toolbar, dpToPx(4))
        ViewCompat.setElevation(linkButtonFrameLayout, dpToPx(4))
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.title = licence.licenceName
        toolbar.subtitle = if (licence.isAuthorExists) getString(R.string.by_colon_string, licence.licenceAuthor) else ""
        if (licence.isLinkExists) {
            linkButton.visibility = View.VISIBLE

            linkButton.setOnClickListener {
                licence.link?.apply { launchWebLink(this, createIntentChooser = true) }
            }
        }else {
            linkButton.visibility = View.GONE
        }
        licenceContentTextView.text = licence.licenceContent
    }

}
