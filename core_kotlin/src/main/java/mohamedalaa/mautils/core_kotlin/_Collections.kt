@file:JvmName("CollectionsUtils")

package mohamedalaa.mautils.core_kotlin

/** Performs given [action] only if collection is not null and not empty isa. */
inline fun <T, R: Collection<T>> R?.performIfNotNullNorEmpty(action: R.() -> Unit) {
    if (this != null && this.isNotEmpty()) {
        this.action()
    }
}