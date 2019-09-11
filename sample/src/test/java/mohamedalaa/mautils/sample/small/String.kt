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

package mohamedalaa.mautils.sample.small

import mohamedalaa.mautils.core_kotlin.extensions.toStringOrNull
import org.junit.Test

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 4/14/2019.
 *
 */
class String {
    
    @Test
    fun quick_toString() {
        1.toStringOrNull().apply(::println).apply(::println)
        (1.toByte()).toStringOrNull().apply(::println)
        (1.toShort()).toStringOrNull().apply(::println)
        1.3f.toStringOrNull().apply(::println)
        1.5.toStringOrNull().apply(::println)
        5L.toStringOrNull().apply(::println)
        false.toStringOrNull().apply(::println)
        "wjqoidjw".toStringOrNull().apply(::println)
    }
    
}