package mohamedalaa.mautils.reflection

inline fun <reified T : Any> T.isObjectInstance(): Boolean
    = this::class.isObject

inline fun <reified T : Any> T.isInterfaceInstance(): Boolean
    = this::class.isInterface

inline fun <reified T : Any> T.isAbstractInstance(): Boolean
    = this::class.isAbstract

inline fun <reified T : Any> T.isSealedInstance(): Boolean
    = this::class.isSealed

inline fun <reified T : Any> T.isPrimitiveInstance(): Boolean
    = this::class.isPrimitive