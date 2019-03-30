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

@file:Suppress("UnusedImport")

package mohamedalaa.mautils.material_design.custom_classes

import android.content.Context
import com.google.android.material.snackbar.Snackbar
import mohamedalaa.mautils.core_android.custom_classes.BaseApplication
import mohamedalaa.mautils.material_design.snackbar
import mohamedalaa.mautils.material_design.dismissSnackbar

/**
 * Used with other functions for easier and better approaches for common tasks isa.
 *
 * Ex. [Context.snackbar] & [Context.dismissSnackbar] & features in the super class [BaseApplication]
 *
 * **Note** for how to use see **How to use** in [BaseApplication] isa.
 *
 * @see [Context.snackbar]
 * @see dismissSnackbar
 */
open class BaseApplication: BaseApplication() {

    var snackbar: Snackbar? = null

}
