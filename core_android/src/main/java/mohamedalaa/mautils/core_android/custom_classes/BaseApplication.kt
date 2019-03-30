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

@file:Suppress("KDocUnresolvedReference", "UnusedImport")

package mohamedalaa.mautils.core_android.custom_classes

import android.app.Application
import android.content.Context
import android.widget.Toast
import mohamedalaa.mautils.core_android.toast
import mohamedalaa.mautils.core_android.dismissToast

/**
 * ## Deprecation Under Condition
 *
 * If you `implement` material_design module then use [mohamedalaa.mautils.material_design.custom_classes.BaseApplication]
 *
 * instead of this [BaseApplication], since it extends from [BaseApplication] and add more functionality
 *
 * Ex. same approach of [Toast] but for [com.google.android.material.snackbar.Snackbar] isa.
 *
 * ## Usage
 *
 * Used with [Context.toast] & [Context.dismissToast] to provide more effective approach to [Toast]
 *
 * with as much less code as possible, along with additional functionality for more clarification See [Context.toast].
 *
 * ## How to Use
 *
 * 1- either extend `this` class then put the derived class in manifest -> android:name=".YourApplication"
 *
 * 2- or directly use `this` class in manifest -> android:name=".BaseApplication"
 *
 * @see dismissToast
 * @see Context.toast
 */
open class BaseApplication: Application() {

    var toast: Toast? = null

}