package mohamedalaa.mautils.mautils.delLaterIsa;

import android.content.Context;
import android.widget.Toast;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import mohamedalaa.mautils.core_android.ContextUtils;

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

}
