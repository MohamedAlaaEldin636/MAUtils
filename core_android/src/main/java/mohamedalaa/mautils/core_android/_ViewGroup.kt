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

/**
 * loop through `this` and every [ViewGroup] found till child view is [T] then return it isa.
 *
 * @param block performs given fun if successfully found the view isa.
 *
 * @return view of type [T] or null if cannot be found isa.
 *
 * @see firstMatchingViewOrNull
 * @see firstMatchingView
 */
inline fun <reified T> ViewGroup.firstMatchingViewIsInstanceOrNull(block: T.() -> Unit): T? {
    val view = firstMatchingViewOrNull(false) { it is T } as? T
    view?.block()

    return view
}

/**
 * todo
 * loop through `this` and every [ViewGroup] found till [predicate] returns true in that case
 * view is returned else if [predicate] is not met at all, null is returned Instead.
 *
 * @return non-empty list of ... or null if no view can apply to given [predicate] isa.
 *
 * @see firstMatchingView
 */
fun ViewGroup.allMatchingViewsOrNull(checkThisViewGroup: Boolean = true, predicate: (view: View) -> Boolean): List<View>? {
    val list = mutableListOf<View>()

    if (checkThisViewGroup && predicate(this)) {
        list.add(this)
    }

    forEach {
        if (predicate(it)) {
            list.add(it)
        }

        if (it is ViewGroup) {
            val innerList = it.allMatchingViewsOrNull(false, predicate) ?: return@forEach

            list.addAll(innerList)
        }
    }

    return if (list.isNotEmpty()) list else null
}

/**
 * todo
 * loop through `this` and every [ViewGroup] found till child view is [T] then return it isa.
 *
 * @param block performs given fun if successfully found the view isa.
 *
 * @return view of type [T] or null if cannot be found isa.
 *
 * @see firstMatchingViewOrNull
 * @see firstMatchingView
 */
inline fun <reified T> ViewGroup.allMatchingViewsIsInstanceOrNull(blockForEach: T.() -> Unit): List<T>? {
    val list = allMatchingViewsOrNull(false) { it is T }?.map { it as T }
    list?.forEach {
        it.blockForEach()
    }

    return list
}

fun ViewGroup.forEachAllViews(block: (View) -> Unit) {
    forEach {
        block(it)

        if (it is ViewGroup) {
            it.forEachAllViews(block)
        }
    }
}