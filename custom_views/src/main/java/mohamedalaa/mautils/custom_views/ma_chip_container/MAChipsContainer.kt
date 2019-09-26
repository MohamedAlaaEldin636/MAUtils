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

package mohamedalaa.mautils.custom_views.ma_chip_container

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.databinding.InverseBindingListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import mohamedalaa.mautils.core_android.extensions.*
import mohamedalaa.mautils.core_kotlin.extensions.toStringOrEmpty
import mohamedalaa.mautils.custom_views.R
import mohamedalaa.mautils.gson.*
import kotlin.math.roundToInt

/**
 * ## Usage
 * 1. works like [ChipGroup] but with better easier approaches for more control with precise amount of coding,
 * and new features as well isa, such as two-way data binding for checked chips isa, and last checked ones isa.
 *
 * ## TODO S
 * 1. Add ability to change via BA chips per row OR change names not checked ones only isa, with or without anim to support Entry style
 * since in Entry we should be able to delete chips isa.
 * 2. other features of regular chips and grouped one isa.
 * 3. ellipsize - make char sequence instead of string for title and subtitle isa.
 * 4. ability to have auto size but if not specified we will use normal not uniform isa.
 * 5. chip color state list and color isa, in case dark theme isa, and expose default color after searching what it is isa
 * of default non checked and checke isa.
 *
 * ## Notes
 * 1. cannot have subtitle without a title
 * 2. any of title or subtitle is gone in visibility if it is null not empty.
 * 3. custom attr checkedChipsNames can be array res or string res (to indicate 1 string in a list)
 * but the corresponding binding adapter only get a param of a list isa.
 *
 * ## **Full XMl Attributes**
 * ```
 * <mohamedalaa.mautils.custom_views.ma_chip_container.MAChipsContainer
 *      android:layout_width="match_parent"
 *      android:layout_height="wrap_content"
 *
 *      android:layout_margin="8dp"
 *      app:chipsMargin="8dp"
 *      app:betweenTitleOrSubtitleAndChipsMargin="8dp"
 *      app:betweenTitleAndSubtitleMargin="0dp"
 *
 *      app:title="Title"
 *      app:titleTextSize="22sp"
 *      app:titleTextColor="@android:color/black"
 *      app:titleMinLines="1"
 *      app:titleMaxLines="1"
 *      app:titleLines="1"
 *
 *      app:subtitle="Subtitle"
 *      app:subtitleTextSize="18sp"
 *      app:subtitleTextColor="#BF000000"
 *      app:subtitleMinLines="1"
 *      app:subtitleMaxLines="1"
 *      app:subtitleLines="1"
 *
 *      app:chipsNames="@array/MAChipsContainer_AllChipsNames"
 *      app:chipsPerRow="2"
 *
 *      app:rowsExcluded="@array/MAChipsContainer_rowsExcluded"
 *      app:chipsExcludedPerRowsExcluded="@array/MAChipsContainer_chipsExcludedPerRowsExcluded"
 *
 *      tools:checkedChipsNames="@string/chip_2"
 *      app:checkedChipsNames="@={viewModel.mutableLiveDataCheckedChipsNames}"
 *      app:maChipsContainer_setOnChipCheckedChangeListener="@{presenter.onChipsContainerChipCheckedChangeListener(context)}"
 *
 *      app:isChipWidthMatchParent="true"
 *
 *      app:chipsStyle="Filter" />
 *
 * // Variables in the attributes
 *
 * <integer-array name="MAChipsContainer_rowsExcluded">
 *      <item>3</item>
 * </integer-array>
 * <integer-array name="MAChipsContainer_chipsExcludedPerRowsExcluded">
 *      <item>1</item>
 * </integer-array>
 * <string-array name="MAChipsContainer_AllChipsNames">
 *      <item>@string/chip_1</item>
 *      <item>@string/chip_2</item>
 *      <item>@string/chip_3</item>
 *      <item>@string/chip_4</item>
 *      <item>@string/chip_5</item>
 *      <item>@string/chip_6</item>
 *      <item>@string/chip_7</item>
 *      <item>@string/chip_8</item>
 * </string-array>
 *
 * val mutableLiveDataCheckedChipsNames = QuickMutableLiveData(listOf(getString(R.string.chip_1)))
 *
 * fun onChipsContainerChipCheckedChangeListener(context: Context)
 *      = fun (_: MAChipsContainer, changedChipName: String, newValueIsChecked: Boolean) {
 *          logError("PRESENTER changedChipName $changedChipName to $newValueIsChecked")
 * }
 *
 * ```
 */
