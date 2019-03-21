package mohamedalaa.mautils.material_design

import androidx.appcompat.widget.SearchView

typealias SearchView_OnQueryTextListener_Typealias = MASearchViewOnQueryTextListener.() -> Unit

class MASearchViewOnQueryTextListener(listener: SearchView_OnQueryTextListener_Typealias?): SearchView.OnQueryTextListener {

    init {
        listener?.invoke(this)
    }

    private var _onQueryTextChange: ((String?) -> Boolean)? = null
    private var _onQueryTextSubmit: ((String?) -> Boolean)? = null

    override fun onQueryTextChange(newText: String?): Boolean
        = _onQueryTextChange?.invoke(newText) ?: false

    /** execute [action] when [onQueryTextChange] is triggered isa. */
    infix fun MASearchViewOnQueryTextListener.onQueryTextChange(action: ((String?) -> Boolean)?): MASearchViewOnQueryTextListener
        = apply { _onQueryTextChange = action }

    override fun onQueryTextSubmit(query: String?): Boolean
        = _onQueryTextSubmit?.invoke(query) ?: false

    /** execute [action] when [onQueryTextSubmit] is triggered isa. */
    infix fun MASearchViewOnQueryTextListener.onQueryTextSubmit(action: ((String?) -> Boolean)?): MASearchViewOnQueryTextListener
        = apply { _onQueryTextSubmit = action }

}

/**
 * Using [listener] for [SearchView.setOnQueryTextListener] instead of regular [SearchView.OnQueryTextListener],
 * for more concise & idiomatic coding isa.
 *
 * @see [MASearchViewOnQueryTextListener]
 */
fun SearchView.setOnQueryTextListenerMA(listener: SearchView_OnQueryTextListener_Typealias?) {
    val genListener = MASearchViewOnQueryTextListener(listener)
    setOnQueryTextListener(genListener)
}