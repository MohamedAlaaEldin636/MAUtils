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

package mohamedalaa.mautils.core_kotlin

fun errorPrintLn(any: Any?) {
    val prefix = "\u001b[0;31m"
    val suffix = "\u001b[0m"

    println("$prefix$any$suffix")
}
fun warnPrintLn(any: Any?) {
    val prefix = "\u001b[0;34m" // BLUE
    val suffix = "\u001b[0m"

    println("$prefix$any$suffix")
}
fun infoPrintLn(any: Any?) {
    val prefix = "\u001b[0;33m" // YELLOW
    val suffix = "\u001b[0m"

    println("$prefix$any$suffix")
}
fun purplePrintLn(any: Any?) {
    val prefix = "\u001b[0;35m" // PURPLE
    val suffix = "\u001b[0m"

    println("$prefix$any$suffix")
}
fun cyanPrintLn(any: Any?) {
    val prefix = "\u001b[1;96m" // CYAN
    // PURPLE
    val suffix = "\u001b[0m"

    println("$prefix$any$suffix")
}

