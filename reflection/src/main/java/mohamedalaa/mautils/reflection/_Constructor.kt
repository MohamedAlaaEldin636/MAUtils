package mohamedalaa.mautils.reflection

import java.lang.reflect.Constructor
import kotlin.reflect.KParameter
import kotlin.reflect.jvm.kotlinFunction

val Constructor<*>.kParametersOrNull: List<KParameter>?
    get() = kotlinFunction?.parameters

val Constructor<*>.kParameters: List<KParameter>
    get() = kParametersOrNull ?: throw RuntimeException("Cannot get params")