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

package mohamedalaa.mautils.sample.shared_pref_core;

import android.content.Context;
import android.os.Build;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.test.core.app.ApplicationProvider;
import mohamedalaa.mautils.shared_pref_core.SharedPrefUtils;

@Config(manifest = Config.NONE, sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class OtherOfSharedPrefJava {

    @Test
    public void clearAll_removeKey_hasKey() {
        String fileName = "fileName";
        Context context = ApplicationProvider.getApplicationContext();

        // has key && remove key
        SharedPrefUtils.set(context, fileName, "k1", "s", String.class);
        Assert.assertTrue(
            SharedPrefUtils.hasKey(context, fileName, "k1")
        );

        SharedPrefUtils.removeKey(context, fileName, "k1");
        Assert.assertFalse(
            SharedPrefUtils.hasKey(context, fileName, "k1")
        );

        // clear all
        SharedPrefUtils.set(context, fileName, "k1", "s 111", String.class);
        SharedPrefUtils.set(context, fileName, "k2", "s 222", String.class);
        Assert.assertEquals(
            2,
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE).getAll().size()
        );
        SharedPrefUtils.clearAll(context, fileName);
        Assert.assertEquals(
            0,
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE).getAll().size()
        );
    }

}
