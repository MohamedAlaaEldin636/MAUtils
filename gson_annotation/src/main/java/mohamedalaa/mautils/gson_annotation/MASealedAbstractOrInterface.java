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
 * <ul>
 *     <li>
 *         Annotate Sealed Class, Abstract Class or Interface, And after that they can be converted via
 *         <code>toJson</code>/<code>fromJson</code> functions.
 *     </li>
 * </ul>
 * <br></br>
 * Checkout the below example
 * </br>
 * <pre>
 * // Declaration
 * <code>@MASealedAbstractOrInterface</code>
 * abstract class AbstractClass
 *
 * data class Impl(var int: Int) : AbstractClass
 *
 * // Conversion
 * val abstractClass: AbstractClass = Impl(33)
 * val json = abstractClass.toJson()
 * val value = json.fromJson< AbstractClass >()
 * assertEquals(abstractClass, value) // Test passed
 * </pre>
 *
 * <ul>
 *     <li>
 *         Not only that but also if a field inside a class with AbstractClass as a type
 *         that class can be serialized/deserialized correctly without any problem isa.
 *     </li>
 * </ul>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface MASealedAbstractOrInterface {}
