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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <ul>
 *     <li>
 *         Can be used with {@link MASharedPrefField} annotation to add additional functions to the generated
 *         code or enhance features supported in the other annotation isa.
 *     </li>
 *     <li>
 *         Note all functions require context as receiver for kotlin or static fun with param context
 *         except for {@link #addFileNameFun()} SharedPrefSomeClassName.fileName();
 *     </li>
 * </ul>
 */
@SuppressWarnings({"JavadocReference", "unused"})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface MASharedPrefField_Configs {

    /**
     * If true then an additional fun will be added in the name sharedPref_[YourClassName]_clearAll,
     * and in case {@link #supportJavaConsumerCode()} is true java static fun will be clearAll isa.
     * which actually calls {@link mohamedalaa.mautils.core_android.extensions.sharedPrefClearAll} isa.
     */
    boolean addClearFun() default false;

    /**
     * If true then an additional fun will be added in the name sharedPref_[YourClassName]_fileName
     * in case you want to act on that key/value pairs shared pref file in any customized way that
     * is not supported by this annotation isa.
     * Created fun is a static fun in the form SharedPref[YourClassName].fileName()
     * Also Note If java needs to be supported for a java consumer to be able to call this static
     * fun, via {@link #supportJavaConsumerCode()} isa.
     */
    boolean addFileNameFun() default false;

    /**
     * In case you used an expression in {@link MASharedPrefField#defaultValue()} then here
     * add all the needed import statements for that expression to work for Ex. if you wanna
     * use an expression as default value as the following "{@link android.content.Context#MODE_PRIVATE}"
     * which returns int value then you need to add here the Context import statement as "android.content.Context" isa.
     */
    String[] imports() default {};

    /**
     * If {@link MASharedPrefField#supportJavaConsumerCode()} is {@link MASharedPrefField.JavaConsumerCode#BEHAVE_AS_IN_CONFIGS}
     * then we check this value which determines whether that key is gonna support java consumer or not isa.
     */
    boolean supportJavaConsumerCode() default false;

}
