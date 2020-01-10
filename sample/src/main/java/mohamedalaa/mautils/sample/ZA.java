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

import android.app.Application;
import android.os.Bundle;

import org.jetbrains.annotations.Contract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import mohamedalaa.mautils.core_android.extensions.BundleUtils;
import mohamedalaa.mautils.core_android.extensions.GetterBundle;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 4/22/2019.
 */
public class ZA {

    public void dwed(
        @NonNull Bundle bundle,
        AndroidViewModel androidViewModel,
        @Nullable Bundle bNullable
    ) {
        //BundleUtils.getterBundle(bundle);

        //Application application = mohamedalaa.mautils.core_android.extensions._AndroidViewModelKt.getApplication(androidViewModel);

        Bundle c = null;
        boolean bb = BundleUtils.isNullOrEmpty(c);

        GetterBundle getterBundle = BundleUtils.getterBundle(bundle);
        int[] primitiveIntArray = getterBundle.getOrNull();
        String string = getterBundle.get();

        Bundle b2 = BundleUtils.orEmpty(bundle);
        bNullable = null;
        Bundle b3 = BundleUtils.orEmpty(bNullable);
        if (b3 == bNullable) {

        }

        boolean b = BundleUtils.isNullOrEmpty(bundle);
        if (b2 != null) {

        }
        if (bundle != null) {
            if (abcdefg(bundle) != null) {

            }
        }
    }

    @Contract(value = "!null -> param1", pure = true)
    @NonNull
    private static Bundle abcdefg(@Nullable Bundle bundle) {
        return bundle == null ? new Bundle() : bundle;
    }

}
