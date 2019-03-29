package mohamedalaa.myapplication.mautils_gson_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate Sealed Class, Abstract Class or Interface
 * <br></br>
 * in case it's inside one of the objects that needs to be serialized/deserialized
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface MASealedAbstractOrInterface {}
