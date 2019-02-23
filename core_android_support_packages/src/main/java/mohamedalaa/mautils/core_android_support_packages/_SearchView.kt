package mohamedalaa.mautils.core_android_support_packages

import android.widget.EditText
import androidx.appcompat.widget.SearchView
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