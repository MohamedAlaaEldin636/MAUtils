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

package mohamedalaa.mautils.sample.mautils_core_android.shared_pref;

import android.content.Context;
import android.os.Build;
import android.util.ArraySet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Set;

import androidx.test.core.app.ApplicationProvider;
import mohamedalaa.mautils.shared_pref_core.SharedPrefUtils;
import mohamedalaa.mautils.test_core.TestingLog;

@Config(manifest = Config.NONE, sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class JavaSharedPrefTest {

    @SuppressWarnings("unchecked")
    @Test
    public void base_test() {
        Context context = ApplicationProvider.getApplicationContext();

        // Getting default value
        Set<String> tempStringSet = new ArraySet<>();
        tempStringSet.add("1");
        tempStringSet.add("2");
        tempStringSet.add("4");

        String string = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_1", "defValue_1", String.class);
        int integer = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_2", 5, int.class);
        boolean bool = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_3", true, boolean.class);
        long longg = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_4", 5L, long.class);
        float floatt = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_5", 5f, float.class);
        @SuppressWarnings("unchecked")
        Set<String> stringSet = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_6", tempStringSet, Set.class);
        println(string + " - " + integer + " - " + bool + " - " + longg + " - " + floatt + " - " + stringSet);

        // Setting a value
        tempStringSet.clear();
        tempStringSet.add("some new def");
        tempStringSet.add("val");

        SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_1", "newValue_1", String.class);
        SharedPrefUtils.sharedPrefSetComplexJava(context, "fileName_1", "key_2", 6, Integer.class);
        SharedPrefUtils.sharedPrefSetComplexJava(context, "fileName_1", "key_3", false, Boolean.class);
        SharedPrefUtils.sharedPrefSetComplexJava(context, "fileName_1", "key_4", 6L, long.class);
        SharedPrefUtils.sharedPrefSetComplexJava(context, "fileName_1", "key_5", 6f, Float.class);
        SharedPrefUtils.sharedPrefSetComplexJava(context, "fileName_1", "key_6", tempStringSet, Set.class);
        TestingLog.i("Set successfully isa");

        // Getting previously set value isa
        string = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_1", "defValue_1", String.class);
        integer = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_2", 5, int.class);
        bool = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_3", true, boolean.class);
        longg = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_4", 5L, long.class);
        floatt = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_5", 5f, float.class);
        stringSet = SharedPrefUtils.sharedPrefGetComplexJava(context, "fileName_1", "key_6", tempStringSet, (Class<Set<String>>)((Class<?>)Set.class));
        println(string + " - " + integer + " - " + bool + " - " + longg + " - " + floatt + " - " + stringSet);
    }

    private void println(Object object) {
        System.out.println(object.toString());
    }

}
