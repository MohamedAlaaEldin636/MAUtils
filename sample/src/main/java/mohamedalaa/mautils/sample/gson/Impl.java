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

import androidx.annotation.Nullable;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 1/13/2020.
 */
public class Impl extends AbstractClass {

    private int integer;

    public Impl(int integer) { this.integer = integer; }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) { this.integer = integer; }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Impl && ((Impl) obj).integer == integer;
    }

    // todo make a data class
} // Impl.java file
