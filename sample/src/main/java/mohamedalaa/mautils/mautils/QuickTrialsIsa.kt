package mohamedalaa.mautils.mautils

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType
import mohamedalaa.mautils.core_kotlin.contains

abstract class CheckTypeParam<E> {

    fun performPrintln() {
        println("javaClass $javaClass")
        println("javaClass.superclass ${javaClass.superclass}")
        println("javaClass.genericSuperclass ${javaClass.genericSuperclass}")
        println("ParameterizedType ${javaClass.genericSuperclass as ParameterizedType}")
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        val l = parameterizedType.actualTypeArguments[0]
        println(l)
        println(l as Class<*>)
        println(l.javaClass)
    }

    fun a1() {
        //val jClass = E // todo see gson ba2a isa
        /*javaClass
        javaClass.superclass*/
        // new QuickViewModel<>(this) msln isa.
        // but what if viewmodel has type param. ... kda lazm gson approach isa.
    }

}

private class Abc1(context: Context): AsyncTask<Void?, Void, Void?>() {

    override fun doInBackground(vararg params: Void?): Void? {
        return null
    }

}

/*
private object A2<R> {

}*/

/**
 * rc_common
 * rc_data_binding todo
 * rc bs no need if it dependes on both above only, while second depend on 1st isa. isa....
 */
abstract class DataBindingRCAdapter<VDB: ViewDataBinding>(@LayoutRes private val layoutRes: Int)
    : RecyclerView.Adapter<DataBindingRCAdapter.ViewHolder<VDB>>() {

    // ---- Overridden Methods ( Abstract )

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingRCAdapter.ViewHolder<VDB> {
        val inflater = LayoutInflater.from(parent.context)

        val viewDataBinding = DataBindingUtil.inflate<VDB>(
            inflater, layoutRes, parent, false)

        return ViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder<VDB>, position: Int)
        = onBindViewHolder(holder.binding, position)

    /**
     * Same as [onBindViewHolder] but provides [binding] as param instead of [ViewHolder]
     * for quick access to [ViewHolder.binding] isa.
     */
    abstract fun onBindViewHolder(binding: VDB, position: Int)

    // ----- View Holder

    class ViewHolder<VDB: ViewDataBinding>(val binding: VDB): RecyclerView.ViewHolder(binding.root)

}


private fun f1(charSequence1: CharSequence?, string1: String?) {
    if (charSequence1 in string1) {
        //ProgressBar
    }
}