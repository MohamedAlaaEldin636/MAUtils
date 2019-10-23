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

package mohamedalaa.mautils.core_android_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

/**
 * <h3>Features</h3>
 *
 * <ul>
 *     <li>
 *         Easy way to generate quick access set/get shared pref values
 *     </li>
 *     <li>
 *         highly customizable according to your needs so in case no complex needs this will
 *         generate fewer lines of code meaning fewer code size.
 *     </li>
 *     <li>
 *         Supports additional types instead of manual conversion to one of the supported ones.
 *     </li>
 *     <li>
 *         <b>Supports expressions</b> to be used in {@link MASharedPrefField#defaultValue()}
 *         instead of only constant values isa.
 *     </li>
 *     <li>
 *         All generated functions(methods) are annotated with {@link kotlin.jvm.Synchronized} isa.
 *     </li>
 * </ol>
 *
 * <h3>Additional Supported Types</h3>
 *
 * <ul>
 *     <li>
 *         {@link Set} of any other non-type-param supported types and can be nullable
 *         items as well meaning you can have {@link Set}<{@link Integer}?> or non-null items like
 *         {@link java.util.Set}<{@link java.lang.Integer}> but nullable items need to be
 *         added explicitly in the annotation by setting {@link #acceptNullableSetItem()} to true isa.
 *     </li>
 *     <li>
 *         {@link kotlin.Pair} && {@link mohamedalaa.mautils.core_kotlin.custom_classes.MutablePair}
 *         of other non-type-param as well but It's own type params CANNOT be null like in {@link Set}.
 *     </li>
 *     <li>
 *         You can only put non-null values in setter in case you don't want to support delete
 *         if value is null or you can put null values but explicitly require this feature by setting
 *         {@link #supportSetterNullValue()} to true isa, <b>Same thing in getter</b> in case you want
 *         to get null value in case for example the key don't exist (not created or deleted) which
 *         is supported for any type not {@link String} only then you can do by setting
 *         {@link #supportGetterNullValue()} to true isa.
 *     </li>
 * </ul>
 *
 * <h3>How to use</h3>
 *
 * <ol>
 *     <li>
 *         Declare a class in Kotlin as <code>class ClassName</code> or in Java as <code>public class ClassName {}</code>
 *     </li>
 *     <li>
 *         Annotate this class with {@link MASharedPrefField} for each shared pref key/value pair,
 *         And Additionally for more configs or general defaults You can as well annotate with
 *         {@link MASharedPrefField_Configs} isa.
 *     </li>
 *     <li>
 *         Then to access the generated code for all cases except one either use a kotlin extension
 *         fun Context.sharedPref_[YourClassName]_Set[yourVariableWithInitialLetterCapitalized]
 *         or in case of a java consumer by using the static method SharedPref[YourClassName].set[yourVariableWithInitialLetterCapitalized]
 *     </li>
 *     <li>
 *         <b>The only exceptional case</b> is if you set {@link MASharedPrefField_Configs#addFileNameFun()}
 *         to true then to get this fun use same naming but with a suffix of "NoContext"
 *         so to get file name from kotlin or java call SharedPref[YourClassName]NoContext.fileName() isa.
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
 *
 * @see mohamedalaa.mautils.core_android.extensions.SharedPrefUtils.sharedPrefSetComplex
 */
@SuppressWarnings({"JavadocReference", "unused"})
@Repeatable(MASharedPrefField.Container.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MASharedPrefField {

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
     *         Class Type of key/value pair isa.
     *     </li>
     * </ul>
     */
    MANonNestedParameterizedClass type();

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
     *         You can pass <code>"null"</code> which is the default value to indicate null value
     *         only if you set {@link MASharedPrefField#supportGetterNullValue()} to true,
     *         Otherwise one of the following default values acc. to type will be used instead isa.
     *         <ol type="I">
     *              <li>
     *                  0 for {@link Integer} && {@link Long} && {@link Float}
     *              </li>
     *              <li>
     *                  false for {@link Boolean}
     *              </li>
     *              <li>
     *                  "" (meaning empty string) for {@link String}
     *              </li>
     *              <li>
     *                  empty Set for any {@link Set}
     *              </li>
     *              <li>
     *                  for {@link kotlin.Pair} && {@link mohamedalaa.mautils.core_kotlin.custom_classes.MutablePair}
     *                  the values will be the default of one of the above except <code>null</code> and set since
     *                  Pairs in this annotation don't support nullable type param nor nested type params.
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
     *         for {@link Long} value use 66L isa.
     *     </li>
     * </ul>
     */
    String defaultValue() default "null";
    /**
     * If true then the set fun will have nullable value as the param <b>Note this MEANS</b>
     * if a null value passed then the key will be deleted from the shared pref, for more info see
     * {@link mohamedalaa.mautils.core_android.extensions.SharedPrefUtils.sharedPrefSetComplex}
     */
    boolean supportSetterNullValue() default false;
    /**
     * If true then the get fun will return nullable value and will have nullable type as the
     * defaultValue See {@link MASharedPrefField#defaultValue()}.
     */
    boolean supportGetterNullValue() default false;
    /**
     * If true then any values to {@link MASharedPrefField#supportSetterNullValue()} &
     * {@link MASharedPrefField#supportGetterNullValue()} is ignored and consider both true,
     * If false then this has no effect at all that's why that is the default value.
     */
    boolean supportSetterAndGetterNullValues() default false;

    /**
     * <ul>
     *     <li>
     *         In case you want possibility of having nullable item inside {@link Set} isa,
     *         If true then setter and getter will have type as {@link Set}<Class?> instead of
     *         {@link Set}<Class> isa, and for nullability of {@link Set} itself see
     *         {@link MASharedPrefField#supportSetterNullValue()} & {@link MASharedPrefField#supportGetterNullValue()} isa.
     *     </li>
     * </ul>
     */
    boolean acceptNullableSetItem() default false;

    /**
     * <ul>
     *     <li>
     *         If {@link MASharedPrefField.JavaConsumerCode#SUPPORT} then a static fun not only
     *         the extension fun will be generated in order for a java consumer to be able to
     *         use the function so If you are gonna call these functions from
     *         kotlin files only then no need to change this value to true, this will eliminate the
     *         usage of {@link kotlin.jvm.JvmOverloads} & {@link kotlin.jvm.JvmName} &
     *         {@link androidx.annotation.Nullable} and if there is no single key in 1 file(class)
     *         is java supported then @file:JvmName will as well not be used isa.
     *     </li>
     *     <li>
     *         if {@link MASharedPrefField.JavaConsumerCode#BEHAVE_AS_IN_CONFIGS} is used
     *         then if {@link MASharedPrefField_Configs#supportJavaConsumerCode()} is true this
     *         will support java consumer else if false then it won't support it this mechanism is used
     *         in case you want support java consumer for 1 specific field and you don't want to
     *         say so in all fields, so only use the one in the Configs and the only different one
     *         use the desired value you want isa.
     *     </li>
     * </ul>
     */
    JavaConsumerCode supportJavaConsumerCode() default JavaConsumerCode.BEHAVE_AS_IN_CONFIGS;

    /**
     * <B>Shouldn't be used at all</B> except in the annotation processor only isa.
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.TYPE})
    @interface Container {
        MASharedPrefField[] value();
    }

    enum JavaConsumerCode {
        SUPPORT, DO_NOT_SUPPORT, BEHAVE_AS_IN_CONFIGS
    }

}
