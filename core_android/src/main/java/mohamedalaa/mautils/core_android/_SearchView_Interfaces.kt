package mohamedalaa.mautils.core_android

import android.widget.SearchView

typealias SearchView_OnQueryTextListener_Typealias = MASearchViewOnQueryTextListener.() -> Unit

/**
 * More concise & idiomatic way for using more than 1 fun interface, See example below.
 * ```
 * fun setupOnQueryTextListener(searchView: SearchView) {
 *      searchView.setOnQueryTextListenerMA {
 *          onQueryTextChange {
 *              // Your code here
 *
 *              true // Depends on what you wanna achieve
 *          } onQueryTextSubmit {
 *              // Your code here
 *
 *              false // Depends on what you wanna achieve
 *          }
 *      }
 * }
 * ```
 *
 * @see [SearchView.setOnQueryTextListenerMA]
 */
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