package mohamedalaa.mautils.mautils.delLaterIsa;

import android.content.Context;
import android.widget.Toast;

import org.w3c.dom.TypeInfo;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import mohamedalaa.mautils.core_android.ContextUtils;
import mohamedalaa.mautils.core_kotlin.ListUtils;
import mohamedalaa.mautils.core_kotlin.StringBuilderUtils;

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
        //GsonUtils.fromJsonOrNull("ksklsms");
        //GsonUtils.fromJsonCheck("ksklsms", JsonCheck.NON_NULL_ELEMENTS_LIST);
        //GsonUtils.fromJsonCheckJ();
        //TypeInfo

        //mohamedalaa.mautils.mautils_gson_java.GsonUtils.toJson
    }

    private void dewiojdoiw() {


        /*
        val pair = 5 to customObject
        val triple = Triple("word", listOfCustomObjects, 55)

        val jsonPair = object : GsonConverter<Pair<Int, CustomObject>>(){}.toJsonOrNull(pair)
        val jsonTriple = object : GsonConverter<Triple<String, List<CustomObject>, Int>>(){}.toJsonOrNull(triple)
        val jsonList = object : GsonConverter<List<CustomObject>>(){}.toJsonOrNull(listOfCustomObjects)

        assertEquals(pair, object : GsonConverter<Pair<Int, CustomObject>>(){}.fromJsonOrNull(jsonPair))
        assertEquals(triple, object : GsonConverter<Triple<String, List<CustomObject>, Int>>(){}.fromJsonOrNull(jsonTriple))
        assertEquals(listOfCustomObjects, object : GsonConverter<List<CustomObject>>(){}.fromJsonOrNull(jsonList))
         */
    }

}
