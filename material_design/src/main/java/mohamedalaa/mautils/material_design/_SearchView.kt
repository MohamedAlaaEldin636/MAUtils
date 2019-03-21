package mohamedalaa.mautils.material_design

import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import mohamedalaa.mautils.core_android.allNestedViewsIsInstanceOrNull
import mohamedalaa.mautils.core_android.firstNestedViewIsInstanceOrNull

var SearchView.textColor: Int?
    get() {
        firstNestedViewIsInstanceOrNull<EditText> {
            return this.currentTextColor
        }

        return null
    }
    set(value) {
        if (value == null) {
            return
        }

        firstNestedViewIsInstanceOrNull<EditText> {
            this.setTextColor(value)
        }
    }

var SearchView.hintTextColor: Int?
    get() {
        firstNestedViewIsInstanceOrNull<EditText> {
            return this.currentHintTextColor
        }

        return null
    }
    set(value) {
        if (value == null) {
            return
        }

        firstNestedViewIsInstanceOrNull<EditText> {
            this.setHintTextColor(value)
        }
    }

var SearchView.text: String?
    get() {
        firstNestedViewIsInstanceOrNull<EditText> {
            return this.text?.toString()
        }

        return null
    }
    set(value) {
        if (value == null) {
            return
        }

        firstNestedViewIsInstanceOrNull<EditText> {
            this.setText(value)
        }
    }

fun SearchView.setIconsTint(@ColorInt color: Int) {
    allNestedViewsIsInstanceOrNull<ImageView> {
        this.setColorFilter(color)
    }
}