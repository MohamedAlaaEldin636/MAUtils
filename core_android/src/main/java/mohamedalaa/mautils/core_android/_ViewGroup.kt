@file:JvmName("ViewGroupUtils")

package mohamedalaa.mautils.core_android

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.forEach

/**
 * @return the first [View] in [ViewGroup.forEach] that matches the given [predicate] isa.
 *
 * @see firstView
 */
inline fun ViewGroup.firstViewOrNull(predicate: (view: View) -> Boolean): View? {
    forEach {
        if (predicate(it)) {
            return it
        }
    }

    return null
}

/**
 * Same as [firstViewOrNull] but instead of returning null if [predicate] not satisfied
 * a [RuntimeException] is thrown instead isa.
 *
 * @throws RuntimeException in case if [predicate] is not met for all views isa.
 */
inline fun ViewGroup.firstView(predicate: (view: View) -> Boolean): View
    = firstViewOrNull(predicate) ?: throw RuntimeException("predicate fun is not satisfied for all views")

/**
 * @return the first [View] in [ViewGroup.forEachNested] that matches the given [predicate] isa.
 *
 * @see firstNestedView
 * @see allNestedViewsOrNull
 */
fun ViewGroup.firstNestedViewOrNull(predicate: (view: View) -> Boolean): View? {
    forEach {
        if (predicate(it)) {
            return it
        }else if (it is ViewGroup) {
            it.firstNestedViewOrNull(predicate)?.apply {
                return this
            }
        }
    }

    return null
}

/**
 * Same as [firstNestedViewOrNull] but instead of returning null if [predicate] not satisfied
 * a [RuntimeException] is thrown instead isa.
 *
 * @throws RuntimeException in case if [predicate] is not met for all nested views isa.
 */
fun ViewGroup.firstNestedView(predicate: (view: View) -> Boolean): View
    = firstNestedViewOrNull(predicate) ?: throw RuntimeException("predicate fun is not satisfied for all nested views")

/**
 * performs given [block] on first [View] in [ViewGroup.forEachNested] that is [T] type
 * then returns it isa.
 */
inline fun <reified T: View> ViewGroup.firstNestedViewIsInstanceOrNull(block: T.() -> Unit): T? {
    val view = firstNestedViewOrNull { it is T } as? T
    view?.block()

    return view
}

/** @return all [View]s in [ViewGroup.forEachNested] that matches given [predicate] isa. */
fun ViewGroup.allNestedViewsOrNull(predicate: (view: View) -> Boolean): List<View>?
    = mapNestedNotNull { if (predicate(it)) it else null }

/**
 * performs given [blockForEach] on all [View]s in [ViewGroup.forEachNested] that is [T] type
 * then returns them isa.
 */
inline fun <reified T: View> ViewGroup.allNestedViewsIsInstanceOrNull(blockForEach: T.() -> Unit): List<T>? {
    val list = mapNestedNotNull { it as? T }
    list.forEach {
        it.blockForEach()
    }

    return list
}

/**
 * performs the given [action] on each [View] in `receiver`, AND if that [View] is [ViewGroup] then
 * this fun is executed on it as well isa.
 */
fun ViewGroup.forEachNested(action: (View) -> Unit) {
    forEach {
        action(it)

        if (it is ViewGroup) {
            it.forEachNested(action)
        }
    }
}

/**
 * Returns a list containing the results of applying the given [transform] function to each [View]
 * in `receiver` isa.
 *
 * @see [ViewGroup.mapNotNull]
 */
inline fun <T> ViewGroup.map(transform: (View) -> T): List<T>
    = children.toList().map { transform(it) }

/**
 * Returns a list containing the results of applying the given [transform] function to each [View]
 * in [ViewGroup.forEachNested] isa.
 */
fun <T> ViewGroup.mapNested(transform: (View) -> T): List<T> {
    val list = mutableListOf<T>()

    forEachNested {
        list.add(transform(it))
    }

    return list
}

/** Same as [ViewGroup.map] but containing only non-null results isa. */
inline fun <T: Any> ViewGroup.mapNotNull(transform: (View) -> T?): List<T>
    = map(transform).mapNotNull { it }

/** Same as [ViewGroup.mapNested] but containing only non-null results isa. */
inline fun <T: Any> ViewGroup.mapNestedNotNull(block: (View) -> T?): List<T> {
    return mapNested { it }.mapNotNull { block(it) }
}
