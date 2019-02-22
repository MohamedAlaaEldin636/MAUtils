package mohamedalaa.mautils.mautils_open_source_licences

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_open_source_licences.*
import mohamedalaa.mautils.core_android.toast
import mohamedalaa.mautils.mautils_open_source_licences.async_tasks.ReadFromAssetsAsyncTask
import mohamedalaa.mautils.mautils_open_source_licences.databinding.ActivityOpenSourceLicencesBinding
import mohamedalaa.mautils.mautils_open_source_licences.extensions.getViewModel
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.view_model.OpenSourceLicencesViewModel

// todo tell whoever uses this MUST isa, have in gradle dataBinding { enabled = true } isa.
// todo maybe remove live data, view model and data binding since they are not used intensively
// so no need to increase lib size, also consider as well constraint layout isa.
class OpenSourceLicencesActivity : AppCompatActivity(), ReadFromAssetsAsyncTask.Listener {

    private lateinit var viewModel: OpenSourceLicencesViewModel

    companion object {
        /** */
        const val INTENT_KEY_USE_OWN_THEME = "INTENT_KEY_USE_OWN_THEME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityOpenSourceLicencesBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_open_source_licences)

        // View model
        viewModel = getViewModel()

        // setup binding
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // TODO see optional colors isa.
        setupXml()

        setupData()
    }

    private fun setupXml() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        // todo item layout and adapter isa.
        // todo item divider isa.
    }

    private fun setupData() {
        // todo see saved instance state isa.
        ReadFromAssetsAsyncTask.launch(this, "", this)
    }

    private fun unexpectedErrorSoFinish() {
        toast("Unexpected error occurred")

        finish()
    }

    // ---- Implementing ReadFromAssetsAsyncTask interface

    override fun deliverResult(result: List<Licence>?) {
        result?.apply {
            if (isEmpty()) {
                unexpectedErrorSoFinish()
            }

            // todo swap adapter isa.
            forEach {
                val msg = "$it\n"

                Log.e("OpenSource", msg)
            }

            viewModel.loading.value = false
        } ?: unexpectedErrorSoFinish()
    }

}
