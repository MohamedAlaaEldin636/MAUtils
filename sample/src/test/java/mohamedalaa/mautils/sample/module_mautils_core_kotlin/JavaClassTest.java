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

package mohamedalaa.mautils.sample.module_mautils_core_kotlin;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 3/1/2019.
 */
public class JavaClassTest {

    @Test
    public void check1() {
        String s1 = "s1";
        String s2 = "s2";
        CharSequence charSequence1 = "char seq 1";

        Assert.assertTrue(s1.getClass().isAssignableFrom(s2.getClass()));
        Assert.assertTrue(charSequence1.getClass().isAssignableFrom(s2.getClass()));
        Assert.assertTrue(s2.getClass().isInstance(charSequence1));
        Assert.assertTrue(charSequence1.getClass().isInstance(charSequence1));
    }

}
