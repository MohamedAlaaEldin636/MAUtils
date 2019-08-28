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

package mohamedalaa.mautils.sample.custom_classes.helper_classes

import mohamedalaa.mautils.core_kotlin.buildMap

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 8/28/2019.
 *
 */


/** @return true if all elements inside the list are true isa. */
fun List<Boolean>.allTrue() = all { it }


@JvmOverloads
fun <V> buildGameMap(checkSize: Boolean = true, buildAction: (Int) -> V): Map<Int, V>
    = buildMap(4, checkSize) { it to buildAction(it) }


/** @return empty string if receiver is null, otherwise conversion to string is returned isa. */
fun Any?.toStringOrEmpty(): String = this?.toString() ?: ""

@Suppress("unused")
fun Any.toStringLikeDataClass(vararg variables: Any?): String {
    val simpleName = this::class.java.simpleName
    return "$simpleName(${variables.joinToString(", ")})"
}
