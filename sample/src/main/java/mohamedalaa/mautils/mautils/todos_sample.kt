package mohamedalaa.mautils.mautils

import androidx.core.os.bundleOf

/**
 * Created by [Mohamed](https://github.com/MohamedAlaaEldin636) on 2/18/2019.
 *
 * todo
 * 1- new module of getBySelfOrListOrArrayOrLazy for baseImplementation
 *
 * 2- new module for licences and notes etc.... , privacy isa, and maybe other about app, dev
 *
 * todo general
 * 1- put licence for your own code isa.
 * 2- put licences and notes in sample isa.
 */

val a = 5

private fun z1(any: Any, ss: String) {
    //bundleOf()
    val a1 = ss::length

    //any::
    val z1 = ::a
    z1.name
    z1.get()
    val z = ::a.name

    val strs = listOf("a", "bc", "def")
    println(strs.map(String::length)) // [1,2,3]
}