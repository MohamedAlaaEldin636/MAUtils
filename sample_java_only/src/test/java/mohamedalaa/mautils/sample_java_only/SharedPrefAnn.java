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

package mohamedalaa.mautils.sample_java_only;

import android.content.Context;
import android.os.Build;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.test.core.app.ApplicationProvider;

@Config(manifest = Config.NONE, sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class SharedPrefAnn {

    @Test
    public void generatedMethods() {
        Context context = ApplicationProvider.getApplicationContext();

        boolean value = SharedPref_Settings.getKeepScreenOn(context); // default is false isa.
        Assert.assertFalse(value);

        SharedPref_Settings.setKeepScreenOn(context, true);
        Assert.assertTrue(
            SharedPref_Settings.getKeepScreenOn(context)
        );
    }

}
