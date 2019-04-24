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
 * <b>How it works</b> <br/>
 * 1. Creates a class whose name is SharedPref + TheAnnotatedClassName <br/>
 * 2. Creates methods in it with set/get for each field and takes only context in case of get and
 * context + newValue in case of set <br/>
 * 3. so all you have to do is make a class with fields and saving and retrieving is done for you isa. <br/>
 * <br/>
 * <b>Prerequisites</b> <br/>
 * 1. VIP In case of kotlin code do not put values in class parameters use it in class body
 * && DO NOT have the following names -> {int, float, long, boolean} <br/>
 * && it must be val with initializer
 * 2. In case of java code use primitives and String only do not use java.lang.Integer
 * otherwise default value will always be 0
 * && Use final keyword otherwise default values will be used instead which are
 * int -> 0
 * boolean -> false
 * long -> 0L
 * float -> 0f
 * String, Set<String> -> null
 * && Implement kotlin even if will not use it since generated code is written in kotlin isa.
 * <br/>
 * 3. All fields can only be types supported by [SharedPreferences] ==>
 * String, int, boolean, long, float, Set<String> <br/>
 * 4. You must implement core_android module for this to work <br/>
 * 5. in case of default value for Set<String> use SetOfStringsDefValue annotation isa.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface MASharedPref {

    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.FIELD)
    @interface SetOfStringsDefValue {
        String[] stringSetValue();
    }

}
