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

package mohamedalaa.mautils.core_android.data_binding;

import androidx.databinding.InverseMethod;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 4/7/2019.
 */
public class DataBindingBoxingUtils {

    @InverseMethod("safeBox")
    public static boolean safeUnbox(Boolean b) {
        return (b != null) && b;
    }

    public static Boolean safeBox(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    @InverseMethod("safeBox")
    public static int safeUnbox(Integer value) {
        return value != null ? value : 0;
    }

    public static Integer safeBox(int value) {
        return value;
    }

}
