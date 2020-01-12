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

package mohamedalaa.mautils.sample.gson;

import android.os.Build;
import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import mohamedalaa.mautils.gson.GetterBundleGson;
import mohamedalaa.mautils.gson.GsonBundleUtils;
import mohamedalaa.mautils.gson.java.GsonConverter;
import mohamedalaa.mautils.sample.fake_data.CustomWithTypeParam;
import mohamedalaa.mautils.sample.gson.model.BackupReminderOrAction;

import static org.junit.Assert.assertEquals;

@Config(manifest = Config.NONE, sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class JavaTestClass2 {

    @Test
    public void m1() {
        // Custom Class
        BackupReminderOrAction backupReminderOrAction1 = new BackupReminderOrAction(
            "Some String",
            3231
        );
        // Custom With Type Param
        CustomWithTypeParam<Float, String> pairs = new CustomWithTypeParam<>(
            3f, "a", "b", "c"
        );

        Bundle bundle = GsonBundleUtils.buildBundleGson(
            "abc", // Type which is normally supported by `Bundle`
            backupReminderOrAction1,
            pairs
        );

        GetterBundleGson getterBundleGson = GsonBundleUtils.getterBundleGson(bundle);

        // Value normally supported by `Bundle` -> No arguments need to be provided isa.
        String string = getterBundleGson.getOrNull();
        assertEquals(string, "abc");

        // Custom Class -> You have to provide the class as a parameter isa.
        BackupReminderOrAction r1 = getterBundleGson.getOrNull(BackupReminderOrAction.class);
        assertEquals(backupReminderOrAction1, r1);

        // Custom With Type Param -> You have to provide GsonConverter of the class as a parameter isa.
        CustomWithTypeParam<Float, String> r2 = getterBundleGson.get(
            null,
            new GsonConverter<CustomWithTypeParam<Float, String>>() {}
        );
        assertEquals(pairs, r2);
    }

}
