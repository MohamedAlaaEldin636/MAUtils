package mohamedalaa.mautils.mautils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mohamedalaa.mautils.core_android.setMaxLength
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
            }

            startActivity(intent)
        }
    }
}
