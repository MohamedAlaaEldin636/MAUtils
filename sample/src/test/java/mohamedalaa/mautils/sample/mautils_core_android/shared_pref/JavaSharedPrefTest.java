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
import android.util.ArraySet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Set;

import androidx.test.core.app.ApplicationProvider;
import mohamedalaa.mautils.core_android.SharedPrefUtils;
import mohamedalaa.mautils.test_core.TestingLog;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 4/24/2019.
 */
@RunWith(RobolectricTestRunner.class)
public class JavaSharedPrefTest {

    @Test
    public void base_test() {
        Context context = ApplicationProvider.getApplicationContext();

        // Getting default value
        Set<String> tempStringSet = new ArraySet<>();
        tempStringSet.add("1");
        tempStringSet.add("2");
        tempStringSet.add("4");

        String string = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_1", "defValue_1", String.class);
        int integer = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_2", 5, int.class);
        boolean bool = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_3", true, boolean.class);
        long longg = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_4", 5L, long.class);
        float floatt = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_5", 5f, float.class);
        @SuppressWarnings("unchecked")
        Set<String> stringSet = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_6", tempStringSet, Set.class);
        println(string + " - " + integer + " - " + bool + " - " + longg + " - " + floatt + " - " + stringSet);

        // Setting a value
        tempStringSet.clear();
        tempStringSet.add("some new def");
        tempStringSet.add("val");

        SharedPrefUtils.sharedPrefSet(context, "fileName_1", "key_1", "newValue_1", String.class);
        SharedPrefUtils.sharedPrefSet(context, "fileName_1", "key_2", 6, Integer.class);
        SharedPrefUtils.sharedPrefSet(context, "fileName_1", "key_3", false, Boolean.class);
        SharedPrefUtils.sharedPrefSet(context, "fileName_1", "key_4", 6L, long.class);
        SharedPrefUtils.sharedPrefSet(context, "fileName_1", "key_5", 6f, Float.class);
        SharedPrefUtils.sharedPrefSet(context, "fileName_1", "key_6", tempStringSet, Set.class);
        TestingLog.i("Set successfully isa");

        // Getting previously set value isa
        string = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_1", "defValue_1", String.class);
        integer = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_2", 5, int.class);
        bool = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_3", true, boolean.class);
        longg = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_4", 5L, long.class);
        floatt = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_5", 5f, float.class);
        //noinspection unchecked
        stringSet = SharedPrefUtils.sharedPrefGet(context, "fileName_1", "key_6", tempStringSet, Set.class);
        println(string + " - " + integer + " - " + bool + " - " + longg + " - " + floatt + " - " + stringSet);
    }

    private void println(Object object) {
        System.out.println(object.toString());
    }

}
