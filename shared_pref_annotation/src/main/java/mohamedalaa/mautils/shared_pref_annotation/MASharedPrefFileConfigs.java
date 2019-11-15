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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <ul>
 *     <li>
 *         Can be used with {@link MASharedPrefKeyValuePair} annotation to add additional functions to the generated
 *         code or enhance features supported in the other annotation isa.
 *     </li>
 *     <li>
 *         Note all functions require context as receiver for kotlin or static fun with param context
 *         for java except for {@link #addFileNameFun()} which won't require context so kotlin
 *         consumer will call it like java calls it as a static fun not an extension function isa.
 *     </li>
 * </ul>
 */
@SuppressWarnings({"JavadocReference", "unused"})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MASharedPrefFileConfigs {

    /**
     * <ul>
     *     <li>
     *         If true then fun will be generated to delete all keys
     *     </li>
     *     <li>
     *         to call from kotlin it will be this fun Context.sharedPref[YourClassName]_clearAll isa.
     *     </li>
     *     <li>
     *         to call from java it will be this static method SharedPref[annotatedClassName].clearAll() isa.
     *     </li>
     * </ul>
     */
    boolean addClearFun() default false;

    /**
     * If true then an additional fun will be added in the name sharedPref[YourClassName]_fileName
     * in case you want to act on that key/value pairs shared pref file in any customized way that
     * is not supported by this annotation isa. <br/>
     * kotlin/java consumer -> SharedPref[YourClassName]_NoContext.filName()
     */
    boolean addFileNameFun() default false;

    /**
     * Adds a fun to get instance of {@link android.content.SharedPreferences.OnSharedPreferenceChangeListener}
     * of this file isa.
     */
    boolean addSharedPrefInstanceFun() default false;

    /**
     * <ul>
     *     <li>
     *         Adds register/unregister ext(static) fun with param {@link android.content.SharedPreferences.OnSharedPreferenceChangeListener}
     *     </li>
     *     <li>
     *         Note this actually calls {@link android.content.SharedPreferences#registerOnSharedPreferenceChangeListener(android.content.SharedPreferences.OnSharedPreferenceChangeListener)}
     *         So check it's documentation which currently have very important note which is
     *         <p class="caution"><strong>Caution:</strong> The preference manager does
     *         not currently store a strong reference to the listener. You must store a
     *         strong reference to the listener, or it will be susceptible to garbage
     *         collection. We recommend you keep a reference to the listener in the
     *         instance data of an object that will exist as long as you need the
     *         listener.</p>
     *     </li>
     * </ul>
     */
    boolean addSharedPrefChangeListener() default false;

    /**
     * In case you used an expression in {@link MASharedPrefKeyValuePair#defaultValue()} or in any other
     * parameter that supports expression(code) then here add all the needed import statements
     * for that expression to work for Ex. if you wanna use an expression as default value as the
     * following "{@link Integer#MAX_VALUE}" then you need to add here the Integer import statement
     * as "java.lang.Integer" isa.
     */
    String[] imports() default {};

    /**
     * if true adds support for java code for all functions created by this annotation and if
     * {@link MASharedPrefKeyValuePair#supportJavaConsumerCode()} is
     * {@link MASharedPrefKeyValuePair.JavaConsumerCode#BEHAVE_AS_IN_CONFIGS} then we add support
     * to the getter/setter functions created by the {@link MASharedPrefKeyValuePair} annotation too isa.
     * <br/> <b>Note</b> if this is true then {@link #supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation}
     * is ignored isa.
     */
    boolean supportJavaConsumerCode() default false;

    /**
     * used in case you only want support for java for methods created by this annotation
     * even if #supportJavaConsumerCode() is false isa.
     *
     * @see #supportJavaConsumerCode()
     */
    boolean supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation() default false;

}
