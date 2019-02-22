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
            startActivity(Intent(this, OpenSourceLicencesActivity::class.java))
        }
    }
}
