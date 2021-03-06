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

package mohamedalaa.mautils.room_gson_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>VIP Notes</b> <br/>
 * <ul>
 *     <li>
 *         Creates type converter for the type of the annotated type isa.
 *     </li>
 *     <li>
 *         Generated type converters are inside a class with a simple name of "MAAutoTypeConverters"
 *         and a package path of this annotation isa.
 *     </li>
 * </ul>
 *
 * <b>How to use</b> <br/>
 * <ul>
 *     <li>
 *         In order for the Room database to use the generated classes from the below annotation
 *         Then you have to have all classes/fields annotated with below annotation in a module
 *         that the Room database have it as a dependency isa.
 *     </li>
 * </ul>
 *
 * @see MARoomGsonTypeConverterType
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface MARoomGsonTypeConverter {}
