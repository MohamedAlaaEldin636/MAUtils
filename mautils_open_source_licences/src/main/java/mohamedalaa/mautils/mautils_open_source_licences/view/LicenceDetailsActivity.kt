package mohamedalaa.mautils.mautils_open_source_licences.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_licence_details.*
import mohamedalaa.mautils.core_android.getExtra
import mohamedalaa.mautils.core_android.getExtraOrNull
import mohamedalaa.mautils.core_android.launchWebLink
import mohamedalaa.mautils.mautils_open_source_licences.R
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.model.isAuthorExists
import mohamedalaa.mautils.mautils_open_source_licences.model.isLinkExists

internal class LicenceDetailsActivity : AppCompatActivity() {

    companion object {
        internal const val INTENT_KEY_BUNDLES_SIZE = "INTENT_KEY_BUNDLES_SIZE"
        internal const val SUB_INTENT_KEY_LIST_AS_ITEM = "SUB_INTENT_KEY_LIST_AS_ITEM"

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

        setupXml()
    }

    private fun resolveIntent() {
        val licenceName = intent.getExtra<String>(INTENT_KEY_LICENCE_NAME)
        val licenceAuthor = intent.getExtraOrNull<String>(INTENT_KEY_LICENCE_AUTHOR)
        val link = intent.getExtraOrNull<String>(INTENT_KEY_LINK)
        val licenceContent = intent.getExtra<String>(INTENT_KEY_LICENCE_CONTENT)

        licence = Licence(licenceName, licenceAuthor, link, licenceContent)
    }

    private fun setupXml() {
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.title = licence.licenceName
        toolbar.subtitle = if (licence.isAuthorExists) getString(R.string.by_colon_string, licence.licenceAuthor) else ""
        if (licence.isLinkExists) {
            linkButton.visibility = View.VISIBLE

            linkButton.setOnClickListener {
                licence.link?.apply { launchWebLink(this) }
            }
        }else {
            linkButton.visibility = View.GONE
        }
        licenceContentTextView.text = licence.licenceContent
    }

}
