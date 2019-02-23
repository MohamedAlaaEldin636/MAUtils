package mohamedalaa.mautils.mautils_open_source_licences.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.forEach
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_open_source_licences.*
import kotlinx.android.synthetic.main.activity_open_source_licences.view.*
import mohamedalaa.mautils.core_android.*
import mohamedalaa.mautils.mautils_open_source_licences.R
import mohamedalaa.mautils.mautils_open_source_licences.async_tasks.ReadFromAssetsAsyncTask
import mohamedalaa.mautils.mautils_open_source_licences.custom_classes.CustomDividerItemDecoration
import mohamedalaa.mautils.mautils_open_source_licences.extensions.setIconsTint
import mohamedalaa.mautils.mautils_open_source_licences.extensions.toArrayList
import mohamedalaa.mautils.mautils_open_source_licences.model.Licence
import mohamedalaa.mautils.mautils_open_source_licences.model.toStringList
import mohamedalaa.mautils.mautils_open_source_licences.view.adapters.RCAdapterLicence

// todo search and sort not added yet isa, find in details msln isa. (match case, any letter, both so checkbox as chips isa.)
class OpenSourceLicencesActivity : AppCompatActivity(), ReadFromAssetsAsyncTask.Listener {

    private lateinit var rcAdapter: RCAdapterLicence

    private var licences: List<Licence>? = null

    private var searchText: String? = null
    private var keyboardWasShown: Boolean = false

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

        /**
         * Change toolbar title color, default is white isa.
         *
         * @see INTENT_KEY_THEME_STYLE_RES
         * @see INTENT_KEY_TOOLBAR_ICON_TINT
         */
        const val INTENT_KEY_TOOLBAR_TITLE_TEXT_COLOR = "INTENT_KEY_TOOLBAR_TITLE_TEXT_COLOR"
        /**
         * Change toolbar nav icon tint color, default is [INTENT_KEY_TOOLBAR_TITLE_TEXT_COLOR] isa.
         *
         * @see INTENT_KEY_TOOLBAR_TITLE_TEXT_COLOR
         * @see INTENT_KEY_THEME_STYLE_RES
         */
        const val INTENT_KEY_TOOLBAR_ICON_TINT = "INTENT_KEY_TOOLBAR_ICON_TINT"

        const val INTENT_KEY_RC_ITEM_BACKGROUND_COLOR = "INTENT_KEY_RC_ITEM_BACKGROUND_COLOR"
        const val INTENT_KEY_RC_ITEM_BACKGROUND_DRAWABLE_RES = "INTENT_KEY_RC_ITEM_BACKGROUND_DRAWABLE_RES"

        const val INTENT_KEY_RC_ITEM_LICENCE_NAME_TEXT_COLOR = "INTENT_KEY_RC_ITEM_LICENCE_NAME_TEXT_COLOR"
        /** Default is [INTENT_KEY_RC_ITEM_LICENCE_NAME_TEXT_COLOR] isa. */
        const val INTENT_KEY_RC_ITEM_LICENCE_AUTHOR_TEXT_COLOR = "INTENT_KEY_RC_ITEM_LICENCE_AUTHOR_TEXT_COLOR"

        const val INTENT_KEY_RC_ITEM_DIVIDER_COLOR = "INTENT_KEY_RC_ITEM_LINK_BUTTON"
        /** Should be [Int] not [Float] isa. */
        const val INTENT_KEY_RC_ITEM_DIVIDER_DIMEN_IN_PX = "INTENT_KEY_RC_ITEM_DIVIDER_DIMEN_IN_PX"
        const val INTENT_KEY_RC_ITEM_DIVIDER_DRAWABLE_RES = "INTENT_KEY_RC_ITEM_DIVIDER_DRAWABLE_RES"

