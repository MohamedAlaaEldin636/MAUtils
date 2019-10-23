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

package mohamedalaa.mautils.sample.core_android.shared_pref;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import mohamedalaa.mautils.core_android_annotation.SharedPrefSomeClassName;
import mohamedalaa.mautils.core_android_annotation.SharedPrefSomeClassNameNoContext;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 10/22/2019.
 */
public class QuickJava {

    private void aa() {
        int a = 0;
        long k = 0;
        float f = 5;
        boolean b = false;


        double d = 9;

        Application application = ApplicationProvider.getApplicationContext();

        SharedPrefSomeClassNameNoContext.fileName();

        SharedPrefSomeClassNameNoContext.fileName();
        SharedPrefSomeClassName.clearAll(application);
        SharedPrefSomeClassName.clearAll(application, true);
    }

}
