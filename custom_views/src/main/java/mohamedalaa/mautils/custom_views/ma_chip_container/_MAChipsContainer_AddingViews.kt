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

import android.graphics.Color
import android.graphics.PorterDuff
import android.text.Layout
import android.text.style.AlignmentSpan
import android.util.TypedValue
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.text.buildSpannedString
import androidx.core.view.ViewCompat
import com.google.android.material.chip.Chip
import mohamedalaa.mautils.core_android.extensions.getDrawableFromRes
import mohamedalaa.mautils.core_android.extensions.inflateLayout
import mohamedalaa.mautils.core_android.extensions.plusAssign
import mohamedalaa.mautils.core_android.extensions.tintColorFilter
import mohamedalaa.mautils.custom_views.R

/**
 * Adds textView to be the title for the chips if [MAChipsContainer.title] is not null nor empty isa.
 *
 * @return if of the added title or null if not added isa.
 */
internal fun MAChipsContainer.addTitleIfPossibleAndGetIdOrNull(): Int? {
    // -- Title -- //

    // Checks for title text
    if (title == null) return null

    // View
    val titleTextView = context.inflateLayout(R.layout.mautils_include_text_view, this) as TextView
    val titleId = ViewCompat.generateViewId()
    titleTextView.id = titleId

    titleTextView.text = title
    titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize)
    titleTextView.setTextColor(titleTextColor)

    if (titleMaxLines < titleMinLines) throw RuntimeException("title's maxLines cannot be < min lines isa.")
    val titleTempLines = when {
        titleLines > 0 -> titleLines
        titleMinLines == titleMaxLines -> titleMinLines
        else -> null
    }
    if (titleTempLines != null) {
        titleTextView.setLines(titleTempLines)
    }else {
        titleTextView.minLines = titleMinLines
        titleTextView.maxLines = titleMaxLines
    }

    // Layout Params
    val titleLayoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
        rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
    }

    addView(titleTextView, titleLayoutParams)

    // Checks for subtitle text isa.
    if (subtitle == null) return titleId

    // Subtitle
    val subTitleTextView = context.inflateLayout(R.layout.mautils_include_text_view, this) as TextView
    val subtitleId = ViewCompat.generateViewId()
    subTitleTextView.id = subtitleId

    subTitleTextView.text = subtitle
    subTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, subtitleTextSize)
    subTitleTextView.setTextColor(subtitleTextColor)

    if (subtitleMaxLines < subtitleMinLines) throw RuntimeException("subtitle's maxLines cannot be < min lines isa.")
    val subTitleTempLines = when {
        subtitleLines > 0 -> subtitleLines
        subtitleMinLines == subtitleMaxLines -> subtitleMinLines
        else -> null
    }
    if (subTitleTempLines != null) {
        subTitleTextView.setLines(subTitleTempLines)
    }else {
        subTitleTextView.minLines = titleMinLines
        subTitleTextView.maxLines = titleMaxLines
    }

    // Layout Params
    val subtitleLayoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        topToBottom = titleId
        leftToLeft = titleId
        rightToRight = titleId

        topMargin = betweenTitleAndSubtitleMargin
    }

    addView(subTitleTextView, subtitleLayoutParams)

    return subtitleId
}

internal fun MAChipsContainer.addChips(titleViewId: Int?) {
    val rows = getRowsOfChipsNames()

    val changeIsChecked = chipsStyle == ChipsStyle.Choice || chipsStyle == ChipsStyle.Filter

    var previousAboveId: Int? = null
    for (row in rows) {
        val marginTop = if (previousAboveId == null) {
            if (titleViewId == null) 0 else betweenTitleOrSubtitleAndChipsMargin
        }else {
            chipsMargin
        }
        previousAboveId = addSingleRow(row, previousAboveId ?: titleViewId, marginTop, changeIsChecked)
    }
}

/**
 * @param aboveId if null use parentId isa.
 *
 * @param changeIsChecked if logical to change it ex. style is Choice or Filter isa.
 *
 * @return current id to be above (first chip's id in this row) isa.
 */
