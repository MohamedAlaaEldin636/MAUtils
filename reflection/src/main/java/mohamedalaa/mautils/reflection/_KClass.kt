package mohamedalaa.mautils.reflection

import kotlin.reflect.KClass

val KClass<*>.isObject: Boolean
    get() = this.objectInstance != null

val KClass<*>.isInterface: Boolean
    get() = java.isInterface

val KClass<*>.isPrimitive: Boolean
    get() = javaPrimitiveType != null