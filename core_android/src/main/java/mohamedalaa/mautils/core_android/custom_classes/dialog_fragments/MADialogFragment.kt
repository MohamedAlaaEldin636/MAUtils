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

@file:Suppress("MemberVisibilityCanBePrivate")

package mohamedalaa.mautils.core_android.custom_classes.dialog_fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import mohamedalaa.mautils.core_kotlin.extensions.throwIfNull

/**
 * - Definition
 * provide essential features for most of the Dialog Fragments, So implementing this instead of [DialogFragment]
 * should save you a lot of bioler plate code isa.
 *
 * - Features
 * 1. according to the given parameters dialog is created for you with these changes easily by overriding [onCreateDialog] isa, and
 * in case you would like to make changes to [Dialog.getWindow] see [onCreateDialogWindowChanges].
 * 2. provide [onBackPressed] which is called via [Dialog.setOnKeyListener] with [KeyEvent.KEYCODE_BACK] isa.
 * 3. provide [fragmentScope] which is auto created and auto destroyed so you can safely use coroutines with it isa.
 * 4. Check **See Also** section in this documentation as well isa.
 * 5. Use [DialogFragment.getDialog] && [Dialog.setCanceledOnTouchOutside] in case you need dynamic access, but in case
 * of static access see the params of this constructor isa.
 * 6. Auto saved [DialogFragment.getTargetFragment] in [onSaveInstanceState] for you isa. todo this one not tested yet isa.
 * // make system kill before 4. and see if target fragment is lost then if so then plan is ok so far isa then
 * add 4. and if not null then plan is 100% ok isa.
 *
 * @see getListener
 * @see ListenerProvider
 * @see onCancelListener
 */
open class MADialogFragment(
    private val widthIsMatchPrent: Boolean = true,
    private val heightIsMatchPrent: Boolean = false,
    private val windowGravity: Int = Gravity.CENTER,
    private val canceledOnTouchOutside: Boolean = true
) : DialogFragment() {

    companion object {
        private const val PRIVATE_TARGET_FRAGMENT_KEY = "PRIVATE_TARGET_FRAGMENT_KEY"
        private const val PRIVATE_TARGET_FRAGMENT_REQUEST_CODE_KEY = "PRIVATE_TARGET_FRAGMENT_REQUEST_CODE_KEY"
    }

    private val fragmentJob = Job()

    val fragmentScope = CoroutineScope(Dispatchers.Main + fragmentJob)

    @Suppress("PropertyName")
    @PublishedApi
    internal var _listener: Any? = null

    final override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return customOnCreateDialog(savedInstanceState).apply {
            window?.apply {
                setLayout(
                    if (widthIsMatchPrent) WindowManager.LayoutParams.MATCH_PARENT else WindowManager.LayoutParams.WRAP_CONTENT,
                    if (heightIsMatchPrent) WindowManager.LayoutParams.MATCH_PARENT else WindowManager.LayoutParams.WRAP_CONTENT
                )

                setGravity(windowGravity)

                onCreateDialogWindowChanges()
            }

            setCanceledOnTouchOutside(canceledOnTouchOutside)

            setOnCancelListener {
                onCancelListener(this@MADialogFragment)
            }

            setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    this@MADialogFragment.onBackPressed()

                    true
                }else {
                    false
                }
            }
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        savedInstanceState?.apply {
            if (targetFragment == null) {
                childFragmentManager.getFragment(this, PRIVATE_TARGET_FRAGMENT_KEY)?.apply InnerApply@ {
                    setTargetFragment(
                        this,
                        getInt(PRIVATE_TARGET_FRAGMENT_REQUEST_CODE_KEY, targetRequestCode)
                    )
                }
            }
        }

        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()

        dialog?.window?.apply {
            setLayout(
                if (widthIsMatchPrent) WindowManager.LayoutParams.MATCH_PARENT else WindowManager.LayoutParams.WRAP_CONTENT,
                if (heightIsMatchPrent) WindowManager.LayoutParams.MATCH_PARENT else WindowManager.LayoutParams.WRAP_CONTENT
            )

            setGravity(windowGravity)
        }
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        targetFragment?.apply {
            childFragmentManager.putFragment(outState, PRIVATE_TARGET_FRAGMENT_KEY, this)
            outState.putInt(PRIVATE_TARGET_FRAGMENT_REQUEST_CODE_KEY, targetRequestCode)
        }

        super.onSaveInstanceState(outState)
    }

    final override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    @CallSuper
    override fun onDestroy() {
        fragmentJob.cancel()

        super.onDestroy()
    }

    // ---- Public open fun

    open fun customOnCreateDialog(savedInstanceState: Bundle?): Dialog = super.onCreateDialog(savedInstanceState)

    open fun Window.onCreateDialogWindowChanges() {}

    open fun onBackPressed() {
        dismiss()
    }

    /**
     * Note
     * 1. for this to be invoked in case of on touch outside event, then [canceledOnTouchOutside] should be true isa.
     * 2. default implementation here is just to call [dismiss] isa.
     */
    open fun onCancelListener(maDialogFragment: MADialogFragment) {
        dismiss()
    }

    // ---- Public fun

    inline fun <reified L> getListener(): L = context.run {
        return (_listener as? L) ?: when {
            this is L -> this
            this is ListenerProvider && getListenerForMADialogFragment() is L -> getListenerForMADialogFragment() as? L
            else -> parentFragment?.run {
                when (this) {
                    is L -> this
                    is ListenerProvider -> this.getListenerForMADialogFragment() as? L
                    else -> null
                }
            }
        }.throwIfNull("Cannot get listener from this context's class ${this?.javaClass?.name} isa.").apply {
            _listener = this
        }
    }

    // ---- Listener

    /**
     * This is used in case the subclass of [MADialogFragment] will have a listener, and the activity or the fragment
     * wants to provide the listener by another variable instead of directly implementing the functions in the
     * activity/fragment so no violation of single class responsibility isa.
     */
    interface ListenerProvider {
        fun getListenerForMADialogFragment(): Any?
    }

}
