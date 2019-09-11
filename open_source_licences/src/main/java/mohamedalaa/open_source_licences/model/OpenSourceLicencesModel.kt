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

@file:Suppress("UnusedImport")

package mohamedalaa.open_source_licences.model

import android.app.Activity
import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import mohamedalaa.mautils.core_android.extensions.addColorAlpha
import mohamedalaa.mautils.core_android.extensions.getColorFromAttrRes
import mohamedalaa.mautils.core_android.extensions.getColorFromRes
import mohamedalaa.mautils.material_design.setIconsTint
import mohamedalaa.open_source_licences.R
import mohamedalaa.open_source_licences.custom_classes.OpenSourceLicencesForActivityInterface
import mohamedalaa.open_source_licences.extensions.startActivityForOpenSourceLicences
import mohamedalaa.open_source_licences.view.OpenSourceLicencesActivity

/**
 * - **VIP** either your app extend this theme Theme.MaterialComponents or pass in [styleRes]
 * any theme that extends it since we use [Chip] component isa.
 * - **VIP** use same `context` to launch [OpenSourceLicencesActivity] as the provided param here,
 * This is not a must but it's the best case isa.
 * - rv means [RecyclerView] isa.
 *
 * @property assetsFolderPath If your .txt licences are in the root directory of assets folder then
 * use empty string (default), Ex. licences .txt files were inside folder "Sub" which is inside
 * folder "Parent" then assign this var to "Parent/Sub" isa, **NOTE** these .txt licences have
 * a name of the licence name and content of the licence itself isa, so you can have infinite number
 * of licences with only ` .txt file if all have same licence the infinite num will be in [LicenceModel] isa.
 *
 * @property styleRes theme to be used by invoking [Activity.setTheme] before [Activity.setContentView] isa,
 * **CAUTION** given res MUST extend any theme with **NoActionBar** in it isa.
 *
 * @property toolbarIconsTint using [Toolbar.setIconsTint] isa.
 *
 * @property openSourceLicencesForActivityInterfaceClassFullName implementer of [OpenSourceLicencesForActivityInterface]
 * via [Class.getName] isa, **Only Used** in case of [startActivityForOpenSourceLicences] isa.
 * @property openSourceLicencesForActivityInterfaceTreatNavIconAsOnBackPressed **Only Used** in case of [startActivityForOpenSourceLicences] isa.
 */
class OpenSourceLicencesModel(context: Context) {
    var assetsFolderPath: String = ""

    @StyleRes var styleRes: Int = 0

    @ColorInt var toolbarTitleTextColor: Int = Color.WHITE
    @ColorInt var toolbarSubtitleTextColor: Int = context.getColorFromRes(R.color.toolbar_subtitle_white)
    var toolbarIconsTint = Color.WHITE
    @DrawableRes var toolbarNavIconDrawableRes: Int? = null

    @DrawableRes var rvItemBackgroundDrawableRes = 0
    @ColorInt var rvItemBackgroundColor = Color.WHITE
    @ColorInt var rvItemTextsColor = Color.BLACK

    @ColorInt var rvItemDividerColor = context.getColorFromRes(R.color.rv_item_divider)
    var rvItemDividerThicknessInDp = 1

    @ColorInt var rvItemLinkButtonTextColor = Color.WHITE
    @ColorInt var rvItemLinkButtonBackgroundColor = context.getColorFromAttrRes(R.attr.colorAccent)

    @ColorInt var searchHighlightColor = Color.YELLOW.addColorAlpha(0.8f)

    @ColorInt var licenceDetailsTextColor = Color.BLACK
    @ColorInt var licenceDetailsBackgroundColor = Color.WHITE

    var openSourceLicencesForActivityInterfaceClassFullName: String = ""
    var openSourceLicencesForActivityInterfaceTreatNavIconAsOnBackPressed: Boolean = false

}