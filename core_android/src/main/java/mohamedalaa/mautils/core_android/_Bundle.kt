@file:JvmName("BundleUtils")

package mohamedalaa.mautils.core_android

import android.os.Bundle

/**
 * @return true if `receiver` is null or isEmpty
 */
fun Bundle?.isNullOrEmpty(): Boolean = this == null || this.isEmpty

inline fun <reified T> Bundle?.getOrNull(key: String?): T? = this?.get(key) as? T

inline fun <reified T> Bundle?.get(key: String?): T = getOrNull<T>(key)
    ?: throw RuntimeException("Cannot get ${T::class}, from key == $key isa.")