@file:JvmName("ClassUtils")

package mohamedalaa.mautils.core_kotlin

/**
 * @return true if `receiver` equals or instance-of(can be assigned to) baseClass, false otherwise isa.
 */
infix fun Class<*>?.instanceOf(baseClass: Class<*>?): Boolean {
    if (this == null || baseClass == null) {
        return false
    }

    return this == baseClass || baseClass.isAssignableFrom(this)
}
