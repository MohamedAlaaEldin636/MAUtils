package mohamedalaa.mautils.mautils_open_source_licences

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_open_source_licences.*
import mohamedalaa.mautils.mautils_open_source_licences.async_tasks.ReadFromAssetsAsyncTask
import mohamedalaa.mautils.mautils_open_source_licences.databinding.ActivityOpenSourceLicencesBinding
import mohamedalaa.mautils.mautils_open_source_licences.extensions.getViewModel
import mohamedalaa.mautils.mautils_open_source_licences.view_model.OpenSourceLicencesViewModel

// todo tell whoever uses this MUST isa, have in gradle dataBinding { enabled = true } isa.
class OpenSourceLicencesActivity : AppCompatActivity() {

    companion object {
        /**
         * Intent key represents Theme of this activity for action bar and loading progress bar
         * colors pass style res of the theme Ex. E.style.my_theme isa.
         */
        const val INTENT_KEY_THEME_RES = "INTENT_KEY_THEME_RES"
    }

    // TODO or better isa, use only my theme no action bar and user only boolean if use his theme
    // and set tool bar and colors and others programmatically isa.
    // todo either theme given by opp must implement no existence of action bar ashan el toolbar yshtaghal isa
    // todo wala faks a3melo hide programmatically isa.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //intent.getExtraOrNull<Int>(INTENT_KEY_THEME_RES)?.apply { setTheme(this) }
        //setTheme()
        val binding: ActivityOpenSourceLicencesBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_open_source_licences)

        // todo if it makes it w/ animation instead of immediately isa, see ->
        // https://developer.android.com/training/system-ui/status
        // https://stackoverflow.com/questions/19545370/android-how-to-hide-actionbar-on-certain-activities
        supportActionBar?.hide()

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

        ReadFromAssetsAsyncTask.launch(this, "")
    }

}
