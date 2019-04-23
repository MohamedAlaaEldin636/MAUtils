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

package mohamedalaa.mautils.sample.delLaterIsa;

import android.content.Context;
import android.widget.Toast;

import org.w3c.dom.TypeInfo;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import mohamedalaa.mautils.core_android.ContextUtils;
import mohamedalaa.mautils.core_kotlin.ListUtils;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/13/2019.
 */
public class JavaClass {

    private void siuahsi() {

    }

    private void m1(Context context) {
        float px = ContextUtils.dpToPx(context, 4);

        ContextUtils.toast(context, "", Toast.LENGTH_SHORT, new Function1<Toast, Unit>() {
            @Override
            public Unit invoke(Toast toast) {
                return null;
            }
        });
    }

    private void m2(StringBuilder b1) {
        //ListUtils.addIfNotInside();
        //ListUtils.performIfNotNullNorEmpty();
        //StringBuilderUtils.plusAssign(b1, "");
        //b1.append()
        //GsonUtils.fromJsonOrNullJava("ksklsms");
        //GsonUtils.fromJsonCheck("ksklsms", JsonCheck.NON_NULL_ELEMENTS_LIST);
        //GsonUtils.fromJsonCheckJ();
    }

    private void dewiojdoiw() {


        /*
        val pair = 5 to customObject
        val triple = Triple("word", listOfCustomObjects, 55)

        val jsonPair = object : GsonConverter<Pair<Int, CustomObject>>(){}.toJsonOrNullJava(pair)
        val jsonTriple = object : GsonConverter<Triple<String, List<CustomObject>, Int>>(){}.toJsonOrNullJava(triple)
        val jsonList = object : GsonConverter<List<CustomObject>>(){}.toJsonOrNullJava(listOfCustomObjects)

        assertEquals(pair, object : GsonConverter<Pair<Int, CustomObject>>(){}.fromJsonOrNullJava(jsonPair))
        assertEquals(triple, object : GsonConverter<Triple<String, List<CustomObject>, Int>>(){}.fromJsonOrNullJava(jsonTriple))
        assertEquals(listOfCustomObjects, object : GsonConverter<List<CustomObject>>(){}.fromJsonOrNullJava(jsonList))
         */
    }

}
