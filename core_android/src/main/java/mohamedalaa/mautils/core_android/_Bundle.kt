@file:JvmName("BundleUtils")

package mohamedalaa.mautils.core_android

import android.os.Bundle

/**
 * @return true if `receiver` is null or isEmpty
 */
fun Bundle?.isNullOrEmpty(): Boolean = this == null || this.isEmpty