internal fun MAChipsContainer.addSingleRow(
    names: List<String>,
    aboveId: Int?,
    marginTop: Int,
    changeIsChecked: Boolean
): Int {
    val ids = names.map { ViewCompat.generateViewId() }
    for (index in names.indices) {
        // -- Add view -- //

        val chipView = context.inflateLayout(R.layout.mautils_include_chip, this) as Chip
        chipView.id = ids[index]

        val chipText = names[index]
        chipView.text = buildSpannedString {
            append(names[index])

            this += AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
        }
        chipView.applyStyle(chipsStyle)
        chipView.isChecked = changeIsChecked && chipText in checkedChipsNames

        val params = ConstraintLayout.LayoutParams(
            if (isChipWidthMatchParent) 0 else ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            // Top margin
            if (index == 0) {
                this.topMargin = marginTop

                horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD
            }

            // Left & Right margins isa.
            if (names.size == 1) {
                leftMargin = 0
                rightMargin = 0

                return@apply
            }
            when (index) {
                0 -> {
                    leftMargin = 0
                    rightMargin = halfChipsMargin
                }
                names.lastIndex -> {
                    leftMargin = halfChipsMargin
                    rightMargin = 0
                }
                else -> {
                    leftMargin = halfChipsMargin
                    rightMargin = halfChipsMargin
                }
            }
        }

        // -- Connect final constraintSet isa. -- //

        // top & bottom
        if (index == 0) {
            if (aboveId != null) {
                params.topToBottom = aboveId
            }else {
                params.topToTop = ConstraintSet.PARENT_ID
            }
        }else {
            params.topToTop = ids[0]
            params.bottomToBottom = ids[0]
        }

        // left & right
        val leftId = index.dec().run { if (this < 0) ConstraintSet.PARENT_ID else ids[this] }
        val rightId = index.inc().run { if (this == names.size) ConstraintSet.PARENT_ID else ids[this] }
        if (leftId == ConstraintSet.PARENT_ID) {
            params.leftToLeft = leftId
        }else {
            params.leftToRight = leftId
        }
        if (rightId == ConstraintSet.PARENT_ID) {
            params.rightToRight = rightId
        }else {
            params.rightToLeft = rightId
        }

        addView(chipView, params)
    }

    return ids[0]
}

// ---- Private fun

/** index 0 represents row 1 and inside it all chips names in that row isa. */
private fun MAChipsContainer.getRowsOfChipsNames(): List<List<String>> {
    val rows = mutableListOf<List<String>>()

    var keepLooping = true
    var currentRowNumber = 1
    val currentLeftChipsNames = chipsNames.toMutableList()
    do {
        val namesList = mutableListOf<String>()

        val chipsNum = if (currentRowNumber in rowsExcluded.orEmpty()) {
            chipsExcludedPerRowsExcluded?.get(rowsExcluded?.indexOf(currentRowNumber) ?: -1)
                .throwNotMatchedExcludesOrNotValidNumberInARow()
        }else {
            chipsPerRow
        }
        for (index in 1..chipsNum) {
            if (currentLeftChipsNames.size < 1) {
                break
            }

            namesList.add(currentLeftChipsNames[0])
            currentLeftChipsNames.removeAt(0)
        }

        rows.add(namesList)

        currentRowNumber++
        if (currentLeftChipsNames.size == 0) {
            keepLooping = false
        }
    }while (keepLooping)

    return rows
}

private fun Chip.applyStyle(chipsStyle: ChipsStyle) {
    // Note checkable is ignored isa.
    when (chipsStyle) {
        ChipsStyle.ACTION -> {
            isCloseIconVisible = false
            isCheckable = false
        }
        ChipsStyle.Choice -> {
            isCheckable = true

            isChipIconVisible = false
            isCheckedIconVisible = false
            isCloseIconVisible = false

            checkedIcon = context.getDrawableFromRes(R.drawable.ic_done_black_24dp).apply {
                tintColorFilter(Color.BLACK, PorterDuff.Mode.DST_IN, true)
            }
        }
        ChipsStyle.Entry -> {
            isCloseIconVisible = true
            isCheckable = false
        }
        ChipsStyle.Filter -> {
            isCheckable = true

            isChipIconVisible = false
            isCloseIconVisible = false

            checkedIcon = context.getDrawableFromRes(R.drawable.ic_done_black_24dp).apply {
                tintColorFilter(Color.BLACK, PorterDuff.Mode.DST_IN, true)
            }
        }
    }
}

private fun Int?.throwNotMatchedExcludesOrNotValidNumberInARow(): Int {
    return this?.run {
        if (this > 0) this else throw RuntimeException("Nu. of chips in any row MUST be > 0 isa.")
    } ?: throw RuntimeException("excluded rows do not match excluded chips isa.")
}
