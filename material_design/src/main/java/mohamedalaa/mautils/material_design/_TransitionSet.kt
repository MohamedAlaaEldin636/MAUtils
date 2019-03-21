@file:JvmName("TransitionSetUtils")

package mohamedalaa.mautils.material_design

import androidx.transition.Transition
import androidx.transition.TransitionSet

/**
 * Loop through each [Transition] in the `receiver` isa.
 */
fun TransitionSet.forEach(block: (Transition) -> Unit) = (0 until transitionCount).forEach { block(getTransitionAt(it)) }