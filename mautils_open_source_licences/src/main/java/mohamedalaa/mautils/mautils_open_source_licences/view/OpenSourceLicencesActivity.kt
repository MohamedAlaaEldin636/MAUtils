package mohamedalaa.mautils.mautils_open_source_licences.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_open_source_licences.*
import mohamedalaa.mautils.core_android.dpToPx
import mohamedalaa.mautils.core_android.getColorFromRes
import mohamedalaa.mautils.core_android.getExtraOrNull
import mohamedalaa.mautils.mautils_open_source_licences.R
import mohamedalaa.mautils.mautils_open_source_licences.async_tasks.ReadFromAssetsAsyncTask
import mohamedalaa.mautils.mautils_open_source_licences.custom_classes.CustomDividerItemDecoration
import mohamedalaa.mautils.mautils_open_source_licences.extensions.toArrayList
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.model.toStringList
import mohamedalaa.mautils.mautils_open_source_licences.view.adapters.RCAdapterLicence

// todo make compileOnly for gson, to save in on save instance state, or let it reload in orient changes isa.
class OpenSourceLicencesActivity : AppCompatActivity(), ReadFromAssetsAsyncTask.Listener {

    private lateinit var rcAdapter: RCAdapterLicence

    private var licences: List<Licence>? = null

    companion object {

        /**
         * If your .txt licences are in the root directory of assets folder then no need to add
         * `this` key in [Intent] isa.
         *
         * **Note** that folder target will loop through all .txt files in it, so if your app
         * has .txt files other than licences then put all licences in a sub folder of assets,
         * then surely mention path to folder with `this` key isa.
         */
        const val INTENT_KEY_ASSETS_FOLDER_PATH = "INTENT_KEY_ASSETS_FOLDER_PATH"

        /**
         * put R.style.YourTheme with this key, to have your own theme for this activity isa,
         *
         * **Warning** YourTheme **MUST** extend from either Theme.MaterialComponents.Light.NoActionBar
         * OR Theme.MaterialComponents.Light.NoActionBar.Bridge isa.
         *
         * - ### Views affected
         * 1. Status bar -> colorPrimaryDark
         * 2. Toolbar -> colorPrimary
         * 3. Item Button -> colorAccent
         * 4. Progress Bar -> colorAccent
         */
        const val INTENT_KEY_THEME_STYLE_RES = "INTENT_KEY_THEME_STYLE_RES"


        //  better make it ?.apply pattern isa... as Intent ext fun isa.
        //  see in detail activity if more is needed isa. like text font, size etc... isa.

        // make recycler view color same as below isa, ashan mayeb2ash fe far2 isa.
        const val INTENT_KEY_RC_ITEM_BACKGROUND_COLOR = "INTENT_KEY_RC_ITEM_BACKGROUND_COLOR"
        const val INTENT_KEY_RC_ITEM_BACKGROUND_DRAWABLE_RES = "INTENT_KEY_RC_ITEM_BACKGROUND_DRAWABLE_RES"

        const val INTENT_KEY_RC_ITEM_LICENCE_NAME_TEXT_COLOR = "INTENT_KEY_RC_ITEM_LICENCE_NAME_TEXT_COLOR"
        // LIKE ABOVE ISA.
        const val INTENT_KEY_RC_ITEM_LICENCE_AUTHOR_TEXT_COLOR = "INTENT_KEY_RC_ITEM_LICENCE_AUTHOR_TEXT_COLOR"

        const val INTENT_KEY_RC_ITEM_DIVIDER_COLOR = "INTENT_KEY_RC_ITEM_LINK_BUTTON"
        const val INTENT_KEY_RC_ITEM_DIVIDER_DIMEN_IN_PX = "INTENT_KEY_RC_ITEM_DIVIDER_DIMEN_IN_PX"
        const val INTENT_KEY_RC_ITEM_DIVIDER_DRAWABLE_RES = "INTENT_KEY_RC_ITEM_DIVIDER_DRAWABLE_RES"

        const val INTENT_KEY_RC_ITEM_LINK_BUTTON_TEXT_COLOR = "INTENT_KEY_RC_ITEM_LINK_BUTTON_TEXT_COLOR"
        const val INTENT_KEY_RC_ITEM_LINK_BUTTON_TINT = "INTENT_KEY_RC_ITEM_LINK_BUTTON_TINT"
        //  DEFAULT LIKE ABOVE ISA.
        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TINT = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TINT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getExtraOrNull<Int>(INTENT_KEY_THEME_STYLE_RES)?.run { setTheme(this) }
        setContentView(R.layout.activity_open_source_licences)

        setupXml()

        setupData(intent.getExtraOrNull<String>(INTENT_KEY_ASSETS_FOLDER_PATH) ?: "")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val keyWithBundle = licences?.mapIndexed { index, licence ->
            val bundle = Bundle()

            bundle.putStringArrayList(LicenceDetailsActivity.SUB_INTENT_KEY_LIST_AS_ITEM,
                licence.toStringList().toArrayList())

            index.toString() to bundle
        }

        outState?.apply {
            keyWithBundle?.run {
                forEach {
                    putBundle(it.first, it.second)
                }

                putInt(LicenceDetailsActivity.INTENT_KEY_BUNDLES_SIZE, size)
            }
        }

        // todo on create to restore isa.

        super.onSaveInstanceState(outState)
    }

    // ---- Private fun

    private fun setupXml() {
        // Toolbar
        ViewCompat.setElevation(toolbar, dpToPx(4))
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Recycler View
        val itemDecoration = CustomDividerItemDecoration(this,
            dividerColor = getColorFromRes(R.color.rc_item_divider))
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.layoutManager = LinearLayoutManager(this)

        rcAdapter = RCAdapterLicence(this, licences, intent)
        recyclerView.adapter = rcAdapter
    }

    private fun setupData(folderPathInAssets: String) {
        if (licences.isNullOrEmpty()) {
            ReadFromAssetsAsyncTask.launch(this, folderPathInAssets, this)
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
