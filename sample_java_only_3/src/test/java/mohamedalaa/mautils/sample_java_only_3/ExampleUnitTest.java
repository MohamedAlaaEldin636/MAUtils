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

package mohamedalaa.mautils.sample_java_only_3;

import android.content.Context;
import android.os.Build;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.test.core.app.ApplicationProvider;
import mohamedalaa.mautils.shared_pref_core.SharedPrefUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Config(manifest = Config.NONE, sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {
    @Test
    public void sharedPref() {
        Context context = ApplicationProvider.getApplicationContext();

        String value = "value";
        SharedPrefUtils.set(context, "fileName", "key", value, String.class);

        Assert.assertEquals(
            value,
            SharedPrefUtils.get(context, "fileName", "key", "", String.class)
        );
    }

    @Test
    public void sharedPref_2() {
        Context context = ApplicationProvider.getApplicationContext();
        //SharedPref_Settings.getKeepScreenOn(context);

        // above compile but do not run MUST be by kapt (kotlin) in gradle isa.
    }


}