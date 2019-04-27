/*
 * Copyright (c) 2019 Mohamed Alaa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package mohamedalaa.mautils.gson_annotation;

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
 * <br></br>
 * <b>Note</b> <br/>
 * - Currently does not support type params in the annotated class, since it does not make much sense.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface MASealedAbstractOrInterface {}
