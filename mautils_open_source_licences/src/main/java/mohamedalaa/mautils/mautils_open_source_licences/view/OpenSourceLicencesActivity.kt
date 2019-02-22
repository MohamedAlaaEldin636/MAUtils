package mohamedalaa.mautils.mautils_open_source_licences.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_open_source_licences.*
import mohamedalaa.mautils.mautils_open_source_licences.R
import mohamedalaa.mautils.mautils_open_source_licences.async_tasks.ReadFromAssetsAsyncTask
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.view.adapters.RCAdapterLicence

// todo tell whoever uses this MUST isa, have in gradle dataBinding { enabled = true } isa.
// todo maybe remove live data, view model and data binding since they are not used intensively
// so no need to increase lib size, also consider as well constraint layout isa.

// todo auto add myself, so user don't have to add yourself isa, either in const val kda msln, AW txt file in my own assets folder isa.
// fa badal application context use context bs isa.

// todo remove local dependencies if can be replaced isa.

// todo another module for getViewModel since it is not used here isa.
class OpenSourceLicencesActivity : AppCompatActivity(), ReadFromAssetsAsyncTask.Listener {

    private lateinit var rcAdapter: RCAdapterLicence

    private var licences: List<Licence>? = null

    companion object {
        /** */
        const val INTENT_KEY_USE_OWN_THEME = "INTENT_KEY_USE_OWN_THEME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_source_licences)

        // todo on save instance state ba2a isa.

        // TODO see optional colors isa.
        setupXml()

        setupData()
    }

    private fun setupXml() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        // todo item divider isa.

        rcAdapter = RCAdapterLicence(licences)
        recyclerView.adapter = rcAdapter
    }

    private fun setupData() {
        if (licences.isNullOrEmpty()) {
            ReadFromAssetsAsyncTask.launch(this, "", this)
        }

        deliverResult(licences)
    }

    // ---- Implementing ReadFromAssetsAsyncTask interface

    override fun deliverResult(result: List<Licence>?) {
        licences = result
        rcAdapter.swapList(licences)

        when {
            result == null -> {
                recyclerView.visibility = View.GONE
                emptyView.visibility = View.GONE
                loadingFrameLayout.visibility = View.VISIBLE
            }
            result.isEmpty() -> {
                recyclerView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
                loadingFrameLayout.visibility = View.GONE
            }
            else -> {
                recyclerView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                loadingFrameLayout.visibility = View.GONE
            }
        }
    }

}
