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

package mohamedalaa.mautils.sample;

import android.content.Context;

import mohamedalaa.mautils.shared_pref_core.SharedPrefUtils;

public class ZQuickTestsJava {
    public static void m1(
        Context context,
        int someInt
    ) {
        /*// No need for specific type name like putString
        String string = "string";
        SharedPrefUtils.sharedPrefSetComplexJava(context, "fileName", "key", string, String.class);
        string == SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName", "k1", "", String.class); // true

        // since Shared preference donot save null types then fallback
        // to delete the key if is null
        int someInt = *//*some code which might be null*//*
        SharedPrefUtils.sharedPrefSetComplexJava(
            context, "fileName", "key", someInt, int.class, true *//* removeKeyIfValueIsNull *//*
        );*/
        SharedPrefUtils.set(
            context, "fileName", "key", someInt, Integer.class, true /* removeKeyIfValueIsNull */
        );
        Integer s = SharedPrefUtils.get(
            context, "fileName", "key", null, Integer.class
        );
    }
}

/*
class ZQuickTestsJava {

    class AAA extends GsonConverterPairBooleanAndListPairIntAndPairFloatAndDouble {}

    public static GsonConverter<Pair<Boolean, List<Pair<Integer, Pair<Float, Double>>>>> s() {
        return new GsonConverterPairBooleanAndListPairIntAndPairFloatAndDouble();
    }

    @NonNull
    private String s = "";

    public static MAKClass getInstance(boolean nullable, Class<String> s, KClass<Integer> i) {
        return new MAKClass() {
            @Override
            public Class<?> kClass() {

                return s;
            }

            @Override
            public boolean nullable() {
                return nullable;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return MAKClass.class;
            }
        };
    }

    public static MASharedPrefFileConfigs getDefaultInstanceOfAnnotation(){
        return new MASharedPrefFileConfigs() {
            @Override
            public boolean addClearFun() {
                return false;
            }

            @Override
            public boolean addFileNameFun() {
                return false;
            }

            @Override
            public boolean addSharedPrefInstanceFun() {
                return false;
            }

            @Override
            public boolean addSharedPrefChangeListener() {
                return false;
            }

            @Override
            public String[] imports() {
                return new String[0];
            }

            @Override
            public boolean supportJavaConsumerCode() {
                return false;
            }

            @Override
            public boolean supportJavaConsumerOnlyForFunctionsCreatedByThisAnnotation() {
                return false;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return MASharedPrefFileConfigs.class;
            }
        };
    }

}*/
