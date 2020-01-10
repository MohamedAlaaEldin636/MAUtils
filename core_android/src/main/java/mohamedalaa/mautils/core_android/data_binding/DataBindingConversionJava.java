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

import org.jetbrains.annotations.Contract;

import androidx.databinding.BindingConversion;

/**
 * Used to make auto safeUnbox so in case of a boxed null value it will be converted to it's
 * default primitive value isa.
 */
public class DataBindingConversionJava {

    /** If `null` then it uses the default value of the primitive isa. */
    @Contract(value = "!null -> param1", pure = true)
    @BindingConversion
    public static int convertIntegerToPrimitive(Integer value) {
        return value == null ? 0 : value;
    }

    /** If `null` then it uses the default value of the primitive isa. */
    @Contract(value = "!null -> param1; null -> false", pure = true)
    @BindingConversion
    public static boolean convertBooleanToPrimitive(Boolean value) {
        return value == null ? false : value;
    }

    /** If `null` then it uses the default value of the primitive isa. */
    @Contract(value = "!null -> param1", pure = true)
    @BindingConversion
    public static long convertLongToPrimitive(Long value) {
        return value == null ? 0L : value;
    }

    /** If `null` then it uses the default value of the primitive isa. */
    @Contract(value = "!null -> param1", pure = true)
    @BindingConversion
    public static float convertFloatToPrimitive(Float value) {
        return value == null ? 0f : value;
    }

    /** If `null` then it uses the default value of the primitive isa. */
    @Contract(value = "!null -> param1", pure = true)
    @BindingConversion
    public static double convertDoubleToPrimitive(Double value) {
        return value == null ? 0.0 : value;
    }

    /** If `null` then it uses the default value of the primitive isa. */
    @Contract(value = "!null -> param1", pure = true)
    @BindingConversion
    public static char convertCharToPrimitive(Character value) {
        return value == null ? '\u0000' : value;
    }

    /** If `null` then it uses the default value of the primitive isa. */
    @Contract(value = "!null -> param1", pure = true)
    @BindingConversion
    public static byte convertByteToPrimitive(Byte value) {
        return value == null ? 0 : value;
    }

    /** If `null` then it uses the default value of the primitive isa. */
    @Contract(value = "!null -> param1", pure = true)
    @BindingConversion
    public static short convertShortToPrimitive(Short value) {
        return value == null ? 0 : value;
    }

}
