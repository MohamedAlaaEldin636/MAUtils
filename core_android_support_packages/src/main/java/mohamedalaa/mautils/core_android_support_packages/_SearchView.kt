package mohamedalaa.mautils.core_android_support_packages

import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SearchView
import mohamedalaa.mautils.core_android.allMatchingViewsIsInstanceOrNull
import mohamedalaa.mautils.core_android.firstMatchingViewIsInstanceOrNull

var SearchView.textColor: Int?
    get() {
        firstMatchingViewIsInstanceOrNull<EditText> {
            return this.currentTextColor
        }

        return null
    }
    set(value) {
        if (value == null) {
            return
        }

        firstMatchingViewIsInstanceOrNull<EditText> {
            this.setTextColor(value)
        }
    }

var SearchView.hintTextColor: Int?
    get() {
        firstMatchingViewIsInstanceOrNull<EditText> {
            return this.currentHintTextColor
        }

        return null
    }
    set(value) {
        if (value == null) {
            return
        }

        firstMatchingViewIsInstanceOrNull<EditText> {
            this.setHintTextColor(value)
        }
    }

var SearchView.text: String?
    get() {
        firstMatchingViewIsInstanceOrNull<EditText> {
            return this.text?.toString()
        }

        return null
    }
    set(value) {
        if (value == null) {
            return
        }

        firstMatchingViewIsInstanceOrNull<EditText> {
            this.setText(value)
        }
    }

fun SearchView.setIconsTint(@ColorInt color: Int) {
    allMatchingViewsIsInstanceOrNull<ImageView> {
        this.setColorFilter(color)
    }
}