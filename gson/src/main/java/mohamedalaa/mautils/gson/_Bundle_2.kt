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

package mohamedalaa.mautils.gson

import android.os.Bundle
import com.google.gson.Gson
import mohamedalaa.mautils.core_android.extensions.addValue
import mohamedalaa.mautils.core_android.extensions.orEmpty

// This approach is a must to get value of type isa not tested yet isa TODO

private const val Getter_Bundle_Gson_COUNTER_INITIAL_VALUE = 1

class GetterBundleGson internal constructor(@PublishedApi internal val bundle: Bundle) {

    @PublishedApi
    internal var counter = Getter_Bundle_Gson_COUNTER_INITIAL_VALUE



}

inline fun <reified V> GetterBundleGson.get(gson: Gson? = null): V {
    val key = "$counter"
    counter++

    return when (val value = bundle.get(key)) {
        is String -> if (V::class.java == String::class.java){
            value as V
        }else {
            value.fromJson(gson)
        }
        else -> value as V
    }
}

fun Bundle?.getterBundleGson2(): GetterBundleGson = GetterBundleGson(orEmpty())

class GetterBundleGsonBuilder internal constructor() {

    @PublishedApi
    internal var counter = Getter_Bundle_Gson_COUNTER_INITIAL_VALUE

    @PublishedApi
    internal val bundle = Bundle()

    inline fun <reified V> GetterBundleGsonBuilder.put(value: V?, gson: Gson? = null): GetterBundleGsonBuilder {
        runCatching {
            bundle.addValue("$counter", value)
        }.getOrElse {
            bundle.putString("$counter", value.toJsonOrNull(gson))
        }

        counter++

        return this@GetterBundleGsonBuilder // todo fara2et isa ?! + same for putForced isa.
    }

    /**
     * only uses gson and if conversion is null leave it as null isa. todo kdoc for all isa
     * but TEST IT FIRSTLY ISA.
     */
    inline fun <reified V> GetterBundleGsonBuilder.putForced(value: V?, gson: Gson? = null): GetterBundleGsonBuilder {
        bundle.putString("$counter", value.toJsonOrNull(gson))

        counter++

        return this@GetterBundleGsonBuilder
    }

}

fun buildBundleGson2(block: GetterBundleGsonBuilder.() -> Unit): Bundle {
    val getterBundleGsonBuilder = GetterBundleGsonBuilder()
    getterBundleGsonBuilder.block()

    return getterBundleGsonBuilder.bundle
}