        const val INTENT_KEY_RC_ITEM_LINK_BUTTON_TEXT_COLOR = "INTENT_KEY_RC_ITEM_LINK_BUTTON_TEXT_COLOR"
        const val INTENT_KEY_RC_ITEM_LINK_BUTTON_TINT = "INTENT_KEY_RC_ITEM_LINK_BUTTON_TINT"

        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_NAME = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_NAME"
        /** Default is 75% opaque of [INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_NAME] isa. */
        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_AUTHOR = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_AUTHOR"
        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_TOOLBAR_ICONS_TINT = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_TOOLBAR_ICONS_TINT"
        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TEXT_COLOR = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TEXT_COLOR"
        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TINT = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_LINK_BUTTON_TINT"
        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_TEXT_COLOR = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_TEXT_COLOR"
        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_ENABLE_CUSTOM_FONT = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_ENABLE_CUSTOM_FONT"
        const val INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_BACKGROUND_COLOR = "INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_BACKGROUND_COLOR"

        private const val SAVED_INSTANCE_KEY_BUNDLES_SIZE = "SAVED_INSTANCE_KEY_BUNDLES_SIZE"
        private const val SAVED_INSTANCE_KEY_LIST_AS_ITEM = "SAVED_INSTANCE_KEY_LIST_AS_ITEM"

        private const val SAVED_INSTANCE_KEY_SEARCH_VIEW_KEYBOARD_IS_SHOWN = "SAVED_INSTANCE_KEY_SEARCH_VIEW_KEYBOARD_IS_SHOWN"
        private const val SAVED_INSTANCE_KEY_SEARCH_VIEW_TEXT = "SAVED_INSTANCE_KEY_SEARCH_VIEW_TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getExtraOrNull<Int>(INTENT_KEY_THEME_STYLE_RES)?.run { setTheme(this) }
        setContentView(R.layout.activity_open_source_licences)

        checkSavedInstanceState(savedInstanceState)

        setupXml()

        setupDevConfigurations()

