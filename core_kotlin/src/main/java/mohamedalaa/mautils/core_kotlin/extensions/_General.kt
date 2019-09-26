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

@file:JvmName("GeneralUtils")

package mohamedalaa.mautils.core_kotlin.extensions

/**
 * Performs given [block] only if the `receiver` is not null, also returns Unit, useful to
 * eliminate small amount of boilerplate code, but makes coding easier.
 *
 * **Examples**
 *
 * 1- Return Unit from a fun that take int: Int? as arg
 * ```
 * // Uses 2 curly brackets not 1, but i want it to be only 1 for shorter code.
 * private fun f1(int: Int?) {
 *      int?.apply {
 *          // Code with non-null receiver.
 *      }
 * }
 * // Note this returns Int?, what if I have to return Unit in case this was an interface signature
 * private fun f2(int: Int?) = int?.apply {
 *      // Code with non-null receiver.
 * }
 * // Returns Unit and at same time no need for `?` like int?.apply {  }, and 1 curly bracket isa.
 * private fun f3(int: Int?) = int.performIfNotNull {
 *      // Code with non-null receiver.
 * }
 * ```
 * @receiver any nullable object.
 * @return [Unit]
 */
inline fun <T> T?.performIfNotNull(block: T.() -> Unit) {
    this?.block()
}

/**
 * Only calls [block] fun if [condition] is true.
 *
 * @return `receiver` of this fun isa.
 */
inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
    return if (condition) {
        apply { this.block() }
    }else {
        this
    }
}

/** @return true if any of [others] is == `receiver` isa. */
fun <T> T.equalAny(vararg others: T): Boolean = this in others

/** @return true if each object in [others] is == `receiver` isa. */
fun <T> T.equalAll(vararg others: T): Boolean = others.all { it == this }

/** EExactly like !! but shows your [msg] as [RuntimeException]'s msg isa. */
fun <T> T?.throwIfNull(msg: String = "Expected not null value"): T
    = this ?: throw RuntimeException(msg)

/**
 * Used with when statement to make it expression so you ensure all events are consumed isa.
 *
 * Inspired from [link](https://www.youtube.com/watch?v=NNWejxBORgc)
 */
val <T> T.exhaustive: T
    get() = this

fun throwRuntimeException(msg: String = "runtime exception thrown isa."): Nothing = throw RuntimeException(msg)
