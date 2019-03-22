@file:JvmName("AnimatorSetUtils")

package mohamedalaa.mautils.core_android

import android.animation.Animator
import android.animation.AnimatorSet

/**
 * Using [listener] for [AnimatorSet.addListener] instead of regular [Animator.AnimatorListener],
 * for more concise & idiomatic coding, for an example See [MAAnimatorAnimatorListener] isa.
 */
fun AnimatorSet.addListenerMA(listener: Animator_AnimatorListener_Typealias?) {
    val genListener = MAAnimatorAnimatorListener(listener)
    addListener(genListener)
}