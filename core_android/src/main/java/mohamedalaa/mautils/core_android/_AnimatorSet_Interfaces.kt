package mohamedalaa.mautils.core_android

import android.animation.Animator
import android.animation.AnimatorSet

/**
 * Used to make [Animator.AnimatorListener] easier in creating it isa.
 *
 * @see [MAAnimatorAnimatorListener]
 */
typealias Animator_AnimatorListener_Typealias = MAAnimatorAnimatorListener.() -> Unit

/**
 * More concise & idiomatic way for using more than 1 fun interface,
 *
 * Now it is easier than before to add [Animator.AnimatorListener],
 *
 * 1- instead of being obligated to override all fun (while you need only 1 or two), use only what you need isa.
 *
 * 2- Using lambda directly instead of object : Anim...Listener isa.
 *
 * See below Example [Animator.addListener]
 *
 * ```
 * fun setupAnimatorSetListener(animatorSet: AnimatorSet) {
 *      animatorSet.addListenerMA {
 *          onAnimationStart {
 *              // Code for on anim start
 *          } onAnimationEnd {
 *              // Code for on anim end
 *          } onAnimationCancel {
 *              // Code for on anim cancel
 *          }
 *          // Don't need -> onAnimationRepeat { /* Do nothing */ }
 *      }
 * }
 * ```
 *
 * @see [AnimatorSet.addListenerMA]
 */
class MAAnimatorAnimatorListener(listener: Animator_AnimatorListener_Typealias?): Animator.AnimatorListener {

    init {
        listener?.invoke(this)
    }

    private var _onAnimationStart: ((Animator?) -> Unit)? = null
    private var _onAnimationEnd: ((Animator?) -> Unit)? = null
    private var _onAnimationCancel: ((Animator?) -> Unit)? = null
    private var _onAnimationRepeat: ((Animator?) -> Unit)? = null

    override fun onAnimationStart(animation: Animator?) {
        _onAnimationStart?.invoke(animation)
    }

    /**
     * Notifies the start of the animation.
     *
     * @param action fun executed on the start of the animation isa.
     */
    infix fun MAAnimatorAnimatorListener.onAnimationStart(action: ((Animator?) -> Unit)?): MAAnimatorAnimatorListener
        = apply { _onAnimationStart = action }

    override fun onAnimationEnd(animation: Animator?) {
        _onAnimationEnd?.invoke(animation)
    }

    /**
     * Notifies the end of the animation, Note this callback is not invoked for animations with repeat
     * count set to [Animator.DURATION_INFINITE].
     *
     * @param action fun executed at the end of the animation, if was not with [Animator.DURATION_INFINITE] isa.
     */
    infix fun MAAnimatorAnimatorListener.onAnimationEnd(action: ((Animator?) -> Unit)?): MAAnimatorAnimatorListener
        = apply { _onAnimationEnd = action }

    override fun onAnimationCancel(animation: Animator?) {
        _onAnimationCancel?.invoke(animation)
    }

    /**
     * Notifies the cancellation of the animation. This callback is not invoked for animations
     * with repeat count set to [Animator.DURATION_INFINITE] isa.
     *
     * @param action fun executed when animation is cancelled, if was not with [Animator.DURATION_INFINITE] isa.
     */
    infix fun MAAnimatorAnimatorListener.onAnimationCancel(action: ((Animator?) -> Unit)?): MAAnimatorAnimatorListener
        = apply { _onAnimationCancel = action }

    override fun onAnimationRepeat(animation: Animator?) {
        _onAnimationRepeat?.invoke(animation)
    }

    /**
     * Notifies the repetition of the animation.
     *
     * @param action fun executed  per repeat isa.
     */
    infix fun MAAnimatorAnimatorListener.onAnimationRepeat(action: ((Animator?) -> Unit)?): MAAnimatorAnimatorListener
        = apply { _onAnimationRepeat = action }

}