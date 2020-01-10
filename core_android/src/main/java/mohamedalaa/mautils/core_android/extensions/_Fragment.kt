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

import android.os.Bundle
import androidx.fragment.app.Fragment


/**
 * @return same [F] instance with setting [bundle] in [Fragment.setArguments] funtion isa.
 *
 * @param [F] any [Fragment] instance isa.
 *
 * @see instanceWithArgBundle
 */
fun <F : Fragment> F.instanceWithArg(bundle: Bundle?): F
    = apply { arguments = bundle }

/**
 * - Same as [instanceWithArg] but provides [Bundle] using [buildBundle] with given [values] isa.
 * - To retrieve values see below Ex. for clarification isa.
 * ```
 * fragment.arguments?.apply {
 *      val getterBundle = getterBundle()
 *      // ...
 * }
 * ```
 *
 * @see instanceWithArg
 */
fun <F : Fragment> F.instanceWithArgBundle(vararg values: Any?): F
    = apply { arguments = buildBundle(*values) }
