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

@file:JvmName("IntentUtils")

package mohamedalaa.mautils.gson

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Used by java devs only, for same functionality for kotlin devs see [Intent.getterBundleGson]
 *
 * Used to retrieve [Bundle] values created by [Context.startActivityBundleGson]
 * OR [Context.startActivityBundleGsonForced] isa.
 */
@JvmName("getterBundleGson")
fun Intent.javaGetterBundleGson() = JGetterBundleGson(extras ?: Bundle())