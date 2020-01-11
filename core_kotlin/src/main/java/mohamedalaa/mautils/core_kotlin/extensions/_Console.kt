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

package mohamedalaa.mautils.core_kotlin.extensions

/**
 * Same as [print], But shows a purple color on console instead of the regular fixed color isa.
 */
fun verbosePrint(any: Any?) {
    val prefix = "\u001b[0;35m" // PURPLE
    val suffix = "\u001b[0m" // Text Reset

    println("${prefix}V/ $any$suffix")
}
/**
 * Same as [println], But shows a purple color on console instead of the regular fixed color isa.
 */
fun verbosePrintLn(any: Any?) {
    verbosePrint(any)
    println()
}

/**
 * Same as [print], But shows a yellow color on console instead of the regular fixed color isa.
 */
fun infoPrint(any: Any?) {
    val prefix = "\u001b[0;33m" // YELLOW
    val suffix = "\u001b[0m" // Text Reset

    print("${prefix}I/ $any$suffix")
}
/**
 * Same as [println], But shows a yellow color on console instead of the regular fixed color isa.
 */
fun infoPrintLn(any: Any?) {
    infoPrint(any)
    println()
}

/**
 * Same as [print], But shows a blue color on console instead of the regular fixed color isa.
 *
 * @param noPrefix if `true` then just [any] will be printed else a prefix of "W/ " will be printed isa.
 */
fun warnPrint(any: Any?, noPrefix: Boolean = true) {
    val prefix = "\u001b[0;34m" // BLUE
    val suffix = "\u001b[0m" // Text Reset

    if (noPrefix) {
        print("$prefix$any$suffix")
    }else {
        print("${prefix}W/ $any$suffix")
    }
}
/**
 * Same as [println], But shows a blue color on console instead of the regular fixed color isa.
 */
fun warnPrintLn(any: Any?, noPrefix: Boolean = true) {
    warnPrint(any, noPrefix)
    println()
}

/**
 * Same as [print], But shows a red color on console instead of the regular fixed color isa.
 */
fun errorPrint(any: Any?) {
    val prefix = "\u001b[0;31m" // RED
    val suffix = "\u001b[0m" // Text Reset

    println("${prefix}E/ $any$suffix")
}
/**
 * Same as [println], But shows a red color on console instead of the regular fixed color isa.
 */
fun errorPrintLn(any: Any?) {
    errorPrint(any)
    println()
}

/**
 * Same as [print], But shows a red background color on console instead of just white text color with no background isa.
 */
fun wtfPrint(any: Any?) {
    val additionalPrefix = "\u001b[41m" // RED Background
    val prefix = "\u001b[0;30m" // BLACK but with this background appears as WHITE isa.

    val suffix = "\u001b[0m" // Text Reset

    println("$additionalPrefix${prefix}WTF/ $any$suffix")
}
/**
 * Same as [println], But shows a red background color on console instead of just white text color with no background isa.
 */
fun wtfPrintLn(any: Any?) {
    wtfPrint(any)
    println()
}
