package mohamedalaa.mautils.mautils_open_source_licences

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_open_source_licences.*
import mohamedalaa.mautils.core_android.getExtraOrNull
import mohamedalaa.mautils.mautils_open_source_licences.async_tasks.ReadFromAssetsAsyncTask
import mohamedalaa.mautils.mautils_open_source_licences.databinding.ActivityOpenSourceLicencesBinding
import mohamedalaa.mautils.mautils_open_source_licences.extensions.getViewModel
import mohamedalaa.mautils.mautils_open_source_licences.view_model.OpenSourceLicencesViewModel

class OpenSourceLicencesActivity : AppCompatActivity() {

    companion object {
        /**
         * Intent key represents Theme of this activity for action bar and loading progress bar
         * colors pass style res of the theme Ex. E.style.my_theme isa.
         */
        const val INTENT_KEY_THEME_RES = "INTENT_KEY_THEME_RES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getExtraOrNull<Int>(INTENT_KEY_THEME_RES)?.apply { setTheme(this) }
        val binding: ActivityOpenSourceLicencesBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_open_source_licences)

        // View model
        val viewModel = getViewModel<OpenSourceLicencesViewModel>()

        // setup binding
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // view model for visibilities, presenter for clicks isa.
        // and on launch async task isa.
        // setup rc isa.
        setupXml()

        setupData()
    }

    private fun setupXml() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        // todo item layout and adapter isa.
        // todo item divider isa.
    }

    private fun setupData() {
        //applicationContext.assets.list("")

        //ReadFromAssetsAsyncTask.launch(this, "")
    }

}
