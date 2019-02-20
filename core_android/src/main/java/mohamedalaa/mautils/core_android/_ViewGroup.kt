@file:JvmName("ViewGroupUtils")

package mohamedalaa.mautils.core_android

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach

/**
 * loop through `this` and every [ViewGroup] found till [predicate] returns true in that case
 * view is returned else if [predicate] is not met at all, null is returned Instead.
 *
 * @return view which if sent as param for [predicate] true is returned, or null if not met for all views.
 *
 * @see firstMatchingView
 */
fun ViewGroup.firstMatchingViewOrNull(checkThisViewGroup: Boolean = true, predicate: (view: View) -> Boolean): View? {
    if (checkThisViewGroup && predicate(this)) {
        return this
    }

    forEach {
        if (predicate(it)) {
            return it
        }else if (it is ViewGroup) {
            it.firstMatchingViewOrNull(false, predicate)?.apply {
                return this
            }
        }
    }

    return null
}

/**
 * loop through `this` and every [ViewGroup] found till [predicate] returns true in that case
 * view is returned else if [predicate] is not met at all, throwing [RuntimeException] is done Instead.
 *
 * @return view which if sent as param for [predicate] true is returned, or throws [RuntimeException] if not met for all views.
 *
 * @throws RuntimeException in case if [predicate] is not met for all views isa.
 *
 * @see firstMatchingViewOrNull
 */
fun ViewGroup.firstMatchingView(checkThisViewGroup: Boolean = true, predicate: (view: View) -> Boolean): View
    = firstMatchingViewOrNull(checkThisViewGroup, predicate) ?: throw RuntimeException("predicate fun is not satisfied for all views")