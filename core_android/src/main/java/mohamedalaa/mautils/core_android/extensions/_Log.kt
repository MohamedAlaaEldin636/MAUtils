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

package mohamedalaa.mautils.core_android.extensions

import android.util.Log
import mohamedalaa.mautils.core_kotlin.extensions.safeNext

fun logError(msg: String) = privateLog(
    LogLevel.ERROR,
    msg
)

fun logWarn(msg: String) = privateLog(
    LogLevel.WARN,
    msg
)

fun logVerbose(msg: String) = privateLog(
    LogLevel.VERBOSE,
    msg
)

private fun privateLog(logLevel: LogLevel, msg: String) {
    val iterator = Thread.currentThread().stackTrace.iterator()
    while (iterator.hasNext()) {
        val methodName = iterator.next().methodName
        if (methodName == ::privateLog.name) {
            iterator.safeNext()
            val a = iterator.safeNext()
            val innerMsg = a.classSimpleName() + " : at line ${a?.lineNumber} : $msg"

            when (logLevel) {
                LogLevel.VERBOSE -> Log.v("V", innerMsg)
                LogLevel.DEBUG -> Log.d("D", innerMsg)
                LogLevel.INFO -> Log.i("I", innerMsg)
                LogLevel.WARN -> Log.w("W", innerMsg)
                LogLevel.ERROR -> Log.e("E", innerMsg)
                LogLevel.ASSERT -> Log.wtf("WTF", innerMsg)
            }

            return
        }
    }
}

private fun StackTraceElement?.classSimpleName() = this?.className?.classSimpleName() ?: "Unknown Class Name"

private enum class LogLevel {
    VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT
}

private fun String.classSimpleName(): String {
    return if ("." !in this) {
        this
    }else {
        val indexOfLastDot = lastIndexOf(".")

        substring(indexOfLastDot.inc())
    }
}
