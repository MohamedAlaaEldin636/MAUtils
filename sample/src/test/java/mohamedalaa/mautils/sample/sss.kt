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

package mohamedalaa.mautils.sample

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.reflect.TypeToken
import mohamedalaa.mautils.gson.toJsonOrNull
import org.bouncycastle.pqc.math.linearalgebra.IntegerFunctions
import java.lang.reflect.Type

private fun SharedPreferences.a() {
    edit {
        //ReplaceWith
        5.rem(4)
        //putse
        //put
    }
    object : TypeToken<IntegerFunctions>(){}.type
}

private inline fun <reified E> a(e: E){
    /*
    Type superclass = subclass.getGenericSuperclass();
    if (superclass instanceof Class) {
      throw new RuntimeException("Missing type parameter.");
    }
    ParameterizedType parameterized = (ParameterizedType) superclass;
    return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
     */
    val type: Type? = E::class.java.genericSuperclass
}

object Settings {
    //val ads = AdsEnum.PERSONALIZED
}

/*
ezan suppoerted values grows a little bit isa -> primitives, array of them, list of them,
pair of primitives, list of pairst isa. (array of any convert it to str array isa, pair make it as two array)
or two items and key instead of name -> becomes -> "name_first", "sec isa." list of pairs
become name_list_first, same_second isa.
however warn user not to use these excessively there are databases these for light weight needed approaches isa.

generated data becomes isa as followin'

// see settings object above isa.
fun Context.sharedPrefSettings_setAds(adsEnum: AdsEnum) {
    // ... set it, ex. .edit{ putEnum() isa. } isa.
    // putEnum same as putString only calls .toString and vice verrsa valeOf() isa.
    //

    // try to allow nested pairs or nested lists isa.

    // todo clear() fun isa, apply/commit isa, setKaza_commit(): Bool
    // SharedprefGenerated.edit { severalChanged at same time isa. }    ======  SharedprefGenerated.getSeveralValues {  }
}

@MASharedPref(
    class = Class(String::class), // Class is Annotation isa, better be inside MASharedPref isa. (required isa.)
    value = "CustomClass(id = 5).toJsonOrNull()", // required
    isConstant = false // default true
)
@MASharedPref(
    class = ParameterizedClass(List::class, String::class),
    value = ,
)
@MASharedPref(
    class = Class(Boolean::class),
    value = "true"
    // isConstant == true as default anyways isa.
)
@MASharedPref(
    class = ParameterizedClass(
        List::class,
        ParameterizedClass(
            MutablePair::class, String::class, Boolean::class
        )
    ),
    value = listOf(
        "\"hh:mm a\" to true" // OR "' ss ' to true",
        null // etc... isa.
    ),
    isConstant = false
)
 */

// prevent _first for pair isa. in processor isa.
/*@MASharedPrefComplex(
    // todo can i retrieve this isa. ?!
    MAComplexClassIdentification(
        MAParameterizedKClass(Int::class)
    )
)*/
private fun SharedPreferences.Editor.sssss() {
    //putFloat()

    "".toJsonOrNull()
}

// ----

object SomeClassForSharedPref
