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

package mohamedalaa.mautils.sample.core_android;

import java.util.Set;

import mohamedalaa.mautils.core_android_annotation.MASharedPref;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 4/24/2019.
 */
@MASharedPref //maybe it is a must for it to be final msln isa ?!
public class AnotherSomeClass {

    final String javaStr = "hello";

    final Integer some = 99;

    int another;

    final int mido = 9;

    final int zero = 0;

    String javaStrNull;

    @MASharedPref.SetOfStringsDefValue(stringSetValue = {"", "sa"})
    Set<String> set;

    Set<String> set2;

    @MASharedPref.SetOfStringsDefValue(stringSetValue = {})
    Set<String> set3;

}