class MAChipsContainer @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var title: CharSequence?
    @Dimension val titleTextSize: Float
    @ColorInt val titleTextColor: Int
    val titleMinLines: Int
    val titleMaxLines: Int
    val titleLines: Int
    @Dimension val betweenTitleOrSubtitleAndChipsMargin: Int

    var subtitle: CharSequence?
    @Dimension val subtitleTextSize: Float
    @ColorInt val subtitleTextColor: Int
    val subtitleMinLines: Int
    val subtitleMaxLines: Int
    val subtitleLines: Int
    @Dimension val betweenTitleAndSubtitleMargin: Int

    val chipsPerRow: Int
    val rowsExcluded: List<Int>?
    val chipsExcludedPerRowsExcluded: List<Int>?

    val chipsNames: List<String>
    val chipsMargin: Int
    val halfChipsMargin: Int

    val isChipWidthMatchParent: Boolean
    internal val chipsStyle: ChipsStyle
    val checkedChipsNames: List<String>

    internal var betweenCurrentAndLastCheckedChipsNames = emptyList<String>()
    internal var lastCheckedChipsNames = emptyList<String>()

    internal var inverseBindingListener: InverseBindingListener? = null
    internal var onChipCheckedChangeListener: OnChipCheckedChangeListener? = null

    /**
     * Do NOT use it in saved state, it's only job is to not call change listener when we restore
     * saved state of checkedNames isa (Ex. orientation change) so that should be initial input
     * like on start of that activity not change to chips checked state isa.
     */
    internal var forceNotInvokeCheckedChangeListener = false

    init {
        // Custom Attributes
        val dp8ToPx = context.dpToPx(8).roundToInt()
        val sp22ToPx = context.spToPx(22)
        val sp18ToPx = context.spToPx(18)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MAChipsContainer)

        title = typedArray.getText(R.styleable.MAChipsContainer_title)
        titleTextSize = typedArray.getDimension(R.styleable.MAChipsContainer_titleTextSize, sp22ToPx)
        titleTextColor = typedArray.getColor(R.styleable.MAChipsContainer_titleTextColor, Color.BLACK)
        titleMinLines = typedArray.getInteger(R.styleable.MAChipsContainer_titleMinLines, 1)
        titleMaxLines = typedArray.getInteger(R.styleable.MAChipsContainer_titleMaxLines, 1)
        titleLines = typedArray.getInteger(R.styleable.MAChipsContainer_titleLines, 0)
        betweenTitleOrSubtitleAndChipsMargin = typedArray.getDimensionPixelOffset(R.styleable.MAChipsContainer_betweenTitleOrSubtitleAndChipsMargin, dp8ToPx)

        subtitle = typedArray.getText(R.styleable.MAChipsContainer_subtitle)
        subtitleTextSize = typedArray.getDimension(R.styleable.MAChipsContainer_subtitleTextSize, sp18ToPx)
        subtitleTextColor = typedArray.getColor(R.styleable.MAChipsContainer_subtitleTextColor, Color.BLACK.addColorAlpha(0.75f))
        subtitleMinLines = typedArray.getInteger(R.styleable.MAChipsContainer_subtitleMinLines, 1)
        subtitleMaxLines = typedArray.getInteger(R.styleable.MAChipsContainer_subtitleMaxLines, 1)
        subtitleLines = typedArray.getInteger(R.styleable.MAChipsContainer_subtitleLines, 0)
        betweenTitleAndSubtitleMargin = typedArray.getDimensionPixelOffset(R.styleable.MAChipsContainer_betweenTitleAndSubtitleMargin, 0)

        chipsPerRow = typedArray.getInteger(R.styleable.MAChipsContainer_chipsPerRow, 1).apply {
            if (this < 1) throw RuntimeException("app:chipsPerRow cannot be < 1 isa.")
        }
        rowsExcluded = typedArray.getResourceId(R.styleable.MAChipsContainer_rowsExcluded, 0).run {
            if (this == 0) null else resources.getIntArray(this)
        }?.toList()
        chipsExcludedPerRowsExcluded = typedArray.getResourceId(R.styleable.MAChipsContainer_chipsExcludedPerRowsExcluded, 0).run {
            if (this == 0) null else resources.getIntArray(this)
        }?.toList()

        chipsNames = typedArray.getResourceId(R.styleable.MAChipsContainer_chipsNames, 0).run {
            if (this == 0) null else resources.getStringArray(this)
        }?.toList() ?: throw RuntimeException("MUST provide at least 1 chip isa.")
        if (chipsNames.size != chipsNames.distinct().size) {
            throw RuntimeException("app:chipsNames MUST have unique names of chips isa.")
        }
        chipsMargin = typedArray.getDimensionPixelOffset(R.styleable.MAChipsContainer_chipsMargin, dp8ToPx)
        halfChipsMargin = chipsMargin.div(2)

        isChipWidthMatchParent = typedArray.getBoolean(R.styleable.MAChipsContainer_isChipWidthMatchParent, false)
        chipsStyle = ChipsStyle.values()[typedArray.getInt(R.styleable.MAChipsContainer_chipsStyle, ChipsStyle.Filter.ordinal)]
        checkedChipsNames = typedArray.getResourceId(R.styleable.MAChipsContainer_checkedChipsNames, 0).run {
            if (this == 0) emptyList() else when (resources.getResourceTypeName(this)) {
                "array" -> (if (this == 0) arrayOf() else resources.getStringArray(this)).toList()
                "string" -> listOf(resources.getString(this))
                else -> emptyList()
            }
        }

        typedArray.recycle()

        // Custom Attrs Checks
        if (chipsStyle == ChipsStyle.Choice && checkedChipsNames.size > 1) {
            throw RuntimeException("Choice style can only have 1 checked chip at a time, kindly check app:checkedChipsNames attr isa.")
        }

        // Inflate Views
        val titleViewId = addTitleIfPossibleAndGetIdOrNull()

        addChips(titleViewId)

        // After Adding Views Checks
        if (chipsStyle == ChipsStyle.Choice || chipsStyle == ChipsStyle.Filter) {
            // If style is Choice then at least 1 chip has to be checked isa.
            if (chipsStyle == ChipsStyle.Choice && checkedChipsNames.isEmpty()) {
                firstNestedViewIsInstanceOrNull<Chip> {
                    isChecked = true

                    lastCheckedChipsNames = listOf(text.toString())
                }
            }else {
                lastCheckedChipsNames = checkedChipsNames
            }
            betweenCurrentAndLastCheckedChipsNames = lastCheckedChipsNames

            children.filterIsInstance<Chip>().forEach { child ->
                child.setOnCheckedChangeListener { compoundButton, isChecked ->
                    onCheckedChangeListenerSetupsForChoiceAndFilterOnly(compoundButton, isChecked)
                }
            }
        }
    }

    // ---- Overridden fun isa. ( Only vars need surviving isa, and special case chips need their old ids otherwise a crash will occur isa. )

    override fun onSaveInstanceState(): Parcelable? {
        val ids = children.map { it.id }.toList()

        return buildBundleGson(
            ids.forceUsingJsonInBundle(),
            super.onSaveInstanceState(),
            lastCheckedChipsNames.forceUsingJsonInBundle(),
            betweenCurrentAndLastCheckedChipsNames.forceUsingJsonInBundle(),
            getCurrentCheckedChipsNames().forceUsingJsonInBundle(),

            title,
            subtitle
        )
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val getterBundleGson = state.getterBundleGson()

            // Act on restored values isa.
            val generatedIds = getterBundleGson.getOrNull<List<Int>>() ?: emptyList()
            generatedIds.zip(children.toList()).forEach {
                it.second.id = it.first
            }
            super.onRestoreInstanceState(
                getterBundleGson.getOrNull<Parcelable>()
            )
            lastCheckedChipsNames = getterBundleGson.getOrNull() ?: emptyList()
            betweenCurrentAndLastCheckedChipsNames = getterBundleGson.getOrNull() ?: emptyList()
            forceNotInvokeCheckedChangeListener = true
            setCurrentCheckedChipsNames(
                getterBundleGson.getOrNull() ?: emptyList()
            )
            forceNotInvokeCheckedChangeListener = false

            setTitle(getterBundleGson.getOrNull())
            setSubtitle(getterBundleGson.getOrNull())
        }else {
            super.onRestoreInstanceState(state)
        }
    }

    // ---- Public fun

    /**
     * @return last selection for the chips note this might confuse you in one special case which is
     * using binding adapter with no default then in case of Filter chipsStyle this returns empty on
     * it's first invocation not what's in the binding adapter unless it is changed surely, and
     * if style was Choice it returns first chip name isa.
     */
    fun getLastCheckedChipsNames(): List<String> = lastCheckedChipsNames

    /**
     * Works the same as [setCurrentCheckedChipsNames] but sets all chips as checked isa.
     *
     * @throws RuntimeException if style is Choice, since that style requires only 1 checked
     * chip at a time isa.
     */
    fun setAllChipsAsChecked() {
        if (chipsStyle == ChipsStyle.Choice) throw RuntimeException("cannot use setAllChipsAsChecked with Choice style for chips isa.")

        val allNames = getAllChips().map { it.text.toStringOrEmpty() }.toList()
        setCurrentCheckedChipsNames(allNames)
    }

    // ---- Internal fun

    internal interface OnChipCheckedChangeListener {
        fun afterChange(maChipsContainer: MAChipsContainer, changedChipName: String, newValueIsChecked: Boolean)
    }

}