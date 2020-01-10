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
import androidx.fragment.app.Fragment
import mohamedalaa.mautils.core_kotlin.extensions.throwIfNull

/**
 * ### Usage
 *
 * - provide essential features for most of the Dialog Fragments, So implementing this instead of [DialogFragment]
 * should save you a lot of boiler plate code isa.
 *
 * ### Features
 *
 * 1. according to the given parameters ([widthIsMatchPrent], [heightIsMatchPrent], [windowGravity],
 * [canceledOnTouchOutside]) dialog is created for you with these changes easily by overriding
 * [onCreateDialog] isa, And in case you would like to make changes to [Dialog.getWindow]
 * override [onCreateDialogWindowChanges].
 * 2. provides [onBackPressed] which is created by using [Dialog.setOnKeyListener] with
 * [KeyEvent.KEYCODE_BACK] isa, **Note** we don't override [Dialog.setOnKeyListener] to give you
 * full control/customization so if you use it then [onBackPressed] won't be called unless
 * you explicitly call it isa.
 * 3. If you set [canceledOnTouchOutside] to true you can either override [onCancelListener] to
 * respond to the dialog's cancellation or leave default implementation which will invoke [dismiss] isa.
 * 4. Instead of adding listener property in the subclass just let the launcher of the dialog
 * implements it's listener then use [getListener] which will get the instance of the listener after
 * checking the subclass itself then it's [Fragment.getParentFragment] then [Fragment.getActivity] isa.
 * for direct implementation or via [ListenerProvider] isa.
 *
 * ### Example for a subclass
 * ```
 * // The Dialog
 * class SomeDialogFragment : MADialogFragment() {
 *
 *      // ...
 *
 *      private fun onChangeOfInputData() {
 *          if (getListener<Listener>().isInputValid()) {
 *              // ...
 *          }else {
 *              // ...
 *          }
 *      }
 *
 *      interface Listener {
 *          fun isInputValid(): Boolean
 *      }
 *
 * }
 *
 * // The host
 * class ParentFragment : Fragment(), SomeDialogFragment.Listener {
 *
 *      // ...
 *
 *      override fun isInputValid() {
 *          // your code here.
 *      }
 *
 *      private fun launchDialog() {
 *          showDialogFragment<SomeDialogFragment>()
 *      }
 *
 * }
 *
 * // OR to use ListenerProvider isa.
 *
 * class ParentFragment2 : Fragment(), MADialogFragment.ListenerProvider {
 *
 *      private val impl: SomeDialogFragment.Listener = // ... // Ex. parentFragment!!.parentFragment as SomeDialogFragment.Listener
 *
 *      // ...
 *
 *      // So you delegate the implementation of the interface's functions to `impl` property isa.
 *      override fun getListenerForMADialogFragment(): Any? = impl
 * }
 * ```
 */
open class MADialogFragment(
    private val widthIsMatchPrent: Boolean = true,
    private val heightIsMatchPrent: Boolean = false,
    private val windowGravity: Int = Gravity.CENTER,
    private val canceledOnTouchOutside: Boolean = true
) : DialogFragment() {

    /**
     * Shouldn't be used by subclasses as this is just a cache value for [getListener] function isa.
     */
    @Suppress("PropertyName")
    protected var _listener: Any? = null

    /** If you wanna run code here consider instead using [onCreateDialogWindowChanges] isa. */
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

    /** Preferable no code runs before `super.onResume()` isa. */
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

    /** Consider overridding [onCancelListener] instead isa. */
    final override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    // ---- Public open fun

    open fun customOnCreateDialog(savedInstanceState: Bundle?): Dialog = super.onCreateDialog(savedInstanceState)

    /** Called inside [onCreateDialog] with [Window] as `receiver` isa. */
    open fun Window.onCreateDialogWindowChanges() {}

    /** Called to interact on onBackPress of virtual keys isa. */
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

    /**
     * Get listener from `this class` or [Fragment.getParentFragment] or [Fragment.getActivity]
     * by checking direct implementation or via implementation of [ListenerProvider] isa.
     */
    protected inline fun <reified L> getListener(): L = context.run {
        return (_listener as? L) ?: when {
            this is L -> this
            this is ListenerProvider && getListenerForMADialogFragment() is L -> getListenerForMADialogFragment() as? L
            else -> parentFragment?.run {
                when {
                    this is L -> this
                    this is ListenerProvider && getListenerForMADialogFragment() is L -> getListenerForMADialogFragment() as? L
                    else -> activity?.run {
                        when {
                            this is L -> this
                            this is ListenerProvider && getListenerForMADialogFragment() is L -> getListenerForMADialogFragment() as? L
                            else -> null
                        }
                    }
                }
            }
        }.throwIfNull("Cannot get listener from this context's class ${this?.javaClass?.name} isa.").apply {
            _listener = this
        }
    }

    // ----- Listener

    /**
     * - This is used in case the subclass of [MADialogFragment] will have a listener, and the
     * activity or the fragment hosting the dialog wants to provide the listener by delegating
     * ro another variable instead of directly implementing the functions in the
     * activity/fragment so that it helps not to violate single class responsibility principle isa.
     */
    interface ListenerProvider {
        fun getListenerForMADialogFragment(): Any?
    }

}
