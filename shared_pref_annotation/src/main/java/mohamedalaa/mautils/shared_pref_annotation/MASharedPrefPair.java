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

package mohamedalaa.mautils.shared_pref_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h3>Features</h3>
 *
 * <ul>
 *     <li>
 *         Eliminate usage of constants for files Names or keys names with getter/setter functions(methods)
 *         extension functions for kotlin developers and static methods for java developers isa.
 *     </li>
 *     <li>
 *         highly customizable according to your needs.
 *     </li>
 *     <li>
 *         Supports any type not just the normally supported types isa.
 *     </li>
 *     <li>
 *         <b>Supports expressions</b> to be used in {@link MASharedPrefKeyValuePair#defaultValue()}
 *         instead of only constant values (in kotlin programming language) isa.
 *     </li>
 *     <li>
 *         All generated functions(methods) that would edit the SharedPreferences are annotated
 *         with {@link kotlin.jvm.Synchronized} isa.
 *     </li>
 * </ol>
 *
 * <h3>How to use</h3>
 *
 * <ol>
 *     <li>
 *         Declare a class in Kotlin as <code>class _ClassName</code> or in Java as <code>public class _ClassName {}</code>,
 *         it's preferred to have <code>_</code> as a prefix to the Name so that in your own code
 *         it won't be shown in AutoCompletion you only need to show the generated code not
 *         this one also it makes it more readable when calling functions ex. sharedPref_ClassName_SetIntValue()
 *         instead of sharedPrefClassName_SetIntValue() isa.
 *     </li>
 *     <li>
 *         Annotate this class with {@link MASharedPrefPair} for each shared pref key/value pair,
 *         And Additionally for more configs or general defaults You can as well annotate with
 *         {@link MASharedPrefFileConfigs} isa.
 *     </li>
 *     <li>
 *         Then to access the generated code for all cases except one either use a kotlin extension
 *         fun Context.sharedPref[YourClassName]_Set[yourVariableWithInitialLetterCapitalized]
 *         or in case of a java consumer by using the static method SharedPref[YourClassName].set[yourVariableWithInitialLetterCapitalized]
 *     </li>
 *     <li>
 *         <b>The only exceptional case</b> is if you set {@link MASharedPrefFileConfigs#addFileNameFun()}
 *         to true then to get this fun use same naming but with a suffix of "_NoContext"
 *         so to get file name from kotlin or java call SharedPref[YourClassName]_NoContext.fileName() isa.
 *     </li>
 * </ol>
 *
 * <h3>VIP Notes</h3>
 *
 * <ul>
 *     <li>
 *         All setter functions will have optional param "commit" which if true then commit will be
 *         used instead of apply when making a change in the file isa, so all setter functions will
 *         have nullable {@link Boolean} as the return type from this fun which is null in case
 *         apply is used or a Boolean in case of commit is used to indicate the returned value
 *         of the commit function isa.
 *     </li>
 * </ou>
 */
@SuppressWarnings({"unused"})
@Repeatable(MASharedPrefPair.Container.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MASharedPrefPair {

    /**
     * <ul>
     *     <li>
     *         Key of the key/value pairs and used as part of the naming in the getter/setter functions isa.
     *     </li>
     * </ul>
     */
    String name();

    /**
     * <ul>
     *     <li>
     *         Type of value of this key/value pair in the sharedPref file isa.
     *     </li>
     *     <li>
     *         This type can have type parameters and nested type parameters check
     *         {@link MAParameterizedKClass} to know how to use it along with how to declare nullability
     *         for class and type params as well isa.
     *     </li>
     *     <li>
     *         Note if class type(not type params) is <code>null</code> then generated getter/setter
     *         functions are nullable, otherwise then are non-null meaning setter accepts only
     *         non-null param value and getter returns only non-null value isa.
     *     </li>
     * </ul>
     *
     * @see MASharedPrefKeyValuePair#supportSetterAndGetterNullValues()
     */
    MAParameterizedKClass type();

    /**
     * <ul>
     *     <li>
     *         Used as the default param value in the kotlin getter fun to be used as the default value when
     *         retrieving the value isa, and same in case of java it's used if you decide to use the overloaded
     *         method that don't require default value as a param isa.
     *     </li>
     * </ul>
     *
     * <h2>Notes</h2>
     *
     * <ul>
     *     <li>
     *         The passed value is being used as an <b>expression in kotlin programming language</b>
     *         so to pass integer of 55 pass it as "55", or you can pass "emptyList()"
     *     </li>
     *
     *     <li>
     *         You can pass <code>null</code> but only if you set
     *         {@link MASharedPrefKeyValuePair#supportGetterNullValue()} to true or an exception will be thrown,
     *         Otherwise one of the following default values acc. to type will be used instead isa.
     *         <ol type="I">
     *              <li>
     *                  0 for numbers isa.
     *              </li>
     *              <li>
     *                  false for {@link Boolean}
     *              </li>
     *              <li>
     *                  "\"\"" (meaning empty string) for {@link String}
     *              </li>
     *              <li>
     *                  "null" for any other type so make sure to check
     *                  {@link MASharedPrefKeyValuePair#supportGetterNullValue()} isa.
     *              </li>
     *         </ol>
     *     </li>
     *
     *     <li>
     *         For {@link String} type add string as the following example isa -> "\"YourString\""
     *         If not an expression Otherwise use immediately without the \" parts for ex. "SomeClass().toString()",
     *         <b>And Note</b> expressions are supported for any type so in case you want a default
     *         set with 1 specific value to be default value use "setOf(7)" isa.
     *     </li>
     *
     *     <li>
     *         Note generated code is in kotlin so to type {@link Float} value use 4f or 4.33f and
     *         for {@link Long} value use 66L isa, and there is no new keyword this might change in future
     *         to support java expressions as well but now it's not supported.
     *     </li>
     * </ul>
     */
    String defaultValue() default "null";

    /**
     * <ul>
     *     <li>
     *         If {@link MASharedPrefPair.JavaConsumerCode#SUPPORT} or in case if
     *         {@link MASharedPrefPair.JavaConsumerCode#BEHAVE_AS_IN_CONFIGS} &&
     *         {@link MASharedPrefFileConfigs#supportJavaConsumerCode()} is <code>true</code>
     *         then java consumer can call this method otherwise only kotlin consumer will be able
     *         to see this fun by not generating {@link kotlin.jvm.JvmOverloads} & {@link kotlin.jvm.JvmName},
     *         which is useful if you don't need it to make the generated code smaller isa.
     *     </li>
     * </ul>
     */
    JavaConsumerCode supportJavaConsumerCode() default JavaConsumerCode.BEHAVE_AS_IN_CONFIGS;

    /**
     * <B>Shouldn't be used at all</B> except by the developer of the library and inside
     * the annotation processor only isa.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.TYPE})
    @interface Container {
        MASharedPrefPair[] value();
    }

    /**
     * <h3>Have only three values {@link #SUPPORT}, {@link #DO_NOT_SUPPORT}, {@link #BEHAVE_AS_IN_CONFIGS}</h3>
     * <ul>
     *     <li>
     *         {@link #SUPPORT} means that the getter and setter generated for this key/value pair
     *         will support java consumer code regardless of the value in {@link MASharedPrefFileConfigs#supportJavaConsumerCode()},
     *         and the exact opposite of not supporting in case of {@link #DO_NOT_SUPPORT} isa.
     *     </li>
     *     <li>
     *         {@link #BEHAVE_AS_IN_CONFIGS} means it will only support or not according to the value of
     *         {@link MASharedPrefFileConfigs#supportJavaConsumerCode()} which is by default false isa.
     *     </li>
     * </ul>
     */
    enum JavaConsumerCode {
        SUPPORT, DO_NOT_SUPPORT, BEHAVE_AS_IN_CONFIGS
    }

}
