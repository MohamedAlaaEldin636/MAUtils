/*
 * Copyright (c) 2019 Mohamed Alaa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package mohamedalaa.mautils.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import kotlinx.android.synthetic.main.activity_main.*
import mohamedalaa.mautils.core_android.extensions.setMaxLength
import mohamedalaa.mautils.core_android.extensions.startActivity
import mohamedalaa.mautils.gson.fromJson
import mohamedalaa.mautils.gson.toJson
import mohamedalaa.mautils.mautils.R
import mohamedalaa.mautils.sample.custom_views.CustomViewsMainActivity
import mohamedalaa.open_source_licences.custom_classes.Constants
import mohamedalaa.open_source_licences.custom_classes.OpenSourceLicencesForActivityInterface
import mohamedalaa.open_source_licences.extensions.startActivityForOpenSourceLicences
import mohamedalaa.open_source_licences.model.LicenceModel
import mohamedalaa.open_source_licences.model.OpenSourceLicencesModel
import mohamedalaa.open_source_licences.view.fragments.OpenSourceLicencesFragment

class MainActivity : AppCompatActivity(), OpenSourceLicencesFragment.Listener {

    val int = 1000 * 1000

    /*var intVal: Int
    var stringVal: String
    var doubleVal: Double
    var floatArray: FloatArray

    // Add all variables to Bundle immediately instead of creating keys for it.
    // add any objects that is supported by Bundle
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.apply {
            addValues(
                intVal,
                stringVal,
                doubleVal,
                floatArray
            )
        }

        super.onSaveInstanceState(outState)
    }

    // But when you retrieve it only limitation is
    // it MUST be retrieved in same order
    override fun onCreate(savedInstanceState: Bundle?) {
        // ...
        savedInstanceState?.apply {
            val getterBundle = getterBundle()

            intVal = getterBundle.get()
            stringVal = getterBundle.getOrNull() ?: "fallback"
            doubleVal = getterBundle.get()
            floatArray = getterBundle.get()
        }
    }*/

    private fun launchOtherActivitiesIsa() {
        startActivity<CustomViewsMainActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        @Suppress("ConstantConditionIf")
        if (true) {
            launchOtherActivitiesIsa()
            return
        }

        editText1.setMaxLength(4)
        editText2.setMaxLength(4, true)

        val o1 = OpenSourceLicencesModel(this).apply {
            assetsFolderPath = "mido"
        }

        val j1 = o1.toJson()

        val r1 = j1.fromJson<OpenSourceLicencesModel>()

        require(o1.assetsFolderPath == r1.assetsFolderPath)

        button.setOnClickListener {
            /*val intent = Intent(this, OpenSourceLicencesActivity::class.java).apply {
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ASSETS_FOLDER_PATH, "licences")
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_THEME_STYLE_RES,
                    R.style.MyOwnOpenSourceLicencesTheme
                )

                *//*putExtra(OpenSourceLicencesActivity.INTENT_KEY_TOOLBAR_TITLE_TEXT_COLOR, Color.BLACK)

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
                putExtra(OpenSourceLicencesActivity.INTENT_KEY_ITEM_DETAIL_ACTIVITY_LICENCE_CONTENT_BACKGROUND_COLOR, Color.CYAN)*//*
            }*/

            //startActivity(intent)
            startActivityForOpenSourceLicences(listOf(
                LicenceModel(
                    "Gson",
                    "Google Inc.",
                    "https://github.com/google/gson",
                    Constants.APACHE_2_0_LICENCE_NAME,
                    listOf("Copyright 2008 Google Inc.")
                )
            )) {
                //styleRes = R.style.AppTheme
                toolbarNavIconDrawableRes = R.drawable.ic_arrow_back_white_24dp

                //searchHighlightColor = getColorFromAttrRes(R.attr.colorAccent)

                openSourceLicencesForActivityInterfaceTreatNavIconAsOnBackPressed = true
                openSourceLicencesForActivityInterfaceClassFullName = OnOpenSourceActivityNavIconClick::class.java.name
            }
        }

        // For Quick Starts isa.
        //startActivity(Intent(this, VisualTestActivity::class.java))
        //startActivity(Intent(this, BoatActivity::class.java))
        //startActivity(Intent(this, MaterialDesignMainActivity::class.java))
        supportFragmentManager.commit {
            replace(R.id.containerFrameLayout, OpenSourceLicencesFragment(
                listOf(
                    LicenceModel(
                        "Gson",
                        "Google Inc.",
                        "https://github.com/google/gson",
                        Constants.APACHE_2_0_LICENCE_NAME,
                        listOf("Copyright 2008 Google Inc.")
                    )
                ),
                OpenSourceLicencesModel(this@MainActivity).apply {
                    toolbarNavIconDrawableRes = R.drawable.ic_filter_list_black_24dp
                }
            ))
        }
    }

    class OnOpenSourceActivityNavIconClick : OpenSourceLicencesForActivityInterface {
        override fun onNavIconClick(activity: AppCompatActivity) {
            Log.e("HII", ".......")
            activity.onBackPressed()
        }
    }

    override fun onNavIconClick() {
        onBackPressed()
    }
}