        setupData(intent.getExtraOrNull<String>(INTENT_KEY_ASSETS_FOLDER_PATH) ?: "")
    }

    override fun onPause() {
        keyboardWasShown = isKeyboardShown()

        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val keyWithBundle = licences?.mapIndexed { index, licence ->
            val bundle = Bundle()

            bundle.putStringArrayList(SAVED_INSTANCE_KEY_LIST_AS_ITEM,
                licence.toStringList().toArrayList())

            index.toString() to bundle
        }

        outState?.apply {
            keyWithBundle?.run {
                forEach {
                    putBundle(it.first, it.second)
                }

                putInt(SAVED_INSTANCE_KEY_BUNDLES_SIZE, size)
            }

            putBoolean(SAVED_INSTANCE_KEY_SEARCH_VIEW_KEYBOARD_IS_SHOWN, keyboardWasShown)
            putString(SAVED_INSTANCE_KEY_SEARCH_VIEW_TEXT, if (searchView.visibility == View.VISIBLE) (searchView.text ?: "") else null)
        }

        super.onSaveInstanceState(outState)
    }

    // ---- Private fun

    private fun checkSavedInstanceState(savedInstanceState: Bundle?){
        savedInstanceState?.apply {
            searchText = getOrNull(SAVED_INSTANCE_KEY_SEARCH_VIEW_TEXT)
            keyboardWasShown = getOrNull(SAVED_INSTANCE_KEY_SEARCH_VIEW_KEYBOARD_IS_SHOWN) ?: false

            val size = getOrNull<Int>(SAVED_INSTANCE_KEY_BUNDLES_SIZE) ?: return

            licences = (0 until size).mapNotNull {
                val bundle = getOrNull<Bundle>(SAVED_INSTANCE_KEY_LIST_AS_ITEM) ?: return@mapNotNull null

                val list = bundle.getOrNull<ArrayList<String?>>(it.toString()) ?: return@mapNotNull null

                Licence.fromStringList(list)
            }
        }
    }

    private fun setupXml() {
        // Toolbar
        ViewCompat.setElevation(toolbar, dpToPx(4))
        toolbar.inflateMenu(R.menu.open_source_licences_activity)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.setOnMenuItemClickListener {
            when(it.title.toString()) {
                getString(R.string.search) -> {
                    searchView.visibility = View.VISIBLE

                    Handler().post {
                        searchView.isIconified = false
                    }
                }
            }

            true
        }
        toolbar.menu.forEach { it.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS) }
        searchText?.also {
            searchView.visibility = View.VISIBLE

            searchView.postWithReceiver {
                searchView.isIconified = false

                searchView.text = it
                searchView.firstMatchingViewIsInstanceOrNull<EditText> { setSelectionToLastChar() }

                if (keyboardWasShown) {
                    showKeyboardFor(searchView, true)
                }else {
                    searchView.clearFocus()
                }
            }
        }

        // Recycler View
        val dividerColor = intent.getExtraOrNull<Int>(INTENT_KEY_RC_ITEM_DIVIDER_COLOR)
        val dimenInPx = intent.getExtraOrNull<Int>(INTENT_KEY_RC_ITEM_DIVIDER_DIMEN_IN_PX)
        val dividerDrawableRes = intent.getExtraOrNull<Int>(INTENT_KEY_RC_ITEM_DIVIDER_DRAWABLE_RES)
        val itemDecoration = CustomDividerItemDecoration(this,
            dividerColor = dividerColor ?: getColorFromRes(R.color.rc_item_divider),
            dividerDrawable = dividerDrawableRes?.run { getDrawableFromRes(this) },
            dividerDimenInPx = dimenInPx)
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.layoutManager = LinearLayoutManager(this)

        rcAdapter = RCAdapterLicence(this, licences, intent)
        recyclerView.adapter = rcAdapter

        // Search View
        ViewCompat.setElevation(searchView, dpToPx(4))
        // -- no need to use searchView.setOnSearchClickListener {  }, since it is always not iconified isa.
        searchView.queryHint = getString(R.string.search_view_query_hint)
        searchView.firstMatchingViewIsInstanceOrNull<EditText> {
            imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        }
        searchView.setOnQueryTextListenerMA {
            onQueryTextChange {
                // todo change rc items with char highlighted isa, acc to match case any letter 2 chips isa.

                true
            } onQueryTextSubmit {
                hideKeyboardFrom(searchView)

                true
            }
        }
        searchView.setOnCloseListener {
            hideKeyboardFrom(searchView)

            searchView.visibility = View.INVISIBLE

            true
        }
    }

    private fun setupDevConfigurations() {
        // Toolbar -> title & icon tint isa.
        val toolbarTitleColor = intent.getExtraOrNull<Int>(INTENT_KEY_TOOLBAR_TITLE_TEXT_COLOR)
        val toolbarIconColor = intent.getExtraOrNull<Int>(INTENT_KEY_TOOLBAR_ICON_TINT)
        when {
            toolbarTitleColor != null -> {
                toolbar.setTitleTextColor(toolbarTitleColor)

                val otherColor = toolbarIconColor ?: toolbarTitleColor

                toolbar.setIconsTint(otherColor)

                searchView.textColor = otherColor
                searchView.hintTextColor = otherColor.addColorAlpha(0.75f)
                searchView.setIconsTint(otherColor)
            }
            toolbarIconColor != null -> {
                toolbar.setIconsTint(toolbarIconColor)

                searchView.textColor = toolbarIconColor
                searchView.hintTextColor = toolbarIconColor.addColorAlpha(0.75f)
                searchView.setIconsTint(toolbarIconColor)
            }
            else -> {
                toolbar.setIconsTint(Color.WHITE)

                searchView.textColor = Color.WHITE
                searchView.hintTextColor = Color.WHITE.addColorAlpha(0.75f)
                searchView.setIconsTint(Color.WHITE)
            }
        }

        // Recycler View Item Background
        intent.getExtraOrNull<Int>(INTENT_KEY_RC_ITEM_BACKGROUND_COLOR)?.apply {
            recyclerView.backgroundCompat = ColorDrawable(this)
        } ?: intent.getExtraOrNull<Int>(INTENT_KEY_RC_ITEM_BACKGROUND_DRAWABLE_RES)?.apply {
            recyclerView.backgroundCompat = getDrawableFromRes(this)
        }
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
