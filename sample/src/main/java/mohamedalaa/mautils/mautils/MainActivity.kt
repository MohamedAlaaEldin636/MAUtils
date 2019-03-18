package mohamedalaa.mautils.mautils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mohamedalaa.mautils.core_android.setMaxLength
import mohamedalaa.mautils.mautils.recycler_view.VisualTestActivity
import mohamedalaa.mautils.core_android.toast as toast1
import mohamedalaa.mautils.mautils_open_source_licences.view.OpenSourceLicencesActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText1.setMaxLength(4)
        editText2.setMaxLength(4, true)

        button.setOnClickListener {
            val intent = Intent(this, OpenSourceLicencesActivity::class.java).apply {
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ASSETS_FOLDER_PATH, "licences")
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_THEME_STYLE_RES, R.style.MyOwnOpenSourceLicencesTheme)

                /*putExtra(OpenSourceLicencesActivity.INTENT_KEY_TOOLBAR_TITLE_TEXT_COLOR, Color.BLACK)

                putExtra(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_BACKGROUND_COLOR, Color.LTGRAY)

                putExtra(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_LICENCE_NAME_TEXT_COLOR, Color.RED)

                putExtra(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_DIVIDER_COLOR, Color.RED)

                putExtra(OpenSourceLicencesActivity.INTENT_KEY_RC_ITEM_LINK_BUTTON_TEXT_COLOR, Color.GREEN)

                putExtra(OpenSourceLicencesActivity.INTENT_KEY_SEARCH_HIGHLIGHT_COLOR, Color.BLUE)

                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_NAME, Color.GREEN)
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_AUTHOR, Color.GREEN)
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_TOOLBAR_ICONS_TINT, Color.GREEN)
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TEXT_COLOR, Color.BLACK)
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TINT, Color.BLUE)
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_TEXT_COLOR, Color.GREEN)
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_ENABLE_CUSTOM_FONT, false)
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_BACKGROUND_COLOR, Color.CYAN)*/
            }

            startActivity(intent)
        }

        // For Quick Starts isa.
        startActivity(Intent(this, VisualTestActivity::class.java))
    }
}
