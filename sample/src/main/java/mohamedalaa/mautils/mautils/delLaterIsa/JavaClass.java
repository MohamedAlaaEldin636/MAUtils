package mohamedalaa.mautils.mautils.delLaterIsa;

import android.content.Context;
import android.widget.Toast;

import org.w3c.dom.TypeInfo;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import mohamedalaa.mautils.core_android.ContextUtils;
import mohamedalaa.mautils.core_kotlin.ListUtils;
import mohamedalaa.mautils.core_kotlin.StringBuilderUtils;
import mohamedalaa.mautils.mautils_gson.GsonUtils;
import mohamedalaa.mautils.mautils_gson.JsonCheck;

/**
 * Created by <a href="https://github.com/MohamedAlaaEldin636">Mohamed</a> on 2/13/2019.
 */
public class JavaClass {

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
        //Integer.class
    }

}
