package mohamedalaa.myapplication.mautils_gson_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate Sealed Class, Abstract Class or Interface
 * <br></br>
 * in case it's inside one of the objects that needs to be serialized/deserialized, see examples below isa
 * <br></br>
 * <pre>
 * class Holder {
 *     // this is an abstract class which needs to be annotated isa.
 *     AbstractClass abstractClass;
 * }
 * </pre>
 * // now to serialize and deserialize `Holder` class you  need to annotated the abstract class
 * <br></br>
 * // otherwise it won't be deserialized correctly or throws an error isa.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface MASealedAbstractOrInterface {}
