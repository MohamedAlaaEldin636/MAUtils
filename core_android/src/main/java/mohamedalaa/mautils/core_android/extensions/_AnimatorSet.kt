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

@file:JvmName("AnimatorSetUtils")

package mohamedalaa.mautils.core_android.extensions

import android.animation.Animator
import android.animation.AnimatorSet

/**
 * Using [listener] for [AnimatorSet.addListener] instead of regular [Animator.AnimatorListener],
 * for more concise & idiomatic coding, for an example See [MAAnimatorAnimatorListener] isa.
 */
fun AnimatorSet.addListenerMA(listener: Animator_AnimatorListener_Typealias?) {
    val genListener =
        MAAnimatorAnimatorListener(listener)
    addListener(genListener)
}